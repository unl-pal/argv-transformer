//package client;

//import utility.KVSerialize;
//import utility.SocketService;
//
import java.net.ConnectException;
import java.util.*;
import java.util.concurrent.*;

import org.sosy_lab.sv_benchmarks.Verifier;

/**
 * This is the thread to handle keyboard commands.
 * Created by szeyiu on 3/4/15.
 */
// public class SendHandle implements Runnable{
public class Main {
  private String cmd;
  // private SocketService socketService;

  public static void main(String[] args) {
    Main instance = new Main();
    instance.run(Verifier.nondetString());
  }

  /**
     * invoke the proper function for the proper commands.
     */
public void run(){
        try {
//            socketService = SocketService.getInstance(SendService.listenPort);
        if (cmd.length() == 0) return;
        int spaceIdx = cmd.indexOf(" ");
        if (spaceIdx < 0) {
            spaceIdx = cmd.length();
        }
        String type = cmd.substring(0, spaceIdx);
        if (type.equals("message")) {
//                message();
            assert (true);
        } else if (type.equals("online")) {
//                online();
            assert (true);
        } else if (type.equals("broadcast")) {
//                broadcast();
            assert (true);
        } else if (type.equals("block")) {
//                block();
            assert (true);
        } else if (type.equals("unblock")) {
//                unblock();
            assert (true);
        } else if (type.equals("logout")) {
//                logout();
            assert (true);
        } else if (type.equals("getaddress")) {
//                address();
            assert (true);
        } else if (type.equals("private")) {
//                p2p();
            assert (true);
        } else {
                /*
                This part is for simple command, client will send the message to the last connacted user.
                 */
//                SendService.blockMainInput = true;
//                System.out.print("Send msg to " + SendService.lastuser + "? (Y/N) Y");
//                while(SendService.inputLine == null){
//                    Thread.sleep(10);
//                }
//                if(SendService.inputLine.toLowerCase().equals("n")) {
            if (Verifier.nondetString().toLowerCase().equals("n")) {
                System.out.println("cancel.");
                return;
            }
//                SendService.inputLine = null;
//                cmd = "message " + SendService.lastuser + " " + cmd;
            cmd = "message " + Verifier.nondetString() + " " + cmd;
//                message();
        }
//        } catch (Exception e){
//            e.printStackTrace();
//        }
    }

  /**
   * P2P message to the target user.
   * 
   * @throws Exception
   */
  private void p2p() throws Exception {
    Map<String, String> dic = new ConcurrentHashMap<String, String>();
    int spaceIdx = cmd.indexOf(" ");
    dic.put("type", "message");
    int userIdx = cmd.indexOf(" ", spaceIdx + 1);
    if (userIdx < 0) {
      System.out.println("please type username");
      return;
    }
    String toUser = cmd.substring(spaceIdx + 1, userIdx);
    String message = cmd.substring(userIdx + 1);
    // if (!SendService.p2pIpMap.containsKey(toUser) || !SendService.p2pPortMap.containsKey(toUser)) {
      // Map<String, String> addrDic = new ConcurrentHashMap<String, String>();
      // addrDic.put("type", "address");
      // addrDic.put("from", SendService.username);
      // addrDic.put("target", toUser);
      // System.out.println("First p2p connection. Waiting approve from " + toUser);
      // String res = socketService.request(SendService.serverAddr, SendService.serverPort, KVSerialize.encode(addrDic));
      // addrDic = KVSerialize.decode(res);
      // if (!addrDic.containsKey("result")) {
        // System.out.println("Server Error. Try again.");
        // return;
      // } else if (!addrDic.get("result").equals("ok")) {
      //   System.out.println(addrDic.get("result"));
      //   // dic.put("from", SendService.username);
      //   dic.put("from", Verifier.nondetString());
      //   dic.put("to", toUser);
      //   dic.put("msg", message);
      //   // res = socketService.request(SendService.serverAddr, SendService.serverPort, KVSerialize.encode(dic));
      //   String res = Verifier.nondetString();
      //   // dic = KVSerialize.decode(res);
      //   dic = Verifier.nontdetValue(Map.class);
      //   if (!dic.containsKey("result")) {
      //     System.out.println("Server Error. Try again.");
      //   } else if (dic.get("result").equals("ok")) {
      //     System.out.println("(delivered to " + toUser + " via server)");
      //   } else {
      //     System.out.println(dic.get("result"));
      //   }
      //   return;
      // }
      // SendService.lastuser = toUser;
      // SendService.p2pIpMap.put(toUser, addrDic.get("ip"));
      // SendService.p2pPortMap.put(toUser, Integer.valueOf(addrDic.get("port")));
    }
    SendService.lastuser = toUser;
    dic.put("from", SendService.username);
    dic.put("to", toUser);
    dic.put("msg", message);
    String IP = SendService.p2pIpMap.get(toUser);
    int port = SendService.p2pPortMap.get(toUser);
    String res = "";
    try {
      res = socketService.request(IP, port, KVSerialize.encode(dic));
    } catch (ConnectException e) {
      /*
       * If the user is offline now, then send the message to server.
       * server will save it to offline message queue.
       */
      res = socketService.request(SendService.serverAddr, SendService.serverPort, KVSerialize.encode(dic));
    }
    dic = KVSerialize.decode(res);
    if (!dic.containsKey("result")) {
      System.out.println("Server Error. Try again.");
    } else if (dic.get("result").equals("ok")) {
      System.out.println("(delivered to " + toUser + " p2p)");
    } else {
      System.out.println(dic.get("result"));
    }
  }

