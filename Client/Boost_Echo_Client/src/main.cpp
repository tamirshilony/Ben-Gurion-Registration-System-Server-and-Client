//
// Created by ofer on 02/01/2021.
//


#include <string>
#include <iostream>
//#include <boost/asio.hpp>
#include <stdlib.h>
#include <iostream>
#include <vector>
//#include "../include/connectionHandler.h"
#include <string>

using namespace std;

int main (int argc, char *argv[]) {
//    ConnectionHandler cH("asd", 33);
//    std::cout << cH.encode("aa ddfd gfg");
    std::string s = "sdaasd";
    std::cout << s.substr(0, s.find(' '));
    std::vector<string> v = {"NOTEXIST", "ADMINREG", "STUDENTREG", "LOGIN", "LOGOUT", "COURSEREG", "KDAMCHECK",
                             "COURSESTAT", "STUDENTSTAT", "ISREGISTERED", "UNREGISTER", "MYCOURSES", "ACK", "ERR"};
    std::string keybboardString = "99 Ofer Tamir";
    std::vector<char> toSend;
    char delimiter = ' ';
    string commandName = keybboardString.substr(0, keybboardString.find(delimiter));
    string restOfString = keybboardString.substr(keybboardString.find(delimiter) + 1, keybboardString.length());;
    short opCode = distance(v.begin(),find(v.begin(), v.end(), commandName));
    toSend.push_back(((opCode >> 8) & 0xFF));
    toSend.push_back(opCode & 0xFF);
    for (char i : restOfString) {
        if (i != delimiter)
            toSend.push_back(i);
        else
            toSend.push_back('\0');
        if ((opCode > 0 && opCode <= 3) || opCode == 8)
            toSend.push_back('\0');
    }

}
