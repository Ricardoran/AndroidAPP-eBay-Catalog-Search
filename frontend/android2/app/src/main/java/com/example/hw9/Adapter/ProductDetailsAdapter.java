package com.example.hw9.Adapter;
import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentManager;

import com.example.hw9.ui.fragment.Main.ProductInformation;
import com.example.hw9.ui.fragment.Main.SellerInformation;
import com.example.hw9.ui.fragment.Main.ShippingDetails;

public class ProductDetailsAdapter extends FragmentPagerAdapter {

    private Context myContext;
    int totalTabs;

    public ProductDetailsAdapter(Context context, FragmentManager fm, int totalTabs) {
        super(fm);
        myContext = context;
        this.totalTabs = totalTabs;
    }

    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                ProductInformation productInformation = new ProductInformation();
                return productInformation;
            case 2:
                ShippingDetails shippingDetails = new ShippingDetails();
                return shippingDetails;
            case 1:
                SellerInformation sellerInformation = new SellerInformation();
                return  sellerInformation;

            default:
                return null;
        }
    }
    // this counts total number of tabs
    @Override
    public int getCount() {
        return totalTabs;
    }
}