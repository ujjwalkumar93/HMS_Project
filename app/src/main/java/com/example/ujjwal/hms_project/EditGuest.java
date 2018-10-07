package com.example.ujjwal.hms_project;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Calendar;

import static android.widget.Toast.*;

public class EditGuest extends AppCompatActivity {
  TextView g,m,idt,rt,ci,c_o,cidate,codate,pri,idn;
  Button cbt;
  EditText gn,mo,idno,rno,pi;
  RadioGroup r1,r2;
  RadioButton idtype1,idtype2,idtype3,roomtype1,roomtype2,myrt,myit;
  String myid,myroom,host,myoldid;
  DatabaseHelper dataB;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_guest);
        dataB=new DatabaseHelper(this);
        g=findViewById(R.id.tg);
        gn=findViewById(R.id.etg);
        m=findViewById(R.id.tm);
        mo=findViewById(R.id.em);
        idt=findViewById(R.id.eidn);
        idn=findViewById(R.id.tid);
        idno=findViewById(R.id.eid);
        rt=findViewById(R.id.trt);
        rno=findViewById(R.id.ern);
        ci=findViewById(R.id.tci);
        c_o=findViewById(R.id.tco);
        cidate=findViewById(R.id.tcid);
        codate=findViewById(R.id.tcod);
        pri=findViewById(R.id.tp);
        pi=findViewById(R.id.ep);
        cbt=findViewById(R.id.bch);
        r1=findViewById(R.id.radioGroup);
        r2=findViewById(R.id.rgroup2);
        idtype1=findViewById(R.id.ra);
        idtype2=findViewById(R.id.rp);
        idtype3=findViewById(R.id.rv);
        roomtype1=findViewById(R.id.radioButton9);
        roomtype2=findViewById(R.id.radioButton10);



        getData();
        cinmsg();
        cal();
        edit();



    }

    public void getData(){
        Bundle b=getIntent().getExtras();
               host=b.getString("myhost");
        String n=b.getString("myguset");
        String m=b.getString("mymob");
        String it=b.getString("myitype");
        myoldid=b.getString("myinum");
        String rt=b.getString("myrtype");
        String rn=b.getString("myrnum");
        String ci=b.getString("mycheckin");
        String co=b.getString("mycheckout");
        String p=b.getString("myprice");
        gn.setText(n);
        mo.setText(m);
        idno.setText(myoldid);
        rno.setText(rn);
        cidate.setText(ci);
        codate.setText(co);
        pi.setText(p);



        if(it.equals("Aadhar")){
            idtype1.setChecked(true);
        }
       else if(it.equals("PAN")){
            idtype2.setChecked(true);
        }
        else if(it.equals("Voter id")){
            idtype3.setChecked(true);
        }
        if(rt.equals("Delux")){
         roomtype1.setChecked(true);
        }
        else if(rt.equals("Semi-Delux")){
            roomtype2.setChecked(true);
        }

    }
    //code for calender
    public void cal(){
        codate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                codate.setError(null);
                Calendar mcurrentDate=Calendar.getInstance();
                int mYear=mcurrentDate.get(Calendar.YEAR);
                int mMonth=mcurrentDate.get(Calendar.MONTH);
                int mDay=mcurrentDate.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog mDatePicker=new DatePickerDialog(EditGuest.this, new DatePickerDialog.OnDateSetListener() {
                    public void onDateSet(DatePicker datepicker, int selectedyear, int selectedmonth, int selectedday) {
                        // TODO Auto-generated method stub
                        String dateString = String.format("%02d/%02d/%d", selectedday, selectedmonth,selectedyear);
                        codate.setText(dateString);
                        /*      Your code   to get date and time    */
                    }
                },mYear, mMonth, mDay);

                mDatePicker.getDatePicker().setMinDate(System.currentTimeMillis() - 1000);
                mDatePicker.show();

            }
        });
    }
