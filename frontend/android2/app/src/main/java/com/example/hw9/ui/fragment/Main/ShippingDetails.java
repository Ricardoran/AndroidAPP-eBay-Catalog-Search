package com.example.hw9.ui.fragment.Main;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Paint;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.hw9.ActivityProductDetails;
import com.example.hw9.R;
import com.example.hw9.utils.APICALLS;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class ShippingDetails extends Fragment {

    View rootview;
    //    zhr
//    public static LinearLayout layout_handling_time,layout_return_within,layout_return_policy,layout_refund,layout_shipped_by,layout_new_return_info;
    public static LinearLayout layout_handling,layout_shipTo,layout_shipFrom,layout_expedited,layout_oneDay,layout_shipType;
    //    public static RelativeLayout layout_return_within,layout_return_policy,layout_refund,layout_shipped_by;
//    public static TextView store_text,feedback_text,feedback_star,popularity_rating,shipping_text,global_text,handling_text,condition_text,ploicy_text,within_text,refund_text,shipped_text, oneday_ava, shipping_type, from_location, location_to, expedited, display_user_id, positive, return_acc, cost_payby;
    public static TextView expedited_text,handling_text,oneday_text,shiptype_text,shipfrom_text,shipto_text;


    // zhr delete
    //    public static ImageView feedback_star;
    public ShippingDetails() {
        // Required empty public constructor

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        rootview = inflater.inflate(R.layout.shipping_information, container, false);



        layout_handling      =(LinearLayout) rootview.findViewById(R.id.layout_handling);
        layout_oneDay        =(LinearLayout) rootview.findViewById(R.id.layout_oneDay);
        layout_shipType      =(LinearLayout) rootview.findViewById(R.id.layout_shipType);
        layout_shipFrom      =(LinearLayout) rootview.findViewById(R.id.layout_shipFrom);
        layout_shipTo        =(LinearLayout) rootview.findViewById(R.id.layout_shipTo);
        layout_expedited     =(LinearLayout) rootview.findViewById(R.id.layout_expedited);


        handling_text   = (TextView) rootview.findViewById(R.id.handling_text);
        expedited_text  = (TextView) rootview.findViewById(R.id.expedited_text);
        oneday_text     = (TextView) rootview.findViewById(R.id.oneday_text);
        shiptype_text   = (TextView) rootview.findViewById(R.id.shiptype_text);
        shipfrom_text   = (TextView) rootview.findViewById(R.id.shipfrom_text);
        shipto_text     = (TextView) rootview.findViewById(R.id.shipto_text);




        return rootview;
    }
}