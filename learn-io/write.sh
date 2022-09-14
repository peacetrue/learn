#!/usr/bin/env sh

# 构建应用
./gradlew clean installBootDist

# 运行并追踪系统调用
strace -c -o learn-io-strace.txt build/install/learn-io-boot/bin/learn-io
