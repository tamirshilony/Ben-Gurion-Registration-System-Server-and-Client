cmake_minimum_required(VERSION 3.17)
project(Client)

set(CMAKE_CXX_STANDARD 11)
include_directories(include)
link_libraries("-lboost_system")
link_libraries(-pthread)

add_executable(Client
       include/connectionHandler.h
        src/connectionHandler.cpp
        src/echoClient.cpp
        )