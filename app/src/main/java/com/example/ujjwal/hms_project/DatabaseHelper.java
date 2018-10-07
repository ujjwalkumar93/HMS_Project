package com.example.ujjwal.hms_project;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

public class DatabaseHelper extends SQLiteOpenHelper{
    public static final String DATABASE_NAME="Hotel_Info.db";
    public static final String TABLE_NAME="Hotel_Database_Table";
    public static final String COL_ID="ID";
    public static final String COL_UserName="User_Name";
    public static final String COL_Password="Password";
    public static final String COL_HotelName="Hotel_Name";
    public static final String COL_TotalDelux="Total_Delux";
    public static final String COL_TotalSemidelux="Total_SemiDelux";

//table for guest database
//public static final String DATABASE_NAME2="Guest_Info.db";
    public static final String TABLE_GUEST="Guest_Database_Table";
    public static final String COL_ENTRY="ENTRY";
    public static final String COL_HostId="Host_Id";
    public static final String COL_GuestName="Guest_Name";
    public static final String COL_Mob="Mob";
    public static final String COL_IdType="ID_TYPE";
    public static final String COL_IdNum="ID_NUM";
    public static final String COL_RoomType="ROOM_TYPE";
    public static final String COL_RoomNum="ROOM_NUM";
    public static final String COL_CheckIn="CheckIn_Date";
    public static final String COL_CheckOut="CheckOut_Date";
    public static final String COL_Price="hotel_price";
    public static final String COL_STATUS="status";




