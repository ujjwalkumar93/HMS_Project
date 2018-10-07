package com.example.ujjwal.hms_project;

import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class EditProfile extends AppCompatActivity {
    EditText hup,dup,sup;
    Button btn;
    DatabaseHelper myDb;
    String myuser,p;
    String h,d,ss;
    TextView ho,de,se,pa;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);
        myDb=new DatabaseHelper(this);
        ho=findViewById(R.id.hotelname);
        de=findViewById(R.id.delid);
        se=findViewById(R.id.semid);
        pa=findViewById(R.id.paaa);
        hup=findViewById(R.id.id_hotelname);
        dup=findViewById(R.id.id_delup);
        sup=findViewById(R.id.id_semiup);
        btn=findViewById(R.id.btnUpdate);
        Bundle b=getIntent().getExtras();
        myuser=b.getString("user").trim();
        String totaldel=myDb.getTotalDelux(myuser);
        String totalsem=myDb.getTotalSemi(myuser);
        String hotel=myDb.hotelName(myuser);
        hup.setText(hotel);
        dup.setText(totaldel);
        sup.setText(totalsem);
        update();
    }
    public void  update(){
        btn.setOnClickListener(new View.OnClickListener() {
            @Override

            public void onClick(View v) {

                h=hup.getText().toString();
                d=dup.getText().toString();
                ss=sup.getText().toString();
                p=pa.getText().toString();



                //code to get total occupies room
                String r="Semi-Delux",s="checked-In";
                int occupSemi=myDb. getoccupiedRoom(myuser,r,s);

                String r1="Delux",s1="checked-In";
                int occupDel=myDb. getoccupiedRoom(myuser,r1,s1);



                if(h.isEmpty()){
                    hup.setError("please fill this field...!!");
                    hup.requestFocus();
                    return;
                }

                if(d.isEmpty()){
                    dup.setError("please fill this field...!!");
                    dup.requestFocus();
                    return;
                }
                if(ss.isEmpty()){
                    sup.setError("please fill this field...!!");
                    sup.requestFocus();
                    return;
                }
                else if(p.isEmpty()){
                    pa.setError("please fill this form...!!");
                    pa.requestFocus();
                }
                if(p.isEmpty()){
                    pa.setError("please fill this field...!!");
                    pa.requestFocus();
                    return;
                }
                String pass=myDb.searchPass(myuser);
                if(!p.equals(pass)){
                    pa.setError("wrong password...!!");
                    pa.requestFocus();
                    return;
                }

                if(p.equals(pass) && Integer.parseInt(ss)<occupSemi || Integer.parseInt(d)<occupDel){
                    String msg="According to new room capacity you have already exceed the booking, it will degrade App performance. it is recomended to checkout guest first.";
                   dialog("Alert:",msg);
                    /////////
                }
                else {
                   // updateDelStatus();
                    updateMyHotel();
                }


                }

        });
    }

    public  void updateMyHotel(){
        myDb.updateHotel(myuser,h,d,ss);
       // Toast.makeText(getApplicationContext(),"Data updated:",Toast.LENGTH_LONG).show();
        finish();
    }

    public void dialog(String title,String message){


                final AlertDialog.Builder builder = new AlertDialog.Builder(EditProfile.this);
                builder.setTitle(title);
                builder.setMessage(message);
                builder.setPositiveButton("Update Anyway", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                       // checkOut();
                        updateMyHotel();
                        Toast.makeText(getApplicationContext(),"Data updated:",Toast.LENGTH_LONG).show();
                    }
                });
                builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                        Toast.makeText(getApplicationContext(),"Data did not update:",Toast.LENGTH_LONG).show();
                    }
                });
                builder.create().show();

    }
}
