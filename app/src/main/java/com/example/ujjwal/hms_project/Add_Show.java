package com.example.ujjwal.hms_project;

import android.content.Intent;
import android.database.Cursor;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;


import java.util.Calendar;


public class Add_Show extends AppCompatActivity {

    DatabaseHelper myDb;
   Button add,show,btnSearch,checkc,btn_gHistory;
   TextView sethotel,delux,semi,delDisp,semDisp;
   EditText etsearch;
    String user;
    int remainD,remainS;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_add__show);
        myDb=new DatabaseHelper(this);

        sethotel=findViewById(R.id.id_hotelName);
        add=findViewById(R.id.id_addGuest);
        delux=findViewById(R.id.checkdel);
        semi=findViewById(R.id.checksemi);

        show=findViewById(R.id.id_show);
        checkc=findViewById(R.id.btn_todaych);
        btnSearch=findViewById(R.id.btn_search);
        etsearch=findViewById(R.id.etSearch);
        delDisp=findViewById(R.id.avld);
        semDisp=findViewById(R.id.avlS);
        btn_gHistory=findViewById(R.id.btn_guestHistory);
        Bundle b=getIntent().getExtras();
         user=b.getString("userName");

         CheckOut_date();
        //occupiedDel();
         updatedel();
         updatesemi();
         addHotelTexview();
         proceed_checkout();
         checkinGuest();
         showGuestDetail();
         guestHistory();

    }
    public void  checkinGuest(){
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {



                Intent i=new Intent(Add_Show.this,Dashboard.class);
                i.putExtra("hostId",user);
                i.putExtra("avlD",remainD);
                i.putExtra("avlS",remainS);
                startActivity(i);
            }
        });
    }
   public  void  addHotelTexview(){

        //myDb=new DatabaseHelper(this);


        String stored_hotel=myDb.hotelName(user);
        sethotel.setText(stored_hotel);

    }
    //code for show guest detail
    public void showGuestDetail(){
        show.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String status="checked-In";
       Cursor res=myDb.readGuest(user,status);
       if(res.getCount()==0){
           showMessage("Error","Nothing Found");
           return;
       }
       StringBuilder sb=new StringBuilder();
       while (res.moveToNext()){
           sb.append("Name: "+res.getString(2)+"\n");
           sb.append("Mob: "+res.getString(3)+"\n");
           sb.append("Id Type: "+res.getString(4)+"\n");
           sb.append("Id Num: "+res.getString(5)+"\n");
           sb.append("Room Type: "+res.getString(6)+"\n");
           sb.append("Room Num: "+res.getString(7)+"\n");
           sb.append("CheckIn: "+res.getString(8)+"\n");
           sb.append("CheckOut: "+res.getString(9)+"\n");
           sb.append("Price: "+res.getString(10)+"\n");
           sb.append("\n");sb.append("\n");

       }
       showMessage("Guest List",sb.toString());



            }
        });
    }

    //code for getting system date and make a list for checkout

    public void CheckOut_date(){
        checkc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //code for getting system date
                Calendar c = Calendar.getInstance();
                int day = c.get(Calendar.DAY_OF_MONTH);
                int month = c.get(Calendar.MONTH);
                int newmonth=month+1;
                int year = c.get(Calendar.YEAR);
                String today = String.format("%02d/%02d/%d", day,newmonth,year);


                String status="checked-In";
                Cursor res=myDb.Todayscheckout(user,today,status);
                if(res.getCount()==0){
                    showMessage("Nothing to show","No guest found:");
                    return;
                }

                StringBuilder sb=new StringBuilder();

               while (res.moveToNext()){
                   sb.append("Name: "+res.getString(2)+"\n");
                   sb.append("Mob: "+res.getString(3)+"\n");
                   sb.append("Id Type: "+res.getString(4)+"\n");
                   sb.append("Id Num: "+res.getString(5)+"\n");
                   sb.append("Room Type: "+res.getString(6)+"\n");
                   sb.append("Room Num: "+res.getString(7)+"\n");
                   sb.append("CheckIn: "+res.getString(8)+"\n");
                   sb.append("CheckOut: "+res.getString(9)+"\n");
                   sb.append("Price: "+res.getString(10)+"\n");
                   sb.append("\n");sb.append("\n");
               }
               showMessage("Guest",sb.toString());

            }

        });
    }

