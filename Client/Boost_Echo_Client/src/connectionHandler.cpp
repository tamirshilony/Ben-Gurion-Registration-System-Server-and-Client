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
    return getFrameAscii(line, '\n');
}

bool ConnectionHandler::sendLine(std::string& line) {
    return sendFrameAscii(line, '\n');
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

bool ConnectionHandler::encode (std::string keybboardString) {
    //replace command by opNum
    vector<char> toSend;
    char delimiter = ' ';
    string commandName = keybboardString.substr(0, keybboardString.find(delimiter));
    string restOfString = keybboardString.substr(keybboardString.find(delimiter) + 1, keybboardString.length());;
    short opCode = distance(getCommands().begin(),
                            find(getCommands().begin(), getCommands().end(), commandName));
    shortToBytes(opCode, toSend);
    for (char i : restOfString) {
        if (i != delimiter)
            toSend.push_back(i);
        else
            toSend.push_back('\0');
    }
    if ((opCode > 0 && opCode <= 3) || opCode == 8)
        toSend.push_back('\0');

    //sendBytes try with pointer to toSend vector
    return true;
}

bool ConnectionHandler::decode(string response) {
    //char to check at each reading
    //get two first bytes and convert to short add to response
    //get next two bytes convert to short add " " and than srcopcode to response
    //if error add \n
    //if ack read till \0
    //add every char to response and \n
}


void ConnectionHandler::shortToBytes(short num, vector<char>& toConvert){
    toConvert.push_back(((num >> 8) & 0xFF));
    toConvert.push_back(num & 0xFF);
}


vector<string> ConnectionHandler::getCommands() {
    return vector<string>{"NOTEXIST","ADMINREG","STUDENTREG","LOGIN","LOGOUT","COURSEREG","KDAMCHECK",
                          "COURSESTAT","STUDENTSTAT","ISREGISTERED","UNREGISTER","MYCOURSES","ACK","ERR"};
}
