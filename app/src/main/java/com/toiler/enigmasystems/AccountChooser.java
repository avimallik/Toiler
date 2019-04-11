package com.toiler.enigmasystems;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.RelativeLayout;

public class AccountChooser extends AppCompatActivity {

    RelativeLayout verifiedAccountOpenBtn, easyAccountOpenBtn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account_chooser);
        verifiedAccountOpenBtn = (RelativeLayout) findViewById(R.id.veified_account_open_btn);
        easyAccountOpenBtn = (RelativeLayout) findViewById(R.id.easy_account_open_btn);

        verifiedAccountOpenBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent verifiedAccountIntent = new Intent(AccountChooser.this, PhoneAuthActivity.class);
                startActivity(verifiedAccountIntent);
            }
        });

        easyAccountOpenBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent easyAccountIntent = new Intent(AccountChooser.this, WorkerRegistrationEasy.class);
                startActivity(easyAccountIntent);
            }
        });

    }

}