//code to print u can not change checkin date
    public void cinmsg(){
        cidate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                makeText(getApplicationContext(),"You can not change checkin date: ", LENGTH_LONG).show();
            }
        });
    }

    //code to edit
    public  void edit(){
       cbt.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               String name,mob,idn,ron,pric,in,out;
               name=gn.getText().toString();
               mob=mo.getText().toString();
               idn=idno.getText().toString();
               ron=rno.getText().toString();
               in=cidate.getText().toString();
               out=codate.getText().toString();
               pric=pi.getText().toString();
               if(name.isEmpty()){
                   gn.setError("please fill guest name...!!");
                   gn.requestFocus();
                   return;
               }
               else if(mob.isEmpty()){
                   mo.setError("please fill mobile number...!!");
                   mo.requestFocus();
                   return;
               }
               else if(mob.length()!=10){
                   mo.setError("Mobile number must be of 10-digit excluding (+91)...!!");
                   mo.requestFocus();
                   return;
               }

               int selectedr=r2.getCheckedRadioButtonId();
               myrt=findViewById(selectedr);
               if(selectedr==-1){
                   myroom="";
               }
               else {
                   myroom=myrt.getText().toString();
               }


               int selectedId=r1.getCheckedRadioButtonId();
               myit=findViewById(selectedId);
               if(selectedId==-1){
                   myid="";
               }
               else {
                   myid=myit.getText().toString();
               }
               if(idn.isEmpty()){
                   idno.setError("please fill id number...!!");
                   idno.requestFocus();
                   return;
               }



               else if(myid.equals("Aadhar")&& idn.length()!=12){
                   idno.setError("Aadhar number must be of 12 character...!!");
                   idno.requestFocus();
                   return;
               }
               else if(myid.equals("PAN")&& idn.length()!=10){
                   idno.setError("PAN number must be of 10 character...!!");
                   idno.requestFocus();
                   return;
               }
               else if(myid.equals("Voter id")&& idn.length()!=11){
                   idno.setError("Voter id number must be of 11 character...!!");
                   idno.requestFocus();
                   return;
               }
               else if( ron.isEmpty()){
                   rno.setError("please fill room number...!!");
                   rno.requestFocus();
                   return;
               }
               else if(pric.isEmpty()){
                   pi.setError("please fill price...!!");
                   pi.requestFocus();
                   return;
               }
               else if(in.equals(out)){
                   Toast.makeText(getApplicationContext(),"You must stay atleast one day",Toast.LENGTH_LONG).show();
                   return;
               }
               String status="checked-In";
               String availRoom=dataB.isRoomBooked(host,myroom,status,ron);
               if(availRoom.equals(ron)){
                   rno.setError("this room is already booked by other guest...!!");
                   rno.requestFocus();
                   return;
               }
               String availId=dataB.isIdRegistered(host,myid,status,idn);
               if(availId.equals(idn)){
                   idno.setError("this id is already registered by other guest...!!");
                   idno.requestFocus();
                   return;
               }

               String r="Delux",s="checked-In";
               int a=dataB. getoccupiedRoom(host,r,s);

               String total=dataB.getTotalDelux(host);
               int intTotal=Integer.parseInt(total);
               int remainD=intTotal-a;

               //
               String r1="Semi-Delux",s1="checked-In";
               int a1=dataB. getoccupiedRoom(host,r1,s1);
               String total1=dataB.getTotalSemi(host);
               int inttotal=Integer.parseInt(total1);
               int remainS=inttotal-a1;

               if(myroom.equals(r) && remainD<=0||myroom.equals(r1) && remainS<=0){
                   rno.setError(myroom+" is already full...!!");
                   rno.requestFocus();
                  // String st1="x",new_status1="checked-In";
                  // dataB.sysStatus(host,idn,st1,new_status1);
                   return;
               }




               else
               {
                   String st="x";

                  //dataB.editguest(host,name,mob,myid,idn,myroom,ron,in,out,pric,st);

                   User user = new User();
                   user.setHostId(host);
                   user.setGuestName(name);
                   user.setMob(mob);
                   user.setIdType(myid);
                   user.setIdNun(idn);
                   user.setRoomType(myroom);
                   user.setRoomNum(ron);
                   user.setCheckIn(in);
                   user.setCheckOut(out);
                   user.setHprice(pric);
                  dataB.editguest(user,myoldid,st);



                      String st1="x",new_status="checked-In";
                      dataB.sysStatus(host,idn,st1,new_status);
                      //dataB.close();
                   Toast.makeText(getApplicationContext(), "Data edited", Toast.LENGTH_LONG).show();



               }
              finish();


           }
       });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        String st1="x",new_status="checked-In";
        dataB.sysStatus(host,myoldid,st1,new_status);
    }
}
