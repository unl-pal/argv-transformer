package tests;

import java.util.ArrayList;

/**
 * Created by remo on 05.04.15.
 */
public final class NetworkUtils {

    public static int ErlangB(double a, double b){
        int n = 0;
        double result = 1;
        while (result>b){
            n++;
            result=(a*result)/(n+a*result);
        }
        return n;
    }

    public static int NtoPCM(int n){
        int result = 0;
        result = (int)Math.ceil((double)n/30);
        return result;
    }

    public static double AoutPstn(int index, ArrayList<ArrayList<Double>> flowMatrix, ArrayList<Node> nodes){
        double result = 0;
        for (Node node: nodes){
            result = result + flowMatrix.get(nodes.indexOf(node)).get(index)*node.aInRt;
        }
        return result;
    }

    public static ArrayList<ArrayList<Double>> calcRtCapacity (NetworkParams params){

        double g711PacketIntens = 1/ params.getG711PacketTime();
        double g729PacketIntens = 1/ params.getG729PacketTime();
        double g711PacketLength = params.getG711PacketTime() *NetworkParams.G_711_CODEC_CAPACITY +NetworkParams.RT_PACKET_HEAD;
        double g729PacketLength = params.getG729PacketTime() *NetworkParams.G_729_CODEC_CAPACITY +NetworkParams.RT_PACKET_HEAD;
        double capacity = 0;

        ArrayList<ArrayList<Double>> result = new ArrayList<ArrayList<Double>>();
        ArrayList<Node> nodes = params.getNodeList();
        ArrayList<ArrayList<Double>> rtFlow = params.getRtMatrix();

        for(Node sourceNode: nodes){
            ArrayList<Double> resultRow = new ArrayList<Double>();
            int j = 0;
            int i = nodes.indexOf(sourceNode);
            if (sourceNode instanceof GWNode){
                for (Node destNode: nodes){
                    j = nodes.indexOf(destNode);
                    if (destNode instanceof GWNode){
                        capacity = rtFlow.get(i).get(j)*((GWNode) sourceNode).getnRIn()*g711PacketIntens*g711PacketLength;
                    }
                    else if (destNode instanceof IpNode){
                        capacity = rtFlow.get(i).get(j)*((GWNode) sourceNode).getnRIn()*g729PacketIntens*g729PacketLength;
                    }
                    resultRow.add(j,capacity/1000000);
                }
            }
            else if (sourceNode instanceof IpNode){
                for (Node destNode: nodes){
                    j = nodes.indexOf(destNode);
                    if (destNode instanceof GWNode){
                        capacity = rtFlow.get(i).get(j)*sourceNode.getaInRt()*g729PacketIntens*g729PacketLength;

                    }
                    else if (destNode instanceof IpNode){
                        capacity = rtFlow.get(i).get(j)*sourceNode.getaInRt()*g729PacketIntens*g729PacketLength;
                    }
                    resultRow.add(j,capacity/1000000);
                }
            }
            result.add(i,resultRow);
        }

        return result;

    }

    public static ArrayList<ArrayList<Double>> calcNrtCapacity (NetworkParams params){

        double capacity = 0;

        ArrayList<ArrayList<Double>> result = new ArrayList<ArrayList<Double>>();
        ArrayList<Node> nodes = params.getNodeList();
        ArrayList<ArrayList<Double>> nrtFlow = params.getNrtMatrix();

        for(Node sourceNode: nodes){
            ArrayList<Double> resultRow = new ArrayList<Double>();
            int j = 0;
            int i = nodes.indexOf(sourceNode);
            if (sourceNode instanceof GWNode){
                for (Node destNode: nodes){
                    j = nodes.indexOf(destNode);
                    if (destNode instanceof GWNode){
                        capacity = 0;
                    }
                    else if (destNode instanceof IpNode){
                        capacity = 0;
                    }
                    resultRow.add(j,capacity/1000000);
                }
            }
            else if (sourceNode instanceof IpNode){
                for (Node destNode: nodes){
                    j = nodes.indexOf(destNode);
                    if (destNode instanceof GWNode){
                        capacity = 0;

                    }
                    else if (destNode instanceof IpNode){
                        capacity = nrtFlow.get(i).get(j)*((IpNode) sourceNode).getNrtIntens()*
                                (params.getNrtPacketLength() + NetworkParams.RT_PACKET_HEAD);
                    }
                    resultRow.add(j,capacity/1000000);
                }
            }
            result.add(i,resultRow);
        }

        return result;

    }

    public static double calcOutCapacity(ArrayList<ArrayList<Double>> capacityMatrix, int nodeID){
        double result = 0;
        for (int i=0; i<capacityMatrix.size();i++){
            ArrayList<Double> list = capacityMatrix.get(i);
            if (i==nodeID){
                for (Double capacity : list){
                    result = result + capacity;
                }
            }
        }
        return result;
    }

    public static double calcInCapacity(ArrayList<ArrayList<Double>> capacityMatrix, int nodeID){
        double result = 0;
        for (int i=0; i<capacityMatrix.size();i++){
            ArrayList<Double> list = capacityMatrix.get(i);
            for (int j=0; j<list.size();j++){
                Double capacity = list.get(j);
                if (j==nodeID){
                    result = result + capacity;
                }
            }
        }
        return result;
    }

