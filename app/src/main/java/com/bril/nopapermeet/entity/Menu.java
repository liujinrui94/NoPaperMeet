package com.bril.nopapermeet.entity;

/**
 * Created by 123456 on 2017/3/29.
 */

public class Menu {
    public Menu() {
    }

    public Menu(String name, int icon) {
        this.name = name;
        this.icon = icon;
    }

    public Menu(String name, int icon, Object itemObj) {
        this.name = name;
        this.itemObj = itemObj;
        this.icon = icon;
    }

    public String name;
    public Object itemObj;
    public int icon;
    public boolean isCheck;
}
