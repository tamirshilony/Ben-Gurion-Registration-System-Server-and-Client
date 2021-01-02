cmake_minimum_required(VERSION 3.17)
project(Client)

set(CMAKE_CXX_STANDARD 11)

add_executable(Client
        Boost_Echo_Client/include/connectionHandler.h
        Boost_Echo_Client/src/connectionHandler.cpp
        Boost_Echo_Client/src/echoClient.cpp
<<<<<<< HEAD
        )
=======
        Boost_Echo_Client/src/main.cpp Boost_Echo_Client/src/main.cpp)
>>>>>>> ofer
