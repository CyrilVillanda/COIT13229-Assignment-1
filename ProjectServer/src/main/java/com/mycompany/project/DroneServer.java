/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.project;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.EOFException;
import java.io.FileReader;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

/**
 *
 * @author cjvil
 */
public class DroneServer {
   public static void main (String args[]) {
    
    String csvFile = "C:\\Users\\cjvil\\OneDrive\\Documents\\NetBeansProjects\\ProjectServer\\fires.csv";
    String line = "";
    String csvSeparator = ",";
       
    try{
      int serverPort=7896; 
      ServerSocket listenSocket=new ServerSocket(serverPort);
      while(true) {
	 Socket clientSocket=listenSocket.accept();
	 Connection c = new Connection(clientSocket);
         System.out.printf("\nServer waiting on: %d for client from %d ",
                 listenSocket.getLocalPort(), clientSocket.getPort() );
      }
    } catch(IOException e){
 	System.out.println("Listen :"+e.getMessage());
      }
    try (BufferedReader br = new BufferedReader(new FileReader(csvFile))) {

            while ((line = br.readLine()) != null) {

                // use comma as separator
                String[] data = line.split(csvSeparator);

                // do something with the data
                System.out.println("FireId: " + data[0] + ", X: " + data[1] + ", Y: " + data[2] + ", ReportingDroneId: " + data[3] + ", FireSeverity: " + data[4]);

            }

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
