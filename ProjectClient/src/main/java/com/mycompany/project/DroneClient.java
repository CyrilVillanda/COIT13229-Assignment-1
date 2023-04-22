/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.project;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.EOFException;
import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;

/**
 *
 * @author cjvil
 */
public class DroneClient {
    public static void main (String args[]) {
     //arguments supply message and hostname of destination
     //if running from a command prompt
    Socket s=null;
    String hostName = "localhost";
    String message ="Output works";
    try{
    int serverPort=7896;
    
    s=new Socket(hostName, serverPort);    
    DataInputStream in=new DataInputStream(s.getInputStream());
    DataOutputStream out=new DataOutputStream(s.getOutputStream());
    out.writeUTF(message);
        String data = in.readUTF();       
    System.out.println("Message Received From Server: "+ data) ;      
       } catch (UnknownHostException e){
       System.out.println("Sock:"+e.getMessage()); 
    } catch (EOFException e){
       System.out.println("EOF:"+e.getMessage());
        } catch (IOException e){
       System.out.println("IO:"+e.getMessage());
        }
    finally {
       if(s!=null)
         try {
              s.close();
             } catch (IOException e){
        System.out.println("close:"+e.getMessage());}
        } 
    }
}
