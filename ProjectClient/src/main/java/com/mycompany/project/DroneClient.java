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
import java.util.Random;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author cjvil
 */
public class DroneClient extends Thread {
    
    static Drone drone;
    
    public static void main (String args[]) throws InterruptedException {
    ArrayList<Fire> fires = new ArrayList<>();
    Random randNum = new Random();
    Scanner input = new Scanner(System.in);
    Socket s = null;
    String hostName = "localhost";
    String droneId;
    String droneName;
    String x = "250";
    String y = "250";
    
    System.out.println("Enter Drone id: ");
        droneId = input.nextLine();
        
        System.out.println("Enter Name: ");
        droneName = input.nextLine();
        
        drone = new Drone(droneId, droneName, x, y);
        
    try{
	int serverPort=7896;
    
	s = new Socket(hostName, serverPort);    
	DataInputStream dataIn =new DataInputStream(s.getInputStream());
	DataOutputStream dataOut =new DataOutputStream(s.getOutputStream());
        ObjectOutputStream objectOut = new ObjectOutputStream(dataOut);
        
        
       
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
    
    DroneClient thread = new DroneClient();
    thread.start();
	
    while(true){
        Thread.sleep(2500);
        int xpos = Integer.parseInt(x);
        int ypos = Integer.parseInt(y);
        
        switch (randNum.nextInt(4)) {
            case 0:
                xpos += randNum.nextInt(10);
                ypos += randNum.nextInt(10);
                break;
            case 1:
                xpos -= randNum.nextInt(10);
                ypos += randNum.nextInt(10);
                break;
            case 2: 
                xpos += randNum.nextInt(10);
                ypos -= randNum.nextInt(10);
                break;
            case 3:
                xpos -= randNum.nextInt(10);
                ypos -= randNum.nextInt(10);
                break;
        }

    x = Integer.toString(xpos);
    y = Integer.toString(ypos);

    drone.setX(x);
    drone.setY(y);
    
    if (randNum.nextInt(30) == 1) {
            int fireLev = randNum.nextInt(9) + 1;
            String fireLevel = Integer.toString(fireLev);
            
            Fire fire = new Fire("",x,y,fireLevel);
            
            fires.add(fire);
        }
    }
}
    
    Drone returnDrone(){
        return drone;
    }
    
    public void run(){
        Socket s = null;
        String hostName = "localhost";
        
        while(true){
            try {
                Thread.sleep(10000);
            } catch (InterruptedException ex) {
                Logger.getLogger(DroneClient.class.getName()).log(Level.SEVERE, null, ex);
            }
            try{
            int serverPort=7896;

            s = new Socket(hostName, serverPort);    
            DataInputStream dataIn =new DataInputStream(s.getInputStream());
            DataOutputStream dataOut =new DataOutputStream(s.getOutputStream());
            ObjectOutputStream objectOut = new ObjectOutputStream(dataOut);
            
            drone = returnDrone();
            
            objectOut.writeObject(DroneClient.drone);

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
    
}

    
