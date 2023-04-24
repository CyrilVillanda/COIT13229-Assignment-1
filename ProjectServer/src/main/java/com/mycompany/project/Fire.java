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
public class Fire {
    
    // initialise variables
    String fireId;
    String x;
    String y;
    String fireLevel;
    
    // default constructor
    public Fire() {
        
    }
    
    // parameterised constructor
    public Fire(String fireId, String x, String y, String fireLevel) {
        this.fireId = fireId;
        this.x = x;
        this.y = y;
        this.fireLevel = fireLevel;
    }
    
    // getters and setters
    public String getFireId() {
        return fireId;
    }

    public void setFireId(String fireId) {
        this.fireId = fireId;
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

    public String getFireLevel() {
        return fireLevel;
    }

    public void setFireLevel(String fireLevel) {
        this.fireLevel = fireLevel;
    }
    
    // toString
    @Override
    public String toString() {
        return "Fire{" + "fireId=" + fireId + ", x=" + x + ", y=" + y + ", fireLevel=" + fireLevel + '}';
    }
    
}
