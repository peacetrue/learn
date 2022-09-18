#!/usr/bin/env sh

#scp root@10.0.0.51:/root/download/prometheus-2.21.0.linux-386/prometheus.yml .
#scp prometheus.yml root@10.0.0.51:/root/download/prometheus-2.21.0.linux-386
nodes=($ZOOKEEPER_NODES)
echo "${nodes[0]  }"
#for node in ${nodes[@]}; do
#  echo "node: $node"
#  ssh "root@$node" "zkServer.sh status"
#done
