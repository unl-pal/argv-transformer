N=$1
CMD=$2

for ((i = 0; i < $N; i++)); do
  ssh -t sonnbc@node-0$i.riak.confluence.emulab.net -C $CMD &
done

wait
