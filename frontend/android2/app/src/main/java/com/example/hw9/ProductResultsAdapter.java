package com.example.hw9;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.hw9.ui.init.ProductDetails;
import com.example.hw9.utils.ImageLoader;
import com.google.gson.Gson;

import java.util.List;

public class ProductResultsAdapter extends RecyclerView.Adapter<ProductResultsAdapter.ProductDetailsViewHolder> {
    private List<ProductDetails> productDetailList;
    private Context context;
    private List<ProductDetails> localStorage;
    ImageLoader imageLoader;

    @RequiresApi(api = Build.VERSION_CODES.N)
    public ProductResultsAdapter(List<ProductDetails> productDetailList, Context context) {
        this.productDetailList = productDetailList;
        this.context = context;
//        sharedPreferenceConfig = new SharedPreferenceConfig(ProductResultsAdapter.this.context);
//        localStorage = sharedPreferenceConfig.loadSharedPreferencesLogList();
        imageLoader = new ImageLoader(context);
    }

    @NonNull
    @Override
    public ProductDetailsViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_layout, viewGroup, false);
        ProductDetailsViewHolder productDetailsViewHolder = new ProductDetailsViewHolder(view, context, productDetailList, this);



        return productDetailsViewHolder;
    }

    @Override
    public void onBindViewHolder(final ProductDetailsViewHolder viewHolder, final int position) {
        //this is for displaying the basic card view
        final ProductDetails productDetail = productDetailList.get(position);
//        Log.d("HW09","Image in adapter class --- "+productDetail.getProductImage().replace("\"]",""));
        imageLoader.DisplayImage(productDetail.getProductImage().replace("\"]",""),viewHolder.productImage);
        viewHolder.productTitle.setText(productDetail.getProductTitle());


        viewHolder.productShipping.setText(Html.fromHtml(productDetail.getProductShipping()));


        viewHolder.productCondition.setText(productDetail.getProductCondition());
        viewHolder.productPrice.setText("$"+productDetail.getProductPrice());

        if(productDetail.getProductTopRate() != null){
            viewHolder.productTopRate.setText(productDetail.getProductTopRate());
        }
//        Log.d("Welcome to productAdapter", "the 1st is : "+productDetail.getProductLocation());
//        Log.d("Welcome to productAdapter", "the 2nd is : "+productDetail.getProductExpeditedShipping());
//        Log.d("Welcome to productAdapter", "the 3rd is : "+productDetail.getProductOneDayShipping());
//        Log.d("Welcome to productAdapter", "the 4th is : "+productDetail.getProductShippingType());
//        Log.d("Welcome to productAdapter", "the 5th is : "+productDetail.getProductShipToLocation());
//        Log.d("Welcome to productAdapter", "the 5th is : "+productDetail.getProductShipping());

        viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Log.d("Welcome to productAdapter", "the 1st is : "+productDetail.getProductLocation());
//                Log.d("Welcome to productAdapter", "the 2nd is : "+productDetail.getProductExpeditedShipping());
//                Log.d("Welcome to productAdapter", "the 3rd is : "+productDetail.getProductOneDayShipping());
//                Log.d("Welcome to productAdapter", "the 4th is : "+productDetail.getProductShippingType());
//                Log.d("Welcome to productAdapter", "the 5th is : "+productDetail.getProductShipToLocation());
//                Log.d("Welcome to productAdapter", "the 5th is : "+productDetail.getProductShipping());
                viewHolder.productTitle.getText().toString().trim();
                Intent intent = new Intent(viewHolder.context,ActivityProductDetails.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                intent.putExtra("prod_title",viewHolder.productTitle.getText().toString().trim());
                intent.putExtra("prod_id",productDetail.getProductId());
                intent.putExtra("prod_price",productDetail.getProductPrice());
                intent.putExtra("prod_location",productDetail.getProductLocation());
                intent.putExtra("prod_expedited",productDetail.getProductExpeditedShipping());
                intent.putExtra("prod_oneDay",productDetail.getProductOneDayShipping());
                intent.putExtra("prod_shipType",productDetail.getProductShippingType());
                intent.putExtra("prod_shipTo",productDetail.getProductShipToLocation());
                intent.putExtra("prod_shipCost",productDetail.getProductShipping());
                intent.putExtra("prod_url",productDetail.getProductUrl());

                intent.putExtra("prod",new Gson().toJson(productDetail));
                viewHolder.context.startActivity(intent);
            }
        });


    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }

    private boolean isInWishList(String productId) {
        for (int i = 0; i < localStorage.size(); i++) {
            if (localStorage.get(i).getProductId().equalsIgnoreCase(productId)) {
                Log.i(" In local storage ", "Yes ");
                return true;
            }
        }
        return false;
    }

    public void setUpdateChange(List<ProductDetails> productDetails) {
        productDetailList = productDetails;
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return productDetailList.size();
    }

    public static class ProductDetailsViewHolder extends RecyclerView.ViewHolder  {

        public ImageView productImage;
        public TextView productTitle;
        public TextView productZip;
        public TextView productShipping;
        public TextView productCondition;
        public TextView productPrice;
        public TextView productTopRate;
        public ImageView wishIcon,wishRemove;
        public TextView productId;
        public Context context;
        public List<ProductDetails> productDetails;
        public ProductResultsAdapter productResultsAdapter;


        public ProductDetailsViewHolder(@NonNull View itemView, Context context, List<ProductDetails> productDetails, ProductResultsAdapter productResultsAdapter) {
            super(itemView);
            this.context = context;
            this.productDetails = productDetails;
            this.productResultsAdapter = productResultsAdapter;

            productImage = itemView.findViewById(R.id.product_image);
            productTitle = itemView.findViewById(R.id.product_title);

            productShipping = itemView.findViewById(R.id.product_shipping);

            productCondition = itemView.findViewById(R.id.product_condition);
            productPrice = itemView.findViewById(R.id.product_price);
            productTopRate = itemView.findViewById(R.id.product_toprate);


        }

    }
}
