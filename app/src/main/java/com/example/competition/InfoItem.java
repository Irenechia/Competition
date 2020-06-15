package com.example.competition;

public class InfoItem {
    private int id;
    private String info;
    private String herf;

    public String getHerf() {
        return herf;
    }

    public void setHerf(String herf) {
        this.herf = herf;
    }

    public InfoItem(String info,String herf) {
        super();
        this.info = info;
        this.herf = herf;
    }

    public InfoItem(){
        super();
        this.info = "";
        this.herf = "";
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }
}
