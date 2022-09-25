#!/usr/bin/env sh

className=${1-Empty}

echo "run.sh $className"

cd "src/main/java"
javac "com/github/peacetrue/learn/strace/${className}Test.java"
rm -rf "$className"*
strace -o "$className.txt" -ff java "com.github.peacetrue.learn.strace.${className}Test"
