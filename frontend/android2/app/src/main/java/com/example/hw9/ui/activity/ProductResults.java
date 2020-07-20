package com.example.hw9.ui.activity;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.RequiresApi;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.hw9.ProductResultsAdapter;
import com.example.hw9.R;
import com.example.hw9.ui.init.ProductDetails;
import com.example.hw9.ui.init.SearchForm;
import com.example.hw9.utils.APICALLS;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ProductResults extends AppCompatActivity {

    public static RecyclerView recyclerView;
    public static RecyclerView.LayoutManager layoutManager;
    public static ProductResultsAdapter adaptor;
    public static List<ProductDetails> productDetails = new ArrayList<>();
    public static Context applicationCtx;
    public static String keywordFromBack = "";
//    xsx
//    public static Context context;

    public static TextView count,keyword;
    public static LinearLayout results_showing;

    public SearchForm searchform;
    public AlphaAnimation inAnimation;
    static AlphaAnimation outAnimation;

    static FrameLayout progressBarHolder;
    static View noRecordView;

    private SwipeRefreshLayout mSwipeRefreshLayout;



    @Override
    public void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    public static void initList() {
        productDetails = new ArrayList<>();
    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    protected void onRestart() {
        super.onRestart();
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    protected void onResume() {
        super.onResume();
        ProductResults.adaptor = new ProductResultsAdapter(productDetails, ProductResults.applicationCtx);
        ProductResults.recyclerView.setAdapter(ProductResults.adaptor);
        return;
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.results_product);

        count = (TextView) findViewById(R.id.count);
        keyword = (TextView) findViewById(R.id.keyword);
        results_showing = (LinearLayout) findViewById(R.id.results_showing);

        keywordFromBack = getIntent().getStringExtra("keyword");



        try {
            Toolbar toolbar = findViewById(R.id.toolbar);
            toolbar.setTitle("Search Results");
            setSupportActionBar(toolbar);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }
        catch(Exception e){}


        searchform = new Gson().fromJson(getIntent().getStringExtra("searchForm"), SearchForm.class);
        System.out.println("searchform------------------------------------");
        System.out.println(searchform);
        recyclerView = findViewById(R.id.recyclerview);
        noRecordView = findViewById(R.id.no_record_found);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        applicationCtx = getApplicationContext();


        progressBarHolder = findViewById(R.id.progressBarHolder2);
        progressBarHolder.dispatchDisplayHint(View.VISIBLE);
        progressBarHolder.setAnimation(inAnimation);
        progressBarHolder.setVisibility(View.VISIBLE);


        FindProductResults findProductResults = new FindProductResults();
        findProductResults.execute(new Gson().toJson(searchform));
        productDetails = findProductResults.findProductResults();


//        Log.d("test toast ***", "noResults is  "+findProductResults.noResults);
//        if(findProductResults.noResults){
//            Toast.makeText(ProductResults.this, "No records found",
//                    Toast.LENGTH_LONG).show();
//        }


        // **************************************test begin ***************************************

        mSwipeRefreshLayout = findViewById(R.id.swiperefresh_items);
        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
//                searchform = new Gson().fromJson(getIntent().getStringExtra("searchForm"), SearchForm.class);
                FindProductResults findProductResults = new FindProductResults();
                findProductResults.execute(new Gson().toJson(searchform));
//                productDetails = findProductResults.findProductResults();
//                ProductResults.adaptor.notifyDataSetChanged();
//                mSwipeRefreshLayout.setRefreshing(false);
                final Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        if(mSwipeRefreshLayout.isRefreshing()) {
                            mSwipeRefreshLayout.setRefreshing(false);
                        }
                    }
                }, 1000);


            }
        });
// **************************************test end xsx ***************************************

//        public static Context getAppContext() {
//            return MyApplication.context;
//        }




       // }

    }
}

class FindProductResults extends AsyncTask<String, Integer, String> {

    public static final int HORIZONTAL = 0;
    public static final int VERTICAL = 1;


    public Map<String, String> categoryValue = new HashMap<>();

//    public boolean noResults = false;

    public List<ProductDetails> findProductResults() {
        return productDetails;
    }

    public List<ProductDetails> productDetails = new ArrayList<>();


