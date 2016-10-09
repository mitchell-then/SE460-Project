JAVAC = javac
JFLAGS = -g -classpath .:./jdom-2.0.6.jar
sources = *.java
classes = $(sources:.java=.class)


all: $(classes)

%.class: %.java
	$(JAVAC) $(JFLAGS) $<

clean:
	rm -f *.class
