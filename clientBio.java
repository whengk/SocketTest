package easyChat;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

public class clientBio {

    private static String DEFAULT_SERVER_IP = "10.100.0.134";
    private static int DEFAULT_SERVER_PORT = 12343;

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        boolean flag = false;
        while(!flag){
            System.out.println("Please input:");
            String expression = scanner.next();
            send(expression);
            while(true){
                System.out.println("Continue to calculate？ Y/N");
                String string = scanner.next();
                if(string.equalsIgnoreCase("N")){
                    flag = true;
                    break;
                }else if(string.equalsIgnoreCase("Y")){
                    System.out.println("Please input：");
                    expression = scanner.next();
                    send(expression);
                }
            }
        }
    }

    public static void send(String expression){
        send(DEFAULT_SERVER_PORT,expression);
    }

    private static void send(int port,String experssion){
        System.out.println("Input： "+ experssion);
        Socket socket = null;
        BufferedReader bufferedReader = null;
        PrintWriter out = null;
        try{
            socket = new Socket(DEFAULT_SERVER_IP,port);
            bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            out = new PrintWriter(socket.getOutputStream(),true);
            out.println(experssion);
            System.out.println("The hashCode result is :"+ bufferedReader.readLine());
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            if (bufferedReader != null){
                try {
                    bufferedReader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if(out != null){
                out.close();
            }
        }
    }
}
