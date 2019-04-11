package com.toiler.enigmasystems;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.Toast;

import com.toiler.enigmasystems.adapter.CustomAdapter;

import java.util.ArrayList;

public class WorkerGrid extends AppCompatActivity {

    CustomAdapter adapter;
    GridView gv;

    String electricianUrl = "http://www.armapprise.com/worker_api/electrician.php";
    String automobilemechanicUrl = "http://www.armapprise.com/worker_api/automobilemechanic.php";
    String electronicsmechanicUrl = "http://www.armapprise.com/worker_api/electronicsmechanic.php";
    String carpenterUrl = "http://www.armapprise.com/worker_api/carpenter.php";
    String housepainterUrl = "http://www.armapprise.com/worker_api/housepainter.php";
    String ac_fridegemechanicUrl = "http://www.armapprise.com/worker_api/ac&fridgemechanic.php";
    String measonUrl = "http://www.armapprise.com/worker_api/meson.php";
    String plumberUrl = "http://www.armapprise.com/worker_api/plumber.php";

    //New API URL
    String locksmithUrl = "http://www.armapprise.com/worker_api/locksmith.php";
    String ironsmithUrl = "http://www.armapprise.com/worker_api/Ironsmith.php";
    String goldsmithUrl = "http://www.armapprise.com/worker_api/Goldsmith.php";
    String mobilerepearerUrl = "http://www.armapprise.com/worker_api/MobileRepairer.php";
    String interiordesignerUrl = "http://www.armapprise.com/worker_api/InteriorDesigner.php";
    String photographer_graphicsdesignerUrl = "http://www.armapprise.com/worker_api/Photographer_Graphics%20Designer.php";
    String wastecleanerUrl = "http://www.armapprise.com/worker_api/Waste_Cleaner_Swiper.php";
    String tvchannelproviderUrl = "http://www.armapprise.com/worker_api/TV_Channel_Provider.php";
    String internetserviceproviderUrl = "http://www.armapprise.com/worker_api/isp.php";
    String beauticianUrl = "http://www.armapprise.com/worker_api/Beautician.php";
    String cookUrl = "http://www.armapprise.com/worker_api/Cook.php";
    String umbrellarepearerUrl = "http://www.armapprise.com/worker_api/UmbrellaRepairer.php";
    String housekeeperUrl = "http://www.armapprise.com/worker_api/Housekeeper.php";
    String babysitterUrl = "http://www.armapprise.com/worker_api/Babysitter.php";
    String truckrenterUrl = "http://www.armapprise.com/worker_api/Truck_renter.php";
    String tailorUrl = "http://www.armapprise.com/worker_api/Tailor.php";
    String cobblerUrl = "http://www.armapprise.com/worker_api/Cobbler.php";
    String purohitUrl = "http://www.armapprise.com/worker_api/Purohit.php";
    String kaziUrl = "http://www.armapprise.com/worker_api/Kazi.php";
    String doortechnicianUrl = "http://www.armapprise.com/worker_api/Door%20Technician.php";
    String decorationmaterialUrl = "http://www.armapprise.com/worker_api/Decoration_Material_Supplier.php";
    String lightingserviceUrl = "http://www.armapprise.com/worker_api/lighting_service.php";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_worker_grid);
        gv= (GridView) findViewById(R.id.gv);

        adapter=new CustomAdapter(this,getData());
        gv.setAdapter(adapter);
        gv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                if(position == 0){
                    Intent electricianIntent = new Intent(WorkerGrid.this, WorkerSearch.class);
                    electricianIntent.putExtra("electrician_110", electricianUrl);
                    startActivity(electricianIntent);
                }else if(position == 1){
                    Intent automobilemechanicIntent = new Intent(WorkerGrid.this, WorkerSearch.class);
                    automobilemechanicIntent.putExtra("automobilemechanic_110", automobilemechanicUrl);
                    startActivity(automobilemechanicIntent);
                }else if(position == 2){
                    Intent electronicsmechanicIntent = new Intent(WorkerGrid.this, WorkerSearch.class);
                    electronicsmechanicIntent.putExtra("electronicsmechanic_110", electronicsmechanicUrl);
                    startActivity(electronicsmechanicIntent);
                }else if(position == 3){
                    Intent carpenterIntent = new Intent(WorkerGrid.this, WorkerSearch.class);
                    carpenterIntent.putExtra("carpenter_110", carpenterUrl);
                    startActivity(carpenterIntent);
                }else if(position == 4){
                    Intent housepainterIntent = new Intent(WorkerGrid.this, WorkerSearch.class);
                    housepainterIntent.putExtra("housepainter_110", housepainterUrl);
                    startActivity(housepainterIntent);
                }else if(position == 5){
                    Intent ac_fridegemechanicIntent = new Intent(WorkerGrid.this, WorkerSearch.class);
                    ac_fridegemechanicIntent.putExtra("ac_fridegemechanic_110", ac_fridegemechanicUrl);
                    startActivity(ac_fridegemechanicIntent);
                }else if(position == 6){
                    Intent measonIntent = new Intent(WorkerGrid.this, WorkerSearch.class);
                    measonIntent.putExtra("meason_110", measonUrl);
                    startActivity(measonIntent);
                }else if(position == 7){
                    Intent pluberIntent = new Intent(WorkerGrid.this, WorkerSearch.class);
                    pluberIntent.putExtra("plumber_110", plumberUrl);
                    startActivity(pluberIntent);
                }else if(position == 8){
                    Intent locksmithIntent = new Intent(WorkerGrid.this, WorkerSearch.class);
                    locksmithIntent.putExtra("locksmith_110", locksmithUrl);
                    startActivity(locksmithIntent);
                }else if(position == 9){
                    Intent ironsmithIntent = new Intent(WorkerGrid.this, WorkerSearch.class);
                    ironsmithIntent.putExtra("ironsmith_110", ironsmithUrl);
                    startActivity(ironsmithIntent);
                }else if(position == 10){
                    Intent goldsmithIntent = new Intent(WorkerGrid.this, WorkerSearch.class);
                    goldsmithIntent.putExtra("goldsmith_110", goldsmithUrl);
                    startActivity(goldsmithIntent);
                }else if(position == 11){
                    Intent mobilerepearrerIntent = new Intent(WorkerGrid.this, WorkerSearch.class);
                    mobilerepearrerIntent.putExtra("mobilerepearer_110", mobilerepearerUrl);
                    startActivity(mobilerepearrerIntent);
                }else if(position == 12){
                    Intent inteririordesignerIntent = new Intent(WorkerGrid.this, WorkerSearch.class);
                    inteririordesignerIntent.putExtra("interiordesigner_110", interiordesignerUrl);
                    startActivity(inteririordesignerIntent);
                }else if(position == 13){
                    Intent photographerIntent = new Intent(WorkerGrid.this, WorkerSearch.class);
                    photographerIntent.putExtra("photographer_110", photographer_graphicsdesignerUrl);
                    startActivity(photographerIntent);
                }else if(position == 14){
                    Intent wastecleanerIntent = new Intent(WorkerGrid.this, WorkerSearch.class);
                    wastecleanerIntent.putExtra("wastecleaner_110", wastecleanerUrl);
                    startActivity(wastecleanerIntent);
                }else if(position == 15){
                    Intent tvchennelIntent = new Intent(WorkerGrid.this, WorkerSearch.class);
                    tvchennelIntent.putExtra("tvchennel_110", tvchannelproviderUrl);
                    startActivity(tvchennelIntent);
                }else if(position == 16){
                    Intent internetserviceIntent = new Intent(WorkerGrid.this, WorkerSearch.class);
                    internetserviceIntent.putExtra("internetservice_110", internetserviceproviderUrl);
                    startActivity(internetserviceIntent);
                }else if(position == 17){
                    Intent beauticianIntent = new Intent(WorkerGrid.this, WorkerSearch.class);
                    beauticianIntent.putExtra("beautician_110", beauticianUrl);
                    startActivity(beauticianIntent);
                }else if(position == 18){
                    Intent cookIntent = new Intent(WorkerGrid.this, WorkerSearch.class);
                    cookIntent.putExtra("cook_110", cookUrl);
                    startActivity(cookIntent);
                }else if(position == 19){
                    Intent umbrellaIntent = new Intent(WorkerGrid.this, WorkerSearch.class);
                    umbrellaIntent.putExtra("umbrella_110", umbrellarepearerUrl);
                    startActivity(umbrellaIntent);
                }else if(position == 20){
                    Intent houskeeperIntent = new Intent(WorkerGrid.this, WorkerSearch.class);
                    houskeeperIntent.putExtra("housekeeper_110", housekeeperUrl);
                    startActivity(houskeeperIntent);
                }else if(position == 21){
                    Intent babysitterIntent = new Intent(WorkerGrid.this, WorkerSearch.class);
                    babysitterIntent.putExtra("babysitter_110", babysitterUrl);
                    startActivity(babysitterIntent);
                }else if(position == 22){
                    Intent truckrentIntent = new Intent(WorkerGrid.this, WorkerSearch.class);
                    truckrentIntent.putExtra("truck_110", truckrenterUrl);
                    startActivity(truckrentIntent);
                }else if(position == 23){
                    Intent tailorIntent = new Intent(WorkerGrid.this, WorkerSearch.class);
                    tailorIntent.putExtra("tailor_110", tailorUrl);
                    startActivity(tailorIntent);
                }else if(position == 24){
                    Intent coolerIntent = new Intent(WorkerGrid.this, WorkerSearch.class);
                    coolerIntent.putExtra("coobler_110", cobblerUrl);
                    startActivity(coolerIntent);
                }else if(position == 25){
                    Intent purohitIntent = new Intent(WorkerGrid.this, WorkerSearch.class);
                    purohitIntent.putExtra("purohit_110", purohitUrl);
                    startActivity(purohitIntent);
                }else if(position == 26){
                    Intent kaziIntent = new Intent(WorkerGrid.this, WorkerSearch.class);
                    kaziIntent.putExtra("kazi_110", kaziUrl);
                    startActivity(kaziIntent);
                }else if(position == 27){
                    Intent doortechnicianIntent = new Intent(WorkerGrid.this, WorkerSearch.class);
                    doortechnicianIntent.putExtra("doortechnician_110", doortechnicianUrl);
                    startActivity(doortechnicianIntent);
                }else if(position == 28){
                    Intent decorationIntent = new Intent(WorkerGrid.this, WorkerSearch.class);
                    decorationIntent.putExtra("decoration_110", decorationmaterialUrl);
                    startActivity(decorationIntent);
                }else if(position == 29){
                    Intent lightingIntent = new Intent(WorkerGrid.this, WorkerSearch.class);
                    lightingIntent.putExtra("lighting_110", lightingserviceUrl);
                    startActivity(lightingIntent);
                }
            }
        });
    }

    private ArrayList getData()
    {
        ArrayList<Model> models = new ArrayList<>();

        Model s=new Model();
        s.setName(getString(R.string.electrician_string));
        s.setImage(R.drawable.electrician_ico);
        models.add(s);

        s=new Model();
        s.setName(getString(R.string.automobile_string));
        s.setImage(R.drawable.automobile_mechanic);
        models.add(s);


        s=new Model();
        s.setName(getString(R.string.electronics_string));
        s.setImage(R.drawable.electronics);
        models.add(s);


        s=new Model();
        s.setName(getString(R.string.carpenter_string));
        s.setImage(R.drawable.carpenter_ico);
        models.add(s);

        s=new Model();
        s.setName(getString(R.string.house_painter_string));
        s.setImage(R.drawable.house_painter);
        models.add(s);

        s=new Model();
        s.setName(getString(R.string.fridge_string));
        s.setImage(R.drawable.fridge_ico);
        models.add(s);

        s=new Model();
        s.setName(getString(R.string.meson_string));
        s.setImage(R.drawable.meson_ico);
        models.add(s);

        s=new Model();
        s.setName(getString(R.string.plumber_string));
        s.setImage(R.drawable.plumber_ico);
        models.add(s);

        //New Grid Item
        s=new Model();
        s.setName(getString(R.string.locksmith_string));
        s.setImage(R.drawable.locksmith);
        models.add(s);

        s=new Model();
        s.setName(getString(R.string.iron_smith_string));
        s.setImage(R.drawable.ironsmith);
        models.add(s);

        s=new Model();
        s.setName(getString(R.string.gold_smith_string));
        s.setImage(R.drawable.goldsmith);
        models.add(s);

        s=new Model();
        s.setName(getString(R.string.mobile_technician_string));
        s.setImage(R.drawable.mobilerepair);
        models.add(s);

        s=new Model();
        s.setName(getString(R.string.interior_designer_string));
        s.setImage(R.drawable.interiordesigner);
        models.add(s);

        s=new Model();
        s.setName(getString(R.string.photo_grapher_string));
        s.setImage(R.drawable.photographer);
        models.add(s);

        s=new Model();
        s.setName(getString(R.string.waste_cleaner_string));
        s.setImage(R.drawable.wastecleaner);
        models.add(s);

        s=new Model();
        s.setName(getString(R.string.dish_provider_string));
        s.setImage(R.drawable.tvchannel);
        models.add(s);

        s=new Model();
        s.setName(getString(R.string.isp_provider_string));
        s.setImage(R.drawable.internet);
        models.add(s);

        s=new Model();
        s.setName(getString(R.string.beautician_string));
        s.setImage(R.drawable.beautician);
        models.add(s);

        s=new Model();
        s.setName(getString(R.string.cook_string));
        s.setImage(R.drawable.cook);
        models.add(s);

        s=new Model();
        s.setName(getString(R.string.umbrella_repair_string));
        s.setImage(R.drawable.umbrella);
        models.add(s);

        //New Grid Item 2
        s=new Model();
        s.setName(getString(R.string.housekeeper_string));
        s.setImage(R.drawable.housekepper);
        models.add(s);


        s=new Model();
        s.setName(getString(R.string.baby_sitter_string));
        s.setImage(R.drawable.babysitter);
        models.add(s);

        s=new Model();
        s.setName(getString(R.string.house_shifter_string));
        s.setImage(R.drawable.truck);
        models.add(s);

        s=new Model();
        s.setName(getString(R.string.tailors_string));
        s.setImage(R.drawable.tailor);
        models.add(s);

        s=new Model();
        s.setName(getString(R.string.cobbler_string));
        s.setImage(R.drawable.cobbler);
        models.add(s);

        s=new Model();
        s.setName(getString(R.string.purohit_string));
        s.setImage(R.drawable.purohit);
        models.add(s);

        s=new Model();
        s.setName(getString(R.string.kazi_string));
        s.setImage(R.drawable.kazi);
        models.add(s);


        s=new Model();
        s.setName(getString(R.string.door_technician_string));
        s.setImage(R.drawable.door);
        models.add(s);


        s=new Model();
        s.setName(getString(R.string.wedding_material_supplier_string));
        s.setImage(R.drawable.decoation);
        models.add(s);

        s=new Model();
        s.setName(getString(R.string.light_illumination_service_string));
        s.setImage(R.drawable.lighting);
        models.add(s);

        return models;
    }



}
