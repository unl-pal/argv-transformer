package client;

import utility.KVSerialize;
import utility.SocketService;

import java.net.ConnectException;
import java.util.*;
import java.util.concurrent.*;

/**
 * This is the thread to handle keyboard commands.
 * Created by szeyiu on 3/4/15.
 */
public class SendHandle implements Runnable{
    private String cmd;
    private SocketService socketService;
    /**
     * P2P message to the target user.
     * @throws Exception
     */
    private void p2p() throws Exception {
        Map<String, String> dic = new ConcurrentHashMap<String, String>();
        int spaceIdx = cmd.indexOf(" ");
        dic.put("type","message");
        int userIdx = cmd.indexOf(" ", spaceIdx+1);
        if(userIdx<0){
            System.out.println("please type username");
            return;
        }
        String toUser = cmd.substring(spaceIdx+1, userIdx);
        String message = cmd.substring(userIdx + 1);
        if(!SendService.p2pIpMap.containsKey(toUser) || !SendService.p2pPortMap.containsKey(toUser)){
            Map<String, String> addrDic = new ConcurrentHashMap<String, String>();
            addrDic.put("type", "address");
            addrDic.put("from", SendService.username);
            addrDic.put("target", toUser);
            System.out.println("First p2p connection. Waiting approve from "+toUser);
            String res = socketService.request(SendService.serverAddr,SendService.serverPort,KVSerialize.encode(addrDic));
            addrDic = KVSerialize.decode(res);
            if(!addrDic.containsKey("result")) {
                System.out.println("Server Error. Try again.");
                return;
            } else if(!addrDic.get("result").equals("ok")){
                System.out.println(addrDic.get("result"));
                dic.put("from", SendService.username);
                dic.put("to", toUser);
                dic.put("msg", message);
                res = socketService.request(SendService.serverAddr, SendService.serverPort, KVSerialize.encode(dic));
                dic = KVSerialize.decode(res);
                if(!dic.containsKey("result")){
                    System.out.println("Server Error. Try again.");
                } else if(dic.get("result").equals("ok")){
                    System.out.println("(delivered to "+toUser+" via server)");
                } else {
                    System.out.println(dic.get("result"));
                }
                return;
            }
            SendService.lastuser = toUser;
            SendService.p2pIpMap.put(toUser, addrDic.get("ip"));
            SendService.p2pPortMap.put(toUser, Integer.valueOf(addrDic.get("port")));
        }
        SendService.lastuser = toUser;
        dic.put("from", SendService.username);
        dic.put("to", toUser);
        dic.put("msg", message);
        String IP = SendService.p2pIpMap.get(toUser);
        int port = SendService.p2pPortMap.get(toUser);
        String res="";
        try {
            res = socketService.request(IP, port, KVSerialize.encode(dic));
        } catch (ConnectException e){
            /*
            If the user is offline now, then send the message to server.
            server will save it to offline message queue.
             */
            res = socketService.request(SendService.serverAddr, SendService.serverPort, KVSerialize.encode(dic));
        }
        dic = KVSerialize.decode(res);
        if(!dic.containsKey("result")){
            System.out.println("Server Error. Try again.");
        } else if(dic.get("result").equals("ok")){
            System.out.println("(delivered to "+toUser+" p2p)");
        } else {
            System.out.println(dic.get("result"));
        }
    }

    /**
     * send message via server
     * @throws Exception
     */
    private void message() throws Exception{
        Map<String, String> dic = new ConcurrentHashMap<String, String>();
        int spaceIdx = cmd.indexOf(" ");
        dic.put("type","message");
        int userIdx = cmd.indexOf(" ", spaceIdx+1);
        if(userIdx<0){
            System.out.println("please type username");
            return;
        }
        String toUser = cmd.substring(spaceIdx+1, userIdx);
        dic.put("msg",cmd.substring(userIdx + 1));
        dic.put("from", SendService.username);
        dic.put("to", toUser);
        SendService.lastuser = toUser;
        String res = socketService.request(SendService.serverAddr, SendService.serverPort, KVSerialize.encode(dic));
        //System.out.println(res);
        Map<String, String> resDic = KVSerialize.decode(res);
        if(resDic.containsKey("result") && !resDic.get("result").equals("ok")){
            System.out.println(resDic.get("result"));
        } else if(resDic.containsKey("result") && resDic.get("result").equals("ok")){
            System.out.println("(delivered to "+toUser+")");
        }
    }
}
