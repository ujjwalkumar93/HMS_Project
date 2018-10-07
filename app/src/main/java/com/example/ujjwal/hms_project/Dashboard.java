package com.example.ujjwal.hms_project;

import android.app.DatePickerDialog;
import android.app.DatePickerDialog.OnDateSetListener;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class Dashboard extends AppCompatActivity {
    EditText g_name, g_mob, id_num, room_num,etPrice;
    RadioButton id_type, room_type;
    RadioGroup idGroup,roomGroup;
    Button btn_sub;
    TextView checkIn, checkOut,cal1,cal2;
    String myRoom,host,today,roomtype;
    DatabaseHelper myDb;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
       // getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        super.onCreate(savedInstanceState);
        //code to get Textview up while keypad appear
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);
        setContentView(R.layout.activity_dashboard);

        myDb=new DatabaseHelper(this);
        g_name = findViewById(R.id.id_guestName);
        g_mob = findViewById(R.id.id_mob);
        cal1 = findViewById(R.id.id_checkin);
        cal2 = findViewById(R.id.id_checkout);
        id_num = findViewById(R.id.id_idNum);
        room_num = findViewById(R.id.room_num);
        idGroup=findViewById(R.id.radiogroup);
        roomGroup=findViewById(R.id.radiogroup2);
        checkIn=findViewById(R.id.cal1View);
        checkOut=findViewById(R.id.cal2View);
        etPrice=findViewById(R.id.id_price);

        btn_sub = findViewById(R.id.id_btn_submit);
        Bundle b=getIntent().getExtras();
        host=b.getString("hostId");

        checkToast();
        showCheckoutCal();
        submitData();
        systemDate();
        checkIn.setText(today);
    }
  //code for guest details
    public void submitData() {
        btn_sub.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {




                String guest, mob, inum, rnum,price,cin,cout;

                guest = g_name.getText().toString().trim();
                mob = g_mob.getText().toString().trim();
                cin = checkIn.getText().toString().trim();
                cout = checkOut.getText().toString().trim();
                inum = id_num.getText().toString().trim();
                rnum = room_num.getText().toString().trim();
                price=etPrice.getText().toString().trim();





            //code for id type
                String a="dd/mm/yyyy",idtype;
                String aa="Aadhar",pa="PAN",vo="Voter id";

                if(guest.isEmpty()){
                    g_name.setError("please enter guest name...!!");
                    g_name.requestFocus();
                    return;
                }
                else if(mob.isEmpty()){
                    g_mob.setError("please enter mobile number");
                    g_mob.requestFocus();
                    return;
                }
                if(mob.length()<10 || mob.length()>10){
                    g_mob.setError("mobile number must be of 10-digit excluding +91.");
                    g_mob.requestFocus();
                    return;
                }
                int selectedId=idGroup.getCheckedRadioButtonId();
                id_type=findViewById(selectedId);
                if(selectedId==-1){
                    idtype="";


                }
                else {

                    idtype = id_type.getText().toString();
                }

                 if(idtype.isEmpty()){
                    id_num.setError("please select id type first...!!");
                    id_num.requestFocus();
                    return;
                }

                else if(inum.isEmpty()){
                    id_num.setError("please fill id number...!!");
                    id_num.requestFocus();
                    return;
                }

                else if(idtype.equals(aa) && inum.length()!=12){
                    id_num.setError("aadhar card number must be of 12 character...!!");
                    id_num.requestFocus();
                    return;
                }
                else if(idtype.equals(pa) && inum.length()!=10){
                    id_num.setError("PAN card number must be of 10 character...!!");
                    id_num.requestFocus();
                    return;
                }
                else if(idtype.equals(vo) && inum.length()!=11){
                    id_num.setError("Voter id number must be of 11 character...!!");
                    id_num.requestFocus();
                    return;
                }



             //code for room type
                int selectedRoom=roomGroup.getCheckedRadioButtonId();
                room_type=findViewById(selectedRoom);
                if(selectedRoom==-1){
                  roomtype="";

                }
                else  {

                    roomtype = room_type.getText().toString();
                    myRoom=roomtype;

                }

                if(roomtype.isEmpty()){
                   room_num.setError("please select room type first...!!");
                    room_num.requestFocus();
                    return;
                }
                else if(rnum.isEmpty()){
                    room_num.setError("please enter room number...!!");
                    room_num.requestFocus();
                    return;
                }

                if(cout.equals(a)){
                    checkOut.setError("please enter checkout date...!!");
                    checkOut.requestFocus();
                    return;
                }
                else if(cout.equals(today)){
                    checkOut.setError("you must stay atleast one day...!!");
                    checkOut.requestFocus();
                    return;
                }
                if(price.isEmpty()){
                    etPrice.setError("please enter price...!!");
                    etPrice.requestFocus();
                    return;
                }






                String status="checked-In";
                String availRoom=myDb.isRoomBooked(host,roomtype,status,rnum);
                if(availRoom.equals(rnum)){
                   room_num.setError("this room is already booked...!!");
                   room_num.requestFocus();
                    return;
                }
                String availId=myDb.isIdRegistered(host,idtype,status,inum);
                if(availId.equals(inum)){
                    id_num.setError("this id is already registered...!!");
                    id_num.requestFocus();
                    return;
                }




                int del,sem;
                Bundle b=getIntent().getExtras();

                del=b.getInt("avlD");
                sem=b.getInt("avlS");

                String s1="Delux",s2="Semi-Delux";
                if(roomtype.equals(s1) && del<=0){
                   // Toast.makeText(getApplicationContext(),"Delux is already Full: ",Toast.LENGTH_LONG).show();
                    room_num.setError("delux is already full...!!");
                    room_num.requestFocus();
                    return;
                }

               // String stSemi=myDb.getTotalSemi(host);
               // int totalSemi=Integer.parseInt(stSemi);

                if(roomtype.equals(s2) && sem<=0){
                   room_num.setError("semi-delux is already full...!!");
                   room_num.requestFocus();
                    return;
                }

                else {
                    //write code here to insert all the guest details in database

                        User user = new User();
                        user.setHostId(host);
                        user.setGuestName(guest);
                        user.setMob(mob);
                        user.setIdType(idtype);
                        user.setIdNun(inum);
                        user.setRoomType(roomtype);
                        user.setRoomNum(rnum);
                        user.setCheckIn(cin);
                        user.setCheckOut(cout);
                        user.setHprice(price);

                        myDb.checkIn(user, status);

                    myDb.close();


                        Toast.makeText(Dashboard.this, "Guset added", Toast.LENGTH_LONG).show();

                    finish();

                }

            }
        });


    }


  //get checkout date picker
    public void showCheckoutCal() {
        checkOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkOut.setError(null);
                Calendar mcurrentDate=Calendar.getInstance();
                int mYear=mcurrentDate.get(Calendar.YEAR);
                int mMonth=mcurrentDate.get(Calendar.MONTH);
                int mDay=mcurrentDate.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog mDatePicker=new DatePickerDialog(Dashboard.this, new OnDateSetListener() {
                    public void onDateSet(DatePicker datepicker, int selectedyear, int newmonth, int selectedday) {
                        // TODO Auto-generated method stub

                        String dateString = String.format("%02d/%02d/%d", selectedday, newmonth+1,selectedyear);
                        checkOut.setText(dateString);
                       /*      Your code   to get date and time    */
                    }
                },mYear, mMonth, mDay);

                mDatePicker.getDatePicker().setMinDate(System.currentTimeMillis() - 1000);
                mDatePicker.show();

            }
        });
    }

     public void systemDate(){
     //   String newMonth,newDay;
         Calendar c = Calendar.getInstance();
         int day = c.get(Calendar.DAY_OF_MONTH);
         int month = c.get(Calendar.MONTH);
         int newmonth=month+1;
         int year = c.get(Calendar.YEAR);
         today = String.format("%02d/%02d/%d", day, newmonth,year);



     }
    protected void onRestart() {
        super.onRestart();
        checkIn.setText(today);
    }
    public void checkToast(){
        checkIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(),"Check-In date can't be changed:",Toast.LENGTH_LONG).show();
            }
        });
    }



}