    public DatabaseHelper(Context context) {
        super(context,DATABASE_NAME, null, 1);
        SQLiteDatabase db=this.getWritableDatabase();
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE "+ TABLE_NAME+"(ID INTEGER PRIMARY KEY not null,User_Name TEXT,Password TEXT,Hotel_Name TEXT,Total_Delux TEXT,Total_SemiDelux TEXT)");

        db.execSQL("CREATE TABLE "+ TABLE_GUEST+"(ENTRY INTEGER PRIMARY KEY NOT NULL,Host_Id TEXT,Guest_Name TEXT," +
                "Mob TEXT,ID_TYPE TEXT,ID_NUM TEXT,ROOM_TYPE TEXT,ROOM_NUM TEXT,CheckIn_Date TEXT,CheckOut_Date TEXT,hotel_price TEXT,status TEXT)");


    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        db.execSQL("DROP TABLE IF EXISTS "+TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS "+TABLE_GUEST);

        onCreate(db);
    }
    //code for inserting signup detail
    public void insertUser(User user){
        SQLiteDatabase db=this.getWritableDatabase();
        ContentValues cv=new ContentValues();
        //cursor is used to read the total no of row
        Cursor cursor=db.rawQuery("select * from "+TABLE_NAME,null);
        //count is user to get total no of row. here we use count instead of ID
        int count=cursor.getCount();
        cv.put(COL_ID,count);
        cv.put(COL_UserName,user.getUsername());
        cv.put(COL_Password,user.getPassword());
        cv.put(COL_HotelName,user.getHotelName());
        cv.put(COL_TotalDelux,user.getTotalDelux());
        cv.put(COL_TotalSemidelux,user.getTotalSemidelux());

         db.insert(TABLE_NAME,null,cv);
         db.close();
    }


    //code to check user exists or not
    public String userExists(String _user){
        SQLiteDatabase db=this.getWritableDatabase();
        Cursor cursor=db.rawQuery("select User_Name from "+TABLE_NAME,null);
        String newuser="U.,s/e1r^%4$&*@!,n@a[m/e? a:va'';i+l9a)b(le:";
        String search_user;
        if(cursor.moveToFirst()){
            do{
                search_user=cursor.getString(0);
                if(search_user.equals(_user)){
                    newuser=cursor.getString(0);
                }
            }while (cursor.moveToNext());
        }
        return newuser;
    }
    //code for searching password
    public String searchPass(String _userName){
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery("SELECT User_Name,Password FROM " + TABLE_NAME, null);
        String search_user,search_pass;
        search_pass="N@#$%^Y$@!*ot)(*DS fo)j(((_+u#@(>nK<d{:";
        if (cursor.moveToFirst())
        {
            do {
                search_user=cursor.getString(0);
                if(search_user.equals(_userName))
                {
                    search_pass= cursor.getString(1);
                }
            } while (cursor.moveToNext());
        }
        return search_pass;
    }
    //code to change password
    public void changepassword(String user,String pass){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv=new ContentValues();
        cv.put(COL_Password,pass);
        db.update(TABLE_NAME,cv,"User_Name=?",new String[] {user});
    }
    public void updateHotel(String host,String hotel,String del,String semi){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv=new ContentValues();
        cv.put(COL_HotelName,hotel);
        cv.put(COL_TotalDelux,del);
        cv.put(COL_TotalSemidelux,semi);
        db.update(TABLE_NAME,cv,"User_Name=?",new String[] {host});

    }


    //code to get hotel name from database to show on add_show page
    public String hotelName(String _user){
        SQLiteDatabase db=this.getWritableDatabase();
        Cursor cursor=db.rawQuery("select User_Name,Hotel_Name from "+TABLE_NAME,null);
        String search_user,hotel="My Hotel";
        if(cursor.moveToFirst()){
            do{
                search_user=cursor.getString(0);
                if(search_user.equals(_user)){
                    hotel=cursor.getString(1);
                }
            }while (cursor.moveToNext());
        }
        return hotel;
    }

    //code for getting total delux hotel

    public String getTotalDelux(String user){
        SQLiteDatabase db=this.getWritableDatabase();
        Cursor cursor=db.rawQuery("select User_Name,Total_Delux from "+TABLE_NAME,null);
        String search_user,delux="Not Found:";
        if(cursor.moveToFirst()){
            do{
                search_user=cursor.getString(0);
                if(search_user.equals(user)){
                    delux=cursor.getString(1);
                }
            }while (cursor.moveToNext());
        }
        return delux;
    }

    //code for getting total semi-delux hotel

    public String getTotalSemi(String user){
        SQLiteDatabase db=this.getWritableDatabase();
        Cursor cursor=db.rawQuery("select User_Name,Total_SemiDelux from "+TABLE_NAME,null);
        String search_user,semidelux="Not Found:";
        if(cursor.moveToFirst()){
            do{
                search_user=cursor.getString(0);
                if(search_user.equals(user)){
                    semidelux=cursor.getString(1);
                }
            }while (cursor.moveToNext());
        }
        return semidelux;
    }

    //code for inserting checkin details
    public void checkIn(User user,String status){
        SQLiteDatabase db=this.getWritableDatabase();
        ContentValues cv=new ContentValues();
        //cursor is used to read the total no of row
        Cursor cursor=db.rawQuery("select * from "+TABLE_GUEST,null);
        //count is user to get total no of row. here we use count instead of ID
        int count=cursor.getCount();
        cv.put(COL_ENTRY,count);
        cv.put(COL_HostId,user.getHostId());
        cv.put(COL_GuestName,user.getGuestName());
        cv.put(COL_Mob,user.getMob());
        cv.put(COL_IdType,user.getIdType());
        cv.put(COL_IdNum,user.getIdNun());
        cv.put(COL_RoomType,user.getRoomType());
        cv.put(COL_RoomNum,user.getRoomNum());
        cv.put(COL_CheckIn,user.getCheckIn());
        cv.put(COL_CheckOut,user.getCheckOut());
        cv.put(COL_Price,user.getHprice());
        cv.put(COL_STATUS,status);
        db.insert(TABLE_GUEST,null, cv);
        db.close();
    }

    //code for edit guest

    public void editguest(User user,String oldid,String status){

        SQLiteDatabase db=this.getWritableDatabase();
        ContentValues cv=new ContentValues();
        cv.put(COL_GuestName,user.getGuestName());
        cv.put(COL_Mob,user.getMob());
        cv.put(COL_IdType,user.getIdType());
        cv.put(COL_IdNum,user.getIdNun());
        cv.put(COL_RoomType,user.getRoomType());
        cv.put(COL_RoomNum,user.getRoomNum());
        cv.put(COL_CheckIn,user.getCheckIn());
        cv.put(COL_CheckOut,user.getCheckOut());
        cv.put(COL_Price,user.getHprice());
        cv.put(COL_STATUS,status);

        db.update(TABLE_GUEST,cv,"( Host_Id= ? AND ID_NUM= ? AND status= ? )", new String[] {user.getHostId(),oldid,status});


    }


//code for update status
 public void sysStatus(String host,String id,String status,String new_status){
     SQLiteDatabase db=this.getWritableDatabase();
     ContentValues cv=new ContentValues();
     cv.put(COL_STATUS,new_status);
     db.update(TABLE_GUEST,cv,"( Host_Id= ? AND ID_NUM= ? AND status= ? )", new String[] {host,id,status});
 }


    //code for get guest detail
  public Cursor readGuest(String user,String status) {
      SQLiteDatabase db = this.getWritableDatabase();

          Cursor cursor = db.rawQuery("select * from Guest_Database_Table where Host_Id = ? AND status=? ", new String[]{user,status});

          return cursor;

  }
  //code for todays checkout list
 public Cursor Todayscheckout(String host,String date,String status){
      SQLiteDatabase db = this.getWritableDatabase();
      Cursor cursor = db.rawQuery("SELECT * FROM Guest_Database_Table WHERE (Host_Id=? AND CheckOut_Date = ? AND status=? )", new String[]{host,date,status});
      return cursor;
  }


    //code for geting details on textview for checkout
    public Cursor checkMeOut(String host,String id,String status){
        SQLiteDatabase db = this.getWritableDatabase();

        Cursor cursor = db.rawQuery("SELECT * FROM Guest_Database_Table WHERE (Host_Id=? AND ID_NUM = ? AND status=? )", new String[]{host,id,status});

        return cursor;
    }

    //code for checkout process
    public boolean checkoutProceed(String host,String id,String status) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv=new ContentValues();
        cv.put(COL_STATUS,status);
        db.update(TABLE_GUEST,cv,"(Host_Id=? AND ID_NUM=? )",new String[] {host,id});
        return true;
    }


