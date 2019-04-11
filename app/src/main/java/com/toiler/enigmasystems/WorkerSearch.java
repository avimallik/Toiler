package com.toiler.enigmasystems;

import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.speech.RecognizerIntent;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.toiler.enigmasystems.HttpParser.HttpParseClass;
import com.toiler.enigmasystems.adapter.ListViewAdapter;
import com.toiler.enigmasystems.workermodel.WorkerModel;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Locale;

import javax.security.auth.Subject;

import static com.toiler.enigmasystems.R.id.parent;

public class WorkerSearch extends AppCompatActivity {

    ListView listView;
    ArrayList<WorkerModel> UserList = new ArrayList<WorkerModel>();
//    String HttpURL = "http://www.armapprise.com/electrician.php";

    String HttpURL ;
    ListViewAdapter listViewAdapter;

    //Search Bar
    TextView searchBar ;

    //Voice recognition Button
    ImageView speechRecognitionBtn;

    //List View Item empty item message
    TextView networkStatus;

    //Speech recognition Components
    private final int SPEECH_RECOGNITION_CODE = 100;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_worker_search);
        this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);

        networkStatus = (TextView) findViewById(R.id.network_status_check);
        networkStatusCheck();
        //List View Item empty item message
        //Intent Value Catcher
        if(getIntent().hasExtra("electrician_110")){
            HttpURL = getIntent().getExtras().getString("electrician_110");
        }else if(getIntent().hasExtra("automobilemechanic_110")){
            HttpURL = getIntent().getExtras().getString("automobilemechanic_110");
        }else if(getIntent().hasExtra("electronicsmechanic_110")){
            HttpURL = getIntent().getExtras().getString("electronicsmechanic_110");
        }else if(getIntent().hasExtra("carpenter_110")){
            HttpURL = getIntent().getExtras().getString("carpenter_110");
        }else if(getIntent().hasExtra("housepainter_110")){
            HttpURL = getIntent().getExtras().getString("housepainter_110");
        }else if(getIntent().hasExtra("ac_fridegemechanic_110")){
            HttpURL = getIntent().getExtras().getString("ac_fridegemechanic_110");
        }else if(getIntent().hasExtra("meason_110")){
            HttpURL = getIntent().getExtras().getString("meason_110");
        }else if(getIntent().hasExtra("plumber_110")){
            HttpURL = getIntent().getExtras().getString("plumber_110");
        }else if(getIntent().hasExtra("locksmith_110")){
            HttpURL = getIntent().getExtras().getString("locksmith_110");
        }else if(getIntent().hasExtra("ironsmith_110")){
            HttpURL = getIntent().getExtras().getString("ironsmith_110");
        }else if(getIntent().hasExtra("goldsmith_110")){
            HttpURL = getIntent().getExtras().getString("goldsmith_110");
        }else if(getIntent().hasExtra("mobilerepearer_110")){
            HttpURL = getIntent().getExtras().getString("mobilerepearer_110");
        }else if(getIntent().hasExtra("interiordesigner_110")){
            HttpURL = getIntent().getExtras().getString("interiordesigner_110");
        }else if(getIntent().hasExtra("photographer_110")){
            HttpURL = getIntent().getExtras().getString("photographer_110");
        }else if(getIntent().hasExtra("wastecleaner_110")){
            HttpURL = getIntent().getExtras().getString("wastecleaner_110");
        }else if(getIntent().hasExtra("tvchennel_110")){
            HttpURL = getIntent().getExtras().getString("tvchennel_110");
        }else if(getIntent().hasExtra("internetservice_110")){
            HttpURL = getIntent().getExtras().getString("internetservice_110");
        }else if(getIntent().hasExtra("beautician_110")){
            HttpURL = getIntent().getExtras().getString("beautician_110");
        }else if(getIntent().hasExtra("cook_110")){
            HttpURL = getIntent().getExtras().getString("cook_110");
        }else if(getIntent().hasExtra("umbrella_110")){
            HttpURL = getIntent().getExtras().getString("umbrella_110");
        }else if(getIntent().hasExtra("housekeeper_110")){
            HttpURL = getIntent().getExtras().getString("housekeeper_110");
        }else if(getIntent().hasExtra("babysitter_110")){
            HttpURL = getIntent().getExtras().getString("babysitter_110");
        }else if(getIntent().hasExtra("truck_110")){
            HttpURL = getIntent().getExtras().getString("truck_110");
        }else if(getIntent().hasExtra("tailor_110")){
            HttpURL = getIntent().getExtras().getString("tailor_110");
        }else if(getIntent().hasExtra("coobler_110")){
            HttpURL = getIntent().getExtras().getString("coobler_110");
        }else if(getIntent().hasExtra("purohit_110")){
            HttpURL = getIntent().getExtras().getString("purohit_110");
        }else if(getIntent().hasExtra("kazi_110")){
            HttpURL = getIntent().getExtras().getString("kazi_110");
        }else if(getIntent().hasExtra("doortechnician_110")){
            HttpURL = getIntent().getExtras().getString("doortechnician_110");
        }else if(getIntent().hasExtra("decoration_110")){
            HttpURL = getIntent().getExtras().getString("decoration_110");
        }else if(getIntent().hasExtra("lighting_110")){
            HttpURL = getIntent().getExtras().getString("lighting_110");
        }

        //Code Start From Here
        //Search Bar Definition
        searchBar = (TextView) findViewById(R.id.searchBar);

        //Data Reload Button Definition
        speechRecognitionBtn = (ImageView) findViewById(R.id.speech_recognition);
        speechRecognitionBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                speechRecognizer();
            }
        });


        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                networkStatusCheck();
                new WorkerSearch.ParseJSonDataClass(WorkerSearch.this).execute();
            }
        });

        FloatingActionButton fabMap = (FloatingActionButton) findViewById(R.id.fabMap);
        fabMap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent mapIntent = new Intent(WorkerSearch.this, WorkerMap.class);
                mapIntent.putExtra("mapUrl", HttpURL);
                startActivity(mapIntent);
            }
        });

        new WorkerSearch.ParseJSonDataClass(this).execute();

        listView = (ListView) findViewById(R.id.listView1);
        listView.setTextFilterEnabled(true);
        searchBar.addTextChangedListener(new TextWatcher() {

            public void afterTextChanged(Editable s) {
            }

            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            public void onTextChanged(CharSequence stringVar, int start, int before, int count) {
                listViewAdapter.getFilter().filter(stringVar.toString());
            }
        });

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {

                WorkerModel dataPicker = (WorkerModel) adapterView.getItemAtPosition(position);
                Intent workerProfileIntent = new Intent(getApplicationContext(), WorkerProfile.class);

                workerProfileIntent.putExtra("worker_profile_name", dataPicker.getName());
                workerProfileIntent.putExtra("worker_profile_phone", dataPicker.getPhone());
                workerProfileIntent.putExtra("worker_profile_address", dataPicker.getAddress());
                workerProfileIntent.putExtra("worker_profile_area", dataPicker.getArea_name());
                workerProfileIntent.putExtra("worker_profile_designation", dataPicker.getDesignation());
                workerProfileIntent.putExtra("worker_profile_city", dataPicker.getCity());

                workerProfileIntent.putExtra("worker_profile_experience", dataPicker.getExperience());
                workerProfileIntent.putExtra("worker_profile_working_description", dataPicker.getDescription());
                workerProfileIntent.putExtra("worker_profile_gender", dataPicker.getGender());
                workerProfileIntent.putExtra("worker_profile_postcode", dataPicker.getPostcode());
                workerProfileIntent.putExtra("worker_profile_account_type", dataPicker.getAccount_type());
                workerProfileIntent.putExtra("worker_profile_image", dataPicker.getImage());
                workerProfileIntent.putExtra("worker_profile_status", dataPicker.getStatus());

                startActivity(workerProfileIntent);

//                if(position == 0){
//                    Toast.makeText(getApplicationContext(),"Hello",Toast.LENGTH_SHORT).show();
//                }else if(position == 1){
//                    Toast.makeText(getApplicationContext(),"Jello",Toast.LENGTH_SHORT).show();
//                }

            }
        });

        //ListView Item Counter Function Calling

    }

    private class ParseJSonDataClass extends AsyncTask<Void, Void, Void> {
        public Context context;
        String FinalJSonResult;

        public ParseJSonDataClass(Context context) {
            this.context = context;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected Void doInBackground(Void... arg0) {

            HttpParseClass httpParseClass = new HttpParseClass(HttpURL);
            try {
                httpParseClass.ExecutePostRequest();
                if (httpParseClass.getResponseCode() == 200) {
                    FinalJSonResult = httpParseClass.getResponse();
                    if (FinalJSonResult != null) {

                        JSONArray jsonArray = null;
                        try {

                            jsonArray = new JSONArray(FinalJSonResult);
                            JSONObject jsonObject;
                            WorkerModel user;

                            UserList = new ArrayList<WorkerModel>();

                            for (int i = 0; i < jsonArray.length(); i++) {

                                jsonObject = jsonArray.getJSONObject(i);

                                String Uname = jsonObject.getString("name").toString();

                                String Uphone = jsonObject.getString("phone").toString();

                                String Uaddress = jsonObject.getString("address").toString();

                                String Udesignation = jsonObject.getString("designation").toString();

                                String Uarea_name = jsonObject.getString("area_name").toString();

                                String Ucity = jsonObject.getString("city").toString();

                                String Ulatitude = jsonObject.getString("latitude").toString();

                                String Ulongitude =  jsonObject.getString("longitude").toString();

                                String Ugender = jsonObject.getString("gender").toString();

                                String Uexperience = jsonObject.getString("experience").toString();

                                String Udescription = jsonObject.getString("description").toString();

                                String Upostcode = jsonObject.getString("postcode").toString();

                                String Uaccount_type = jsonObject.getString("account_type").toString();

                                String Uimage = jsonObject.getString("image").toString();

                                String Ustatus = jsonObject.getString("status").toString();

                                user = new WorkerModel(Uname, Uphone, Uaddress, Udesignation, Uarea_name, Ucity, Ulatitude,Ulongitude, Ugender, Uexperience,
                                        Udescription, Upostcode, Uaccount_type, Uimage, Ustatus);

                                UserList.add(user);
                            }
                        } catch (JSONException e) {
                            // TODO Auto-generated catch block
                            e.printStackTrace();
                        }
                    }
                } else {

                    Toast.makeText(context, httpParseClass.getErrorMessage(), Toast.LENGTH_SHORT).show();
                }
            } catch (Exception e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void result){
//
            ProgressBar progressBar;
            progressBar = (ProgressBar) findViewById(R.id.progressbar);
            progressBar.setVisibility(View.INVISIBLE);
            listViewAdapter = new ListViewAdapter(WorkerSearch.this, R.layout.listview_items_layout, UserList);
            listView.setAdapter(listViewAdapter);
        }
    }

    //Speech Recognizer Function
    public void speechRecognizer(){
        Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.getDefault());
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL,
                RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
        intent.putExtra(RecognizerIntent.EXTRA_PROMPT,
                "Give Command to the Robolox");
        try {
            startActivityForResult(intent, SPEECH_RECOGNITION_CODE);
        } catch (ActivityNotFoundException a) {
            Toast.makeText(getApplicationContext(),
                    "Sorry this device not support voice recognition !",
                    Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case SPEECH_RECOGNITION_CODE: {
                if (resultCode == RESULT_OK && null != data) {
                    ArrayList<String> result = data
                            .getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
                    String recognizerString = result.get(0);
                    searchBar.setText(recognizerString);
                }
                break;
            }
        }
    }

    //Network Status Checking Function
    public void networkStatusCheck(){
        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        android.net.NetworkInfo wifi = cm
                .getNetworkInfo(ConnectivityManager.TYPE_WIFI);
        android.net.NetworkInfo datac = cm
                .getNetworkInfo(ConnectivityManager.TYPE_MOBILE);
        if ((wifi != null & datac != null)
                && (wifi.isConnected() | datac.isConnected())) {
            networkStatus.setVisibility(View.INVISIBLE);
        }else{
            networkStatus.setText(getResources().getString(R.string.network_status));
        }
    }
}
