/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.project;

import java.io.Serializable;

/**
 *
 * @author cjvil
 */
public class Drone implements Serializable{
    String droneId;
    String droneName;
    String x;
    String y;
    
    public Drone(){
        
    }

    public Drone(String droneId, String droneName, String x, String y) {
        this.droneId = droneId;
        this.droneName = droneName;
        this.x = x;
        this.y = y;
    }

    public String getDroneId() {
        return droneId;
    }

    public void setDroneId(String droneId) {
        this.droneId = droneId;
    }

    public String getDroneName() {
        return droneName;
    }

    public void setDroneName(String droneName) {
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

    @Override
    public String toString() {
        return "Drone: " + " droneId = " + droneId + ", droneName = " + droneName + ", x = " + x + ", y = " + y + "\n";
    }

    
}
