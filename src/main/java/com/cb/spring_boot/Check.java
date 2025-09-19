package com.cb.spring_boot;

public class Check {

    private String id;
    private String name;
    private boolean valid;

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
    public void setValid(boolean valid){
        this.valid=valid;
    }
    public boolean getValid(){
        return valid;
    }

}
