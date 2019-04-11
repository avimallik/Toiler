package com.toiler.enigmasystems;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.ButtonBarLayout;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class WorkerLogin extends AppCompatActivity {

    ProgressDialog dialogLogin;
    private static final String LOGIN_URL = "http://www.armapprise.com/data_display.php";
    public static final String KEY_PHONE_NUM = "phone";
    public static final String KEY_PASSWORD_PASS = "password";
    private String countryCode = "+88";
    String phoneNumberRegex = "^(?:\\\\0|01)?\\d{11}\\r?$";

    EditText phoneNumberGet, passwordGet;
    Button login;
    TextView registerBtn;

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_worker_login);

        phoneNumberGet = (EditText) findViewById(R.id.phoneNumberGet);
        passwordGet = (EditText) findViewById(R.id.passwordGet);
        login = (Button) findViewById(R.id.login);


        registerBtn = (TextView) findViewById(R.id.registerBtn);
        registerBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent registraTionIntent = new Intent(WorkerLogin.this, AccountChooser.class);
                startActivity(registraTionIntent);
            }
        });

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Pattern patternPhoneNumber = Pattern.compile(phoneNumberRegex);
                Matcher matcherPhoneNumber = patternPhoneNumber.matcher(phoneNumberGet.getText().toString());
                if(matcherPhoneNumber.matches()){
                    login();
                    dialogLogin = ProgressDialog.show(WorkerLogin.this, "", getResources().getString(R.string.progress_dialog_msg_login), true);
                }else{
                    decoratedDialog(getResources().getString(R.string.phone_validate_msg));
                }
            }
        });
    }

    public void login(){
        final String login_phone = phoneNumberGet.getText().toString().trim();
        final String login_password = passwordGet.getText().toString().trim();

        final StringRequest stringRequest = new StringRequest(Request.Method.POST, LOGIN_URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONArray jsonarray = new JSONArray(response);

                            for(int i=0; i < jsonarray.length(); i++) {
                                JSONObject jsonobject = jsonarray.getJSONObject(i);
                                //Data Picker
                                String id = jsonobject.getString("id");
                                String name = jsonobject.getString("name");
                                String designation = jsonobject.getString("designation");
                                String area = jsonobject.getString("area_name");
                                String city = jsonobject.getString("city");
                                String address = jsonobject.getString("address");
                                String phone = jsonobject.getString("phone");
                                String password = jsonobject.getString("password");
                                String image_url = jsonobject.getString("image");

                                String account_type = jsonobject.getString("account_type");
                                String experience = jsonobject.getString("experience");
                                String description = jsonobject.getString("description");
                                String post_code = jsonobject.getString("postcode");
                                String gender = jsonobject.getString("gender");
                                String status = jsonobject.getString("status");

                                Toast.makeText(getApplicationContext(),name,Toast.LENGTH_SHORT).show();
                                dialogLogin.dismiss();
                                Intent loginProfileIntent = new Intent(getApplicationContext(), LoginProfile.class);

                                //Data Picker Keys
                                loginProfileIntent.putExtra("id_key", id);
                                loginProfileIntent.putExtra("name_key", name);
                                loginProfileIntent.putExtra("designation_key", designation);
                                loginProfileIntent.putExtra("phone_key", phone);
                                loginProfileIntent.putExtra("area_key", area);
                                loginProfileIntent.putExtra("city_key", city);
                                loginProfileIntent.putExtra("address_key", address);

                                loginProfileIntent.putExtra("account_type_key", account_type);
                                loginProfileIntent.putExtra("experience_key", experience);
                                loginProfileIntent.putExtra("description_key", description);
                                loginProfileIntent.putExtra("post_code", post_code);
                                loginProfileIntent.putExtra("gender_key", gender);
                                loginProfileIntent.putExtra("status_key", status);

                                loginProfileIntent.putExtra("password_key", password);

                                loginProfileIntent.putExtra("image_url_key", image_url);

                                startActivity(loginProfileIntent);
                            }

                        } catch (JSONException e) {

                            LayoutInflater layoutInflater = LayoutInflater.from(WorkerLogin.this);
                            View promptView = layoutInflater.inflate(R.layout.custom_dialog_alart, null);
                            AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(WorkerLogin.this);
                            alertDialogBuilder.setView(promptView);
                            final TextView dialogAlert = (TextView) promptView.findViewById(R.id.custom_dialog_alert);
                            dialogAlert.setText(response);

                            alertDialogBuilder.setCancelable(false)
                                    .setPositiveButton(getResources().getString(R.string.dismiss_btn_text), new DialogInterface.OnClickListener() {
                                        public void onClick(DialogInterface dialog, int id) {
                                            dialog.dismiss();
                                        }
                                    });
                            // create an alert dialog
                            AlertDialog alert = alertDialogBuilder.create();
                            alert.show();
                            dialogLogin.dismiss();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                        LayoutInflater layoutInflater = LayoutInflater.from(WorkerLogin.this);
                        View promptView = layoutInflater.inflate(R.layout.custom_dialog_alart, null);
                        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(WorkerLogin.this);
                        alertDialogBuilder.setView(promptView);
                        final TextView dialogAlert = (TextView) promptView.findViewById(R.id.custom_dialog_alert);
                        dialogAlert.setText(error.toString());

                        alertDialogBuilder.setCancelable(false)
                                .setPositiveButton(getResources().getString(R.string.dismiss_btn_text), new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int id) {
                                        dialog.dismiss();
                                    }
                                });
                        // create an alert dialog
                        AlertDialog alert = alertDialogBuilder.create();
                        alert.show();
                        dialogLogin.dismiss();

                    }
                })
        {
            @Override
            protected Map<String,String> getParams(){
                Map<String,String> params = new HashMap<String, String>();
                params.put(KEY_PHONE_NUM,countryCode+login_phone);
                params.put(KEY_PASSWORD_PASS,login_password);
                //Search Key is Here

                return params;
            }

        };

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }

    public void decoratedDialog(String dialogMsg){
        LayoutInflater layoutInflater = LayoutInflater.from(WorkerLogin.this);
        View promptView = layoutInflater.inflate(R.layout.custom_dialog_alart, null);
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(WorkerLogin.this);
        alertDialogBuilder.setView(promptView);
        final TextView dialogAlert = (TextView) promptView.findViewById(R.id.custom_dialog_alert);
        dialogAlert.setText(dialogMsg);

        alertDialogBuilder.setCancelable(false)
                .setPositiveButton(getResources().getString(R.string.dismiss_btn_text), new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.dismiss();
                    }
                });
        // create an alert dialog
        AlertDialog alert = alertDialogBuilder.create();
        alert.show();
    }

}
