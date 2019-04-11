package com.toiler.enigmasystems;

import android.*;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import java.util.regex.Pattern;

import de.hdodenhof.circleimageview.CircleImageView;

public class WorkerProfile extends AppCompatActivity {

    TextView workerProfileName, workerProfilePhone, workerProfileArea, workerProfileDesignation, workerProfileCity, workerProfileAddress,
            workerExperience, workerWorkingDescription, workerGender, workerAccountType, workerPostCode;
    RelativeLayout phone_btn, message_btn;
    CircleImageView workerImage;

    String image_url_worker;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_worker_profile);


        //worker Phone and Message Btn
        phone_btn = (RelativeLayout) findViewById(R.id.phone_btn);
        message_btn = (RelativeLayout) findViewById(R.id.message_btn);

        //Worker Profile Components Declaration
        workerProfileName = (TextView) findViewById(R.id.profile_worker_name);
        workerProfilePhone = (TextView) findViewById(R.id.profile_worker_phone);
        workerProfileAddress = (TextView) findViewById(R.id.profile_worker_address);
        workerProfileArea = (TextView) findViewById(R.id.profile_worker_area);
        workerProfileDesignation = (TextView) findViewById(R.id.profile_worker_designation);
        workerProfileCity = (TextView) findViewById(R.id.profile_worker_city);

        workerExperience = (TextView) findViewById(R.id.profile_worker_experience);
        workerWorkingDescription = (TextView) findViewById(R.id.profile_worker_description);
        workerGender = (TextView) findViewById(R.id.profile_worker_gender);
        workerAccountType = (TextView) findViewById(R.id.profile_worker_account_type);
        workerPostCode = (TextView) findViewById(R.id.profile_worker_post);
        workerImage = (CircleImageView) findViewById(R.id.profile_worker_image);



        workerProfileName.setText(getIntent().getExtras().getString("worker_profile_name"));

        //Error Value Capture
        workerProfilePhone.setText(getIntent().getExtras().getString("worker_profile_phone"));
        workerProfileAddress.setText(getIntent().getExtras().getString("worker_profile_address"));
        workerProfileDesignation.setText(getIntent().getExtras().getString("worker_profile_designation"));
        workerProfileCity.setText(getIntent().getExtras().getString("worker_profile_city"));
        workerProfileArea.setText(getIntent().getExtras().getString("worker_profile_area"));

        workerExperience.setText(getIntent().getExtras().getString("worker_profile_experience"));
        workerWorkingDescription.setText(getIntent().getExtras().getString("worker_profile_working_description"));
        workerGender.setText(getIntent().getExtras().getString("worker_profile_gender"));
        workerAccountType.setText(getIntent().getExtras().getString("worker_profile_account_type")+" Worker");
        workerPostCode.setText(getIntent().getExtras().getString("worker_profile_postcode"));

        //Worker Image URL Analysis
        if(getIntent().getExtras().getString("worker_profile_image").equals("") || getIntent().getExtras().getString("worker_profile_image") == null){
            workerImage.setImageResource(R.drawable.icon_one);
        }else{
            Picasso.with(getApplicationContext()).load(getIntent().getExtras().getString("worker_profile_image")).into(workerImage);
        }


        phone_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String validNumber = "^[+]?[0-9]{8,15}$";
                String number = workerProfilePhone.getText().toString();
                if (number.matches(validNumber)) {
                    callIntenStart("tel:"+number);
                } else {
                    Toast.makeText(getApplicationContext(), "no phone number available", Toast.LENGTH_SHORT).show();
                }
            }
        });

        message_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String mblNumVar = workerProfilePhone.getText().toString();
                Intent smsMsgAppVar = new Intent(Intent.ACTION_VIEW);
                smsMsgAppVar.setData(Uri.parse("sms:" +  mblNumVar));
                smsMsgAppVar.putExtra("sms_body", getResources().getString(R.string.user_message));
                startActivity(smsMsgAppVar);
            }
        });
    }

    // Function for Multiple Device permission in Runtime

    void callIntenStart(String number){
        Intent callIntent = new Intent(Intent.ACTION_CALL);
        callIntent.setData(Uri.parse(number));
        if (ActivityCompat.checkSelfPermission(getApplicationContext(), android.Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        startActivity(callIntent);
    }

    //Message Sending Function
//    void messageSend(String numberMessage){
//
//    }


}
