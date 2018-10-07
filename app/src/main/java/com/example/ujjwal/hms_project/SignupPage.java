package com.example.ujjwal.hms_project;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class SignupPage extends AppCompatActivity {
   EditText user,pass,conPass;
   Button btn_next;
   DatabaseHelper myDb;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup_page);
        myDb=new DatabaseHelper(this);
        user=findViewById(R.id.id_user);
        pass=findViewById(R.id.editText2);
        conPass=findViewById(R.id.editText3);
        btn_next=findViewById(R.id.btnN);

       btn_next.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               String mUser,mPass,mConPass;
               mUser=user.getText().toString().trim();
               mPass=pass.getText().toString().trim();
               mConPass=conPass.getText().toString().trim();
               if(mUser.isEmpty()){
                   user.setError("please fill this field...!!");
                   user.requestFocus();
                   return;
               }
               else if(mUser.length()<3){
                   user.setError("username must be atleast 3-digit...!!");
                   user.requestFocus();
                   return;
               }
                if(mPass.isEmpty()){
                   pass.setError("please fill this field...!!");
                   pass.requestFocus();
                   return;
               }
              else if(mPass.length()<4){
                   pass.setError("password must be atleast 4-digit...!!");
                   pass.requestFocus();
                   return;
               }
                if(mConPass.isEmpty()){
                   conPass.setError("please fill this field...!!");
                   conPass.requestFocus();
                   return;

               }
             else if(!mPass.equals(mConPass))
               {
                   conPass.setError("password not matched...!!");
                   conPass.requestFocus();
                   return;
               }
               String userAlreadyExists=myDb.userExists(mUser);
               if(mUser.equals(userAlreadyExists)){

                   user.setError("Please try another, User already exists...!!");
                   user.requestFocus();
                   return;
               }
               else {


                   Intent i=new Intent(getApplicationContext(),RoomDetails.class);
                   i.putExtra("user_key",mUser);
                   i.putExtra("pass_key",mPass);
                   startActivity(i);

                   return;

               }
           }
       });

    }


}
