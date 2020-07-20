package com.example.hw9;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.net.Uri;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;


import com.example.hw9.Adapter.ProductDetailsAdapter;


public class ActivityProductDetails extends AppCompatActivity{
    TabLayout tabLayout;
    ViewPager viewPager;
    ImageView back,redirect;
    TextView productName;
    RelativeLayout mylayout;
    public static  String productTitle,productId,productURL;
    FloatingActionButton floating_btn_add,floating_btn_rem;
//    SharedPreferenceConfig sharedPreferenceConfig;
    String prodClassInfo;
    String[] textArray = {"PRODUCT","SELLER INFO", "SHIPPING"};
    int[] drawableArray = {R.drawable.ic_product_info,R.drawable.ic_seller, R.drawable.ic_shipping};
    private String productPrice;

    @SuppressLint("RestrictedApi")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_details);

        tabLayout=(TabLayout)findViewById(R.id.tabLayout);
        viewPager=(ViewPager)findViewById(R.id.viewPager);
        back=(ImageView)findViewById(R.id.back_btn);
        productName=(TextView)findViewById(R.id.head_text);
        redirect=(ImageView)findViewById(R.id.redirect);


        prodClassInfo = getIntent().getStringExtra("prod");



        productTitle=getIntent().getStringExtra("prod_title");
        productPrice=getIntent().getStringExtra("prod_price");
        productURL  =getIntent().getStringExtra("prod_url");
        productName.setText(productTitle);
        Log.d("HW", "productName ------ " + productTitle);
        productId=getIntent().getStringExtra("prod_id");
        Log.d("HW", "productId ------ " + productId);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
               // onBackPressed();
            }
        });


        mylayout = (RelativeLayout) findViewById(R.id.tooltip);//Your layout instance
        // edit
//        redirect=(ImageView)findViewById(R.id.bookButton);
//        redirect.setOnClickListener(new OnClickListener()
//        {
//
//            @Override
//            public void onClick(View v) {
//                // TODO Auto-generated method stub
//                myImgView.setImageResource(R.drawable. blue_bar);
//                mylayout.setImageResource(R.drawable. blue_bar);
//            }
//        });





        redirect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String fbID = productURL;
                Intent i = new Intent(Intent.ACTION_VIEW);
                i.setData(Uri.parse(fbID));
                startActivity(i);
            }
        });


        for (int i=0; i<textArray.length;i++){
            TabLayout.Tab tab = tabLayout.newTab().setCustomView(R.layout.custom_tab_design);
            View view= tab.getCustomView();
            TextView txtCount= (TextView) view.findViewById(R.id.text_tab);
            ImageView image = (ImageView) view.findViewById(R.id.image_tab);

            txtCount.setText(textArray[i]);
            image.setImageDrawable(getResources().getDrawable(drawableArray[i]));

            tabLayout.addTab(tab);
        }

        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);

        final ProductDetailsAdapter adapter = new ProductDetailsAdapter(this,getSupportFragmentManager(), tabLayout.getTabCount());
        viewPager.setOffscreenPageLimit(2);
        viewPager.setAdapter(adapter);

        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));

        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
    }

}