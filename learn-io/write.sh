#!/usr/bin/env sh

# 构建应用并运行

./gradlew clean installBootDist
strace build/install/learn-io-boot/bin/learn-io