    public static double calcConnectionCapacity(Connection connection, ArrayList<ArrayList<Route>> routes,
                                                ArrayList<ArrayList<Double>> capacityMatrix){

        double result = 0;

        System.out.printf("Przepływności RT dla łącza " + connection.getSource()+ " " + connection.getDestination()+ ":\n");
        for(ArrayList<Route> routeList: routes){
            for(Route route : routeList){
                if (route.hasConnection(connection)){
                    result=result+capacityMatrix.get(route.getSource()-1).get(route.getDestination()-1);
                    System.out.printf("c"+route.getSource()+route.getDestination()+", ");
                }
            }
        }
        System.out.printf("\n");

        return result;
    }

    public static double calcNRTConnectionCapacity(Connection connection, ArrayList<ArrayList<Route>> routes,
                                                ArrayList<ArrayList<Double>> capacityMatrix){

        double result = 0;

        System.out.printf("Przepływności NRT dla łącza " + connection.getSource()+ " " + connection.getDestination()+ ":\n");

        for(ArrayList<Route> routeList: routes){
            for(Route route : routeList){
                for (Connection connect: route.getConnections()){
                    if (connect.getSource().equals(connection.getSource())) {
                        if (connect.getDestination().equals(connection.getDestination())){
                            result=result+capacityMatrix.get(route.getSource()-1).get(route.getDestination()-1);
                            System.out.printf("c"+route.getSource()+route.getDestination()+", ");
                        }
                    }
                }
            }
        }
        System.out.printf("\n");

        return result;
    }

    public static double countIPLR(double aRT, int queueLength){
        double result = 0;
        result = (1-aRT)/(1-Math.pow(aRT,queueLength+2))*Math.pow(aRT,queueLength+1);
        return result;
    }

    public static double countIPDT(int length, double aRT, int queueLength, double packetLength){
        double result = 0;
        double tOcz = 0;
        double tNad = 0;
        double tProp = 0;

        tProp = length*0.000005;
        tNad = packetLength/ NetworkParams.STM1_CAPACITY;
        tOcz = aRT*tNad*(1+Math.pow(aRT,queueLength)*(queueLength*aRT-(queueLength+1)))/((1-aRT)*(1-Math.pow(aRT,queueLength+2))); // tu sie wynik nie zgadza
                                                                                           result = tOcz + tNad + tProp;
        return result;
    }

    public static double countIPDTMin(double packetLength){
        double result = 0;
        result = packetLength/ NetworkParams.STM1_CAPACITY;
        return result;
    }

    public static double countIPDTMaxRT(int queueLength, double rtPacketLength, double nrtPacketLength){
        double result = 0;
        double tNadRT = 0;
        double tNadNRT = 0;

        tNadRT = rtPacketLength/ NetworkParams.STM1_CAPACITY;
        tNadNRT = nrtPacketLength/ NetworkParams.STM1_CAPACITY;
        result = (queueLength+1)*tNadRT+tNadNRT;
        return result;
    }

    public static double countIPDTMaxNRT(int queueLength, double aRT, double nrtPacketLength){
        double result = 0;
        double tNadNRT = 0;

        tNadNRT = nrtPacketLength/ NetworkParams.STM1_CAPACITY;
        result = ((queueLength+1)*tNadNRT)/(1-aRT);
        return result;
    }

    public static void calcQOS(String type, Route route, NetworkParams params){
        if (type.equalsIgnoreCase("rt")){
            for(Connection connection : route.getConnections()){
                connection.setRtCapacity(NetworkUtils.calcConnectionCapacity(connection, params.getRoutes(), params.getRtCapacity()));
                connection.setIplr(NetworkUtils.countIPLR(connection.getaRT(), params.getRtQueueLength()));
                connection.setIpdt(NetworkUtils.countIPDT(connection.getLength(),connection.getaRT(), params.getRtQueueLength(),960));
                connection.setIpdtMin(NetworkUtils.countIPDTMin(960));
                connection.setIpdtMax(NetworkUtils.countIPDTMaxRT(params.getRtQueueLength(),960, params.getNrtPacketLength()));
                connection.setIpdvMax(connection.getIpdtMax()-connection.getIpdtMin());

            }
        }
        else if (type.equalsIgnoreCase("nrt")){
            for(Connection connection : route.getConnections()){
                connection.setRtCapacity(NetworkUtils.calcConnectionCapacity(connection, params.getRoutes(), params.getRtCapacity()));
                connection.setNrtCapacity(NetworkUtils.calcNRTConnectionCapacity(connection, params.getRoutes(), params.getNrtCapacity()));
                connection.setIplr(NetworkUtils.countIPLR(connection.getaNRT(), params.getNrtQueueLength()));
                connection.setIpdt(NetworkUtils.countIPDT(connection.getLength(),connection.getaNRT(), params.getNrtQueueLength(),
                        params.getNrtPacketLength() +NetworkParams.NRT_PACKET_HEAD));
                connection.setIpdtMin(NetworkUtils.countIPDTMin(params.getNrtPacketLength() +NetworkParams.NRT_PACKET_HEAD));
                connection.setIpdtMax(NetworkUtils.countIPDTMaxNRT(params.getNrtQueueLength(), connection.getaRT(),
                        params.getNrtPacketLength() +NetworkParams.NRT_PACKET_HEAD));
                connection.setIpdvMax(connection.getIpdtMax()-connection.getIpdtMin());
            }
        }
    }



}
