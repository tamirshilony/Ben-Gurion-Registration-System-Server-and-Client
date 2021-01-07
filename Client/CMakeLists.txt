cmake_minimum_required(VERSION 3.17)
project(ClientTemp)


set(CMAKE_CXX_STANDARD 11)
include_directories(include)
link_libraries("-lboost_system")
link_libraries(-pthread)

add_executable(ClientTemp
        src/connectionHandler.cpp
        src/echoClient.cpp
        include/connectionHandler.h)