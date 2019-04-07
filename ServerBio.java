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
            System.out.println("服务器已启动 端口号："+ port);
            Socket socket;
            //  通过无限循环监听客户连接
            //如果没有客户端接入  将阻塞在accept操作上
            while(true){
                socket = serverSocket.accept();
                System.out.println("socket : "+ socket +"已连接");
                //当有新的客户端接入是 汇之星下面的代码
                //然后创建一个新的线程处理这条Socket 链路
                new Thread((new ServerHandler(socket))).start();
            }
        }finally {
            //一些必要的清理工作
            if(serverSocket != null){
                System.out.println("服务器已关闭");
                serverSocket.close();
                serverSocket = null;
            }
        }

    }

}
