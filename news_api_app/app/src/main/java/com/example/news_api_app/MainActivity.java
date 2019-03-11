package com.example.news_api_app;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.Switch;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
     private ListView list;
     private Adapter adapter;
     private EditText editText;
     private RelativeLayout bar;
     private Button btn;
     private ArrayList<item> arrlist;
     private Switch sw;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        AndroidNetworking.initialize(getApplicationContext());
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        list=(ListView)findViewById(R.id.listview);
        bar=(RelativeLayout)findViewById(R.id.rel);
        btn=(Button)findViewById(R.id.btn);
        editText=(EditText)findViewById(R.id.edittext);
        sw=(Switch)findViewById(R.id.switch1);


        btn.setOnClickListener(new View.OnClickListener() {//Search button listener
            @Override
            public void onClick(View v) {
                bar.setVisibility(View.VISIBLE);
                if(sw.isChecked())//switch (headline news)
                    make_request(editText.getText().toString(),"top-headlines");
                    else make_request(editText.getText().toString(),"everything");

            }
        });

        sw.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            //if switch changed
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked)
                    make_request(editText.getText().toString(),"top-headlines");
                else make_request(editText.getText().toString(),"everything");
            }
        });
        //click item in list view
     list.setOnItemClickListener(new AdapterView.OnItemClickListener() {

         @Override
         public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

             item u=arrlist.get(position);
             //open browser
             Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(u.getUrl()));
             startActivity(browserIntent);

         }
     });

    }

    //networking using fast android Networking libary deatils visit :https://github.com/amitshekhariitbhu/Fast-Android-Networking
    void make_request(String query,String type){
        AndroidNetworking
                .get("https://newsapi.org/v2/"+type+"?apiKey=72d81fd83ab94b1dad32435d63570d5c&sortBy=popularity")
                .addQueryParameter("q",query)
                .build().getAsJSONObject(new JSONObjectRequestListener() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                     arrlist=new ArrayList<>();
                     //more deatiles about the request visit :https://newsapi.org
                    JSONArray arr=response.getJSONArray("articles");
                    for (int i = 0; i < arr.length(); i++) {
                        JSONObject o=arr.getJSONObject(i);
                        String date=o.getString("publishedAt");
                        arrlist.add(new item(o.getString("urlToImage"),o.getString("title")
                        ,o.getString("url"),date.substring(0,date.length()-4)));
                    }
                    adapter=new Adapter(MainActivity.this,0,arrlist);

                    bar.setVisibility(View.GONE);//make RelativeLayout gone
                    list.setVisibility(View.VISIBLE);
                    list.setAdapter(adapter);

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onError(ANError anError) {

            }
        });

    }
}
