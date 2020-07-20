package com.example.hw9.ui.fragment.Main;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.card.MaterialCardView;
import android.support.v4.app.Fragment;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AlphaAnimation;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;


import com.example.hw9.ActivityProductDetails;
import com.example.hw9.R;
import com.example.hw9.utils.ImageLoader;
import com.example.hw9.utils.APICALLS;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;


public class ProductInformation extends Fragment {
    int responseCode;
    ProgressDialog dialog = null;
    View rootview;
    LinearLayout layout,specs,price,brandhead;
    RelativeLayout layout_specification,layout_feature;
    TextView product_title_info,product_price_info,product_ship_info,desc_text, price_entry,brand_text,spec_text,high_light_label;
    String prod_price,prod_title,prod_location,prod_expedited,prod_oneDay,prod_shipType,prod_shipTo,prod_shipCost;
    ImageView spec_icon,high_light_icon;
    View between_product_spec,between_img_product;

    //    static FrameLayout progressBarHolder;
    static AlphaAnimation inAnimation;
    static AlphaAnimation outAnimation;

    static FrameLayout progressBarHolder;
//    static ProgressBar progressBar;


    public static String fbproductlink;

    public ProductInformation() {
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        rootview=inflater.inflate(R.layout.product_information, container, false);
        progressBarHolder = rootview.findViewById(R.id.progressBarHolder_three);

        new getProductDetails().execute(APICALLS.FIND_PRODUCT_DETAILS+ ActivityProductDetails.productId);

        Log.d("success", "The second API URL is : "+APICALLS.FIND_PRODUCT_DETAILS+ ActivityProductDetails.productId);
        layout = (LinearLayout) rootview.findViewById(R.id.linear);
        specs = (LinearLayout) rootview.findViewById(R.id.lineaer_specs);
        price = (LinearLayout) rootview.findViewById(R.id.price) ;
        brandhead =(LinearLayout) rootview.findViewById(R.id.brandhead);
        layout_specification = (RelativeLayout) rootview.findViewById(R.id.layout_specification);
        layout_feature = (RelativeLayout) rootview.findViewById(R.id.layout_feature);

        product_title_info = (TextView) rootview.findViewById(R.id.product_title_info);
        product_price_info = (TextView) rootview.findViewById(R.id.product_price_info);
        product_ship_info = (TextView) rootview.findViewById(R.id.product_ship_info);
        price_entry = (TextView) rootview.findViewById(R.id.price_entry);
        brand_text = (TextView) rootview.findViewById(R.id.brand_text);
        spec_text = (TextView) rootview.findViewById(R.id.specifications_text) ;
        high_light_label = (TextView) rootview.findViewById(R.id.high_light_label) ;
        spec_icon = (ImageView) rootview.findViewById(R.id.specifications_icon) ;
        high_light_icon = (ImageView) rootview.findViewById(R.id.high_light_icon) ;



        between_product_spec = (View) rootview.findViewById(R.id.between_product_spec) ;
        between_img_product  = (View) rootview.findViewById(R.id.between_img_product) ;



        // get all data from API 1
        prod_price     = getActivity().getIntent().getStringExtra("prod_price");
        prod_title     = getActivity().getIntent().getStringExtra("prod_title");
        prod_location  = getActivity().getIntent().getStringExtra("prod_location");
        prod_expedited = getActivity().getIntent().getStringExtra("prod_expedited");
        prod_oneDay    = getActivity().getIntent().getStringExtra("prod_oneDay");
        prod_shipType  = getActivity().getIntent().getStringExtra("prod_shipType");
        prod_shipTo    = getActivity().getIntent().getStringExtra("prod_shipTo");
        prod_shipCost  = getActivity().getIntent().getStringExtra("prod_shipCost");
//        Log.d("test  transmit 1st api ", "---------------------------------");
//        Log.d("test  transmit 1st api ", "prod_location is  "+prod_location);
//        Log.d("test  transmit 1st api ", "prod_title is  "+prod_title);
//        Log.d("test  transmit 1st api ", "prod_price is  "+prod_price);
//        Log.d("test  transmit 1st api ", "prod_expedited is  "+prod_expedited);
//        Log.d("test  transmit 1st api ", "prod_oneDay is  "+prod_oneDay);
//        Log.d("test  transmit 1st api ", "prod_shipType is  "+prod_shipType);
//        Log.d("test  transmit 1st api ", "prod_shipTo is  "+prod_shipTo);
//        Log.d("test  transmit 1st api ", "prod_shipCost is  "+prod_shipCost);
//        Log.d("test  transmit 1st api ", "---------------------------------");



        return rootview;

    }


