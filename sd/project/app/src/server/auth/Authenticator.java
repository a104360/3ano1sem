package server.auth;

import java.io.File;

public class Authenticator {
    private File database;
    
    public Authenticator(String filePath){
        this.database = new File(filePath);
    }

    public boolean registaConta(String user,String pass){
        
        return false;
    }
}