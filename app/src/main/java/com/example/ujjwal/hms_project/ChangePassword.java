package com.example.ujjwal.hms_project;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class ChangePassword extends AppCompatActivity {
    EditText oldP,newP,comP;
    Button btn;
    DatabaseHelper db;
    String myuser;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_password);
        db=new DatabaseHelper(this);
        oldP=findViewById(R.id.oldpass);
        newP=findViewById(R.id.newpass);
        comP=findViewById(R.id.conpass);
        btn=findViewById(R.id.btnchange);
        Bundle b=getIntent().getExtras();
        myuser=b.getString("user").trim();
        passChange();

    }

    private void passChange() {
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String myOld,myNew,myCon;
                myOld=oldP.getText().toString().trim();
                myNew=newP.getText().toString().trim();
                myCon=comP.getText().toString().trim();
                String searchPass=db.searchPass(myuser).trim();
                if(!myOld.equals(searchPass)){
                    oldP.setError("you entered wrong old password...!!");
                    oldP.requestFocus();

                    return;
                }
                else if(myNew.length()<4){
                    newP.setError("password must have atleast 4 character...!!");
                    newP.requestFocus();
                    return;
                }
                else if(!myNew.equals(myCon)){
                    comP.setError("password not conformed...!!");
                    comP.requestFocus();
                    return;
                }
                else if(myOld.equals(searchPass) ){
                    db.changepassword(myuser,myCon);
                    Toast.makeText(getApplicationContext(),"Password changed: ",Toast.LENGTH_LONG).show();
                }
                finish();
            }
        });

    }
}
