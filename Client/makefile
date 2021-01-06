CFLAGS:=-c -Wall -Weffc++ -g -std=c++11 -Iinclude 
LDFLAGS:=-lboost_system -pthread -lboost_thread


all: bin/BGRSclient

@echo 'Building target: BGRSclient'
@echo 'Invoking: C++ Linker'
bin/BGRSclient: bin/BGRSclient.o bin/connectionHandler.o
	 g++ -Wall -Weffc++ -o bin/BGRSclient bin/connectionHandler.o bin/BGRSclient.o $(LDFLAGS)
@echo 'Finished building target: BGRSclient'

bin/connectionHandler.o: src/connectionHandler.cpp
	g++ $(CFLAGS) -o bin/connectionHandler.o src/connectionHandler.cpp $(LDFLAGS)

bin/BGRSclient.o: src/echoClient.cpp
	g++ $(CFLAGS) -o bin/BGRSclient.o src/echoClient.cpp



.PHONY: clean
clean:
	@echo 'Cleaning'
	rm -f bin/*

