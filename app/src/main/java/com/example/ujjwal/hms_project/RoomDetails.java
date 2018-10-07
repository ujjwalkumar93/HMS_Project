package com.example.ujjwal.hms_project;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class RoomDetails extends AppCompatActivity {
 EditText _hotelName,_delux,_semidelux;
 Button btn_done;
 DatabaseHelper mydb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_room_details);
       mydb=new DatabaseHelper(this);
        _hotelName=findViewById(R.id.hname);
        _delux=findViewById(R.id.editText4);
        _semidelux=findViewById(R.id.editText5);
        btn_done=findViewById(R.id.button);


        hotel_reg();
    }
  public void hotel_reg(){
       btn_done.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               String hName,nD,nS;
               String myUser,myPass;
               hName=_hotelName.getText().toString().trim();
               nD=_delux.getText().toString().trim();
               nS=_semidelux.getText().toString().trim();
              if(hName.isEmpty()){
                 _hotelName.setError("pleasr enter hotel name...!!");
                 _hotelName.requestFocus();
                  return;
              }
              else if(nD.isEmpty()){
                  _delux.setError("please enter total number of delux"+"\n"+"else enter 0");
                  _delux.requestFocus();
                  return;
              }
              else if(nS.isEmpty()){
                  _delux.setError("please enter total number of semi-delux"+"\n"+"else enter 0");
                  _delux.requestFocus();
                  return;
              }
              else {
                  User user=new User();
                  Bundle b=getIntent().getExtras();

                  myUser=b.getString("user_key");
                  myPass=b.getString("pass_key");

                  user.setUsername(myUser);
                  user.setPassword(myPass);

                  user.setHotelName(hName);
                  user.setTotalDelux(nD);
                  user.setTotalSemidelux(nS);

                  //code for copy room table
                 // User u=new User();
                 // u.setUsername(myUser);

                //  u.setTotalDelux(nD);
                //  u.setTotalSemidelux(nS);
                  //code for copying data into room_table
                  mydb.insertUser(user);


                  Toast.makeText(getApplicationContext(),"Data Inserted",Toast.LENGTH_LONG).show();

                  Intent i=new Intent(RoomDetails.this,MainActivity.class);
                  startActivity(i);
                  finish();
                  return;
              }

           }
       });
  }


}
