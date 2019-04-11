package com.toiler.enigmasystems;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.location.Address;
import android.location.Geocoder;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.squareup.picasso.Picasso;
import com.toiler.enigmasystems.HttpParser.HttpParseClass;
import com.toiler.enigmasystems.adapter.ListViewAdapter;
import com.toiler.enigmasystems.workermodel.WorkerModel;

import org.apache.commons.logging.Log;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class LoginProfile extends AppCompatActivity {

    ProgressDialog dialogUpdate, dialogDelete, dialogPhoneUpdate;
//    final ProgressDialog dialog;
    SharedPreferences pref;
    SharedPreferences.Editor editor;

    TextView  designationGet, accountTypeGet, wellcomeName, genderGet, logOut;
    TextView dataManipulationHelper;
    RelativeLayout passwordFieldClicker, phoneNumberClicker;
    Switch statusSwitch;

    ImageView updateWorkerBtn, deleteWokerBtn, passwordChosser, profileImage;

    EditText nameGet,areaGet,addressGet,experinceGet,descriptionGet,postCodeGet;
    AutoCompleteTextView cityGet;
    TextView phoneGet,imageTempUrl;
    TextView passwordGet,loginPhone;

    String UPDATE_URL = "http://www.armapprise.com/update_worker.php";
    String DELETE_URL = "http://www.armapprise.com/delete_worker.php";
    String PHONE_NUMBER_UPDATE_URL = "http://www.armapprise.com/phone_number_update.php";
    String STATUS_URL = "http://www.armapprise.com/status_update.php";

    //Status Generator Values
    String available_status = "Available",
    unavailable_status = "Unavailable";
    String status_temp_val;




    ArrayList<WorkerModel> UserList = new ArrayList<WorkerModel>();
    String name ;
    String phoneNumberTemp;
    String countryCodeReplacer;
//    String countryCodeBD = "+88";
    String phoneNumberRegex = "^(?:\\\\0|01)?\\d{11}\\r?$";
    String postCodeRegEx ="\\d{4}";
    String id;
    boolean doubleBackToExitPressedOnce = false;
    String latitude = null;
    String longitude = null;
    String image_url_shrink;
    String countryCodeBD = "+88";

    //Declaration for data posting
    public static final String KEY_ID = "id";
    public static final String KEY_NAME = "name";
    public static final String KEY_PHONE = "phone";
    public static final String KEY_ADDRESS = "address";

    public static final String KEY_EXPERIENCE = "experience";
    public static final String KEY_DESCRIPTION = "description";
    public static final String KEY_POSTCODE = "postcode";
    public static final String KEY_AREA = "area_name";
    public static final String KEY_CITY = "city";
    public static final String LATITUDE_KEY = "latitude";
    public static final String LONGITUDE_KEY = "longitude";
    public static final String PASSWORD_KEY = "password";
    public static final String IMAGE_KEY = "image";
    public static final String STATUS_KEY = "status";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_profile);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
        pref = getApplicationContext().getSharedPreferences("MyPref", 0);
        editor = pref.edit();


        //Logout Btn
        logOut = (TextView) findViewById(R.id.logOut);
        logOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent panelIntent = new Intent(LoginProfile.this, WorkerLogin.class);
                startActivity(panelIntent);
                finish();
            }
        });

        updateWorkerBtn = (ImageView) findViewById(R.id.update_worker_data);
        deleteWokerBtn = (ImageView) findViewById(R.id.delete_worker_data);
        passwordChosser = (ImageView) findViewById(R.id.passwordChooser);
        profileImage = (ImageView) findViewById(R.id.longin_profile_image);
        passwordFieldClicker = (RelativeLayout) findViewById(R.id.passwordFieldClicker);
        passwordFieldClicker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                passwordStrage();
            }
        });
        passwordChosser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                passwordStrage();
            }
        });

        loginPhone = (TextView) findViewById(R.id.login_phone);

        phoneNumberClicker = (RelativeLayout) findViewById(R.id.phoneNumberClicker);
        phoneNumberClicker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                LayoutInflater layoutInflater = LayoutInflater.from(LoginProfile.this);
                View promptView = layoutInflater.inflate(R.layout.phone_update, null);
                final AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(LoginProfile.this);
                alertDialogBuilder.setView(promptView);
                final EditText tempPhoneNumberTaker = (EditText) promptView.findViewById(R.id.phoneNumber);
                alertDialogBuilder.setCancelable(false)
                        .setPositiveButton(getResources().getString(R.string.yes_btn_text), new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {

                                dialogPhoneUpdate = ProgressDialog.show(LoginProfile.this, "", getResources().getString(R.string.progress_dialog_msg_update_phone), true);
                                Pattern patternPhoneNumber = Pattern.compile(phoneNumberRegex);
                                Matcher matcherPhoneNumber = patternPhoneNumber.matcher(tempPhoneNumberTaker.getText().toString());

                                if(matcherPhoneNumber.matches() || tempPhoneNumberTaker.getText().toString().isEmpty()){

                                final String helper_id = dataManipulationHelper.getText().toString().trim();
                                final String phone = tempPhoneNumberTaker.getText().toString().trim();

                                final StringRequest stringRequest = new StringRequest(Request.Method.POST, PHONE_NUMBER_UPDATE_URL,
                                        new Response.Listener<String>() {
                                            @Override
                                            public void onResponse(String response) {
                                                decoratedDialog(response);
                                                loginPhone.setText(tempPhoneNumberTaker.getText().toString());
                                                dialogPhoneUpdate.dismiss();
                                            }
                                        },
                                        new Response.ErrorListener() {
                                            @Override
                                            public void onErrorResponse(VolleyError error) {
                                                decoratedDialog(error.toString());
                                                dialogPhoneUpdate.dismiss();
                                            }

                                        }){
                                    @Override
                                    protected Map<String,String> getParams(){
                                        Map<String,String> params = new HashMap<String, String>();
                                        params.put(KEY_ID, helper_id);
                                        params.put(KEY_PHONE,countryCodeBD+phone);
                                        //Search Key is Here
                                        return params;
                                    }

                                };

                                RequestQueue requestQueue = Volley.newRequestQueue(LoginProfile.this);
                                requestQueue.add(stringRequest);
                                }else {
                                    decoratedDialog(getResources().getString(R.string.phone_number_dialog_error));
                                }
                            }
                        })
                        .setNegativeButton(getResources().getString(R.string.cancel_btn_text),
                                new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int id) {
                                        dialog.cancel();
                                    }
                                });

                // create an alert dialog
                AlertDialog alert = alertDialogBuilder.create();
                alert.show();
            }
        });

        statusSwitch = (Switch) findViewById(R.id.statusSwitch);
        statusSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                if(isChecked){
//                    Toast.makeText(getApplicationContext(),"Hello",Toast.LENGTH_SHORT).show();
                    final String helper_id = dataManipulationHelper.getText().toString().trim();
                    final StringRequest stringRequest = new StringRequest(Request.Method.POST, STATUS_URL,
                            new Response.Listener<String>() {
                                @Override
                                public void onResponse(String response) {
                                    Toast.makeText(getApplicationContext(),response,Toast.LENGTH_SHORT).show();
                                }
                            },
                            new Response.ErrorListener() {
                                @Override
                                public void onErrorResponse(VolleyError error) {
                                    Toast.makeText(getApplicationContext(),error.toString(),Toast.LENGTH_SHORT).show();

                                }

                            }){
                        @Override
                        protected Map<String,String> getParams(){
                            Map<String,String> params = new HashMap<String, String>();
                            params.put(KEY_ID, helper_id);
                            params.put(STATUS_KEY,unavailable_status);
                            //Search Key is Here
                            return params;
                        }
                    };

                    RequestQueue requestQueue = Volley.newRequestQueue(LoginProfile.this);
                    requestQueue.add(stringRequest);


                }else{

//                    Toast.makeText(getApplicationContext(),"Hopa",Toast.LENGTH_SHORT).show();
                    final String helper_id = dataManipulationHelper.getText().toString().trim();
                    final StringRequest stringRequest = new StringRequest(Request.Method.POST, STATUS_URL,
                            new Response.Listener<String>() {
                                @Override
                                public void onResponse(String response) {
                                    Toast.makeText(getApplicationContext(),response,Toast.LENGTH_SHORT).show();
                                }
                            },
                            new Response.ErrorListener() {
                                @Override
                                public void onErrorResponse(VolleyError error) {
                                    Toast.makeText(getApplicationContext(),error.toString(),Toast.LENGTH_SHORT).show();

                                }

                            }){
                        @Override
                        protected Map<String,String> getParams(){
                            Map<String,String> params = new HashMap<String, String>();
                            params.put(KEY_ID, helper_id);
                            params.put(STATUS_KEY,available_status);
                            //Search Key is Here
                            return params;
                        }
                    };

                    RequestQueue requestQueue = Volley.newRequestQueue(LoginProfile.this);
                    requestQueue.add(stringRequest);

                }
            }
        });

        phoneNumberTemp = getIntent().getExtras().getString("phone_key");
        countryCodeReplacer = phoneNumberTemp.replace("+88","");

        id = getIntent().getExtras().getString("id_key");

        dataManipulationHelper = (TextView) findViewById(R.id.login_data_manipulation_helper);

        nameGet = (EditText) findViewById(R.id.login_name);
        designationGet = (TextView) findViewById(R.id.login_designation);
        phoneGet = (TextView) findViewById(R.id.login_phone);
        cityGet = (AutoCompleteTextView) findViewById(R.id.login_city);
        areaGet = (EditText) findViewById(R.id.login_area);
        addressGet = (EditText) findViewById(R.id.login_address);
        passwordGet = (TextView) findViewById(R.id.login_password);
        wellcomeName = (TextView) findViewById(R.id.longin_name_welcome);



        experinceGet = (EditText) findViewById(R.id.login_working_experience);
        accountTypeGet = (TextView) findViewById(R.id.login_account_type);
        descriptionGet = (EditText) findViewById(R.id.login_working_description);
        postCodeGet = (EditText) findViewById(R.id.login_post_code);
        genderGet = (TextView) findViewById(R.id.login_gender);


        dataManipulationHelper.setText(id);

        wellcomeName.setText(getIntent().getExtras().getString("name_key"));
        nameGet.setText(getIntent().getExtras().getString("name_key"));
        designationGet.setText(getIntent().getExtras().getString("designation_key"));
        phoneGet.setText(countryCodeReplacer);
