package com.example.ujjwal.hms_project;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class CheckOutProcess extends AppCompatActivity {
   TextView etName,gname,etMob,gmob,etIdType,gidType,etidNum,gidNum,etRoomType,groomType,etRoomNum,groomNum,etCheckIn,gcheckin,etCheckOut,gcheckOut,etPrice,gprice;
   //CheckBox cb;
   Button checkOutBtn;
   DatabaseHelper myDb;
   Add_Show a;
 String id,host;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_check_out_process);
        myDb=new DatabaseHelper(this);
        etName=findViewById(R.id.idname);
        gname=findViewById(R.id.idgname);
        etMob=findViewById(R.id.idmobile);
        gmob=findViewById(R.id.idgmobile);
        etIdType=findViewById(R.id.ididtype);
        gidType=findViewById(R.id.idIdname);
        etidNum=findViewById(R.id.ididnumber);
        gidNum=findViewById(R.id.idgidnumber);
        etRoomType=findViewById(R.id.idroomtype);
        groomType=findViewById(R.id.idgrommtype);
        etRoomNum=findViewById(R.id.idroomnumber);
        groomNum=findViewById(R.id.idgroomnumber);
        etCheckIn=findViewById(R.id.idcheckin);
        gcheckin=findViewById(R.id.idgcheckin);
        etCheckOut=findViewById(R.id.idcheckout);
        gcheckOut=findViewById(R.id.idgcheckout);
        etPrice=findViewById(R.id.textView2);
        gprice=findViewById(R.id.idgprice);

        // cb=findViewById(R.id.id_checkBox);
        checkOutBtn=findViewById(R.id.id_btncheckOut);
        callingCheckOut();
        dialog();

    }
    public void dialog(){

        checkOutBtn.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               final AlertDialog.Builder builder = new AlertDialog.Builder(CheckOutProcess.this);
               builder.setTitle("Checkout");
               builder.setMessage("Are you sure to checkout?");
               builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                   @Override
                   public void onClick(DialogInterface dialog, int which) {
                       checkOut();
                   }
               });
               builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                   @Override
                   public void onClick(DialogInterface dialog, int which) {
                      dialog.cancel();
                   }
               });
               builder.create().show();


           }
       });


    }
    public void callingCheckOut(){
        //String name,mob,idType,idNum,roomType,roomNum,checkin,checkout,price;
        Bundle b=getIntent().getExtras();

        host=b.getString("host");
        id=b.getString("id");
        gname.setText(b.getString("name"));
        gmob.setText(b.getString("mob"));
        gidType.setText(b.getString("idType"));
        gidNum.setText(b.getString("idNum"));
        groomType.setText(b.getString("roomType"));
        groomNum.setText(b.getString("roomNum"));
        gcheckin.setText(b.getString("checkin"));
        gcheckOut.setText(b.getString("checkout"));
       gprice.setText(b.getString("price"));

    }
    //code to send data at change guest

    public void changeGuset(){
        Intent i=new Intent(this,EditGuest.class);
        String egname,egmob,egidType,egidNum,egroomType,egroomNum,egcheckin,egcheckOut,egprice;
        egname=gname.getText().toString();
        egmob=gmob.getText().toString();
        egidType=gidType.getText().toString();
        egidNum=gidNum.getText().toString();
        egroomType=groomType.getText().toString();
        egroomNum=groomNum.getText().toString();
        egcheckin=gcheckin.getText().toString();
        egcheckOut=gcheckOut.getText().toString();
        egprice=gprice.getText().toString();
        i.putExtra("myhost",host);
        i.putExtra("myguset",egname);
        i.putExtra("mymob",egmob);
        i.putExtra("myitype",egidType);
        i.putExtra("myinum",egidNum);
        i.putExtra("myrtype",egroomType);
        i.putExtra("myrnum",egroomNum);
        i.putExtra("mycheckin",egcheckin);
        i.putExtra("mycheckout",egcheckOut);
        i.putExtra("myprice",egprice);
        startActivity(i);
        String new_status="x",status="checked-In";
        myDb.sysStatus(host,egidNum,status,new_status);
        finish();

    }

    public void checkOut(){
        String status="checked-Out";
        boolean checkout= myDb.checkoutProceed(host,id,status);
        if(checkout==true)
        {
            Toast.makeText(getApplicationContext(),"Guest Checked out",Toast.LENGTH_LONG).show();
        }
        else {
            Toast.makeText(getApplicationContext(),"Task not completed: ",Toast.LENGTH_LONG).show();
        }
        //updateRoom();

        finish();

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.option,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id=item.getItemId();
        if(id==R.id.eg);
        //Intent i=new Intent(CheckOutProcess.this,EditGuest.class);
       // startActivity(i);
        changeGuset();
       return true;
    }



    @Override
    protected void onRestart() {
        super.onRestart();
        callingCheckOut();
    }
}


