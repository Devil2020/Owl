package com.example.mohammedmorsemorsefcis.owlchat.Models;

import java.util.ArrayList;

public class UserData {


    ArrayList<Room> rooms;

    public ArrayList<Room> getRooms() {
        return rooms;
    }

    public void setRooms(ArrayList<Room> rooms) {
        this.rooms = rooms;
    }

    public ArrayList<Message> getChat() {
        return chat;
    }

    public void setChat(ArrayList<Message> chat) {
        this.chat = chat;
    }

    public Person getData() {
        return data;
    }

    public void setData(Person data) {
        this.data = data;
    }

    ArrayList<Message> chat;
    Person data;
}
