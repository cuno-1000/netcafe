package com.netcafe.common.data;

import java.io.Serializable;

public class GreetParams implements Serializable {

    private static final long serialVersionUID = -2813120366138988480L;

    private String id;
    private int age;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }
}
