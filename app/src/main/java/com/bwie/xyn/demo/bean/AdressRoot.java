package com.bwie.xyn.demo.bean;

import java.io.Serializable;

/**
 * Created by Administrator on 2016/12/17.
 */
public class AdressRoot  implements Serializable {

    private String pName;
    private String pPhone;
    private String pCity;
    private String pAdress;
    private int id;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public AdressRoot() {
    }

    public AdressRoot(String pName, String pPhone, String pCity, String pAdress) {
        this.pName = pName;
        this.pPhone = pPhone;
        this.pCity = pCity;
        this.pAdress = pAdress;
    }

    public void setpName(String pName) {
        this.pName = pName;
    }

    public void setpPhone(String pPhone) {
        this.pPhone = pPhone;
    }

    public void setpCity(String pCity) {
        this.pCity = pCity;
    }

    public void setpAdress(String pAdress) {
        this.pAdress = pAdress;
    }

    public String getpName() {
        return pName;
    }

    public String getpPhone() {
        return pPhone;
    }

    public String getpCity() {
        return pCity;
    }

    public String getpAdress() {
        return pAdress;
    }
}