//code for dialog builder

    public void showMessage(String title,String message){
        AlertDialog.Builder builder=new AlertDialog.Builder(this);
        builder.setCancelable(true);
        builder.setTitle(title);
        builder.setMessage(message);
        builder.show();

    }
    //code for takin input as guestid and send data to checkoutprocess
    public void proceed_checkout(){
        btnSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {



                String id=etsearch.getText().toString().trim();
                if(id.isEmpty()){
                   Toast.makeText(getApplicationContext(),"Please fill the guest id: ",Toast.LENGTH_LONG).show();

                    return;
                }
                else {

                    Bundle b=getIntent().getExtras();
                    String user=b.getString("userName");
                    String status="checked-In";
                    Cursor res=myDb.checkMeOut(user,id,status);
                    if(res.getCount()==0){
                       Toast.makeText(getApplicationContext(),"No guest found:",Toast.LENGTH_LONG).show();


                        return;
                    }
                    while (res.moveToNext()){
                        String name,mob,idType,idNum,roomType,roomNum,checkin,checkout,price;
                        name=res.getString(2).trim();
                        mob=res.getString(3).trim();
                        idType=res.getString(4).trim();
                        idNum=res.getString(5).trim();
                        roomType=res.getString(6).trim();
                        roomNum=res.getString(7).trim();
                        checkin=res.getString(8).trim();
                        checkout=res.getString(9).trim();
                        price=res.getString(10).trim();

                        Intent i=new Intent(Add_Show.this,CheckOutProcess.class);

                        i.putExtra("host",user);
                        i.putExtra("id",id);
                        i.putExtra("name",name);
                        i.putExtra("mob",mob);
                        i.putExtra("idType",idType);
                        i.putExtra("idNum",idNum);
                        i.putExtra("roomType",roomType);
                        i.putExtra("roomNum",roomNum);
                        i.putExtra("checkin",checkin);
                        i.putExtra("checkout",checkout);
                        i.putExtra("price",price);
                      //sending room data to checkout procedure


                        startActivity(i);
                        etsearch.setText("");
                    }


            }
            }
        });

    }
    //code to get all guest history
    public void  guestHistory(){
       btn_gHistory.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {

                String status="checked-Out";
               Cursor res=myDb.readHistory(user,status);
               if(res.getCount()==0){
                   showMessage("Nothing found","No guest to show");
               }
  else {
                   StringBuilder sb = new StringBuilder();
                   while (res.moveToNext()) {
                       sb.append("Name: " + res.getString(2) + "\n");
                       sb.append("Mob: " + res.getString(3) + "\n");
                       sb.append("Id Type: " + res.getString(4) + "\n");
                       sb.append("Id Num: " + res.getString(5) + "\n");
                       sb.append("Room Type: " + res.getString(6) + "\n");
                       sb.append("Room Num: " + res.getString(7) + "\n");
                       sb.append("CheckIn: " + res.getString(8) + "\n");
                       sb.append("CheckOut: " + res.getString(9) + "\n");
                       sb.append("Price: " + res.getString(10) + "\n");
                       sb.append("\n");
                       sb.append("\n");

                   }
                   showMessage("Guest List", sb.toString());

               }

           }
       });
    }
//code for option menu
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id=item.getItemId();
        if(id==R.id.log){
            Intent i=new Intent(Add_Show.this,MainActivity.class);
            startActivity(i);
            finish();
            Toast.makeText(getApplicationContext(),"You Are Logged Out:",Toast.LENGTH_LONG).show();
            return true;
        }
        if(id==R.id.pchange)
        {
            Intent i=new Intent(Add_Show.this,ChangePassword.class);
            i.putExtra("user",user);
            startActivity(i);

            return true;
        }
        if(id==R.id.pedit)
        {
            Intent i=new Intent(Add_Show.this,EditProfile.class);
            i.putExtra("user",user);
            startActivity(i);

            return true;
        }
       return true;

    }

    //code for display delux on textview
    public void updatedel(){

        String r="Delux",s="checked-In";
        int a=myDb. getoccupiedRoom(user,r,s);

        String total=myDb.getTotalDelux(user);
        int intTotal=Integer.parseInt(total);
        remainD=intTotal-a;
        if(remainD<0){
            delDisp.setText("0");
        }
        else {
        String c=Integer.toString(remainD);
        delDisp.setText(c);}
    }
    public void updatesemi(){
        String r="Semi-Delux",s="checked-In";
        int a=myDb. getoccupiedRoom(user,r,s);
       String total=myDb.getTotalSemi(user);
       int inttotal=Integer.parseInt(total);
        remainS=inttotal-a;
        if(remainS<0){
            semDisp.setText("0");
        }
        else{
       String c=Integer.toString(remainS);
       semDisp.setText(c);}
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        updatedel();
        updatesemi();
        addHotelTexview();
    }
}
