package cz.rehamza.arboretum_client;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Build;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageRequest;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONException;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private TextView lbl_sign_in;
    private ProgressBar prg_sign_in;
    private Button btn_get_data;
    private Button btn_switch;
    private TextView txt_data;
    private TextView txt_tree_name;
    private TextView txt_planted;
    private TextView txt_height;
    private ImageView img_photo;
    private TextView txt_description;
    private TextView txt_distance;
    private TextView txt_closest_name;
    private TextView txt_latin;
    private TextView txt_origin;
    private TextView btn_navigation;
    private ImageView img_needle;
    private CheckBox chBoxVisited;
    private ImageView imgCloseEnough;

    private LocationListener locationListener;
    private LocationManager locationManager;

    private List<String> dloadNames;
    private File localFile;
    private File localTreesDirectory;
    private File localImgDirectory;
    private File photoPath;
    private File displayedImage;
    private File treesPath;
    private FileOutputStream fos;
    private boolean imagesExist;
    private boolean dataExist;
    private int count;
    private int obtained;
    private int errorCount;
    private ProgressDialog progressDload;
    private ImageView imgLogo1;
    private ImageView imgLogo2;
    private int navigationIndex = -1;
    private boolean receivingLocations = false;

    private double treeDistance;
    private int closestIndex;

    private final String IP = "10.0.6.189";
    //private final String IP = "10.12.0.2";
    private final String ADDRESS = String.format("http://%s/arboretum_api/", IP);
    private RequestQueue queue;
    private final String ADDRESS_IMG = String.format("http://%s/arboretum_img/", IP);
    private final int NAVIGATE_TO_REQUEST = 7;
    private SensorManager sm;
    private Sensor sensorOrientation;
    private SensorEventListener sensorListener;
    public Location lastLocation;
    public Location tempTreeLocation;

    private Trees trees;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setLogo(R.mipmap.ic_hamza_round);
        getSupportActionBar().setDisplayUseLogoEnabled(true);
        setContentView(R.layout.activity_main);

        lbl_sign_in = findViewById(R.id.lbl_login);
        prg_sign_in = findViewById(R.id.prg_sing_in);
        btn_get_data = findViewById(R.id.btn_get_data);
        btn_switch = findViewById(R.id.sw_locate);
        txt_data = findViewById(R.id.txt_data);
        txt_tree_name = findViewById(R.id.lbl_tree_name);
        txt_planted = findViewById(R.id.lbl_planted);
        txt_height = findViewById(R.id.lbl_height);
        img_photo = findViewById(R.id.img_tree_photo);
        txt_description = findViewById(R.id.txt_description);
        txt_distance = findViewById(R.id.txt_distance);
        txt_closest_name = findViewById(R.id.txt_closest_name);
        txt_latin = findViewById(R.id.lbl_tree_latin);
        txt_origin = findViewById(R.id.lbl_origin);
        imgLogo1 = findViewById(R.id.img_logo1);
        imgLogo2 = findViewById(R.id.img_logo2);
        btn_navigation = findViewById(R.id.btn_navigation);
        img_needle = findViewById(R.id.img_needle);
        chBoxVisited = findViewById(R.id.chBox_visited);
        imgCloseEnough = findViewById(R.id.img_close_enough);

        sm = (SensorManager)getSystemService(SENSOR_SERVICE);
        sensorOrientation = sm.getDefaultSensor(Sensor.TYPE_ORIENTATION);
        sensorListener = new SensorEventListener(this, img_needle);

        progressDload = new ProgressDialog(this);
        progressDload.setTitle("Stahování obrázků...");
        progressDload.setMessage("Prosím, nevypínejte aplikaci. Dokončeno: x/y");

        locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);

        locationListener = new LocationListener() {
            @Override
            public void onLocationChanged(Location location) {
                if(trees.size() > 0){
                    lastLocation = location;
                    if(navigationIndex == -1){
                        getClosestTree(location.getLatitude(), location.getLongitude());
                        txt_distance.setText((int)treeDistance + " m");
                        if(treeDistance <= 20){
                            img_needle.setVisibility(View.INVISIBLE);
                            imgCloseEnough.setVisibility(View.VISIBLE);
                        }
                        else{
                            imgCloseEnough.setVisibility(View.INVISIBLE);
                            img_needle.setVisibility(View.VISIBLE);
                        }
                        txt_closest_name.setText(trees.get(closestIndex).getName());
                        txt_distance.setVisibility(View.VISIBLE);
                        txt_closest_name.setVisibility(View.VISIBLE);
                        btn_navigation.setVisibility(View.INVISIBLE);
                        tempTreeLocation = new Location("program");
                        tempTreeLocation.setLatitude(Double.parseDouble(trees.get(closestIndex).getLat()));
                        tempTreeLocation.setLongitude(Double.parseDouble(trees.get(closestIndex).getLon()));
                        sm.registerListener(sensorListener, sensorOrientation, SensorManager.SENSOR_DELAY_NORMAL);
                        if(treeDistance <= 20){
                            openTreeInfo(closestIndex);
                        }
                        else{
                            txt_tree_name.setVisibility(View.INVISIBLE);
                            txt_latin.setVisibility(View.INVISIBLE);
                            txt_origin.setVisibility(View.INVISIBLE);
                            txt_planted.setVisibility(View.INVISIBLE);
                            chBoxVisited.setVisibility(View.INVISIBLE);
                            txt_height.setVisibility(View.INVISIBLE);
                            img_photo.setVisibility(View.INVISIBLE);
                            txt_description.setVisibility(View.INVISIBLE);
                            imgLogo1.setVisibility(View.VISIBLE);
                            imgLogo2.setVisibility(View.VISIBLE);
                        }
                    }
                    else{
                        treeDistance = calculateDistance(location.getLatitude(), location.getLongitude(), trees.get(navigationIndex).getLat(), trees.get(navigationIndex).getLon());
                        txt_distance.setText((int)treeDistance + " m");
                        if(treeDistance <= 20){
                            img_needle.setVisibility(View.INVISIBLE);
                            imgCloseEnough.setVisibility(View.VISIBLE);
                        }
                        else{
                            imgCloseEnough.setVisibility(View.INVISIBLE);
                            img_needle.setVisibility(View.VISIBLE);
                        }
                        txt_closest_name.setText(trees.get(navigationIndex).getName());
                        txt_distance.setVisibility(View.VISIBLE);
                        txt_closest_name.setVisibility(View.VISIBLE);
                        btn_navigation.setVisibility(View.VISIBLE);
                        tempTreeLocation = new Location("program");
                        tempTreeLocation.setLatitude(Double.parseDouble(trees.get(navigationIndex).getLat()));
                        tempTreeLocation.setLongitude(Double.parseDouble(trees.get(navigationIndex).getLon()));
                        sm.registerListener(sensorListener, sensorOrientation, SensorManager.SENSOR_DELAY_NORMAL);
                    }
                }
                else{
                    btn_switch.setBackground(ContextCompat.getDrawable(MainActivity.this, R.drawable.button_rounded_off));
                    btn_switch.setText(R.string.sw_off);
                    txt_distance.setVisibility(View.INVISIBLE);
                    txt_closest_name.setVisibility(View.INVISIBLE);
                    btn_navigation.setVisibility(View.INVISIBLE);
                    imgLogo1.setVisibility(View.VISIBLE);
                    imgLogo2.setVisibility(View.VISIBLE);
                    receivingLocations = false;
                    navigationIndex = -1;
                    prg_sign_in.setVisibility(View.INVISIBLE);
                    lbl_sign_in.setVisibility(View.INVISIBLE);
                    lbl_sign_in.setText(getString(R.string.search_trees));
                    locationManager.removeUpdates(locationListener);
                    Toast.makeText(MainActivity.this, "Data nejsou k dispozici", Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onStatusChanged(String s, int i, Bundle bundle) {

            }

            @Override
            public void onProviderEnabled(String s) {

            }

            @Override
            public void onProviderDisabled(String s) {

                Intent i = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                startActivity(i);
            }
        };

        trees = new Trees();

        queue = Volley.newRequestQueue(MainActivity.this);

        giveBtnListeners();
        localTreesDirectory = new File(getApplicationInfo().dataDir + "/trees");
        if(!localTreesDirectory.exists()){
            localTreesDirectory.mkdirs();
        }
        localImgDirectory = new File(getApplicationInfo().dataDir + "/img");
        if(!localImgDirectory.exists()){
            localImgDirectory.mkdirs();
        }
        treesPath = new File(localTreesDirectory + "/treeslist.bin");
        photoPath = new File(localImgDirectory + "/");

        prg_sign_in.setVisibility(View.INVISIBLE);
        lbl_sign_in.setVisibility(View.INVISIBLE);
        btn_switch.setVisibility(View.VISIBLE);
        txt_data.setVisibility(View.VISIBLE);
        btn_get_data.setVisibility(View.VISIBLE);

        checkDirectories();
        getLocation();
    }

    public void onSensorChanged(SensorEvent event){

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_settings:
                Intent intentList = new Intent(MainActivity.this, ListActivity.class);
                intentList.putExtra("treesPath", treesPath);
                intentList.putExtra("imgPath", localImgDirectory);
                startActivityForResult(intentList, NAVIGATE_TO_REQUEST);
                return true;

            default:
                // If we got here, the user's action was not recognized.
                // Invoke the superclass to handle it.
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data){
        if(requestCode == NAVIGATE_TO_REQUEST){
            navigationIndex = data.getIntExtra("resultIndex", -1);
            if(navigationIndex == -1)
                receivingLocations = false;
            else {
                receivingLocations = true;
                txt_closest_name.setText(trees.get(navigationIndex).getName());
            }
        }
    }

    @Override
    public void onResume() {
        super.onResume();

        checkDirectories();
        if(dataExist){
            trees.load(treesPath);
        }
        if(navigationIndex != -1){
            openTreeInfo(navigationIndex);
            if(receivingLocations){
                locationToggle();
            }
            txt_closest_name.setVisibility(View.VISIBLE);
            btn_navigation.setVisibility(View.VISIBLE);
        }
    }

    private void openTreeInfo(int index){
        prg_sign_in.setVisibility(View.INVISIBLE);
        lbl_sign_in.setVisibility(View.INVISIBLE);
        imgLogo1.setVisibility(View.INVISIBLE);
        imgLogo2.setVisibility(View.INVISIBLE);

        if(trees.get(index).getName() == null || trees.get(index).getName().equals("")){
            txt_tree_name.setVisibility(View.INVISIBLE);
        }
        else{
            txt_tree_name.setText(trees.get(index).getName());
            txt_tree_name.setVisibility(View.VISIBLE);
        }
        if(trees.get(index).getLatin() == null || trees.get(index).getLatin().equals("") || trees.get(index).getLatin().equals("Zadejte název místa")){
            txt_latin.setVisibility(View.INVISIBLE);
        }
        else{
            txt_latin.setText(trees.get(index).getLatin());
            txt_latin.setVisibility(View.VISIBLE);
        }
        if(trees.get(index).getOrigin() == null || trees.get(index).getOrigin().equals("") || trees.get(index).getOrigin().equals("Neuvedeno")){
            txt_origin.setVisibility(View.INVISIBLE);
        }
        else{
            txt_origin.setText("Původ: " + trees.get(index).getOrigin());
            txt_origin.setVisibility(View.VISIBLE);
        }
        if(trees.get(index).getPlanted() == null || trees.get(index).getPlanted().equals("") || trees.get(index).getPlanted().equals("Neuvedeno")){
            txt_planted.setVisibility(View.INVISIBLE);
        }
        else{
            txt_planted.setText("Zasazeno: " + trees.get(index).getPlanted());
            txt_planted.setVisibility(View.VISIBLE);
        }
        if(trees.get(index).getHeight() == null || trees.get(index).getHeight().equals("") || trees.get(index).getHeight().equals("Neuvedeno")){
            txt_height.setVisibility(View.INVISIBLE);
        }
        else{
            txt_height.setText("Výška: " + trees.get(index).getHeight() + " m");
            txt_height.setVisibility(View.VISIBLE);
        }
        if(trees.get(index).getDescription() == null || trees.get(index).getDescription().equals("")){
            txt_description.setVisibility(View.INVISIBLE);
        }
        else{
            txt_description.setText(trees.get(index).getDescription());
            txt_description.setVisibility(View.VISIBLE);
        }

        if(trees.get(index).getImageCode() == null || trees.get(index).getImageCode().equals("None")){
            img_photo.setVisibility(View.INVISIBLE);
        }
        else{
            displayedImage = new File (localImgDirectory + "/" + trees.get(index).getImageCode() + ".jpg");
            if(displayedImage.exists()){
                Bitmap tempBitmap = BitmapFactory.decodeFile(displayedImage.getAbsolutePath());
                img_photo.setImageBitmap(tempBitmap);
                img_photo.setVisibility(View.VISIBLE);
            }
            else{
                img_photo.setVisibility(View.INVISIBLE);
            }
        }
        chBoxVisited.setChecked(trees.get(index).hasBeenVisited());
        chBoxVisited.setVisibility(View.VISIBLE);
    }

    private void getClosestTree(double lat1, double lon1){
        double shortestDistance;
        double newDistance;
        int index = 0;
        shortestDistance = calculateDistance(lat1, lon1, trees.get(0).getLat(), trees.get(0).getLon());
        for(int i = 1; i < trees.size(); i++){
            newDistance = calculateDistance(lat1, lon1, trees.get(i).getLat(), trees.get(i).getLon());
            if(newDistance < shortestDistance){
                shortestDistance = newDistance;
                index = i;
            }
        }
        treeDistance = shortestDistance;
        closestIndex = index;
    }

    private double calculateDistance(double lat1, double lon1, String lat2str, String lon2str) {
        double lat2 = Double.parseDouble(lat2str);
        double lon2 = Double.parseDouble(lon2str);
        double theta = lon1 - lon2;
        double dist = Math.sin(deg2rad(lat1)) * Math.sin(deg2rad(lat2))
                + Math.cos(deg2rad(lat1)) * Math.cos(deg2rad(lat2))
                * Math.cos(deg2rad(theta));
        dist = Math.acos(dist);
        dist = rad2deg(dist);
        dist = dist * 60;
        dist = dist * 1852;
        return (dist);
    }

    private double deg2rad(double deg) {
        return (deg * Math.PI / 180.0);
    }

    private double rad2deg(double rad) {
        return (rad * 180.0 / Math.PI);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode){
            case 10:
                getLocation();
                break;
            default:
                break;
        }
    }

    private void getLocation(){
        if (ActivityCompat.checkSelfPermission(MainActivity.this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(MainActivity.this, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                requestPermissions(new String[]{android.Manifest.permission.ACCESS_COARSE_LOCATION, android.Manifest.permission.ACCESS_FINE_LOCATION, android.Manifest.permission.INTERNET}
                        ,10);
            }

        }
        else{
            receivingLocations = true;
            locationManager.requestLocationUpdates("gps", 1000, 0, locationListener);
            btn_switch.setBackground(ContextCompat.getDrawable(MainActivity.this, R.drawable.button_rounded_on));
            btn_switch.setText(R.string.sw_on);
        }
    }

    @Override
    protected void onPause(){
        super.onPause();
        btn_switch.setBackground(ContextCompat.getDrawable(MainActivity.this, R.drawable.button_rounded_off));
        btn_switch.setText(getString(R.string.sw_off));
        locationManager.removeUpdates(locationListener);
        txt_tree_name.setVisibility(View.INVISIBLE);
        txt_latin.setVisibility(View.INVISIBLE);
        txt_origin.setVisibility(View.INVISIBLE);
        txt_planted.setVisibility(View.INVISIBLE);
        txt_height.setVisibility(View.INVISIBLE);
        chBoxVisited.setVisibility(View.INVISIBLE);
        img_photo.setVisibility(View.INVISIBLE);
        txt_description.setVisibility(View.INVISIBLE);
        imgLogo1.setVisibility(View.VISIBLE);
        imgLogo2.setVisibility(View.VISIBLE);
        navigationIndex = -1;
        receivingLocations = false;
        txt_distance.setVisibility(View.INVISIBLE);
        txt_closest_name.setVisibility(View.INVISIBLE);
        btn_navigation.setVisibility(View.INVISIBLE);
        img_needle.setVisibility(View.INVISIBLE);
        imgCloseEnough.setVisibility(View.INVISIBLE);
        sm.unregisterListener(sensorListener);
    }

    private void giveBtnListeners(){
        btn_get_data.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkDirectories();

                AlertDialog.Builder a_builder = new AlertDialog.Builder(MainActivity.this);
                a_builder.setTitle("Upozornění");
                a_builder.setMessage(R.string.dload_message);
                a_builder.setPositiveButton("Ano", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if(imagesExist){
                            checkDirectories();
                            loadTrees(treesPath, photoPath);
                            checkDirectories();
                        }
                        else{
                            checkDirectories();
                            loadTrees(treesPath, photoPath);
                            checkDirectories();
                        }
                    }
                })
                        .setNegativeButton("Ne", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.cancel();
                            }
                        });
                AlertDialog alert = a_builder.create();
                alert.show();
            }
        });
        btn_switch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                locationToggle();
            }
        });
        btn_navigation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                navigationIndex = -1;
                btn_navigation.setVisibility(View.INVISIBLE);
                txt_tree_name.setVisibility(View.INVISIBLE);
                txt_latin.setVisibility(View.INVISIBLE);
                txt_origin.setVisibility(View.INVISIBLE);
                txt_planted.setVisibility(View.INVISIBLE);
                txt_height.setVisibility(View.INVISIBLE);
                chBoxVisited.setVisibility(View.INVISIBLE);
                img_photo.setVisibility(View.INVISIBLE);
                txt_description.setVisibility(View.INVISIBLE);
            }
        });
        chBoxVisited.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView,boolean isChecked) {
                if(navigationIndex == -1)
                    trees.get(closestIndex).setVisited(isChecked);
                else
                    trees.get(navigationIndex).setVisited(isChecked);
                trees.save(treesPath);
            }
        });
    }

    public void locationToggle(){
        if (receivingLocations){
            receivingLocations = false;
            btn_switch.setBackground(ContextCompat.getDrawable(MainActivity.this, R.drawable.button_rounded_off));
            btn_switch.setText(R.string.sw_off);
            txt_distance.setVisibility(View.INVISIBLE);
            txt_closest_name.setVisibility(View.INVISIBLE);
            btn_navigation.setVisibility(View.INVISIBLE);
            img_needle.setVisibility(View.INVISIBLE);
            imgCloseEnough.setVisibility(View.INVISIBLE);
            prg_sign_in.setVisibility(View.INVISIBLE);
            lbl_sign_in.setVisibility(View.INVISIBLE);
            lbl_sign_in.setText(getString(R.string.search_trees));
            locationManager.removeUpdates(locationListener);
            sm.unregisterListener(sensorListener);
        }
        else{
            receivingLocations = true;
            btn_switch.setBackground(ContextCompat.getDrawable(MainActivity.this, R.drawable.button_rounded_on));
            btn_switch.setText(R.string.sw_on);
            lbl_sign_in.setText(getString(R.string.search_trees));
            getLocation();
        }
    }

    private void checkDirectories(){
        if (localImgDirectory.exists()){

            File[] contents = localImgDirectory.listFiles();
            if(contents == null){
                //Slozka nenalezena/neni spravne rozpoznana
                imagesExist = false;
            }
            else if (contents.length == 0) {
                //Slozka existuje, ale je prazdna
                imagesExist = false;
            }
            else{
                //Slozka obsahuje soubory
                imagesExist = true;
            }
        }
        else{
            localImgDirectory.mkdirs();
            checkDirectories();
        }
        if (localTreesDirectory.exists()){

            File[] contents = localTreesDirectory.listFiles();
            if(contents == null){
                //Slozka nenalezena/neni spravne rozpoznana
                dataExist = false;
            }
            else if (contents.length == 0) {
                //Slozka existuje, ale je prazdna
                dataExist = false;
            }
            else{
                //Slozka obsahuje soubory
                dataExist = true;
            }
        }
        else{
            localTreesDirectory.mkdirs();
            checkDirectories();
        }
        if(dataExist || imagesExist){
            txt_data.setTextColor(getResources().getColor(R.color.good));
            txt_data.setText("Data nalezena");
        }
        else{
            txt_data.setTextColor(getResources().getColor(R.color.bad));
            txt_data.setText("Data chybí");
        }
    }

    private void downloadImages(){
        count = 0;
        obtained = 0;
        errorCount = 0;

        dloadNames = new ArrayList<>();

        for (int i = 0; i < trees.size(); i++){
            if(trees.get(i).getImageCode() == null || trees.get(i).getImageCode().equals("None")){

            }
            else{
                count++;
                dloadNames.add(trees.get(i).getImageCode());
            }
        }

        progressDload.setMessage("Prosím, nevypínejte aplikaci. Dokončeno: " + obtained + "/" + count);
        progressDload.show();

        for (String imageName : dloadNames){
            localFile = new File(localImgDirectory, imageName);

        }
    }

    private void showDloadDialog(){
        AlertDialog.Builder a_builder = new AlertDialog.Builder(MainActivity.this);
        a_builder.setTitle("Upozornění");
        a_builder.setMessage("Přejete si stáhnout fotografie? Může to trvat několiv minut.");
        a_builder.setPositiveButton("Ano", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                downloadImages();
            }
        })
                .setNegativeButton("Ne", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });
        AlertDialog alert = a_builder.create();
        alert.show();
    }

    private void makeRequest(Request request) {
        if (request != null) {
            queue.add(request);
        }
    }

    public void loadTrees(final File localTreesPath, final File localImgPath) {
        String url = ADDRESS + "list.php";

        Request jsonArrayRequest = new JsonArrayRequest(
                url,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        try {
                            trees.clear();

                            for (int i = 0; i < response.length(); i++) {
                                Tree tree = new Tree((JSONObject) response.get(i));
                                trees.add(tree);
                            }
                            trees.save(localTreesPath);
                            txt_data.setTextColor(getResources().getColor(R.color.good));
                            downloadImages(localImgPath);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
                });

        makeRequest(jsonArrayRequest);
    }

    private void downloadImages(final File localImgPath) {
        count = 0;
        obtained = 0;

        File[] content = localImgPath.listFiles();
        for(File file : content){
            file.delete();
        }

        dloadNames = new ArrayList<>();

        String url = ADDRESS + "get_img_names.php";
        Request jsonArrayRequest = new JsonArrayRequest(
                url,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        try {
                            dloadNames.clear();

                            for (int i = 0; i < response.length(); i++) {
                                if (response.getString(i).equals("ignore")) {
                                    //not counted
                                } else {
                                    count++;
                                    dloadNames.add(response.getString(i));
                                }
                            }

                            if(obtained >= count) {
                                progressDload.dismiss();
                                Toast.makeText(MainActivity.this, "Dokončeno", Toast.LENGTH_LONG).show();
                                checkDirectories();
                            }
                            else{
                                progressDload.setMessage("Prosím, nevypínejte aplikaci. Dokončeno: " + obtained + "/" + count);
                                progressDload.show();
                            }

                            startDownload(localImgPath);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
                });

        makeRequest(jsonArrayRequest);
    }

    public void updateProgress() {
        progressDload.setMessage("Prosím, nevypínejte aplikaci. Dokončeno: " + ++obtained + "/" + count);
        if(obtained >= count) {
            progressDload.dismiss();
            Toast.makeText(this, "Dokončeno", Toast.LENGTH_LONG).show();
            checkDirectories();
        }
    }

    private void startDownload(File imgPath){
        for(String imageName : dloadNames){
            ImageDownload imgDld = new ImageDownload(this);
            imgDld.execute(ADDRESS_IMG + imageName, imgPath.toString() + "/" + imageName);
        }
    }
}
