package com.example.hw9.ui.activity;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.design.widget.TabLayout;
import android.support.v4.app.ActivityCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.hw9.R;
import com.example.hw9.ui.init.SearchForm;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;
//import com.example.hw9.ViewPagerAdapter;

public class MainActivity extends AppCompatActivity {
    private Button btnsearch;
    private Button btnclear;
    EditText searchkeyword,minprice,maxprice;
    CheckBox newca,used,unspecified;
    Spinner spinner;
    TextView keyworderror;

    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        System.out.println("Recreate Main Activity");




        setContentView(R.layout.activity_main);
        Toolbar myToolbar = (Toolbar) findViewById(R.id.toolbar);
        myToolbar.setTitle("eBay Catalog Search");
        setSupportActionBar(myToolbar);




        findViewById(R.id.keyword_id).setEnabled(true);
        findViewById(R.id.keyword_error_id).setVisibility(View.GONE);
        findViewById(R.id.min_price_id).setEnabled(true);
        findViewById(R.id.max_price_id).setEnabled(true);
        findViewById(R.id.new_id).setEnabled(true);
        findViewById(R.id.used_id).setEnabled(true);
        findViewById(R.id.unspecified_id).setEnabled(true);
        findViewById(R.id.search_button_id).setEnabled(true);
        findViewById(R.id.clear_button_id).setEnabled(true);



        btnsearch = (Button) findViewById(R.id.search_button_id);
        btnclear = (Button) findViewById(R.id.clear_button_id);



        searchkeyword = (EditText) findViewById(R.id.keyword_id);
        minprice = (EditText) findViewById(R.id.min_price_id);
        maxprice = (EditText) findViewById(R.id.max_price_id);
        keyworderror = (TextView) findViewById(R.id.keyword_error_id);



        newca = (CheckBox) findViewById(R.id.new_id);
        used = (CheckBox) findViewById(R.id.used_id);
        unspecified = (CheckBox) findViewById(R.id.unspecified_id);



