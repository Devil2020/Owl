package com.example.mohammedmorsemorsefcis.owlchat.Models;

import com.firebase.ui.auth.data.model.User;

import java.io.Serializable;

public class Room implements Serializable {

    public String getRoomName() {
        return RoomName;
    }

    public void setRoomName(String roomName) {
        RoomName = roomName;
    }

    public String getRoomOwner() {
        return RoomOwner;
    }

    public void setRoomOwner(String roomOwner) {
        RoomOwner = roomOwner;
    }

    public String getPassword() {
        return Password;
    }

    public void setPassword(String password) {
        Password = password;
    }

    private String RoomName;
    private String RoomOwner;
    private String Password;

    public boolean isRemeberMe() {
        return RemeberMe;
    }

    public void setRemeberMe(boolean remeberMe) {
        RemeberMe = remeberMe;
    }

    private boolean RemeberMe;
}
