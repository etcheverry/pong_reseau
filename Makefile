all:
	javac $(shell find . -name \*.java)

run: all
	java pong.Main

clean:
	rm $(shell find . -name \*.class)
