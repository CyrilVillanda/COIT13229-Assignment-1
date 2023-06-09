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
import java.io.ObjectInputStream;
import static java.lang.Integer.parseInt;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

/**
 *
 * @author cjvil
 */


public class DroneServer extends Thread implements ActionListener{
    
    protected static ArrayList<Drone> drones = new ArrayList<>();
    protected static ArrayList<Fire> fires = new ArrayList<>();
    public static DisplayObjectsOnBackground mapPanel;
    
    public static void main (String args[]) {
        
        // Create map panel components
        mapPanel = new DisplayObjectsOnBackground();
        //mapPanel.setBackground(Color.red); // See panel boundries
        mapPanel.setBounds(200, 50, 500, 500);
        mapPanel.setLayout(null);
        
        // Create side panel components
        JPanel sidePanel = new JPanel();
        //sidePanel.setBackground(Color.blue); // See panel boundries
        sidePanel.setBounds(0, 50, 200, 500);
        sidePanel.setLayout(null);
        
        // Create title lable components
        JLabel titleLabel = new JLabel();
        titleLabel.setText("Drone Application");
        titleLabel.setVerticalAlignment(JLabel.TOP);
        titleLabel.setHorizontalAlignment(JLabel.CENTER);
        titleLabel.setBounds(0, 0, 100, 50);
        
        // Create x button components
        JButton deleteFireButton = new JButton();
        deleteFireButton.setBounds(0, 10, 200, 20);
        deleteFireButton.setText("Delete Fire");
        
        // Create x button components
        JButton recallDroneButton = new JButton();
        recallDroneButton.setBounds(0, 40, 200, 20);
        recallDroneButton.setText("Recall Drone");
        
        // Create x button components
        JButton moveDroneButton = new JButton();
        moveDroneButton.setBounds(0, 70, 200, 20);
        moveDroneButton.setText("Move Drone");
        
        // Create x button components
        JButton exitButton = new JButton();
        exitButton.setBounds(0, 100, 200, 20);
        exitButton.setText("Save & Exit");
        exitButton.addActionListener(e -> writeFile());
        
        // Create x button components
        JTextField droneDetails = new JTextField();
        droneDetails.setBounds(0, 130, 200, 370);
        
        // Create JFrame components
        JFrame frame = new JFrame("Drone App");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(null);
        frame.setSize(750,600);
        frame.add(titleLabel);
        sidePanel.add(deleteFireButton);
        sidePanel.add(recallDroneButton);
        sidePanel.add(moveDroneButton);
        sidePanel.add(exitButton);
        sidePanel.add(droneDetails);
        frame.add(sidePanel);
        frame.add(mapPanel);
        frame.setVisible(true);
        
        // reads the drone binary file and fire CSV file
        readFile();
        // creates and starts thread
        DroneServer thread = new DroneServer();
        thread.start();
        
        // Opens server for connections
        try{
            int serverPort=7896; 
            ServerSocket listenSocket=new ServerSocket(serverPort);
            while(true) {
                // Accept client socket
                Socket clientSocket=listenSocket.accept();
            
                // Creates connection
                Connection c = new Connection(clientSocket);
                System.out.printf("\nServer waiting on: %d for client from %d ",
                    listenSocket.getLocalPort(), clientSocket.getPort() );
            }
        }
        catch(IOException e){
            System.out.println("Listen :"+e.getMessage());
        }
    }
    // Thread to update map panel every 10 sseconds
    public void run(){
        while (true) {
            try {
                mapPanel.repaint();
                Thread.sleep(10000);
            } catch (InterruptedException ex) {
                Logger.getLogger(DroneServer.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
    
    // Read file method
    public static void readFile(){
        String binFile = "drone.bin";
        String csvFile = "fire.bin";
        String line;
        
        try {
            // create input  streams
            //BufferedReader br = new BufferedReader(new FileReader(csvFile));
            
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
            
            /*
            // Read data from CSV file and add to arraylist
            while ((line = br.readLine()) != null) {
                String[] data = line.split(","); // split CSV data using comma delimiter

                String fireId = data[0];
                String x = data[1];
                String y = data[2];
                String fireLevel = data[3];
                
                Fire f = new Fire(fireId,x,y,fireLevel);
                fires.add(f);
            }
            */
            
            // Print the data to the console
            System.out.println(drones);
            //System.out.println(fires);
            
            fileInput.close();
            dataOutput.close();
            //br.close();

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
            
            // Exits the application 
            System.exit(0);
            
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    // Add drone method
    public void addDrone(Drone drone){
        drones.add(drone);
    }

    @Override
    public void actionPerformed(ActionEvent arg0) {
        
    }
}

// Class for map panel components
class DisplayObjectsOnBackground extends JPanel {
    
    public DisplayObjectsOnBackground() {

        setBackground(Color.WHITE);
        setLayout(null);
        setBounds(200, 50, 500, 500);
    }
    
    // Graphics for GUI components
    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        
        // Adds drones to coordinate Map from arraylist
        for (Drone d : DroneServer.drones) {

            int x = (parseInt(d.getX()));
            int y = (parseInt(d.getY()) - 500) * -1;

            g.setColor(Color.BLUE);
            g.fillRect(x, y, 50, 25);
            g.setColor(Color.WHITE);
            g.setFont(new Font("Arial", Font.PLAIN, 10));
            g.drawString(d.getDroneName(), x + 5, y + 10);
        }
        
        // Adds fire to coordinate Map from arraylist
        for (Fire f : DroneServer.fires) {

            int x = (parseInt(f.getX()));
            int y = (parseInt(f.getY()) - 500) * -1;

            g.setColor(Color.RED);
            g.fillOval(x, y, 50, 50);
            g.setColor(Color.WHITE);
            g.setFont(new Font("Arial", Font.PLAIN, 20));
            g.drawString(f.getFireId(), x + 5, y + 10);  
        }
    }
}

// Connection Thread
class Connection extends Thread {
    DataInputStream in;
    DataOutputStream out;
    ObjectInputStream objectIn;
    
    Socket clientSocket;
    
    // Connection method
    public Connection (Socket aClientSocket) {
        try {
            clientSocket = aClientSocket;
            in=new DataInputStream(
                    clientSocket.getInputStream());
            out=new DataOutputStream(
                    clientSocket.getOutputStream());
            objectIn = new ObjectInputStream(
                    clientSocket.getInputStream());
            this.start();
        } catch(IOException e){
            System.out.println("Connection:" +e.getMessage());
        }
    }
    @Override
    public void run(){
        try { // an echo server
            // Checks for duplicate drone id
            Drone tempD = (Drone)objectIn.readObject();
            
            boolean newDrone = true;
            
            for (Drone d : DroneServer.drones) {
                
                if (d.getDroneId().equals(tempD.getDroneId())) {
                
                d.setDroneName(tempD.getDroneName());
                d.setX(tempD.getX());            
                d.setY(tempD.getY());
                
                newDrone = false;
                }
            }
            // new drone is added to arraylist
            if (newDrone) {
            DroneServer.drones.add(tempD);
            }
            // output if drone succsessfully added
            out.writeUTF("Drone Added Successfully");
        
        }catch(EOFException e) {
            System.out.println("EOF:"+e.getMessage());
        }catch(IOException e){
            System.out.println("IO:"+e.getMessage());
        }catch (ClassNotFoundException ex) {
            Logger.getLogger(Connection.class.getName()).log(Level.SEVERE, null, ex);
        }
	
        // closes socket after successful connection
	finally {
	   try {clientSocket.close();
           }
	    catch(IOException e){/*close failed*/
           }
        }
    }
}
