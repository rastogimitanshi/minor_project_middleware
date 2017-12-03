package com.example.sl.mypro;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    String ip;
    TextView name;
    Button logout, btaction;
    ListView listView;
    String user_id;
    ArrayList<ContentFormat> arrayListEmailContent =new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //Log.d("*********","In MAIN 1");

        user_id = getIntent().getExtras().getString("userid");
        ip= getIntent().getExtras().getString("ip");

        //Toast.makeText(this, "Hey "+user_id, Toast.LENGTH_SHORT).show();
        name = (TextView)findViewById(R.id.name);
        logout = (Button)findViewById(R.id.button);
        btaction= (Button)findViewById(R.id.button1);
        listView= (ListView)findViewById(R.id.content);

        fetchContent();

        btaction.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(MainActivity.this, "Sending content to 3rd party app...", Toast.LENGTH_LONG).show();
            }
        });

        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i1= new Intent(MainActivity.this, LoginActivity.class);
                startActivity(i1);
            }
        });

    }

    public void fetchContent(){

        String tag_string_req = "req_login";
//        Log.d("*************","IN FETCH");

        StringRequest strReq = new StringRequest(Request.Method.POST,
                "http://"+ip+"/minorproject/fetchContent.php?userid="+user_id, new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
                Log.d("***********","ON RESPONSE");
                Log.d("request", "Request: " + response.toString());


                try {
                    JSONObject jo = new JSONObject(response);
                    //Toast.makeText(MainActivity.this, "weeee.."+response, Toast.LENGTH_SHORT).show();
                    Log.d("*****************", "Meow"+response);

                    boolean error = jo.getBoolean("error");
                    if (arrayListEmailContent.size()>0)
                        arrayListEmailContent.clear();

                    if(!error){
                        String fetchedName= jo.getString("name");
                        name.setText("Welcome "+fetchedName+"!");
                        Toast.makeText(MainActivity.this, "Hello Dear "+fetchedName, Toast.LENGTH_SHORT).show();

                        JSONArray jArr = jo.getJSONArray("content");

                        if (arrayListEmailContent.size()>0)
                            arrayListEmailContent.clear();
                        //Toast.makeText(MainActivity.this, "weeee.."+jArr.length(), Toast.LENGTH_SHORT).show();


                        for (int i = 0; i < jArr.length(); i++) {
//
                            JSONObject eventObj = jArr.getJSONObject(i);
                            String time = eventObj.getString("time");
                            String date = eventObj.getString("date");
                            String address= eventObj.getString("address");
                            time=time.trim();
                            date= date.trim();
                            address= address.trim();

                            arrayListEmailContent.add(new ContentFormat(time,date, address));

                            Log.d("********!!", String.valueOf(arrayListEmailContent));


                        }

                    }

                    else{
                        // Error occurred in fetching. Get the error
                        // message
                        String errorMsg = jo.getString("error_msg");
                        Toast.makeText(getApplicationContext(),
                                errorMsg, Toast.LENGTH_SHORT).show();
                    }

                    MyAdapter itemAdapter = new MyAdapter(MainActivity.this, R.layout.list_item_content, arrayListEmailContent);

                    listView.setAdapter(itemAdapter);


                } catch (JSONException e) {
                    // JSON error
                    e.printStackTrace();
                    Toast.makeText(getApplicationContext(), "Json error: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                }
                Log.d("ArrayList","size"+ arrayListEmailContent.size());
            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("request", "Request List Error: " + error.getMessage());
                Toast.makeText(getApplicationContext(),
                        error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
        // Adding request to request queue
        AppController.getInstance().addToRequestQueue(strReq, tag_string_req);

    }


}
