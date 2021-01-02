//
// Created by ofer on 02/01/2021.
//
#include <stdlib.h>
#include <iostream>
#include "../include/connectionHandler.h"

int main (int argc, char *argv[]){
    ConnectionHandler cH("asd", 33);
    std::cout << cH.encode("aa ddfd gfg");
}