    public class getProductDetails extends AsyncTask<String, String, String>  {

        protected void onPreExecute() {
            super.onPreExecute();

            ProductInformation.progressBarHolder.setVisibility(View.VISIBLE);
            ProductInformation.progressBarHolder.dispatchDisplayHint(View.VISIBLE);
            ProductInformation.progressBarHolder.setAnimation(inAnimation);

        }

        @Override
        protected String doInBackground(String... voids) {


            StringBuffer stringBuffer = new StringBuffer();
            try {
                URL url = new URL(voids[0]);
                HttpURLConnection httpConnection = (HttpURLConnection) url.openConnection();
                httpConnection.connect();
                if (httpConnection.getResponseCode() == HttpURLConnection.HTTP_OK) {
                    InputStream stream = httpConnection.getInputStream();
                    InputStreamReader isr = new InputStreamReader(stream);
                    BufferedReader br = new BufferedReader(isr);
                    String line = "";
                    while ((line = br.readLine()) != null) {
                        stringBuffer.append(line + "\n");
                    }
                    responseCode = httpConnection.getResponseCode();
                    return stringBuffer.toString();

                } else {
                    return "Not valid";
                }
            } catch (Exception e) {
            }
            return null;
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);


            if (result.isEmpty()){
                ProductInformation.progressBarHolder.setVisibility(View.VISIBLE);
            }
            else {

                ProductInformation.progressBarHolder.setAnimation(ProductInformation.outAnimation);
                ProductInformation.progressBarHolder.setVisibility(View.GONE);
            }



