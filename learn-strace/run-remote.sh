#!/usr/bin/env sh

className=${1-LockSupport}
workingDir="/root/learn/learn-strace"
scp -r . "root@test-node01:$workingDir"

ssh root@test-node01 <<EOF
 cd "$workingDir"
 echo "$className"
 sh run.sh "$className"
EOF

#scp root@test-node01:/root/learn/learn-strace/src/main/java