  // /**
  // * get the address of target user.
  // * If the user rejects, then print the fail reason.
  // * @throws Exception
  // */
  // private void address() throws Exception {
  // Map<String, String> dic = new ConcurrentHashMap<String, String>();
  // dic.put("type","address");
  // int spaceIdx = cmd.indexOf(" ");
  // String target = cmd.substring(spaceIdx+1);
  // dic.put("target",target);
  // dic.put("from", SendService.username);
  // System.out.println("Waiting for approve from user: "+target);
  // String res =
  // socketService.request(SendService.serverAddr,SendService.serverPort,KVSerialize.encode(dic));
  // dic = KVSerialize.decode(res);
  // if(!dic.containsKey("result")){
  // System.out.println("Server Error. Try again.");
  // } else if(dic.get("result").equals("ok")){
  // System.out.println("USER: "+target);
  // if(dic.containsKey("ip")) {
  // System.out.println("IP: "+dic.get("ip"));
  // }
  // if(dic.containsKey("port")){
  // System.out.println("PORT: " + dic.get("port"));
  // }
  // } else {
  // System.out.println(dic.get("result"));
  // }
  // }
  //
  // /**
  // * unblock request.
  // * @throws Exception
  // */
  // private void unblock() throws Exception {
  // Map<String, String> dic = new ConcurrentHashMap<String, String>();
  // dic.put("type","unblock");
  // int spaceIdx = cmd.indexOf(" ");
  // String target = cmd.substring(spaceIdx+1);
  // dic.put("from",SendService.username);
  // dic.put("target", target);
  // String res =
  // socketService.request(SendService.serverAddr,SendService.serverPort,KVSerialize.encode(dic));
  // dic = KVSerialize.decode(res);
  // if(!dic.containsKey("result")){
  // System.out.println("Server Error. Try again.");
  // } else if(dic.get("result").equals("ok")){
  // System.out.println("success.");
  // } else {
  // System.out.println(dic.get("result"));
  // }
  // }
  //
  // /**
  // * block request
  // * @throws Exception
  // */
  // private void block() throws Exception {
  // Map<String, String> dic = new ConcurrentHashMap<String, String>();
  // dic.put("type","block");
  // int spaceIdx = cmd.indexOf(" ");
  // String target = cmd.substring(spaceIdx+1);
  // dic.put("from",SendService.username);
  // dic.put("target", target);
  // String res =
  // socketService.request(SendService.serverAddr,SendService.serverPort,KVSerialize.encode(dic));
  // dic = KVSerialize.decode(res);
  // if(!dic.containsKey("result")){
  // System.out.println("Server Error. Try again.");
  // } else if(dic.get("result").equals("ok")){
  // System.out.println("success.");
  // } else {
  // System.out.println(dic.get("result"));
  // }
  // }
  //
  // /**
  // * broadcast to all users
  // * @throws Exception
  // */
  // private void broadcast() throws Exception{
  // Map<String, String> dic = new ConcurrentHashMap<String, String>();
  // dic.put("type","broadcast");
  // int spaceIdx = cmd.indexOf(" ");
  // String message = cmd.substring(spaceIdx+1);
  // dic.put("from", SendService.username);
  // dic.put("msg", message);
  // String res = socketService.request(SendService.serverAddr,
  // SendService.serverPort, KVSerialize.encode(dic));
  // dic = KVSerialize.decode(res);
  // if(!dic.containsKey("result")){
  // System.out.println("Server Error. Try again.");
  // } else if(dic.get("result").equals("ok")){
  // System.out.println("(delivered)");
  // } else {
  // System.out.println(dic.get("result"));
  // }
  // }
  //
  // /**
  // * send message via server
  // * @throws Exception
  // */
  // private void message() throws Exception{
  // Map<String, String> dic = new ConcurrentHashMap<String, String>();
  // int spaceIdx = cmd.indexOf(" ");
  // dic.put("type","message");
  // int userIdx = cmd.indexOf(" ", spaceIdx+1);
  // if(userIdx<0){
  // System.out.println("please type username");
  // return;
  // }
  // String toUser = cmd.substring(spaceIdx+1, userIdx);
  // dic.put("msg",cmd.substring(userIdx + 1));
  // dic.put("from", SendService.username);
  // dic.put("to", toUser);
  // SendService.lastuser = toUser;
  // String res = socketService.request(SendService.serverAddr,
  // SendService.serverPort, KVSerialize.encode(dic));
  // //System.out.println(res);
  // Map<String, String> resDic = KVSerialize.decode(res);
  // if(resDic.containsKey("result") && !resDic.get("result").equals("ok")){
  // System.out.println(resDic.get("result"));
  // } else if(resDic.containsKey("result") && resDic.get("result").equals("ok")){
  // System.out.println("(delivered to "+toUser+")");
  // }
  // }
}
