package com.example.hw9.ui.fragment.Main;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.hw9.R;

import org.w3c.dom.Text;


public class SellerInformation extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";



    View rootview;


    public static LinearLayout layout_top,layout_inter, layout_feedback ,layout_userId,layout_positive,layout_rateStar,layout_refund,layout_returnWith,layout_costBy,layout_returnAcc;
    public static LinearLayout layout_sellerInfo,layout_returnPolicy;
    public static TextView top_text,inter_text,return_acc_text,costby_text,within_text,refund_text,ratestar_text,positive_text,userid_text,feedback_score_text;
    public SellerInformation() {
        // Required empty public constructor
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        // Inflate the layout for this fragment
        rootview = inflater.inflate(R.layout.seller_information, container, false);


        layout_sellerInfo  =  (LinearLayout) rootview.findViewById(R.id.layout_sellerInfo);
        layout_returnPolicy =  (LinearLayout) rootview.findViewById(R.id.layout_returnPolicy);



        layout_feedback      = (LinearLayout) rootview.findViewById(R.id.layout_feedback);
        layout_userId        = (LinearLayout) rootview.findViewById(R.id.layout_userId);
        layout_positive      = (LinearLayout) rootview.findViewById(R.id.layout_positive);
        layout_rateStar      = (LinearLayout) rootview.findViewById(R.id.layout_rateStar);
        layout_top           = (LinearLayout) rootview.findViewById(R.id.layout_top);

        layout_refund        = (LinearLayout) rootview.findViewById(R.id.layout_refund);
        layout_returnWith    = (LinearLayout) rootview.findViewById(R.id.layout_returnWith);
        layout_costBy        = (LinearLayout) rootview.findViewById(R.id.layout_costBy);
        layout_returnAcc     = (LinearLayout) rootview.findViewById(R.id.layout_returnAcc);
        layout_inter         = (LinearLayout) rootview.findViewById(R.id.layout_inter);




        feedback_score_text = (TextView) rootview.findViewById(R.id.feedback_score_text);
        userid_text         = (TextView) rootview.findViewById(R.id.userid_text);
        positive_text       = (TextView) rootview.findViewById(R.id.positive_text);
        ratestar_text       = (TextView) rootview.findViewById(R.id.ratestar_text);
        top_text            = (TextView) rootview.findViewById(R.id.top_text);

        refund_text         = (TextView) rootview.findViewById(R.id.refund_text);
        within_text         = (TextView) rootview.findViewById(R.id.within_text);
        costby_text         = (TextView) rootview.findViewById(R.id.costby_text);
        return_acc_text     = (TextView) rootview.findViewById(R.id.return_acc_text);
        inter_text          =  (TextView) rootview.findViewById(R.id.inter_text);



        return rootview;
    }
}