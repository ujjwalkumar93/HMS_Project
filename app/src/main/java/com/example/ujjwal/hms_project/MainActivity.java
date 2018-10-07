package com.example.ujjwal.hms_project;

import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Layout;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import java.util.List;
public class MainActivity extends AppCompatActivity {
    EditText _username,_password;
    Button _btn_lognin;
    TextView _signUp;
    DatabaseHelper myDb;
    String uName,pass;
    CheckBox cb;
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;
    private static final String PREF_NAME="prefs";
    private static final String KEY_REMEMBER="remember";
    private static final String KEY_USERNAME="username";
    private static final String KEY_PASS="password";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //object of database
        myDb=new DatabaseHelper(this);

       // ConstraintLayout layout = findViewById(R.id.lin);
       //code for shared pref
        sharedPreferences=getSharedPreferences(PREF_NAME,MODE_PRIVATE);
        editor=sharedPreferences.edit();

        //typecasting
        _username=findViewById(R.id.name);
        _password=findViewById(R.id.pass);
        _btn_lognin=findViewById(R.id.btn);
        _signUp=findViewById(R.id.signup);
        cb=findViewById(R.id.check);

        if(sharedPreferences.getBoolean(KEY_REMEMBER,false)){
            cb.setChecked(true);
        }
        else
            cb.setChecked(false);

        _username.setText(sharedPreferences.getString(KEY_USERNAME,""));
        _password.setText(sharedPreferences.getString(KEY_PASS,""));




        //if user is not registered make them signup
        makeSignup();
        //signin for user
        signIn();
    }

    private void managePref() {
        if(cb.isChecked()){
            editor.putString(KEY_USERNAME,uName);
            editor.putString(KEY_PASS,pass);
            editor.putBoolean(KEY_REMEMBER,true);
            editor.apply();
        }
        else{
            editor.putBoolean(KEY_REMEMBER,false);
            editor.remove(KEY_PASS);
            editor.remove(KEY_USERNAME);
            editor.apply();
        }
    }

    private void makeSignup() {
        _signUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(MainActivity.this,SignupPage.class);
                startActivity(i);

            }
        });
    }

    public void signIn(){
        _btn_lognin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                uName = _username.getText().toString().trim();
                pass = _password.getText().toString().trim();
                String stored_pass=myDb.searchPass(uName).trim();
                String checkUser=myDb.userExists(uName);

               if(uName.isEmpty()){
                   _username.setError("username cant be Blank...!!");
                   _username.requestFocus();
               }
               else if(pass.isEmpty()){
                   _password.setError("Password cant be Blank...!!");
                   _password.requestFocus();
                }
                else if(pass.equals(stored_pass)){

                   Intent i=new Intent(MainActivity.this,Add_Show.class);
                   managePref();
                   i.putExtra("userName",uName);
                   startActivity(i);
                   finish();
               }
               else if(uName.equals(checkUser) && !pass.equals(stored_pass)) {
                   _password.setError("wrong password...!!");
                   _password.requestFocus();


               }
               else if(!uName.equals(checkUser)){
                   _username.setError("wrong username...!!");
                   _username.requestFocus();
               }


            }
        });
    }
}
