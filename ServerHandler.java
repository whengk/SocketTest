package easyChat;

import java.io.*;
import java.net.Socket;

public class ServerHandler implements Runnable{
    //定义一个变量来保存 socket   --> 主线程监听到的  子线程有请求的一个 socket
    private Socket socket;
    public ServerHandler(Socket socket) {
        this.socket = socket;
    }

    //在run方法中写具体需要做的事情 定义这个IO流和client 之间的通讯 这些东西用完之后要关闭

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
                //通过BufferedReader读取一行
                //如果已经读到输入流尾部 返回null 退出循环
                //如果得到非空值 就尝试计算结果并返回
                if((expression = bufferedReader.readLine()) == null) break;
                System.out.println("服务器收到 "+socket+" 消息： " + expression);
                try{
                    result = String.valueOf(expression.hashCode());
                }catch (Exception e){
                    result = "计算错误" + e.getMessage();
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
