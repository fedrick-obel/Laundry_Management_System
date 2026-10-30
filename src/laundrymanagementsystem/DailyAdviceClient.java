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
public class DailyAdviceClient {
        
    public void go(){
        try{
            Socket s = new Socket("127.0.0.1", 4242);
            InputStreamReader streamReader = new InputStreamReader(s.getInputStream());
            BufferedReader reader = new BufferedReader(streamReader);
            
            String advice = reader.readLine();
            System.out.println("Today you should: " + advice);
            reader.close();
        } catch(IOException ex){
            ex.printStackTrace();
        }
    }// close go
    
    public static void main(String []args){
        DailyAdviceClient client = new DailyAdviceClient();
        client.go();
    }
}
