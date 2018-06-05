JAR=jar
JAVA=java
JAVAC=javac
JFLAGS=

# build classpath
CLASSPATH=.

# all src files
SRCDIR=.
SRC=$(shell find $(SRCDIR) -not -path './test/*' -name '*.java')

# all class files
OBJDIR=build
OBJS=$(patsubst $(SRCDIR)/%.java, $(OBJDIR)/%.class, $(SRC))


# VSim script
VSim.jar: build META-INF/MANIFEST.MF Makefile $(OBJS)
	$(RM) VSim.jar
	$(JAR) -cvfm VSim.jar META-INF/MANIFEST.MF -C build/ .

# create build directory
build:
	@echo "creating build directory..."
	mkdir -p build

# compile vsim
$(OBJS): $(OBJDIR)/%.class: $(SRCDIR)/%.java
	$(JAVAC) $(JFLAGS) -cp $(CLASSPATH) -d build $<

.PHONY: clean

clean:
	rm -rf build VSim.jar