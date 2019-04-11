package com.toiler.enigmasystems;

import android.*;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.location.Address;
import android.location.Geocoder;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.test.mock.MockPackageManager;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.location.LocationRequest;
import com.google.firebase.auth.FirebaseAuth;


import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import de.hdodenhof.circleimageview.CircleImageView;

public class WorkerRegistrationEasy extends AppCompatActivity {

    ProgressDialog dialogRegEasy;
    boolean doubleBackToExitPressedOnce = false;
    private static final int PICK_IMAGE_REQUEST= 99;

//  Bitmap emptyBitmap;
    Bitmap bitmap;

    String latitude = null;
    String longitude = null;

    Geocoder geocoder;

    String postCodeRegEx ="\\d{4}";
    String phoneNumberRegex = "^(?:\\\\0|01)?\\d{11}\\r?$";
    String bangladeshCountryCode = "+88";

    private static final String REGISTER_URL = "http://www.armapprise.com/insert_worker.php";
    public static final String KEY_NAME = "name";
    public static final String KEY_PHONE = "phone";
    public static final String KEY_ADDRESS = "address";
    public static final String KEY_DESIGNATION = "designation";

    public static final String KEY_GENDER = "gender";
    public static final String KEY_WORKINGEXPERIENCE = "experience";
    public static final String KEY_DESCRIPTION = "description";
    public static final String KEY_ACCOUNT_TYPE = "account_type";

    public static final String KEY_POSTCODE = "postcode";

    public static final String KEY_AREA_NAME= "area_name";
    public static final String KEY_CITY = "city";
    public static final String PASSWORD_KEY = "password";
    public static final String LATITUDE_KEY = "latitude";
    public static final String LONGITUDE_KEY = "longitude";
    public static final String KEY_SEARCH_KEY = "search_key";
    public static final String KEY_IMAGE_KEY = "image";


    private static final String TAG = "PhoneAuthActivity";
//    private FirebaseAuth mAuth;

    LocationRequest locationRequest;

//    //Location Manger Object
//    private FusedLocationProviderClient client;

    String addressLocation;

    String lati_val, longi_val;



    String mPermission = android.Manifest.permission.ACCESS_FINE_LOCATION;
    private static final int REQUEST_CODE_PERMISSION = 2;

    //Lat & Long Value Storage
//    String latitude_val;
//    String longitude_val;

    EditText nameTxt, phoneTxt, addressTxt, areaNameTxt, passwordtxt, passwordConfirmTxt, experienceTxt, descriptionTxt, postcodeTxt;
    AutoCompleteTextView cityNameTxt;
    Button addWorkerDataBtn, showWorkerDataBtn;
    ImageView clearFieldBtn;
    CircleImageView imageChosser;
    Spinner designationSpinner, citySpinner;
    TextView accountType;

    //Gender & working Exp Spinner
    Spinner genderSpinner, workingExperienceSpinerType;



    //Designation Spinner Variable to store Spinner Value
    String spinnerRecordDesignation = "";

    //Gender Spinner Variable to store Spinner Value
    String spinnerRecordGender = "";

    //Gender Spinner Variable to store Spinner Value
    String spinnerRecordExperience = "";


    //Designation Adapter
    ArrayAdapter<String> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_worker_registration_easy);
        this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);

//        bitmap = Bitmap.createBitmap(emptyBitmap.getWidth(), emptyBitmap.getHeight(), emptyBitmap.getConfig());



//        //Location Permission Check
//        if (ContextCompat.checkSelfPermission(getApplicationContext(), android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getApplicationContext(), android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED)
//        {
//            ActivityCompat.requestPermissions(this, new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION, android.Manifest.permission.ACCESS_COARSE_LOCATION}, 101);
//        }

        try {
            if (ActivityCompat.checkSelfPermission(this, mPermission)
                    != MockPackageManager.PERMISSION_GRANTED) {

                ActivityCompat.requestPermissions(this, new String[]{mPermission, android.Manifest.permission.READ_PHONE_STATE},
                        REQUEST_CODE_PERMISSION);
//                locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
            }else{
                //read location

            }
        } catch (Exception e) {
            e.printStackTrace();
        }


        nameTxt = (EditText) findViewById(R.id.ediNameEasyTxt);
        phoneTxt = (EditText) findViewById(R.id.ediPhoneEasyTxt);
        addressTxt = (EditText) findViewById(R.id.ediAddressEasyTxt);
        areaNameTxt = (EditText) findViewById(R.id.ediAreaEasyTxt);
        cityNameTxt = (AutoCompleteTextView) findViewById(R.id.ediCityEasyTxt);
        passwordtxt = (EditText) findViewById(R.id.ediPasswordEasyTxt);
        passwordConfirmTxt = (EditText) findViewById(R.id.ediPasswordConfirmEasyTxt);
        postcodeTxt = (EditText) findViewById(R.id.editPostCodeEasy);

        accountType = (TextView) findViewById(R.id.easyAccStatus);

        experienceTxt = (EditText) findViewById(R.id.editExperienceEasy);
        descriptionTxt = (EditText) findViewById(R.id.ediDescriptionEasy);


        //Field Clear Button
        clearFieldBtn = (ImageView) findViewById(R.id.clearFieldEasy);
        imageChosser = (CircleImageView) findViewById(R.id.imageChosser);

        //Submit Button
        addWorkerDataBtn = (Button) findViewById(R.id.insertDataBtnEasy);


