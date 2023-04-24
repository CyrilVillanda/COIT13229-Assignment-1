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
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Scanner;

/**
 *
 * @author cjvil
 */
public class DroneClient {
    public static void main (String args[]) {
    ArrayList<Drone> drones = new ArrayList<>();
    Scanner input = new Scanner(System.in);
    Socket s = null;
    String hostName = "localhost";
    String droneId;
    String droneName;
    String x = "250";
    String y = "250";
    try{
	int serverPort=7896;
    
	s = new Socket(hostName, serverPort);    
	DataInputStream dataIn =new DataInputStream(s.getInputStream());
	DataOutputStream dataOut =new DataOutputStream(s.getOutputStream());
        ObjectOutputStream objectOut = new ObjectOutputStream(dataOut);
        
        System.out.print("Enter Drone id: ");
        droneId = input.next();
        
        System.out.print("Enter Name id: ");
        droneName = input.next();
        
        Drone drone = new Drone(droneId, droneName, x, y);
       
	objectOut.writeObject(drone);
        
        String data = dataIn.readUTF();
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

    
