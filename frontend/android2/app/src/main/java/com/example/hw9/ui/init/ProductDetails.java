package com.example.hw9.ui.init;

import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Data
@Getter
@Setter
@NoArgsConstructor
public class ProductDetails {
    public String productTitle;

    public void setProductImage(String productImage) {
        this.productImage = productImage;
    }

    public void setProductTitle(String productTitle) {
        this.productTitle = productTitle;
    }

    public void setProductZip(String productZip) {
        this.productZip = productZip;
    }

    public void setProductPrice(String productPrice) {
        this.productPrice = productPrice;
    }


    public void setProductShipping(String productShipping) {
        if(Double.parseDouble(productShipping)==0.0)
            this.productShipping = "<b>FREE</b> Shipping";
        else
            this.productShipping= "Ships for <b>$"+ productShipping + "</b>";
    }

    public void setProductCondition(String productCondition) {
        this.productCondition = productCondition;
    }
    public void setProductId(String productId) {   this.productId = productId;   }

    public void setProductTopRate(String productTopRate) { this.productTopRate = productTopRate;}


    public void setProductOneDayShipping(String productOneDayShipping)  {this.productOneDayShipping = productOneDayShipping; }
    public void setProductShippingType(String productShippingType) {this. productShippingType= productShippingType;}
    public void setProductShipToLocation(String productShipToLocation) {this. productShipToLocation=productShipToLocation ;}
    public void setProductExpeditedShipping(String productExpeditedShipping ) {this.productExpeditedShipping =productExpeditedShipping ;}
    public void setProductLocation(String productLocation) {this.productLocation = productLocation;}

    public void setProductUrl(String productUrl) {
        this.productUrl = productUrl;
    }

    public void setWish(boolean wish) {
        isInWish = wish;
    }

    private String productImage;
    private String productZip;
    private String productPrice;
    private String productShipping;
    private String productCondition;
    private String productId;
    public String productTopRate;
    public String productUrl;
    public String productLocation;
    public String productOneDayShipping;
    public String productShippingType;
    public String productExpeditedShipping;
    public String productShipToLocation;


    public String getViewItemURL() {
        return viewItemURL;
    }

    public void setViewItemURL(String viewItemURL) {
        this.viewItemURL = viewItemURL;
    }

    public String viewItemURL;
    private boolean isInWish;

    public String shippingPrice;
    public String shippingCost;

    public String getItemURL() {
        return itemURL;
    }

    public void setItemURL(String itemURL) {
        this.itemURL = itemURL;
    }

    public String itemURL;

    public boolean isInWish() {
        return isInWish;
    }

    public void setInWish(boolean inWish) {
        isInWish = inWish;
    }

    public String getShippingPrice() {
        return shippingPrice;
    }

    public void setShippingPrice(String shippingPrice) {
        this.shippingPrice = shippingPrice;
    }

    public String getShippingCost() {
        return shippingCost;
    }

    public void setShippingCost(String shippingCost) {
        if(Double.parseDouble(shippingCost)==0.0)
            this.shippingCost = "Free Shipping";
        else
            this.shippingCost= "$"+ shippingCost;
    }

    public String getDaysLeft() {
        return daysLeft;
    }

    public void setDaysLeft(String daysLeft) {
        this.daysLeft = daysLeft;
    }

    public String daysLeft;
    //this five is from 1st api , which are used for transmit to 3rd tab for combine with 2nd api
    public String getProductLocation()          {return productLocation          ;}
    public String getProductOneDayShipping()    {return  productOneDayShipping   ;}
    public String getProductShippingType()      {return   productShippingType    ;}
    public String getProductShipToLocation()    {return  productShipToLocation   ;}
    public String getProductExpeditedShipping() {return  productExpeditedShipping;}

    public String getProductTopRate()           {return productTopRate;}




public String getProductImage(){return productImage;}
    public String getProductZip(){return productZip;}
    public String getProductTitle(){return productTitle;}
    public String getProductPrice() {
        return productPrice;
    }

    public String getProductShipping() {
        return productShipping;
    }

    public String getProductCondition() {
        return productCondition;
    }

    public String getProductId() {
        return productId;
    }

    public String getProductUrl() { return productUrl; }

    public boolean getWish() {
        return isInWish;
    }
}