//        //Fire Base Authentication
//        mAuth = FirebaseAuth.getInstance();
//        if(mAuth!=null){
//            phoneTxt.setText(mAuth.getCurrentUser().getPhoneNumber());
//        }


        //Designation Spinner Components
        designationSpinner = (Spinner) findViewById(R.id.designationEasySpinner);
        //Designation Item List
        final List<String> designationItem = new ArrayList<String>();
        designationItem.add(getResources().getString(R.string.none_string));
        designationItem.add(getResources().getString(R.string.electrician_string));
        designationItem.add(getResources().getString(R.string.plumber_string));
        designationItem.add(getResources().getString(R.string.electronics_string));
        designationItem.add(getResources().getString(R.string.carpenter_string));
        designationItem.add(getResources().getString(R.string.automobile_string));
        designationItem.add(getResources().getString(R.string.fridge_string));
        designationItem.add(getResources().getString(R.string.meson_string));
        designationItem.add(getResources().getString(R.string.house_painter_string));

        //New Designation
        designationItem.add(getResources().getString(R.string.locksmith_string));
        designationItem.add(getResources().getString(R.string.iron_smith_string));
        designationItem.add(getResources().getString(R.string.gold_smith_string));
        designationItem.add(getResources().getString(R.string.mobile_technician_string));
        designationItem.add(getResources().getString(R.string.interior_designer_string));
        designationItem.add(getResources().getString(R.string.photo_grapher_string));
        designationItem.add(getResources().getString(R.string.waste_cleaner_string));
        designationItem.add(getResources().getString(R.string.dish_provider_string));
        designationItem.add(getResources().getString(R.string.isp_provider_string));
        designationItem.add(getResources().getString(R.string.beautician_string));
        designationItem.add(getResources().getString(R.string.cook_string));
        designationItem.add(getResources().getString(R.string.umbrella_repair_string));
        designationItem.add(getResources().getString(R.string.housekeeper_string));
        designationItem.add(getResources().getString(R.string.baby_sitter_string));
        designationItem.add(getResources().getString(R.string.house_shifter_string));
        designationItem.add(getResources().getString(R.string.tailors_string));
        designationItem.add(getResources().getString(R.string.door_technician_string));
        designationItem.add(getResources().getString(R.string.cobbler_string));
        designationItem.add(getResources().getString(R.string.purohit_string));
        designationItem.add(getResources().getString(R.string.kazi_string));
        designationItem.add(getResources().getString(R.string.wedding_material_supplier_string));
        designationItem.add(getResources().getString(R.string.light_illumination_service_string));



        //Gender Spinner Components
        genderSpinner = (Spinner) findViewById(R.id.genderSpinnerEasy);
        List<String> genderItem = new ArrayList<String>();
        genderItem.add(getResources().getString(R.string.none_gender_string));
        genderItem.add(getResources().getString(R.string.male_string));
        genderItem.add(getResources().getString(R.string.female_string));
        genderItem.add(getResources().getString(R.string.trans_other));


        //Experience Spinner Components
        workingExperienceSpinerType = (Spinner) findViewById(R.id.work_experience_spinner_type_easy);
        List<String> workingExperienceItem = new ArrayList<String>();
        workingExperienceItem.add(getResources().getString(R.string.working_exp_none_string));
        workingExperienceItem.add(getResources().getString(R.string.working_exp_month_string));
        workingExperienceItem.add(getResources().getString(R.string.working_exp_year_string));

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



        //Designation Adapter
        ArrayAdapter<String> designationType = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, designationItem);
        designationType.setDropDownViewResource(android.R.layout.simple_list_item_1);
        designationSpinner.setAdapter(designationType);

        //Gender Adapter
        ArrayAdapter<String> genderType = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, genderItem);
        genderType.setDropDownViewResource(android.R.layout.simple_list_item_1);
        genderSpinner.setAdapter(genderType);

        //Working Experience Adpter
        ArrayAdapter<String> workingExpType = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, workingExperienceItem);
        workingExpType.setDropDownViewResource(android.R.layout.simple_list_item_1);
        workingExperienceSpinerType.setAdapter(workingExpType);

        //City Name Adapter
        ArrayAdapter<String> cityType = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, cityNameItem);
        cityNameTxt.setThreshold(1);
        cityNameTxt.setAdapter(cityType);

        //Designation Spinner Item chooser
        designationSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                //Spinner Position Value
                switch (position){

                    case 0:
                        spinnerRecordDesignation = "None";
                        break;

                    case 1:
                        spinnerRecordDesignation = "Electrician";
                        break;

                    case 2:
                        spinnerRecordDesignation = "Plumber";
                        break;

                    case 3:
                        spinnerRecordDesignation = "Electronics Mechanic";
                        break;

                    case 4:
                        spinnerRecordDesignation = "Carpenter";
                        break;

                    case 5:
                        spinnerRecordDesignation = "Automobile Mechanic";
                        break;

                    case 6:
                        spinnerRecordDesignation = "AC/Fridge Mechanic";
                        break;

                    case 7:
                        spinnerRecordDesignation ="Meson";
                        break;

                    case 8:
                        spinnerRecordDesignation = "House Painter";
                        break;

                    //new Designation
                    case 9:
                        spinnerRecordDesignation = "Locksmith";
                        break;

                    case 10:
                        spinnerRecordDesignation = "Ironsmith";
                        break;

                    case 11:
                        spinnerRecordDesignation = "Goldsmith";
                        break;

                    case 12:
                        spinnerRecordDesignation = "Mobile Repairer";
                        break;

                    case 13:
                        spinnerRecordDesignation = "Interior Designer";
                        break;

                    case 14:
                        spinnerRecordDesignation = "Photographer/Graphics Designer";
                        break;

                    case 15:
                        spinnerRecordDesignation = "Waste Cleaner/Swiper";
                        break;

                    case 16:
                        spinnerRecordDesignation = "TV Channel Provider";
                        break;

                    case 17:
                        spinnerRecordDesignation = "Internet Service Provider (ISP)";
                        break;

                    case 18:
                        spinnerRecordDesignation = "Beautician";
                        break;

                    case 19:
                        spinnerRecordDesignation = "Cook";
                        break;

                    case 20:
                        spinnerRecordDesignation = "Umbrella Repairer";
                        break;

                    case 21:
                        spinnerRecordDesignation = "Housekeeper";
                        break;

                    case 22:
                        spinnerRecordDesignation = "Babysitter";
                        break;

                    case 23:
                        spinnerRecordDesignation = "Truck renter";
                        break;

                    case 24:
                        spinnerRecordDesignation = "Tailor";
                        break;

                    case 25:
                        spinnerRecordDesignation = "Door Technician";
                        break;

                    case 26:
                        spinnerRecordDesignation = "Cobbler";
                        break;

                    case 27:
                        spinnerRecordDesignation = "Purohit";
                        break;

                    case 28:
                        spinnerRecordDesignation = "Kazi";
                        break;

                    case 29:
                        spinnerRecordDesignation = "Decoration Material Supplier";
                        break;

                    case 30:
                        spinnerRecordDesignation = "Lighting service";
                        break;

                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        genderSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                //Spinner Position Value
                switch (position){

                    case 0:
                        spinnerRecordGender = "None";
                        break;

                    case 1:
                        spinnerRecordGender = "Male";
                        break;

                    case 2:
                        spinnerRecordGender = "Female";
                        break;

                    case 3:
                        spinnerRecordGender = "Other";
                        break;

                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        workingExperienceSpinerType.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                //Spinner Position Value
                switch (position){

                    case 0:
                        spinnerRecordExperience = "None";
                        break;

                    case 1:
                        spinnerRecordExperience = "Month";
                        break;

                    case 2:
                        spinnerRecordExperience = "Year";
                        break;

                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        //Information Submission Button Action
        addWorkerDataBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Pattern patternPostCode = Pattern.compile(postCodeRegEx);
                Matcher matcherPostCode = patternPostCode.matcher(postcodeTxt.getText().toString());

                Pattern patternPhoneNumber = Pattern.compile(phoneNumberRegex);
                Matcher matcherPhoneNumber = patternPhoneNumber.matcher(phoneTxt.getText().toString());

                if(nameTxt.getText().toString().isEmpty()||nameTxt.getText().toString() == " "){

                    nameTxt.setError(getResources().getString(R.string.worker_name_error));
                    decoratedDialog(getResources().getString(R.string.worker_name_error));

                }else if(phoneTxt.getText().toString().isEmpty() || phoneTxt.getText().toString() == " "){

                    phoneTxt.setError(getResources().getString(R.string.worker_phone_error));
                    decoratedDialog(getResources().getString(R.string.worker_phone_error));

                }else if (addressTxt.getText().toString().isEmpty() || addressTxt.getText().toString() == " "){

                    addressTxt.setError(getResources().getString(R.string.worker_address_error));
                    decoratedDialog(getResources().getString(R.string.worker_address_error));

                }else if(areaNameTxt.getText().toString().isEmpty() || areaNameTxt.getText().toString() == " "){

                    areaNameTxt.setError(getResources().getString(R.string.worker_area_error));
                    decoratedDialog(getResources().getString(R.string.worker_area_error));

                }else if(spinnerRecordDesignation == "None"){

                    ((TextView)designationSpinner.getSelectedView()).setError("Error message");
                    decoratedDialog(getResources().getString(R.string.designation_spinner_error));

                }else if(spinnerRecordGender == "None"){

                    ((TextView)genderSpinner.getSelectedView()).setError("Error message");
                    decoratedDialog(getResources().getString(R.string.gender_spinner_error));

                } else if(spinnerRecordExperience == "None"){

                    ((TextView)workingExperienceSpinerType.getSelectedView()).setError("Error message");
                    decoratedDialog(getResources().getString(R.string.working_exp_spinner_error));

                }else if(cityNameTxt.getText().toString().isEmpty() || cityNameTxt.getText().toString() == " "){

                    cityNameTxt.setError(getResources().getString(R.string.worker_cityname_error));
                    decoratedDialog(getResources().getString(R.string.worker_cityname_error));

                }else if(passwordtxt.getText().toString().isEmpty() || !passwordtxt.getText().toString().equals(passwordConfirmTxt.getText().toString())){

                    passwordtxt.setError(getResources().getString(R.string.worker_password_error));
                    passwordConfirmTxt.setError(getResources().getString(R.string.worker_password_error));
                    decoratedDialog(getResources().getString(R.string.worker_password_error));

                }else if(passwordtxt.getText().toString().length()<6 && passwordConfirmTxt.getText().toString().length()<6){

                    passwordtxt.setError(getResources().getString(R.string.worker_password_length_error));
                    passwordConfirmTxt.setError(getResources().getString(R.string.worker_password_length_error));
                    decoratedDialog(getResources().getString(R.string.worker_password_length_error));

                }else if (experienceTxt.getText().toString().isEmpty() || experienceTxt.getText().toString() == " "){

                    experienceTxt.setError(getResources().getString(R.string.experience_range_error));
                    decoratedDialog(getResources().getString(R.string.experience_range_error));

                }else if (descriptionTxt.getText().toString().isEmpty() || descriptionTxt.getText().toString() == " "){

                    descriptionTxt.setError(getResources().getString(R.string.description_error));
                    decoratedDialog(getResources().getString(R.string.description_error));

                }else if (postcodeTxt.getText().toString().isEmpty() || postcodeTxt.getText().toString() == " "){

                    postcodeTxt.setError(getResources().getString(R.string.postcode_error));
                    decoratedDialog(getResources().getString(R.string.postcode_error));

                }else if(bitmap == null){

                    decoratedDialog(getResources().getString(R.string.image_select_error));

                }
                else if(matcherPostCode.matches() && matcherPhoneNumber.matches()){

                    submitInformation();
                    dialogRegEasy = ProgressDialog.show(WorkerRegistrationEasy.this, "", getResources().getString(R.string.progress_dialog_msg), true);

                }else {
                    decoratedDialog(getResources().getString(R.string.phone_post_error));
                }
                }
        });

        clearFieldBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                nameTxt.setText("");
                areaNameTxt.setText("");
                addressTxt.setText("");
                cityNameTxt.setText("");
                passwordtxt.setText("");
                passwordConfirmTxt.setText("");
                experienceTxt.setText("");
                descriptionTxt.setText("");
                postcodeTxt.setText("");
            }
        });

        imageChosser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showFileChooser();
            }
        });

    }


    private void showFileChooser() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE_REQUEST);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null) {
            Uri filePath = data.getData();
            try {
                //Getting the Bitmap from Gallery
                bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), filePath);
                Toast.makeText(this, ""+bitmap, Toast.LENGTH_SHORT).show();
                //Setting the Bitmap to ImageView
                imageChosser.setImageBitmap(bitmap);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    //Information Submission Function
    private void submitInformation(){

        final String name = nameTxt.getText().toString().trim();
        final String phone = phoneTxt.getText().toString().trim();
        final String address = addressTxt.getText().toString().trim();
        final String designation = spinnerRecordDesignation;

        final String postcode = postcodeTxt.getText().toString().trim();

        //Here is Designation(Next Time Implementation)

        final String area_name = areaNameTxt.getText().toString().trim();
        final String city_name = cityNameTxt.getText().toString().trim();
        final String password_storage = passwordtxt.getText().toString().trim();

        final String description = descriptionTxt.getText().toString().trim();

        final String experienceType = spinnerRecordExperience;
        final String experience = experienceTxt.getText().toString().trim();

        final String gender = spinnerRecordGender;

        final String account_type = accountType.getText().toString().trim();

        final String images = getStringImage(bitmap);


        //Geolocation Combiner
        String workerLocationAddress = address+","+area_name+","+city_name+postcode;

        Geocoder coder = new Geocoder(getApplicationContext());
        try {
            ArrayList<Address> adresses = (ArrayList<Address>) coder.getFromLocationName(workerLocationAddress, 50);
            for(Address add : adresses){
//                           if (statement) {//Controls to ensure it is right address such as country etc.
//                               double longitude = add.getLongitude();
//                               double latitude = add.getLatitude();
//                           }
                double longitudeAnalyse = add.getLongitude();
                double latitudeAnalyse = add.getLatitude();


                longitude = String.valueOf(longitudeAnalyse);
                latitude = String.valueOf(latitudeAnalyse);


            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        Toast.makeText(getApplicationContext(),latitude+","+longitude,Toast.LENGTH_SHORT).show();

        final StringRequest stringRequest = new StringRequest(Request.Method.POST, REGISTER_URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        LayoutInflater layoutInflater = LayoutInflater.from(WorkerRegistrationEasy.this);
                        View promptView = layoutInflater.inflate(R.layout.custom_dialog_alart, null);
                        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(WorkerRegistrationEasy.this);
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

                        dialogRegEasy.dismiss();

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                        LayoutInflater layoutInflater = LayoutInflater.from(WorkerRegistrationEasy.this);
                        View promptView = layoutInflater.inflate(R.layout.custom_dialog_alart, null);
                        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(WorkerRegistrationEasy.this);
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
                        dialogRegEasy.dismiss();
                    }

                }){
            @Override
            protected Map<String,String> getParams(){
                Map<String,String> params = new HashMap<String, String>();
                params.put(KEY_NAME,name);
                params.put(KEY_PHONE,bangladeshCountryCode+phone);
                params.put(KEY_ADDRESS,address);

                //Designation Here
                params.put(KEY_DESIGNATION, designation);

                params.put(KEY_AREA_NAME,area_name);
                params.put(KEY_CITY, city_name);
                params.put(PASSWORD_KEY, password_storage);

                params.put(LATITUDE_KEY, latitude);
                params.put(LONGITUDE_KEY, longitude);

//                params.put(KEY_GENDER, spinnerRecordGender);
                params.put(KEY_GENDER,gender);
                params.put(KEY_WORKINGEXPERIENCE, experience+" "+experienceType);
                params.put(KEY_DESCRIPTION, description);
                params.put(KEY_POSTCODE, postcode);
                params.put(KEY_ACCOUNT_TYPE, account_type);
                params.put(KEY_IMAGE_KEY, images);
                //Search Key is Here

                return params;
            }

        };

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }

    public void decoratedDialog(String dialogMsg){
        LayoutInflater layoutInflater = LayoutInflater.from(WorkerRegistrationEasy.this);
        View promptView = layoutInflater.inflate(R.layout.custom_dialog_alart, null);
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(WorkerRegistrationEasy.this);
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

    public String getStringImage(Bitmap bitmap){
        Log.i("Arm_Avi","" + bitmap);
        ByteArrayOutputStream baos = new  ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG,50, baos);
        byte [] b=baos.toByteArray();
        String temp= Base64.encodeToString(b, Base64.DEFAULT);
        return temp;
    }
}
