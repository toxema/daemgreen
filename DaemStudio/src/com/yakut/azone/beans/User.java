/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.yakut.azone.beans;

import java.io.Serializable;
import javax.persistence.*;

/**
 *
 * @author yakut
 */
@Entity
@Table(name="KULLANICI")
public class User implements Serializable{

    @Id
     @GeneratedValue(strategy= GenerationType.SEQUENCE)
    private int id;
    @Column(length = 20)
    private String name;
    @Column(length = 20)
    private String password;
    @Column(length = 20)
    private String haklar;

    public User() {
        super();
    }

    public String getHaklar() {
        return haklar;
    }

    public void setHaklar(String haklar) {
        this.haklar = haklar;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

          @Override
          public String toString() {
                    return   name  ;
          }
    
}
