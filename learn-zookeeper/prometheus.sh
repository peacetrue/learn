#!/usr/bin/env sh

#scp root@10.0.0.51:/root/download/prometheus-2.21.0.linux-386/prometheus.yml .
#scp prometheus.yml root@10.0.0.51:/root/download/prometheus-2.21.0.linux-386
nodes=(`echo $ZOOKEEPER_NODES`)
for node in ${nodes[@]}; do
  echo "node: $node"
  ssh "root@$node" "zkServer.sh start"
  echo "\n"
done
