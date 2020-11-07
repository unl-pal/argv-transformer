N=$1

DIR=$( cd "$( dirname "${BASH_SOURCE[0]}" )" && pwd )
USER=$(cat $DIR/account | grep USER | awk '{print $2}')
DOMAIN=$(cat $DIR/account | grep DOMAIN | awk {'print $2'})

echo $DIR $USER $DOMAIN

for ((i = 0; i < $N; i++)); do
  ssh -t $USER@node-0$i.$DOMAIN -C "
    sudo apt-get update;
    sudo apt-get -y install openjdk-7-jdk;
    sudo apt-get -y install maven;
    sudo apt-get -y install ant;
    sudo apt-get -y install python-numpy
  " &
done

wait
