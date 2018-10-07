package com.example.ujjwal.hms_project;

import java.util.Date;

public class User {
    String username,password,hotelName,totalDelux,totalSemidelux,guestName,mob,idType,
            idNun,_roomType,roomNum,hostId,hprice
  ,checkIn,checkOut;
    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setUsername(String username) {
        this.username = username;
    }
    public String getHotelName(){
        return hotelName;
    }
    public void setHotelName(String hotelName){
        this.hotelName=hotelName;
    }
    public String getTotalDelux(){
        return totalDelux;
    }
    public void setTotalDelux(String totalDelux){
        this.totalDelux=totalDelux;
    }
    public String getTotalSemidelux(){
        return totalSemidelux;
    }
    public void setTotalSemidelux(String totalSemidelux){
        this.totalSemidelux=totalSemidelux;
    }
    public String getGuestName(){return guestName;}
    public void  setGuestName(String guestName){this.guestName=guestName;}
    public String getMob(){return mob;}
    public void  setMob(String mob){this.mob=mob;}
    public String getIdType(){return idType;}
    public void  setIdType(String idType){this.idType=idType;}
    public String getIdNun(){return idNun;}
    public void setIdNun(String idNun){this.idNun=idNun;}

    public String getRoomType(){return  _roomType;}
    public void setRoomType(String _roomType){this._roomType=_roomType;}
    public String getRoomNum(){return roomNum;}
    public void setRoomNum(String roomNum){this.roomNum=roomNum;}
    public String getCheckIn(){return  checkIn;}
    public  void setCheckIn(String checkIn){this.checkIn=checkIn;}
    public String getCheckOut(){return checkOut;}
    public void setCheckOut(String checkOut){this.checkOut=checkOut;}
    public void setHostId(String hostId){this.hostId=hostId;}
    public String getHostId(){return hostId;}
    public void setHprice(String hprice){this.hprice=hprice;}
    public String getHprice(){return hprice;}


}
