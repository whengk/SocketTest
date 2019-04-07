package easyChat;

import java.io.*;
import java.net.Socket;

public class ServerHandler implements Runnable{
    private Socket socket;
    public ServerHandler(Socket socket) {
        this.socket = socket;
    }
    
    @Override
    public void run() {
        BufferedReader bufferedReader = null;
        BufferedWriter bufferedWriter = null;
        try {
            bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            bufferedWriter = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
            String expression;
            String result;
            while(true){
                if((expression = bufferedReader.readLine()) == null) break;
                System.out.println("Server "+socket+" recerved the message： " + expression);
                try{
                    result = String.valueOf(expression.hashCode());
                }catch (Exception e){
                    result = "calculation mistack" + e.getMessage();
                }
                bufferedWriter.write(result+"\n");
                bufferedWriter.flush();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            if(bufferedReader != null ){
                try {
                    bufferedReader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                bufferedReader = null;
            }
            if (bufferedWriter != null){
                try {
                    bufferedWriter.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                bufferedWriter = null;
            }
        }
    }
}