            try {

                JSONObject jsonObject = new JSONObject(result);
                JSONObject itemObj = jsonObject.getJSONObject("Item");
                Log.d("HW9", "Product Result ------ " + itemObj);

                String title ;
                String subtitle;
                boolean miss_subtitle = false;
                boolean miss_brand = false;
                String currentPrice = itemObj.getJSONObject("CurrentPrice").getString("Value");

                Log.d("debug", "----------------------------------1 ");


  //************************************** ShippingDetails -- 3rd Tab **********************************
                if(!itemObj.isNull("HandlingTime")){
                    ShippingDetails.handling_text.setText(" " + itemObj.getString("HandlingTime"));
                }else{
                    ShippingDetails.layout_handling.setVisibility(View.GONE);
                }



                // zhr + set one day ship
                //Log.d("display one day", "one day shipping is going to be displayed: " + prod_oneDay);

                ShippingDetails.oneday_text.setText(prod_oneDay);

                //Log.d("display one day", "display successful");

                // display ship type
                ShippingDetails.shiptype_text.setText(prod_shipType);

                // display ship location
                ShippingDetails.shipfrom_text.setText(prod_location);

                // display ship location
                ShippingDetails.shipto_text.setText(prod_shipTo);

                // display expedited shipping
                ShippingDetails.expedited_text.setText(prod_expedited);

                Log.d("Congratulations", "3RD TAB--- SHIPPING DETAILS done ");

                //**************************************ShippingDetails -- 3rd Tab  end **********************************




                //**************************************Seller Information -- 2nd Tab  begin **********************************

                Log.d("debug", "----------------------------------2 ");

                if(!itemObj.isNull("Seller")){

                    JSONObject seller = itemObj.getJSONObject("Seller");
                    String userID ;
                    String feedbackRatingStar ;
                    String feedbackScore ;
                    String positiveFeedbackPercent;
                    String topRatedSeller;
                    Log.d("debug", "----------------------------------3 ");

                    if (!seller.isNull("UserID")) {
                        userID = seller.getString("UserID");
                    }else {

                        userID = "";
                        SellerInformation.layout_userId.setVisibility(View.GONE);

                    }

                    Log.d("debug", "----------------------------------4 ");

                    if (!seller.isNull("FeedbackRatingStar")) {
                        feedbackRatingStar = seller.getString("FeedbackRatingStar");
                    }else {
                        feedbackRatingStar = "";
                        SellerInformation.layout_rateStar.setVisibility(View.GONE);

                    }
                    Log.d("debug", "----------------------------------5 ");


                    if (!seller.isNull("FeedbackScore")) {
                        feedbackScore = seller.getString("FeedbackScore");
                    }else {
                        feedbackScore = "";
                        SellerInformation.layout_feedback.setVisibility(View.GONE);

                    }
                    Log.d("debug", "----------------------------------6 ");


                    if (!seller.isNull("PositiveFeedbackPercent")) {
                        positiveFeedbackPercent = seller.getString("PositiveFeedbackPercent");
                    }else {
                        positiveFeedbackPercent = "";
                        SellerInformation.layout_positive.setVisibility(View.GONE);

                    }

                    if(!seller.isNull("TopRatedSeller")){
                        topRatedSeller = seller.getString("TopRatedSeller");
                    }else {
                        topRatedSeller = "";
                        SellerInformation.layout_top.setVisibility(View.GONE);
                    }

                    SellerInformation.feedback_score_text.setText(feedbackScore);
                    SellerInformation.userid_text.setText(userID);
                    SellerInformation.positive_text.setText(positiveFeedbackPercent);
                    SellerInformation.ratestar_text.setText(feedbackRatingStar);
                    SellerInformation.top_text.setText(topRatedSeller);

                    Log.d("debug", "----------------------------------7 ");

                } else{
                    SellerInformation.layout_sellerInfo.setVisibility(View.GONE);

                }

                //return policy

                if(!itemObj.isNull("ReturnPolicy")){

                    JSONObject returnPolicy = itemObj.getJSONObject("ReturnPolicy");
                    String shippingCostPaidBy;
                    String refund;
                    String returnsAccepted;
                    String returnsWithin;
                    String international;

                    //TextView temp ;
                    if (!returnPolicy.isNull("Refund")) {
                        refund = returnPolicy.getString("Refund");
                    }else {
                        refund = "";
                        SellerInformation.layout_refund.setVisibility(View.GONE);
                    }
                    Log.d("inner debug", "debug ----1");


                    if (!returnPolicy.isNull("ReturnsWithin")) {
                        returnsWithin = returnPolicy.getString("ReturnsWithin");
                    }else {
                        returnsWithin = "";
                        SellerInformation.layout_returnWith.setVisibility(View.GONE);

                    }
                    Log.d("inner debug", "debug ----2");



                    if (!returnPolicy.isNull("ReturnsAccepted")) {
                        returnsAccepted = returnPolicy.getString("ReturnsAccepted");
                    }else {
                        returnsAccepted = "";
                        SellerInformation.layout_returnAcc.setVisibility(View.GONE);
                    }
                    Log.d("inner debug", "debug ----3");


                    if (!returnPolicy.isNull("ShippingCostPaidBy")) {
                        shippingCostPaidBy = returnPolicy.getString("ShippingCostPaidBy");
                    }else {
                        shippingCostPaidBy = "";
                        SellerInformation.layout_costBy.setVisibility(View.GONE);

                    }


                    if (!returnPolicy.isNull("InternationalReturnsAccepted")) {
                        international = returnPolicy.getString("InternationalReturnsAccepted");
                    }else {
                        international = "";
                        SellerInformation.layout_inter.setVisibility(View.GONE);
                    }

                    Log.d("inner debug", "debug ----4");


                    SellerInformation.refund_text.setText(refund);
                    SellerInformation.within_text.setText(returnsWithin);
                    SellerInformation.costby_text.setText(shippingCostPaidBy);
                    SellerInformation.return_acc_text.setText(returnsAccepted);
                    SellerInformation.inter_text.setText(international);

                }

                else{

                    SellerInformation.layout_returnPolicy.setVisibility(View.GONE);

                }



                Log.d("Congratulations", "2ND TAB--- Seller INFO  HAS BEEN FINISHED!!!! ");






                //**************************************Seller Information -- 2nd Tab  end **********************************



                //**************************************Product Information -- 1st Tab  begin **********************************


                Log.d("debug", "----------------------------------8 ");


                if (!itemObj.isNull("Subtitle")) {
                    subtitle = itemObj.getString("Subtitle");
                    price.setVisibility(View.VISIBLE);
                }else {
                    subtitle = "";
                    price.setVisibility(View.GONE);
                    miss_subtitle = true;
                }
                Log.d("debug", "----------------------------------9 ");


                if(itemObj.isNull("ItemSpecifics")){
                    brandhead.setVisibility(View.GONE);
                    miss_brand = true;
                    layout_specification.setVisibility(View.GONE);



                }else{
                    JSONArray nameValuePair = itemObj.getJSONObject("ItemSpecifics").getJSONArray("NameValueList");


                    int index = 0;
                    boolean brandOrNot = false;
                    boolean cardstop = false;
                    Log.d("debug", "----------------------------------10 ");

                    if(nameValuePair.length() == 1 && nameValuePair.getJSONObject(0).getString("Name").equals("Brand")) {
                        layout_specification.setVisibility(View.GONE);
                        Log.d("debug", "----------------------------------11");
                    }
                    Log.d("debug", "----------------------------------12");


                    for (int i=0; i<nameValuePair.length();i++){
                        if(nameValuePair.getJSONObject(i).getString("Name").equals("Brand")){
                            brand_text.setText(nameValuePair.getJSONObject(i).getString("Value").replace("[\"","").replace("\"]","").replace("\\", ""));
                            brandOrNot = true;
                            continue;
                        }
                        if(index == 5){
                            cardstop = true;
                            if(brandOrNot){
                                break;
                            }
                        }
                        if(!cardstop){
                            TextView textView = new TextView(getActivity());
                            textView.setId(i);
                            textView.setTextSize(18);


                            String temp_text = nameValuePair.getJSONObject(i).getString("Value");

                            String str= temp_text.replace("[\"","").replace("\"]","");

                            String final_txt = str.replace("\"", "").replace("\\", "");

                            textView.setText("\u2022 "+ final_txt);


                            System.out.println("regex is " + final_txt);

                            specs.addView(textView);
                            index++;
                        }

                    }
                    Log.d("debug", "----------------------------------13");

                    if(!brandOrNot){
                        miss_brand = true;
                        brandhead.setVisibility(View.GONE);
                    }

                }
                Log.d("debug", "----------------------------------14");



                if(miss_subtitle && miss_brand){
                    // say byebye to product features
                    between_img_product.setVisibility(View.GONE);
                    layout_feature.setVisibility(View.GONE);
                }

                Log.d("debug", "----------------------------------15");



                product_title_info.setText(prod_title);
                product_price_info.setText("$"+prod_price+" ");
                product_ship_info.setText(Html.fromHtml(prod_shipCost));
                price_entry.setText(subtitle);



                JSONArray pictureURL = itemObj.getJSONArray("PictureURL");

                ImageLoader imageLoader = new ImageLoader(getContext());
                for (int i = 0; i < pictureURL.length(); i++) {
                    ImageView imageView = new ImageView(getActivity());
                    imageView.setId(i);
                    imageLoader.DisplayImage(pictureURL.getString(i),imageView);
                    imageView.setMinimumWidth(700);
                    imageView.setMinimumHeight(3500);
                    layout.addView(imageView);
                }

                Log.d("success", "1st TAB--- Product INFO  HAS BEEN FINISHED!!!! ");


                //**************************************Product Information -- 1st Tab  end **********************************



                Log.d("final debug", "Completely done!");




            } catch (Exception e) {

                Log.d("debug", "exception happens!");

            }

        }
    }
}