    // code to read old guest history
    public Cursor readHistory(String host,String status){
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor=db.rawQuery("SELECT * FROM Guest_Database_Table WHERE Host_Id=? AND  status=?", new String[]{host,status});
       return cursor;
    }
    //check room is already booked or not
    public String isRoomBooked(String host,String roomType,String status,String roomNumber){
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM Guest_Database_Table WHERE Host_Id=? AND ROOM_TYPE=? AND status=? ",new String[]{host,roomType,status});
        String newRoom="Room available:";
        String search_Room;
        if(cursor.moveToFirst()){
            do{
                search_Room=cursor.getString(7);
                if(search_Room.equals(roomNumber)){
                    newRoom=cursor.getString(7);
                }
            }while (cursor.moveToNext());
        }
        return newRoom;
    }
  //check id is already registered or not
    public String isIdRegistered(String host,String idType,String status,String idNumber){
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM Guest_Database_Table WHERE Host_Id=? AND ID_TYPE=? AND status=? ",new String[]{host,idType,status});
        String newId="id available:";
        String search_id;
        if(cursor.moveToFirst()){
            do{
                search_id=cursor.getString(5);
                if(search_id.equals(idNumber)){
                    newId=cursor.getString(5);
                }
            }while (cursor.moveToNext());
        }
        return newId;
    }

    public int getoccupiedRoom(String h,String r,String s){
        SQLiteDatabase db = this.getReadableDatabase();
        int count = (int) DatabaseUtils.queryNumEntries(db, TABLE_GUEST,"Host_Id=? AND ROOM_TYPE=? AND status=?",new String[] {h,r,s});
        db.close();
        return count;
    }

}








