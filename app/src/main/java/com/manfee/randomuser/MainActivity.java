package com.manfee.randomuser;

import android.app.DownloadManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

public class MainActivity extends AppCompatActivity {

    Button getButton;
    ImageView mImageView;
    TextView mTextViewName, mTextViewEmail, mTextViewPhoneNumber, mTextViewDOB, mTextViewAdd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        getButton = (Button) findViewById(R.id.button_getachick);
        mImageView = (ImageView) findViewById(R.id.imageview_photo);
        mTextViewName = (TextView) findViewById(R.id.textview_name);
        mTextViewEmail = (TextView) findViewById(R.id.textview_email);
        mTextViewPhoneNumber = (TextView) findViewById(R.id.textview_phonenumber);
        mTextViewDOB = (TextView) findViewById(R.id.textview_dob);
        mTextViewAdd = (TextView) findViewById(R.id.textview_address);


        getButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Instantiate the RequestQueue.
                RequestQueue queue = Volley.newRequestQueue(MainActivity.this);
                String url = "https://randomuser.me/api";

                // Request a string response from the provided URL.
                StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                        new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {
                                // Display the first 500 characters of the response string.

                                try {
                                    JSONObject myJSON = new JSONObject(response);
                                    JSONObject result = myJSON.getJSONArray("results").getJSONObject(0);

                                    String name = result.getJSONObject("name").getString("first");
                                    String lastname = result.getJSONObject("name").getString("last");
                                    String title = result.getJSONObject("name").getString("title");
                                    mTextViewName.setText(title + " " + name + " " + lastname);

                                    String email = result.getString("email");
                                    mTextViewEmail.setText(email);

                                    String phonenumber = result.getString("cell");
                                    mTextViewPhoneNumber.setText(phonenumber);

                                    String dob = result.getString("dob");
                                    mTextViewDOB.setText(dob);

                                    String address = result.getJSONObject("location").getString("street");
                                    String city = result.getJSONObject("location").getString("city");
                                    String state = result.getJSONObject("location").getString("state");
                                    String postcode = result.getJSONObject("location").getString("postcode");
                                    mTextViewAdd.setText(address + " " + city + " " + state + " " + postcode);

                                    String photo = result.getJSONObject("picture").getString("large");
                                    Picasso.with(MainActivity.this).load(photo).into(mImageView);

                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }

                            }
                        }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        mTextViewName.setText("That didn't work!");
                    }
                });


                // Add the request to the RequestQueue.
                queue.add(stringRequest);

            }
        });


    }
}
