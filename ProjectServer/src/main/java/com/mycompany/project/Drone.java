/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.project;

/**
 *
 * @author cjvil
 */
public class Drone {
    String droneName;
    String x;
    String y;
    String droneId;
    
    public Drone(){
        
    }

    public Drone(String droneName, String x, String y, String droneId) {
        this.droneName = droneName;
        this.x = x;
        this.y = y;
        this.droneId = droneId;
      
    }
    
    public void addDrone(String fireId, String x, String y, String droneId, String fireSeverity){
        
    }

    public String getdroneName() {
        return droneName;
    }

    public void setFireId(String fireId) {
        this.droneName = droneName;
    }

    public String getX() {
        return x;
    }

    public void setX(String x) {
        this.x = x;
    }

    public String getY() {
        return y;
    }

    public void setY(String y) {
        this.y = y;
    }

    public String getDroneId() {
        return droneId;
    }

    public void setDroneId(String droneId) {
        this.droneId = droneId;
    }

    @Override
    public String toString() {
        return "Drone" + "droneName = " + droneName + ", x = " + x + ", y = " + y + ", droneId = " + droneId;
    }
}
