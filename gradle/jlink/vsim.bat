@echo off
set DIR="%~dp0"
set JAVA_EXEC="%DIR:"=%\\java"
%JAVA_EXEC% ${jvmArgs} -m ${moduleName}/${mainClassName}  %*
