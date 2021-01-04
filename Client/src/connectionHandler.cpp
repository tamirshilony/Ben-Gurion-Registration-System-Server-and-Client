#include "../include/connectionHandler.h"

using boost::asio::ip::tcp;

using std::cin;
using std::cerr;
using std::endl;
using std::string;


ConnectionHandler::ConnectionHandler(string host, short port): host_(host), port_(port), io_service_(),
    socket_(io_service_){
}

ConnectionHandler::~ConnectionHandler() {
    close();
}

bool ConnectionHandler::connect() {
    std::cout << "Starting connect to "
        << host_ << ":" << port_ << std::endl;
    try {
		tcp::endpoint endpoint(boost::asio::ip::address::from_string(host_), port_); // the server endpoint
		boost::system::error_code error;
		socket_.connect(endpoint, error);
		if (error)
			throw boost::system::system_error(error);
    }
    catch (std::exception& e) {
        std::cerr << "Connection failed (Error: " << e.what() << ')' << std::endl;
        return false;
    }
    return true;
}

bool ConnectionHandler::getBytes(char bytes[], unsigned int bytesToRead) {
    size_t tmp = 0;
	boost::system::error_code error;
    try {
        while (!error && bytesToRead > tmp ) {
			tmp += socket_.read_some(boost::asio::buffer(bytes+tmp, bytesToRead-tmp), error);
        }
		if(error)
			throw boost::system::system_error(error);
    } catch (std::exception& e) {
        std::cerr << "recv failed (Error: " << e.what() << ')' << std::endl;
        return false;
    }
    return true;
}

bool ConnectionHandler::sendBytes(const char bytes[], int bytesToWrite) {
    int tmp = 0;
	boost::system::error_code error;
    try {
        while (!error && bytesToWrite > tmp ) {
			tmp += socket_.write_some(boost::asio::buffer(bytes + tmp, bytesToWrite - tmp), error);
        }
		if(error)
			throw boost::system::system_error(error);
    } catch (std::exception& e) {
        std::cerr << "recv failed (Error: " << e.what() << ')' << std::endl;
        return false;
    }
    return true;
}

bool ConnectionHandler::getLine(std::string& line) {
    return decode(line, ' ');
}

bool ConnectionHandler::sendLine(std::string& line) {
    return encode(line, ' ');
}


bool ConnectionHandler::getFrameAscii(std::string& frame, char delimiter) {
    char ch;
    // Stop when we encounter the null character.
    // Notice that the null character is not appended to the frame string.
    try {
	do{
		if(!getBytes(&ch, 1))
		{
			return false;
		}
		if(ch!='\0')
			frame.append(1, ch);
	}while (delimiter != ch);
    } catch (std::exception& e) {
	std::cerr << "recv failed2 (Error: " << e.what() << ')' << std::endl;
	return false;
    }
    return true;
}


bool ConnectionHandler::sendFrameAscii(const std::string& frame, char delimiter) {
	bool result=sendBytes(frame.c_str(),frame.length());
	if(!result) return false;
	return sendBytes(&delimiter,1);
}

// Close down the connection properly.
void ConnectionHandler::close() {
    try{
        socket_.close();
    } catch (...) {
        std::cout << "closing failed: connection already closed" << std::endl;
    }
}

bool ConnectionHandler::encode (string &keybboardString, char delimiter) {
    //replace command by opNum
    vector<char> toSend;
    //resolve opcode
    string commandName = keybboardString.substr(0, keybboardString.find(delimiter));
    string restOfString = keybboardString.substr(keybboardString.find(delimiter) + 1, keybboardString.length());
    vector<string> allCommands = getCommands();
    short opCode = distance(allCommands.begin(), find(allCommands.begin(), allCommands.end(), commandName));
    shortToBytes(opCode, toSend);
    //encode rest of string
    for (char i : restOfString) {
        if (i != delimiter)
            toSend.push_back(i);
        else
            toSend.push_back('\0');
    }
    if ((opCode > 0 && opCode <= 3) || opCode == 8)
        toSend.push_back('\0');

    //sendBytes try with pointer to toSend vector
    bool result=sendBytes(&toSend[0],toSend.size());
    if(!result) return false;
    return sendBytes(&delimiter,1);
}

bool ConnectionHandler::decode(string &response, char delimiter) {
    //char to check at each reading
    char bytes[2];
    try {
        //get two first bytes convert to short add corresponding command to response
        if (!getBytes(bytes, 2))
            return false;
        string command = getCommands()[bytesToShort(bytes)];
        response.append(command);
        //get next two bytes convert to short add " " and srcopcode to response
        if (!getBytes(bytes, 2))
            return false;
        response.append(" ");
        response.append(1, bytesToShort(bytes));
        //if ack continue reading
        if (command == "ACK") {
            response.append(" ");
            while (bytes[1] != '\0'){
                if (!getBytes(&bytes[1], 1))
                    return false;
                response.append(1, bytes[1]);
            }
        }
    }catch (exception& e) {
        cerr << "recv failed2 (Error: " << e.what() << ')' << endl;
        return false;
    }return true;
}


void ConnectionHandler::shortToBytes(short num, vector<char>& toConvert){
    toConvert.push_back(((num >> 8) & 0xFF));
    toConvert.push_back(num & 0xFF);
}

short ConnectionHandler::bytesToShort(char* bytesArr){
    short result = (short)((bytesArr[0] & 0xff) << 8);
    result += (short)(bytesArr[1] & 0xff);
    return result;
}


vector<string> ConnectionHandler::getCommands() {
    return vector<string>{"NOTEXIST","ADMINREG","STUDENTREG","LOGIN","LOGOUT","COURSEREG","KDAMCHECK",
                          "COURSESTAT","STUDENTSTAT","ISREGISTERED","UNREGISTER","MYCOURSES","ACK","ERR"};
}
