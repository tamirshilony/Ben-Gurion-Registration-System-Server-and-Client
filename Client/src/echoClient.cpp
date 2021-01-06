#include <stdlib.h>
#include <iostream>
#include <thread>
#include <mutex>
#include "../include/connectionHandler.h"

class KeyboardReader{
private:
    ConnectionHandler &connectionHandler;
    bool interupted;
    const short bufferSize = 1024;

public:
    KeyboardReader(ConnectionHandler & connectionHandler_):connectionHandler(connectionHandler_),interupted(false){};

    bool isInerupted(){
        return interupted;
    }
    void run(){
        while (!interupted){
            char buf[bufferSize];
            // get line from user to buf
            cin.getline(buf,bufferSize);
            // convert buf to string
            string command(buf);
            if(!connectionHandler.sendLine(command)){
                cout<<"connection terminated"<<endl;
                interupted = true;
            }
        }
    }
};

int main (int argc, char *argv[]) {
    if (argc < 3) {
        std::cerr << "Usage: " << argv[0] << " host port" << std::endl << std::endl;
        return -1;
    }
    std::string host = argv[1];
    short port = atoi(argv[2]);

    ConnectionHandler connectionHandler(host, port);
    if (!connectionHandler.connect()) {
        std::cerr << "Cannot connect to " << host << ":" << port << std::endl;
        return 1;
    }
    bool interupted = false;
    KeyboardReader keyboardReader(connectionHandler);
    thread thread1 (&KeyboardReader::run,&keyboardReader);

    while (!interupted) {
        std::string answer;
        if(!connectionHandler.getLine(answer)){
            cout<<"connection terminated"<<endl;
            interupted = true;
            break;
        }
        cout <<  answer <<  endl;
        if (answer == "ACK 4")
            interupted = true;
    }
    if(keyboardReader.isInerupted())
        thread1.detach();
    return 0;
}
