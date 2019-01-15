package com.example.android.recipeapp;

import android.os.Parcel;
import android.os.Parcelable;

public class Recipe  implements Parcelable {

    private String publisher;
    private String title;
    private String imgUrl;
    private String sourceUrl;

    public Recipe(String publisher, String title, String imgUrl, String sourceUrl) {

        this.publisher = publisher;
        this.title = title;
        this.imgUrl = imgUrl;
        this.sourceUrl = sourceUrl;
    }

    protected Recipe(Parcel in) {
        publisher = in.readString();
        title = in.readString();
        imgUrl = in.readString();
        sourceUrl = in.readString();
    }

    public static final Creator<Recipe> CREATOR = new Creator<Recipe>() {
        @Override
        public Recipe createFromParcel(Parcel in) {
            return new Recipe(in);
        }

        @Override
        public Recipe[] newArray(int size) {
            return new Recipe[size];
        }
    };

    public String getPublisher() {
        return this.publisher;
    }

    public String getTitle() {
        return this.title;
    }

    public String getImgUrl() {
        return this.imgUrl;
    }

    public String getSourceUrl() {
        return this.sourceUrl;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(publisher);
        parcel.writeString(title);
        parcel.writeString(imgUrl);
        parcel.writeString(sourceUrl);
    }
}