        Spinner spinner = findViewById(R.id.sortby_id);
        System.out.println("saved state: " + savedInstanceState);
        if (savedInstanceState != null) {
            // Restore value of members from saved state
            System.out.println("recreated with keyword: " + (String)savedInstanceState.get("keyword"));
            searchkeyword.setText((String)savedInstanceState.get("keyword"));
        }
        List<String> sortbys = new ArrayList<>();
        sortbys.add("Best Match");
        sortbys.add("Price:highest first");
        sortbys.add("Price + Shopping: Highest first");
        sortbys.add("Price + Shopping: Lowest first");
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, sortbys);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(dataAdapter);


        btnsearch.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onClick(View v) {
                searchProducts(v);
            }
        });


        btnclear.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onClick(View v) {
                clearAllFields(v);
            }
        });


        searchkeyword.setOnTouchListener(new View.OnTouchListener() {

            @Override
            public boolean onTouch(View v, MotionEvent event) {
                keyworderror.setVisibility(View.GONE);
                return false;
            }
        });
    }


    //clear 函数
    @SuppressLint("ResourceType")
    public void clearAllFields(View view1) {
        ((TextView) findViewById(R.id.keyword_id)).setText("");
        findViewById(R.id.keyword_error_id).setVisibility(View.GONE);
        ((TextView) findViewById(R.id.min_price_id)).setText("");
        ((TextView) findViewById(R.id.max_price_id)).setText("");
        findViewById(R.id.price_error_id).setVisibility(View.GONE);

        ((Spinner) findViewById(R.id.sortby_id)).setSelection(0);

        CheckBox temp =  ((CheckBox) findViewById(R.id.new_id));
        CheckBox temp1 =  ((CheckBox) findViewById(R.id.used_id));
        CheckBox temp2 =  ((CheckBox) findViewById(R.id.unspecified_id));

        clearCheck(temp); clearCheck(temp1); clearCheck(temp2);

    }

    //clearCheck
    public void clearCheck(CheckBox ch)
    {
        if (ch.isChecked())
            ch.toggle();
    }

    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {
        System.out.println("save state");
        savedInstanceState.putString("keyword", searchkeyword.getText().toString());
        super.onSaveInstanceState(savedInstanceState);
    }


    @RequiresApi(api = Build.VERSION_CODES.N)
    public void searchProducts(View view1){

        findViewById(R.id.price_error_id).setVisibility(View.GONE);
        findViewById(R.id.keyword_error_id).setVisibility(View.GONE);

        SearchForm searchform = new SearchForm();

        Pattern p = Pattern.compile("[^a-zA-Z0-9' ]");
        boolean valid = false;
        String keyword = searchkeyword.getText().toString();
        String minp_str = minprice.getText().toString();
        String maxp_str = maxprice.getText().toString();



        if(TextUtils.isEmpty(keyword) || keyword.length() <= 0 ){
            findViewById(R.id.keyword_error_id).setVisibility(View.VISIBLE);
            valid = true;
        }
        Log.d("success", "keyword: "+keyword);





        if(!TextUtils.isEmpty(minp_str) && !TextUtils.isEmpty(maxp_str) ){

            int minp    = Integer.parseInt(minprice.getText().toString());
            int maxp    = Integer.parseInt(maxprice.getText().toString());
            if( (minp < 0) || (maxp < 0) || (minp > maxp) ){
                findViewById(R.id.price_error_id).setVisibility(View.VISIBLE);
                valid = true;
                Log.d("success", "welcome to price both error?");
            }else{

                searchform.setMinprice(minp_str);
                searchform.setMaxprice(maxp_str);
                Log.d("success", "minp: "+minp_str+" maxp :"+maxp_str);

            }

        }else if(!TextUtils.isEmpty(minp_str) && TextUtils.isEmpty(maxp_str)){
            int minp    = Integer.parseInt(minprice.getText().toString());
            if( minp < 0 ){
                findViewById(R.id.price_error_id).setVisibility(View.VISIBLE);
                valid = true;
                Log.d("success", "welcome to min price error?");
            }else{

                searchform.setMinprice(minp_str);
                Log.d("success", "minp: "+minp_str);

            }


        }else if(TextUtils.isEmpty(minp_str) && !TextUtils.isEmpty(maxp_str)){
            int maxp    = Integer.parseInt(maxprice.getText().toString());
            if( maxp < 0 ){
                findViewById(R.id.price_error_id).setVisibility(View.VISIBLE);
                valid = true;
                Log.d("success", "welcome to max price error?");
            }else{

                searchform.setMaxprice(maxp_str);
                Log.d("success", "maxp: "+maxp_str);

            }

        }



        if (valid) {
            Toast.makeText(view1.getContext(), "Please fix all fields with errors",
                    Toast.LENGTH_SHORT).show();
            return;
        }



        Spinner spinner = findViewById(R.id.sortby_id);

        String sortby = spinner.getSelectedItem().toString();
        Log.d("success", "sortby is: "+sortby);

        searchform.setKeyword(keyword);

        if(sortby == "Best Match"){
            searchform.setSortby("BestMatch");
        }
        if(sortby == "Price:highest first" ){
            searchform.setSortby("CurrentPriceHighest");
        }

        if(sortby == "Price + Shopping: Highest first" ) {
            searchform.setSortby("PricePlusShippingHighest");
        }

        if(sortby == "Price + Shopping: Lowest first" ){
            searchform.setSortby("PricePlusShippingLowest");
        }






        CheckBox temp =  ((CheckBox) findViewById(R.id.new_id));
        CheckBox temp1 =  ((CheckBox) findViewById(R.id.used_id));
        CheckBox temp2 =  ((CheckBox) findViewById(R.id.unspecified_id));

        Log.d("success", "new is checked? "+temp.isChecked());
        Log.d("success", "used is checked? "+temp1.isChecked());
        Log.d("success", "unspecified is checked? "+temp2.isChecked());


        if (temp.isChecked())
            searchform.setNew("true");
        else
            searchform.setNew("false");

        if (temp1.isChecked())
            searchform.setUsed("true");
        else
            searchform.setUsed("false");

        if (temp2.isChecked())
            searchform.setUnspecified("true");
        else
            searchform.setUnspecified("false");





        Intent newIntent = new Intent(this, ProductResults.class);
            ProductResults.initList();
            newIntent.putExtra("keyword",searchkeyword.getText().toString().trim());
            newIntent.putExtra("searchForm", new Gson().toJson(searchform));
        startActivity(newIntent);

    }



}
