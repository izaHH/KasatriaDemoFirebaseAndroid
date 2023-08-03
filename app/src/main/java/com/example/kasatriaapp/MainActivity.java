package com.example.kasatriaapp;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.analytics.FirebaseAnalytics;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.analytics.FirebaseAnalytics.Param;
import com.google.firebase.analytics.FirebaseAnalytics.Event;


public class MainActivity extends AppCompatActivity {

    FirebaseAuth auth;
    FirebaseAnalytics mFirebaseAnalytics = FirebaseAnalytics.getInstance(this);
    TextView textView;
    TextView editCustomerIDtext;
    FirebaseUser user;
    Button button;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        // Obtain the FirebaseAnalytics instance.
        mFirebaseAnalytics = FirebaseAnalytics.getInstance(this);
        auth = FirebaseAuth.getInstance();
        button = findViewById(R.id.btn_signout);
        textView = findViewById(R.id.user_details);
        editCustomerIDtext = findViewById(R.id.c_id_detail);

        user = auth.getCurrentUser();
        if(user == null){
            //Fire LOGIN event
            Bundle params = new Bundle();
            params.putString(FirebaseAnalytics.Param.METHOD, "login");
            String customer_id = "";
            mFirebaseAnalytics.setUserProperty("12345e", customer_id);

            Intent intent = new Intent(getApplicationContext(), Login.class);
            startActivity(intent);
            finish();
        }
        else{
           // Set Customer ID
            String customer_id = "";
            mFirebaseAnalytics.setUserProperty("12345e", customer_id);

            //Display User Profile once logged in
            textView.setText(user.getEmail());
            editCustomerIDtext.setText((user.getUid()));
        }

        button.setOnClickListener(new View.OnClickListener(){
            //mFirebaseAnalytics = FirebaseAnalytics.getInstance(this);
            @Override
            public void onClick(View view) {

                //Custom Logout Event
                Bundle bundle = new Bundle();
                bundle.putString("logout", "logout");
                mFirebaseAnalytics.logEvent("logout", bundle);
                //Firebase User Sign out
                FirebaseAuth.getInstance().signOut();

                Intent intent = new Intent(getApplicationContext(), Login.class);
                startActivity(intent);
                //finish();

            }
        });

    }




}