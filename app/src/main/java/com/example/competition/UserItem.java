package com.example.competition;

public class UserItem {
    private int id;
    private String username;
    private String password;
    private String TBNAME;

    public  UserItem(String username,String password,String TBNAME){
        super();
        this.username = username;
        this.password = password;
        this.TBNAME = TBNAME;
    }

    public  UserItem(){
        super();
        this.username = "";
        this.password = "";
        this.TBNAME = "";
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getTBNAME() {
        return TBNAME;
    }

    public void setTBNAME(String TBNAME) {
        this.TBNAME = TBNAME;
    }
}
