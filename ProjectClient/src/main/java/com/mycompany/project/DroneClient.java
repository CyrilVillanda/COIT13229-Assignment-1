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
    String x = "350";
    String y = "350";
    
    System.out.println("Enter Drone id: ");
    droneId = input.nextLine();

    System.out.println("Enter Name: ");
    droneName = input.nextLine();

    drone = new Drone(droneId, droneName, x, y);
        
    try{
        //Server port
	int serverPort=7896;
        // Creating socket 
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
    
    // creates and starts a new thread
    DroneClient thread = new DroneClient();
    thread.start();
    
    // Loop to randomly set the drones x and y positions
    while(true){
        Thread.sleep(2500);
        // converts the x and y string to integer
        int xpos = Integer.parseInt(x);
        int ypos = Integer.parseInt(y);
        
        // Switch case to randomly select x and y positions
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
        
    // converts the x and y integer back to string
    x = Integer.toString(xpos);
    y = Integer.toString(ypos);
    
    // sets the drone x and y positions
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
    // return drone method
    Drone returnDrone(){
        return drone;
    }
    // Thread to reconnect to server every 10 seconds
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

    
