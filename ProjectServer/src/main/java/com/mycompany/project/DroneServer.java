/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.project;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.EOFException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

/**
 *
 * @author cjvil
 */


public class DroneServer {
    
    public static void main (String args[]) {

        JPanel mapPanel = new JPanel();
        mapPanel.setBackground(Color.red);
        mapPanel.setBounds(200, 50, 500, 500);

        JFrame frame = new JFrame();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(700,550);
        frame.setVisible(true);

    try{
      int serverPort=7896; 
      ServerSocket listenSocket=new ServerSocket(serverPort);
      while(true) {
     Socket clientSocket=listenSocket.accept();
         
         ReadFile rf = new ReadFile();
     Connection c = new Connection(clientSocket);
         
         rf.start();
         
         System.out.printf("\nServer waiting on: %d for client from %d ",
                 listenSocket.getLocalPort(), clientSocket.getPort() );
        }
    }
    catch(IOException e){
    System.out.println("Listen :"+e.getMessage());
        }
    }
}


class ReadFile extends Thread{
    @Override
    public void run(){
        String csvFile = "C:\\Users\\cjvil\\OneDrive\\Documents\\NetBeansProjects\\ProjectServer\\drone.csv";
        String line;
        
        try {
            // create input and output streams
            BufferedReader br = new BufferedReader(new FileReader(csvFile));
            //BufferedWriter bw = new BufferedWriter(new FileWriter("C:\\Users\\cjvil\\OneDrive\\Documents\\NetBeansProjects\\ProjectServer\\fires.csv"));

            // read data from input file and write to output file
            while ((line = br.readLine()) != null) {
                String[] data = line.split(","); // split CSV data using comma delimiter

                String droneId = data[0];
                String droneName = data[1];
                
                String output = String.join(",",droneId , droneName); // join CSV data using comma delimiter
                System.out.println("\nDroneId: " +data[0] + ", DroneName: " + data[1]);
                //bw.newLine();
            }

            // close input and output streams
            br.close();
            //bw.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

class Connection extends Thread {
      DataInputStream in;
      DataOutputStream out;
      Socket clientSocket;
      public Connection (Socket aClientSocket) {
        try {
          clientSocket = aClientSocket;
          in=new DataInputStream( 
                    clientSocket.getInputStream());
          out=new DataOutputStream(
                    clientSocket.getOutputStream());
          this.start();
        } catch(IOException e){
           System.out.println("Connection:" +e.getMessage());
          }
      }
      @Override
     public void run(){
        try { // an echo server
           String data = in.readUTF();
           
          out.writeUTF("Server received:"+data);
          System.out.println("Server received:"+data);

        }catch(EOFException e) {
             System.out.println("EOF:"+e.getMessage());
        }
        catch(IOException e){
           System.out.println("IO:"+e.getMessage());
        }
     
    finally {
       try {clientSocket.close();
           }
        catch(IOException e){/*close failed*/
           }
        }
    }
}
