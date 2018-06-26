JAR=jar
JAVA=java
JAVAC=javac
JAVADOC=javadoc
JFLAGS=-d build -Xlint:unchecked -Xlint:deprecation

# JCUP
JCUP=lib/java-cup-11b.jar
JCUPFLAGS = -parser Parser -symbols Token

# JFLEX
JFLEX=lib/jflex-1.6.1.jar

# build classpath
CLASSPATH=.:$(JCUP)

# all src files
SRCDIR=.
SRC=$(shell find $(SRCDIR) -not -path './test/*' -type f -name '*.java')

# all class files
OBJDIR=build
OBJS=$(patsubst $(SRCDIR)/%.java, $(OBJDIR)/%.class, $(SRC))

# parser and lexer
SYNTAX=vsim/assembler/Lexer.java vsim/assembler/Parser.java


# VSim script
VSim.jar: build META-INF/MANIFEST.MF Makefile $(SYNTAX) $(OBJS)
	$(RM) VSim.jar
	$(JAR) -cvfm VSim.jar META-INF/MANIFEST.MF -C build/ .

# create build directory
build:
	@echo "creating build directory..."
	mkdir -p build

# compile vsim
$(OBJS): $(OBJDIR)/%.class: $(SRCDIR)/%.java
	$(JAVAC) -cp $(CLASSPATH) $(JFLAGS) $<

# build lexer
vsim/assembler/Lexer.java: vsim/assembler/syntax/lexer.flex
	$(JAVA) -jar $(JFLEX) $<
	mv vsim/assembler/syntax/Lexer.java vsim/assembler

# build parser
vsim/assembler/Parser.java: vsim/assembler/syntax/parser.cup
	$(JAVA) -jar $(JCUP) $(JCUPFLAGS) $<
	mv Parser.java vsim/assembler
	mv Token.java vsim/assembler

test: VSim.jar
	python3 test.py

.PHONY: clean doc test

# create documentation
doc:
	rm -rf doc
	mkdir doc
	find $(SRCDIR) -type f -name '*.java' | xargs $(JAVADOC) -cp .:$(JCUP) -d doc

# clean all
clean:
	rm -rf build VSim.jar $(SYNTAX) vsim/assembler/Token.java
