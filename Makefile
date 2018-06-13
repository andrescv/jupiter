JAR=jar
JAVA=java
JAVAC=javac
JAVADOC=javadoc
JFLAGS=-d build

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
	mkdir -p build/vsim/lib
	cp lib/java-cup-11b.jar build/vsim/lib
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

.PHONY: clean doc

# create documentation
doc:
	rm -rf doc
	mkdir doc
	find $(SRCDIR) -type f -name '*.java' | xargs $(JAVADOC) -d doc

# clean all
clean:
	rm -rf build doc VSim.jar
