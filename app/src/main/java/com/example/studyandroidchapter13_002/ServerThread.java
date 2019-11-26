package com.example.studyandroidchapter13_002;

import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
import java.util.ArrayList;
import java.util.Iterator;

public class ServerThread implements Runnable {
    // 定义当前线程所处理的Socket
    Socket s = null;
    // 该线程所处理的Socket所对应的输入流
    BufferedReader br = null;
    public ServerThread(Socket s) throws IOException {
        this.s = s;
        // 初始化该Socket对应的输入流
        br = new BufferedReader(new InputStreamReader(s.getInputStream(), "utf-8"));
    }
    public void run(){
        try {
            String content = null;
            // 采用循环不断从Socket中读取客户端发送过来的数据
            while ((content = readFromClient()) != null){
                // 遍历socketList中的每个Socket
                // 将读到的内容向每个Socket发送一次
                for(Iterator<Socket> it = MainActivity.socketList.iterator(); it.hasNext();){
                    Socket s = it.next();
                    try {
                        OutputStream os = s.getOutputStream();
                        os.write((content + "\n").getBytes("utf-8"));
                    }catch (SocketException e){
                        e.printStackTrace();
                        it.remove();
                        Log.e("liujianDebug", String.valueOf(s));
                    }
                }
            }
/*            while((content = readFromClient()) != null){
                // 遍历socketList中的每个Socket
                // 将读到的内容向每个Socket发送一次
                for (Socket s : MainActivity.socketList){
                    OutputStream os = s.getOutputStream();
                    os.write((content + "\n").getBytes("utf-8"));
                }
            }*/
        }catch (IOException e){
            e.printStackTrace();
        }
    }

    // 定义读取客户端数据的方法
    private String readFromClient(){
        try{
            return br.readLine();
        }catch (IOException e){
            Log.e("liujianDebug","客户端断开连接");
            e.printStackTrace();
            MainActivity.socketList.remove(s);
        }
        return null;
    }
}
