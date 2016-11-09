package ru.sbt.SchoolChat;


import java.io.IOException;
import java.net.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;


public class Server implements Runnable{
    private final int LGN = 0;
    private final int MSG = 1;
    private final int GETALLMSG = 2;
    private final int EXIT = 3;
    private final Map<String, SocketAddress> addressBook = new HashMap<>();
    private volatile boolean online;
    private InetAddress group;
    private MulticastSocket socket;
    private  Map<SocketAddress, List<String>> history;

    public static void main(String[] args) {
        Server server = new Server();
        server.run();
    }
    public void run(){
          online = true;
          history = new ConcurrentHashMap<>();
        try{
            socket = new MulticastSocket(1234);
            group = InetAddress.getByName("239.255.255.0");
            socket.joinGroup(group);
            byte[] buf = new byte[256];
            DatagramPacket datagram = new DatagramPacket(buf, buf.length);
            do {
                socket.receive(datagram);
                processDatagram(datagram);

            }while(online);
            socket.leaveGroup(group);

        }catch(IOException e){
            e.printStackTrace();
        }
    }
    private void addAdress(String login, SocketAddress address){
        addressBook.put(login, address);
    }

    private void processDatagram(DatagramPacket dpMessage) throws IOException {
        String line = new String(dpMessage.getData(), 0, dpMessage.getLength());
        switch(Integer.parseInt(line.substring(0, 1))) {
            case LGN:
                addAdress(line.substring(1), dpMessage.getSocketAddress());
                annotate(line + " joined the chat.");
                System.out.println(line.substring(1) + " joined the chat.");
                break;
            case GETALLMSG:
                //sendHistory(dpMessage.getSocketAddress());
                break;
            case MSG:
                sendToReceiver(dpMessage);
                System.out.println(line.substring(1));
                break;
            case EXIT:
                annotate(line + " left the chat.");
                addressBook.remove(line.substring(1));
                System.out.println(line.substring(1) + " left the chat.");
                break;
            default:
                throw new IOException("Unknown command.");
        }
    }

    private void annotate(String message){
        byte[] data = message.getBytes();
        addressBook.forEach((name, address) -> {
            DatagramPacket datagram = new DatagramPacket(data, data.length, address);
            try {
                socket.send(datagram);
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }


   /* private void sendHistory(SocketAddress receiverAddress){
        history.get(receiverAddress).
    }*/

    private void sendToReceiver(DatagramPacket dpMessage) throws IOException {
        String line = new String(dpMessage.getData(), 0, dpMessage.getLength());
        line = line.substring(1);
        SocketAddress address = addressBook.get(line.split("-->")[1].split(">>")[0]);//TODO аккуратно разбить на цель отправителя текст
        if(address != null) {
            dpMessage.setSocketAddress(address);
        }
        else{
            dpMessage.setData("Receiver is offline or does not exist.".getBytes());
        }
        socket.send(dpMessage);
    }
}
