/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import java.io.*;
import java.net.*;
/**
 *
 * @author nabaasa evarist
 */
public class DailyAdviceServer {
    String [] adviceList = {"Take small bites", "Go for hair cut","put on jeans"};
    
    public void go(){
        try{
            ServerSocket serverSock = new ServerSocket(4242);
            
            while(true){
                Socket sock = serverSock.accept();
                PrintWriter writer = new PrintWriter(sock.getOutputStream());
                String advice = getAdvice();
                writer.println(advice);
                writer.close();
                
            }
        } catch(IOException ex){
            ex.printStackTrace();
        }
    }// close go
    
    private String getAdvice(){
        int random = (int) (Math.random() * adviceList.length);
        return adviceList[random];
    }
    
    public static void main(String []args){
        DailyAdviceServer server = new DailyAdviceServer();
        server.go();
    }
}
