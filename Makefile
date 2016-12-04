JAVAC = javac
JFLAGS = -g -classpath .:./jdom-2.0.6.jar:./jdatepicker-1.3.4.jar
sources = assignment.java bill.java course.java gui.java SIMPL.java swing_console.java
classes = $(sources:.java=.class)


all: $(classes)

%.class: %.java
	$(JAVAC) $(JFLAGS) $<

clean:
	rm -f *.class
