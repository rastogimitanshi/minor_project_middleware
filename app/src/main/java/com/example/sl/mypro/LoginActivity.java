package com.example.sl.mypro;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class LoginActivity extends AppCompatActivity {

    EditText userid, ip_add;
    Button login;
    String id,ip;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        userid = (EditText)findViewById(R.id.userid);
        ip_add = (EditText)findViewById(R.id.ip);
        login= (Button)findViewById(R.id.btnLogin);



        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                id= (String.valueOf(userid.getText()));
                ip= (String.valueOf(ip_add.getText()));
                ip= ip.trim();
                id= id.trim();

                if(id.length()==0)
                {
                    userid.setError("Enter User Id");

                }if(ip.length()==0)
                {
                    ip_add.setError("Enter IP Address");

                }
                if((id.length()!=0)&&(ip.length()!=0)) {


//                Toast.makeText(getBaseContext(), "Hey "+id , Toast.LENGTH_SHORT).show();
                    Log.d("*********", id);
                    Intent i = new Intent(LoginActivity.this, MainActivity.class);
                    Log.d("*********", id);
                    i.putExtra("userid", id);
                    i.putExtra("ip", ip);
                    startActivity(i);

                }
            }
        });
    }
}
