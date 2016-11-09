package ru.sbt.SchoolChat;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.locks.ReentrantLock;

public class User {
    private final int LGN = 0;
    private final int MSG = 1;
    private final int GETALLMSG = 2;
    private final int EXIT = 3;

    private String name;
    private volatile boolean connected;
    public static void main(String[] args){
        User user = new User();
        try {
            user.run();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    private void run() throws IOException {
         connected = true;
        InetAddress group = InetAddress.getByName("239.255.255.0");
        try (DatagramSocket socket = new DatagramSocket();
             BufferedReader console = new BufferedReader(new InputStreamReader(System.in))) {
            joinChat(console, socket, group);
            byte[] buf = new byte[256];
            DatagramPacket datagram = new DatagramPacket(buf, buf.length);
            requests(console, socket, group);
            ReentrantLock lock  = new ReentrantLock();
            do {
                try {
                    socket.receive(datagram);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                String command = new String(datagram.getData(), 0, datagram.getLength());
                System.out.println(command.substring(1));
            } while (connected);
            System.out.println("Good Bye!");
        }

    }

    private void joinChat(BufferedReader console,DatagramSocket socket, InetAddress group) throws IOException {
        System.out.print("Login: ");
        name = console.readLine();
        String login = LGN + name;
        byte[] loginBytes = login.getBytes();
        DatagramPacket authorization = new DatagramPacket(loginBytes, loginBytes.length, group, 1234);
        socket.send(authorization);
        connected = true;
    }

    private void newMessage(BufferedReader console,DatagramSocket socket, InetAddress group) throws IOException{
        String line = console.readLine();
        if(!line.equalsIgnoreCase("exit")){
            line = MSG + name + "-->" + line;
            byte[] bytes = line.getBytes();
            DatagramPacket dp = new DatagramPacket(bytes, bytes.length, group, 1234);
            socket.send(dp);
        }else{
            line = EXIT + name;
            byte[] bytes = line.getBytes();
            DatagramPacket dp = new DatagramPacket(bytes, bytes.length, group, 1234);
            socket.send(dp);
            connected = false;
        }
    }

    private void requests(BufferedReader console,DatagramSocket socket, InetAddress group)throws SocketException{
        ExecutorService outputThread = Executors.newSingleThreadExecutor();

        Runnable printMessage = () -> {
            do {
                try {
                    newMessage(console, socket, group);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            } while (connected);
        };
        outputThread.execute(printMessage);
        outputThread.shutdown();
    };

}
