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
import java.util.Scanner;

/**
 *
 * @author cjvil
 */
public class DroneClient {
    public static void main (String args[]) {
    Scanner input = new Scanner(System.in);
    Socket s = null;
    String hostName = "localhost";
    String droneId;
    String droneName;
    String x = "0";
    String y = "0";
    try{
	int serverPort=7896;
    
	s = new Socket(hostName, serverPort);    
	DataInputStream in =new DataInputStream(s.getInputStream());
	DataOutputStream out =new DataOutputStream(s.getOutputStream());
        
        System.out.print("Enter Drone id: ");
        droneId = input.next();
        
        System.out.print("Enter Name id: ");
        droneName = input.next();
        
        String drone = droneId +","+ droneName +","+ x +","+ y;
       
	out.writeUTF(drone);
        String data = in.readUTF();	      
	System.out.println("Message Received From Server: "+ data) ;      
       } catch (UnknownHostException e){
	   System.out.println("Sock:"+e.getMessage()); 
	} catch (EOFException e){
	   System.out.println("EOF:"+e.getMessage());
    	} catch (IOException e){
	   System.out.println("IO:"+e.getMessage());
        }
	
    }
}
