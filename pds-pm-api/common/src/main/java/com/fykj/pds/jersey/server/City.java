package com.fykj.pds.jersey.server;


import javax.ws.rs.FormParam;

/**
 * Created by weijia on 2017/11/20.
 */


public class City {
    @FormParam("id")
    private String id;

    @FormParam("name")
    private String name;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