//        phoneGet.setText(countryCodeReplacer);
        cityGet.setText(getIntent().getExtras().getString("city_key"));
        addressGet.setText(getIntent().getExtras().getString("address_key"));
        areaGet.setText(getIntent().getExtras().getString("area_key"));

        experinceGet.setText(getIntent().getExtras().getString("experience_key"));
        accountTypeGet.setText(getIntent().getExtras().getString("account_type_key"));
        descriptionGet.setText(getIntent().getExtras().getString("description_key"));
        postCodeGet.setText(getIntent().getExtras().getString("post_code"));
        genderGet.setText(getIntent().getExtras().getString("gender_key"));
        passwordGet.setText(getIntent().getExtras().getString("password_key"));

//        if(getIntent().getExtras().getString("status_key").toString().contains("available")){
//            statusSwitch.setChecked(true);
//        }else if(getIntent().getExtras().getString("status_key").toString().contains("unavailable")){
//            statusSwitch.setChecked(false);
//        }

        SharedPreferences sharedPref = getSharedPreferences("status_memory", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putString("status_storage",getIntent().getExtras().getString("status_key"));
        editor.apply();

        status_temp_val = sharedPref.getString("status_storage", "");


        if(status_temp_val.equals("Available")){
            statusSwitch.setChecked(false);
        }else if(status_temp_val.equals("Unavailable")){
            statusSwitch.setChecked(true);
        }

        Picasso.with(getApplicationContext()).load(getIntent().getExtras().getString("image_url_key")).resize(200, 200).into(profileImage);

        image_url_shrink = getIntent().getExtras().getString("image_url_key").replace("http://www.armapprise.com/upload_image/","");
//        imageTempUrl.setText(image_url_shrink);
        editor.putString("image_ul_temp", image_url_shrink);
        editor.apply();

        //City Name Item List
        List<String> cityNameItem = new ArrayList<String>();

        //Chittagong division
        cityNameItem.add("Comilla");
        cityNameItem.add("Feni");
        cityNameItem.add("Brahmanbaria");
        cityNameItem.add("Rangamati");
        cityNameItem.add("Noakhali");
        cityNameItem.add("Chandpur");
        cityNameItem.add("Lakshmipur");
        cityNameItem.add("Chittagong");
        cityNameItem.add("Coxsbazar");
        cityNameItem.add("Khagrachhari");
        cityNameItem.add("Bandarban");

        //Rajshahi Division
        cityNameItem.add("Sirajganj");
        cityNameItem.add("Pabna");
        cityNameItem.add("Bogra");
        cityNameItem.add("Rajshahi");
        cityNameItem.add("Natore");
        cityNameItem.add("Joypurhat");
        cityNameItem.add("Chapainawabganj");
        cityNameItem.add("Naogaon");

        //Khulna Division
        cityNameItem.add("Khulna");
        cityNameItem.add("Jessore");
        cityNameItem.add("Satkhira");
        cityNameItem.add("Meherpur");
        cityNameItem.add("Narail");
        cityNameItem.add("Chuadanga");
        cityNameItem.add("Kushtia");
        cityNameItem.add("Magura");
        cityNameItem.add("Bagerhat");
        cityNameItem.add("Jhenaidah");

        //Barisal Division
        cityNameItem.add("Jhalakathi");
        cityNameItem.add("Patuakhali");
        cityNameItem.add("Pirojpur");
        cityNameItem.add("Barisal");
        cityNameItem.add("Bhola");
        cityNameItem.add("Barguna");

        //Sylhet Division
        cityNameItem.add("Sylhet");
        cityNameItem.add("Moulvibazar");
        cityNameItem.add("Habiganj (Sylhet)");
        cityNameItem.add("Sunamganj (Sylhet)");

        //Dhaka Division
        cityNameItem.add("Narsingdi");
        cityNameItem.add("Gazipur");
        cityNameItem.add("Shariatpur");
        cityNameItem.add("Narayanganj");
        cityNameItem.add("Tangail");
        cityNameItem.add("Kishoreganj");
        cityNameItem.add("Manikganj");
        cityNameItem.add("Dhaka");
        cityNameItem.add("Munshiganj");
        cityNameItem.add("Rajbari");
        cityNameItem.add("Madaripur");
        cityNameItem.add("Gopalganj");
        cityNameItem.add("Faridpur (Dhaka)");

        //Rangpur Division
        cityNameItem.add("Panchagarh");
        cityNameItem.add("Dinajpur");
        cityNameItem.add("Lalmonirhat");
        cityNameItem.add("Nilphamari");
        cityNameItem.add("Gaibandha");
        cityNameItem.add("Thakurgaon");
        cityNameItem.add("Rangpur");
        cityNameItem.add("Kurigram");

        //Mymensingh Division
        cityNameItem.add("Sherpur (Mymensingh)");
        cityNameItem.add("Mymensingh (Mymensingh)");
        cityNameItem.add("Jamalpur (Mymensingh)");
        cityNameItem.add("Netrokona (Mymensingh)");

        //City Name Adapter
        ArrayAdapter<String> cityType = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, cityNameItem);
        cityGet.setThreshold(1);
        cityGet.setAdapter(cityType);

        updateWorkerBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                LayoutInflater layoutInflater = LayoutInflater.from(LoginProfile.this);
                View promptView = layoutInflater.inflate(R.layout.custom_dialog_alart, null);
                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(LoginProfile.this);
                alertDialogBuilder.setView(promptView);
                final TextView dialogAlert = (TextView) promptView.findViewById(R.id.custom_dialog_alert);
                dialogAlert.setText(getResources().getString(R.string.message_btn_text));

                alertDialogBuilder.setCancelable(false)
                        .setPositiveButton(getResources().getString(R.string.yes_btn_text), new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                Pattern patternPostCode = Pattern.compile(postCodeRegEx);
                                Matcher matcherPostCode = patternPostCode.matcher(postCodeGet.getText().toString());
//
//                                Pattern patternPhoneNumber = Pattern.compile(phoneNumberRegex);
//                                Matcher matcherPhoneNumber = patternPhoneNumber.matcher(phoneGet.getText().toString());
//
                                if(nameGet.getText().toString().toString().isEmpty()){
                                    decoratedDialog(getResources().getString(R.string.worker_name_error));
                                }else if(phoneGet.getText().toString().isEmpty()){
                                    decoratedDialog(getResources().getString(R.string.worker_phone_error));
                                }else if(cityGet.getText().toString().isEmpty()){
                                    decoratedDialog(getResources().getString(R.string.worker_cityname_error));
                                }else if(areaGet.getText().toString().isEmpty()){
                                    decoratedDialog(getResources().getString(R.string.worker_area_error));
                                }else if(postCodeGet.getText().toString().isEmpty()){
                                    decoratedDialog(getResources().getString(R.string.post_code_error));
                                }else if(addressGet.getText().toString().isEmpty()){
                                    decoratedDialog(getResources().getString(R.string.worker_address_error));
                                }else if(descriptionGet.getText().toString().isEmpty()){
                                    decoratedDialog(getResources().getString(R.string.description_error));
                                }else if(experinceGet.getText().toString().isEmpty()){
                                    decoratedDialog(getResources().getString(R.string.experience_range_error));
                                }else if(passwordGet.getText().toString().length()<6){
                                    decoratedDialog(getResources().getString(R.string.worker_password_length_error));
                                }else if(matcherPostCode.matches()){
                                    updateInformation();
                                    dialogUpdate = ProgressDialog.show(LoginProfile.this, "", getResources().getString(R.string.progress_dialog_msg_update), true);
                                }else{
                                    decoratedDialog(getResources().getString(R.string.post_code_error));
                                }
                            }
                        }).setNegativeButton(getResources().getString(R.string.no_btn_text), new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            dialog.dismiss();
                        }
                });
                // create an alert dialog
                AlertDialog alert = alertDialogBuilder.create();
                alert.show();
            }
        });

        deleteWokerBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                LayoutInflater layoutInflater = LayoutInflater.from(LoginProfile.this);
                View promptView = layoutInflater.inflate(R.layout.custom_dialog_alart, null);
                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(LoginProfile.this);
                alertDialogBuilder.setView(promptView);
                final TextView dialogAlert = (TextView) promptView.findViewById(R.id.custom_dialog_alert);
                dialogAlert.setText(getResources().getString(R.string.message_btn_delete_text));

                alertDialogBuilder.setCancelable(false)
                        .setPositiveButton(getResources().getString(R.string.yes_btn_text), new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                deleteInformation();
                                dialogDelete = ProgressDialog.show(LoginProfile.this, "", getResources().getString(R.string.progress_dialog_msg_delete), true);

                            }
                        }).setNegativeButton(getResources().getString(R.string.no_btn_text), new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.dismiss();
                    }
                });
                // create an alert dialog
                AlertDialog alert = alertDialogBuilder.create();
                alert.show();
            }
        });
    }

    //Information Submission Function
    private void updateInformation(){
        final String helper_id = dataManipulationHelper.getText().toString().trim();
        final String name = nameGet.getText().toString().trim();
        final String phone = phoneGet.getText().toString().trim();

        final String address = addressGet.getText().toString().trim();
        final String experience = experinceGet.getText().toString().trim();
        final String description = descriptionGet.getText().toString().trim();
        final String postcode = postCodeGet.getText().toString().trim();
        final String area_name = areaGet.getText().toString().trim();
        final String city = cityGet.getText().toString().trim();
        final String password = passwordGet.getText().toString().trim();

        //Geolocation Combiner
        String workerLocationAddress = address+","+area_name+","+city+postcode;
        Geocoder coder = new Geocoder(getApplicationContext());
        try {
            ArrayList<Address> adresses = (ArrayList<Address>) coder.getFromLocationName(workerLocationAddress, 50);
            for(Address add : adresses){

                double longitudeAnalyse = add.getLongitude();
                double latitudeAnalyse = add.getLatitude();

                longitude = String.valueOf(longitudeAnalyse);
                latitude = String.valueOf(latitudeAnalyse);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        Toast.makeText(getApplicationContext(),latitude+","+longitude,Toast.LENGTH_SHORT).show();

        final StringRequest stringRequest = new StringRequest(Request.Method.POST, UPDATE_URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        decoratedDialog(response);
                        dialogUpdate.dismiss();
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        decoratedDialog(error.toString());
                        dialogUpdate.dismiss();
                    }

                }){
            @Override
            protected Map<String,String> getParams(){
                Map<String,String> params = new HashMap<String, String>();
                params.put(KEY_ID, helper_id);
                params.put(KEY_NAME,name);
//                params.put(KEY_PHONE,countryCodeBD+phone);
                params.put(KEY_ADDRESS,address);
                params.put(KEY_AREA,area_name);
                params.put(KEY_CITY, city);
                params.put(KEY_EXPERIENCE, experience);
                params.put(KEY_DESCRIPTION, description);
                params.put(KEY_POSTCODE,postcode);
                params.put(LATITUDE_KEY, latitude);
                params.put(LONGITUDE_KEY, longitude);
                params.put(PASSWORD_KEY, password);

                //Search Key is Here
                return params;
            }

        };

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }


    //Information Submission Function
    private void deleteInformation(){
        final String helper_id = dataManipulationHelper.getText().toString().trim();
        final String image = pref.getString("key_name", image_url_shrink);
//        imageTempUrl.getText().toString();

        final StringRequest stringRequest = new StringRequest(Request.Method.POST, DELETE_URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        decoratedDialogQuit(response);
                        dialogDelete.dismiss();
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        decoratedDialog(error.toString());
                        dialogDelete.dismiss();
                    }

                }){
            @Override
            protected Map<String,String> getParams(){
                Map<String,String> params = new HashMap<String, String>();
                params.put(KEY_ID, helper_id);
                params.put(IMAGE_KEY, image);
                return params;
            }

        };

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }

    public void decoratedDialog(String dialogMsg){
        LayoutInflater layoutInflater = LayoutInflater.from(LoginProfile.this);
        View promptView = layoutInflater.inflate(R.layout.custom_dialog_alart, null);
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(LoginProfile.this);
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

    public void decoratedDialogQuit(String dialogMsg){
        LayoutInflater layoutInflater = LayoutInflater.from(LoginProfile.this);
        View promptView = layoutInflater.inflate(R.layout.custom_dialog_alart, null);
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(LoginProfile.this);
        alertDialogBuilder.setView(promptView);
        final TextView dialogAlert = (TextView) promptView.findViewById(R.id.custom_dialog_alert);
        dialogAlert.setText(dialogMsg);

        alertDialogBuilder.setCancelable(false)
                .setPositiveButton(getResources().getString(R.string.dismiss_btn_text), new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        Intent panelIntent = new Intent(LoginProfile.this, WorkerLogin.class);
                        startActivity(panelIntent);
                        finish();
                    }
                });
        // create an alert dialog
        AlertDialog alert = alertDialogBuilder.create();
        alert.show();
    }

    public void passwordStrage(){
        LayoutInflater layoutInflater = LayoutInflater.from(LoginProfile.this);
        View promptView = layoutInflater.inflate(R.layout.password_dialog, null);
        final AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(LoginProfile.this);
        alertDialogBuilder.setView(promptView);
        final EditText tempPasswordTaker = (EditText) promptView.findViewById(R.id.tempPasswordTaker);
        alertDialogBuilder.setCancelable(false)
                .setPositiveButton(getResources().getString(R.string.yes_btn_text), new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        if(tempPasswordTaker.getText().toString().length()<6){
                            decoratedDialog(getResources().getString(R.string.worker_password_length_error));
                        }else{
                            passwordGet.setText(tempPasswordTaker.getText().toString());
                        }
                    }
                })
                .setNegativeButton(getResources().getString(R.string.cancel_btn_text),
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                dialog.cancel();
                            }
                        });

        // create an alert dialog
        AlertDialog alert = alertDialogBuilder.create();
        alert.show();
    }

    @Override
    public void onBackPressed() {
        if (doubleBackToExitPressedOnce) {
            super.onBackPressed();
            return;
        }

        this.doubleBackToExitPressedOnce = true;
        Toast.makeText(this, getResources().getString(R.string.exit_string), Toast.LENGTH_SHORT).show();
        new Handler().postDelayed(new Runnable() {

            @Override
            public void run() {
                doubleBackToExitPressedOnce=false;
            }
        }, 2000);
    }


}
