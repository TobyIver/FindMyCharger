package com.app.toby.findmycharger;

/**
 * Created by Toby Iverson
 *
 * Listview with help from tutorial by Lars Vogel
*http://www.vogella.com/tutorials/AndroidListView/article.html
*
*/

import android.app.ListActivity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.location.Address;
import android.location.Geocoder;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

public class MainActivity extends ListActivity {
    public DataSource datasource;





    @Override
    public  void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_main);

        startChargeListner();
        LoadList();
    }



    public void startChargeListner() {
        PowerStateManager psm = new PowerStateManager();

        Intent intent = this.registerReceiver(psm,
                new IntentFilter(Intent.ACTION_BATTERY_CHANGED));
        psm.onReceive(this,intent);



        datasource = new DataSource(this);
        datasource.open();

    }

    public void LoadList(){
        List<Data> ListData = datasource.getAllData();
        ListView listview = (ListView) findViewById(R.id.listview);

        ArrayList<Map<String,String>> list = new ArrayList<Map<String, String>>();



        for (int i = ListData.size() -1 ; i >= 0; i = i-1) {

             //to get City-Name from coordinates
            String cityName=null;
            Geocoder gcd = new Geocoder(getBaseContext(),
                    Locale.getDefault());
            List<Address>  addresses;


            Double LatD = Double.parseDouble(ListData.get(i).getlat());
            Double LongD = Double.parseDouble(ListData.get(i).getlong());
           try {

                addresses = gcd.getFromLocation(LatD, LongD, 1);
                if (addresses.size() > 0){
                    System.out.println(addresses.get(0).getLocality());
                    cityName=addresses.get(0).getLocality();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }

            list.add(putData(ListData.get(i).getDate(), cityName + " " + LatD + ", " + LongD));
        }


        String[] from = { "date", "location"};
        int[] to = { android.R.id.text1, android.R.id.text2 };

        SimpleAdapter sadapter = new SimpleAdapter(this, list,
                android.R.layout.simple_list_item_2, from, to);
        setListAdapter(sadapter);

    }

    public HashMap<String, String> putData(String date, String location) {
        HashMap<String, String> item = new HashMap<String, String>();
        item.put("date", date);
        item.put("location", location);
        return item;
    }

    @Override
    public void onListItemClick(ListView l, View v, int pos, long id) {
        // Do something when a list item is clicked
        List<Data> ListData = datasource.getAllData();
        int position = ListData.size() - pos -1;

        String lon = ListData.get(position).getlong();
        String lat = ListData.get(position).getlat();


        String uri = String.format(Locale.ENGLISH, "geo:"+ lat + "," + lon);
        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(uri));
        startActivity(intent);

        Toast.makeText(getApplicationContext(), "loading map " + uri,
                Toast.LENGTH_LONG).show();


    }









    @Override
    protected void onResume() {

       LoadList();
        super.onResume();

    }

    @Override
    protected void onPause() {
        //datasource.close();
        super.onPause();
    }


    public class ShutdownReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            datasource.close();
        }

    }


}