    @Override
    protected String doInBackground(String... strings) {


        StringBuffer surl = new StringBuffer();
        surl.append(APICALLS.Local_URL);

        SearchForm searchForm = new Gson().fromJson(strings[0], SearchForm.class);
        surl.append("result?input_keyword=").append(searchForm.keyword);
        Log.d("success", "minprice: "+searchForm.minprice);
        Log.d("success", "maxprice: "+searchForm.maxprice);
        Log.d("success", "new1: "+searchForm.new1);
        Log.d("success", "used: "+searchForm.used);
        Log.d("success", "unspecified: "+searchForm.unspecified);


        if(searchForm.minprice != null){
            surl.append("&input_from=").append(searchForm.minprice);

        }
        if(searchForm.maxprice != null) {
            surl.append("&input_to=").append(searchForm.maxprice);
        }

        if(searchForm.new1.equals("true")){
            surl.append("&condition=New");
        }
        if(searchForm.used.equals("true")){
            surl.append("&condition=Used");
        }
        if(searchForm.unspecified.equals("true")){
            surl.append("&condition=Unspecified");
        }

        surl.append("&select_category=").append(searchForm.sortby);


        Log.d("success", "first url is: "+surl );


        StringBuilder stringBuilder = new StringBuilder();
        HttpURLConnection connection = null;
        URL temp_url;
        try {

            temp_url = new URL(surl.toString().replaceAll(" ","%20"));
            connection = (HttpURLConnection) temp_url.openConnection();
            connection.setRequestMethod("GET");
            connection.setConnectTimeout(20000);
            connection.setReadTimeout(20000);

            connection.connect();

            BufferedReader rd = new BufferedReader(new InputStreamReader(connection.getInputStream()));


            String line;
            while ((line = rd.readLine()) != null) {
                stringBuilder.append(line);
            }

            rd.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
        finally {
            if (connection != null) {
                connection.disconnect();
            }
        }
        System.out.println("This is the response: " + stringBuilder.toString());
        return stringBuilder.toString();
    }


    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    protected void onPostExecute(String response) {
        if (response.isEmpty()){
            ProductResults.progressBarHolder.setVisibility(View.VISIBLE);
        }
        else {

            productDetails = processResponse(response);
            ProductResults.productDetails = productDetails;
            ProductResults.adaptor = new ProductResultsAdapter(productDetails, ProductResults.applicationCtx);
            ProductResults.recyclerView.setLayoutManager(new GridLayoutManager(ProductResults.applicationCtx, 2));
            ProductResults.recyclerView.setAdapter(ProductResults.adaptor);
            ProductResults.progressBarHolder.setAnimation(ProductResults.outAnimation);
            ProductResults.progressBarHolder.setVisibility(View.GONE);

            // zhr add
            DividerItemDecoration itemDecor_hor = new DividerItemDecoration(ProductResults.recyclerView.getContext(), HORIZONTAL);
            DividerItemDecoration itemDecor_ver = new DividerItemDecoration(ProductResults.recyclerView.getContext(), VERTICAL);

            ProductResults.recyclerView.addItemDecoration(itemDecor_hor);
            ProductResults.recyclerView.addItemDecoration(itemDecor_ver);

        }
    }

    private List<ProductDetails> processResponse(String response) {
        try {
            Log.i("Hitting Url  inside..",response);
            JSONObject jsonObject = new JSONObject(response); // get json response
            // process json
            String resultscount = jsonObject.getInt("count") + "";
//            Log.i("Hitting Url Param..",resultscount);
            if (resultscount.equals("0")){
                ProductResults.noRecordView.setVisibility(View.VISIBLE);
                ProductResults.results_showing.setVisibility(View.GONE);

                Toast.makeText(ProductResults.applicationCtx, "No records",
                        Toast.LENGTH_SHORT).show();


            }else {
                ProductResults.results_showing.setVisibility(View.VISIBLE);
                ProductResults.count.setText(resultscount);
                ProductResults.keyword.setText(ProductResults.keywordFromBack);
                ProductResults.noRecordView.setVisibility(View.GONE);
                JSONArray resultsTable = jsonObject.getJSONArray("data");
                Log.d("success", "resultTable isxxxxxxx: "+resultsTable);
//                Log.i("Hitting Url Param..",String.valueOf(resultsTable));
                Log.d("success", "resultTable isxxxxxxx: "+resultsTable);

                for (int i = 0; i < resultsTable.length(); i++) {
                    ProductDetails resultsTableContent = new ProductDetails();




                    resultsTableContent.setProductImage(resultsTable.getJSONObject(i).getString("image"));
                    resultsTableContent.setProductTitle(resultsTable.getJSONObject(i).getString("title"));
                    resultsTableContent.setProductCondition(resultsTable.getJSONObject(i).getString("condition"));
                    resultsTableContent.setProductPrice(resultsTable.getJSONObject(i).getString("price"));
                    resultsTableContent.setProductShipping(resultsTable.getJSONObject(i).getString("ShippingCost"));
                    resultsTableContent.setProductId(resultsTable.getJSONObject(i).getString("productID").replace("[\"", "").replace("\"]", ""));

                    resultsTableContent.setProductTopRate(resultsTable.getJSONObject(i).getString("top"));
                    resultsTableContent.setProductUrl(resultsTable.getJSONObject(i).getString("ItemURL").replace("[\"", "").replace("\"]", ""));


                    resultsTableContent.setProductLocation(resultsTable.getJSONObject(i).getString("location"));
                    resultsTableContent.setProductOneDayShipping(resultsTable.getJSONObject(i).getString("OneDayShippingAvailable"));
                    resultsTableContent.setProductShippingType(resultsTable.getJSONObject(i).getString("ShippingType"));
                    resultsTableContent.setProductShipToLocation(resultsTable.getJSONObject(i).getString("Shippingtoloacations"));
                    resultsTableContent.setProductExpeditedShipping(resultsTable.getJSONObject(i).getString("ExpeditedShipping"));








                    productDetails.add(resultsTableContent);
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
            // ProductResults.noRecordView.setVisibility(View.VISIBLE);
        }

        return productDetails;
    }
}



