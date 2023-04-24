/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.project;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import javax.swing.ImageIcon;
import java.awt.PopupMenu;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.EOFException;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

/**
 *
 * @author cjvil
 */


public class DroneServer implements ActionListener{
    
    protected static ArrayList<Drone> drones = new ArrayList<>();
    
    public static void main (String args[]) {
        
        //JPanel mapPanel = new JPanel();
        DisplayObjectsOnBackground mapPanel = new DisplayObjectsOnBackground();
        //mapPanel.setBackground(Color.red); // See panel boundries
        mapPanel.setBounds(200, 50, 500, 500);
        mapPanel.setLayout(null);
        
        JPanel sidePanel = new JPanel();
        //sidePanel.setBackground(Color.blue); // See panel boundries
        sidePanel.setBounds(0, 50, 200, 500);
        sidePanel.setLayout(null);
        
        JLabel titleLabel = new JLabel();
        titleLabel.setText("Drone Application");
        titleLabel.setVerticalAlignment(JLabel.TOP);
        titleLabel.setHorizontalAlignment(JLabel.CENTER);
        titleLabel.setBounds(0, 0, 100, 50);
        
        JButton deleteFireButton = new JButton();
        deleteFireButton.setBounds(0, 10, 200, 20);
        deleteFireButton.setText("Delete Fire");
        
        JButton recallDroneButton = new JButton();
        recallDroneButton.setBounds(0, 40, 200, 20);
        recallDroneButton.setText("Recall Drone");
        
        JButton moveDroneButton = new JButton();
        moveDroneButton.setBounds(0, 70, 200, 20);
        moveDroneButton.setText("Move Drone");
        
        JButton exitButton = new JButton();
        exitButton.setBounds(0, 100, 200, 20);
        exitButton.setText("Save & Exit");
        
        JTextField droneDetails = new JTextField();
        droneDetails.setBounds(0, 130, 200, 370);
        
        JFrame frame = new JFrame("Drone App");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(null);
        frame.setSize(700,600);
        frame.add(titleLabel);
        sidePanel.add(deleteFireButton);
        sidePanel.add(recallDroneButton);
        sidePanel.add(moveDroneButton);
        sidePanel.add(exitButton);
        sidePanel.add(droneDetails);
        frame.add(sidePanel);
        frame.add(mapPanel);
        frame.setVisible(true);
        
        readFile();
        writeFile();
        
    try{
      int serverPort=7896; 
      ServerSocket listenSocket=new ServerSocket(serverPort);
      while(true) {
	 Socket clientSocket=listenSocket.accept();
         
	 Connection c = new Connection(clientSocket);
         
         System.out.printf("\nServer waiting on: %d for client from %d ",
                 listenSocket.getLocalPort(), clientSocket.getPort() );
        }
    }
    catch(IOException e){
 	System.out.println("Listen :"+e.getMessage());
        }
        
        
    }
    
    public static void readFile(){
        String binFile = "drone.bin";
        String line;
        
        try {
            // create input  streams
            FileInputStream fileInput = new FileInputStream(binFile);
            DataInputStream dataOutput = new DataInputStream(fileInput);

            // Read data from the file and add it to the ArrayList
            while (dataOutput.available() > 0) {
                String droneId = dataOutput.readUTF();
                String droneName = dataOutput.readUTF();
                String x = dataOutput.readUTF();
                String y = dataOutput.readUTF();
                
                Drone d = new Drone(droneId,droneName,x,y);
                drones.add(d);
                
            }

            // Print the data to the console
            System.out.println(drones);
            
            fileInput.close();
            dataOutput.close();

        } catch (FileNotFoundException e) {
            
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    public static void writeFile(){

        try {
            // Create a FileOutputStream for the binary file
            FileOutputStream fileOutput = new FileOutputStream("drone.bin");

            // Create a DataOutputStream to write data to the output stream
            DataOutputStream dataOutput = new DataOutputStream(fileOutput);

            // Write each Person object to the file
            for (Drone drone : drones) {
                dataOutput.writeUTF(drone.getDroneId());
                dataOutput.writeUTF(drone.getDroneName());
                dataOutput.writeUTF(drone.getX());
                dataOutput.writeUTF(drone.getY());
            }

            // Close the output streams
            fileOutput.close();
            dataOutput.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    public void addDrone(String droneId, String droneName){
        drones.add(new Drone(droneId,droneName,x,y));
    }

    @Override
    public void actionPerformed(ActionEvent arg0) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}


class DisplayObjectsOnBackground extends JPanel {
    
    public DisplayObjectsOnBackground() {

        setBackground(Color.WHITE);
        setLayout(null);
        setBounds(200, 50, 500, 500);
    }
    
    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
            
        g.setColor(Color.BLUE);
        g.fillRect(300, 200, 50, 25);
        g.setColor(Color.WHITE);
        g.setFont(new Font("Arial", Font.PLAIN, 10));
        g.drawString("Drone 1", 0, 0);
        
        
        g.setColor(Color.RED);
        g.fillOval(350, 300, 50, 50);
        g.setColor(Color.WHITE);
        g.setFont(new Font("Arial", Font.PLAIN, 20));
        g.drawString("Fire", 355, 330);        
        
       
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
           
            out.writeUTF("Drone Added: "+ data);
            //System.out.println("Server received:"+data);

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
