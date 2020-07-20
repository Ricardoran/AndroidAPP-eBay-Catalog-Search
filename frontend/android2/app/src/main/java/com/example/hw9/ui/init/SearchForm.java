package com.example.hw9.ui.init;

public class SearchForm {
    public String sortby;
    public String keyword;
    public String location;
    public String minprice;
    public String maxprice;
    public String new1;
    public String used;
    public String unspecified;


    public String getSortby() {
        return sortby;
    }

    public void setSortby(String sortby) {
        this.sortby = sortby;
    }

    public String getKeyword() {
        return keyword;
    }

    public void setKeyword(String keyword) {
        this.keyword = keyword;
    }

    public void setMinprice(String minprice){
        this.minprice = minprice;
    }

    public String getMinprice(){
        return minprice;
    }

    public void setMaxprice(String maxprice){
        this.maxprice = maxprice;
    }

    public String getMaxprice(){
        return maxprice;
    }


    public void setNew(String new1) {
        this.new1=new1;
    }

    public void setUsed(String used) {
        this.used=used;
    }

    public void setUnspecified(String unspecified) {
        this.unspecified=unspecified;
    }

    @Override
    public String toString() {
        return "SearchForm{" +
                "sortby='" + sortby + '\'' +
                ", keyword='" + keyword + '\'' +
                ", location='" + location + '\'' +
                ", minprice=" + minprice +
                ", maxprice=" + maxprice +
                ", new1='" + new1 + '\'' +
                ", used='" + used + '\'' +
                ", unspecified='" + unspecified + '\'' +
                '}';
    }
}
