package com.example.mohammedmorsemorsefcis.owlchat.RoomActivityPackage;

import com.example.mohammedmorsemorsefcis.owlchat.Models.Room;

public interface RoomInterface {
     void onCreateRoomClicked();
     void onRoomClicked(Room room);
     void onRoomLoginResult(boolean b);

}
