package org.example;

import java.util.HashMap;

public class AutorisationManager {
    HashMap<String, String> userdata = new HashMap<>();

    public AutorisationManager(){
    }
    public void adduser(User user){
        userdata.put(user.getLogin(), user.getPassword());
    }

    public boolean ispasswordcorrect(User user){
        if(userdata.get(user.getLogin()).equals(user.getPassword())) {
            return true;
        }
        else {
            return false;
        }
    }
}
