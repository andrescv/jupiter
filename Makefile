JAVA=java
JAVAC=javac
CLASSPATH=.
TESTCLASSPATH=.:$(JUNIT_HOME)
JFLAGS=-cp $(CLASSPATH) -d build

# all src files
SRCDIR=.
SRC=$(shell find $(SRCDIR) -not -path './test/*' -name '*.java')

# all compiled files
OBJDIR=build
OBJS=$(patsubst $(SRCDIR)/%.java, $(OBJDIR)/%.class, $(SRC))


main: build $(OBJS) Makefile
	$(RM) main
	echo '#!/bin/sh' >> main
	echo 'cd build'  >> main
	echo '$(JAVA) -cp $(CLASSPATH) Main $$*' >> main
	chmod 755 main

build:
	@echo "creating build directory..."
	mkdir -p build

$(OBJS): $(OBJDIR)/%.class: $(SRCDIR)/%.java
	$(JAVAC) $(JFLAGS) $<

.PHONY: clean

clean:
	rm -rf build main
	rm -rf test/*.class