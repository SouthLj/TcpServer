package com.example.studyandroidchapter13_002;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    //定义保存所有Socket的ArrayList
    public static ArrayList<Socket> socketList
            = new ArrayList<Socket>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        new Thread(){
            @Override
            public void run(){
                try{
                    ServerSocket ss = new ServerSocket(30000);
                    while (true){
                        // 此行代码会阻塞，将一直等待别人的连接
                        Socket s = ss.accept();
                        Log.e("liujianDebug", "socket"+s.toString());
                        socketList.add(s);
                        // 每当客户端连接后启动一条ServerThread线程为该客户端服务
                        new Thread(new ServerThread(s)).start();
                    }
                }catch (IOException e){
                    e.printStackTrace();
                }
            }
        }.start();


    }

}
