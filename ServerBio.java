package easyChat;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class ServerBio extends Thread{

    private static int DEFAULT_PORT = 12343;
    private static ServerSocket serverSocket;

    public static void main(String art[]){
        try {
            start(DEFAULT_PORT);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

//    public static void start() throws IOException {
//            start(DEFAULT_PORT);
//    }
    private static void start(int port) throws IOException{
        if(serverSocket != null) return;
        try{
            serverSocket = new ServerSocket(port); // bind   listen()
            System.out.println("The server has satrted. The port number is ："+ port);
            Socket socket;
            while(true){
                socket = serverSocket.accept();
                System.out.println("socket : "+ socket +"is connected");
                new Thread((new ServerHandler(socket))).start();
            }
        }finally {
            if(serverSocket != null){
                System.out.println("Server is down");
                serverSocket.close();
                serverSocket = null;
            }
        }

    }

}
