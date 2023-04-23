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
    String fireId;
    String fireLevel;

    public Fire() {
    }

    public Fire(String fireId, String fireLevel) {
        this.fireId = fireId;
        this.fireLevel = fireLevel;
    }

    public String getFireId() {
        return fireId;
    }

    public void setFireId(String fireId) {
        this.fireId = fireId;
    }

    public String getFireLevel() {
        return fireLevel;
    }

    public void setFireLevel(String fireLevel) {
        this.fireLevel = fireLevel;
    }

    @Override
    public String toString() {
        return "Fire" + "fireId = " + fireId + ", fireLevel = " + fireLevel;
    }
    
    
}
