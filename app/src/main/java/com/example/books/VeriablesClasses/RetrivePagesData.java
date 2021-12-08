package com.example.books.VeriablesClasses;

import android.os.Parcel;
import android.os.Parcelable;

public class RetrivePagesData implements Parcelable {
    String pageNo, page;

    public RetrivePagesData() {
    }

    public RetrivePagesData(String pageNo, String page) {
        this.pageNo = pageNo;
        this.page = page;
    }

    protected RetrivePagesData(Parcel in) {
        pageNo = in.readString();
        page = in.readString();
    }

    public static final Creator<RetrivePagesData> CREATOR = new Creator<RetrivePagesData>() {
        @Override
        public RetrivePagesData createFromParcel(Parcel in) {
            return new RetrivePagesData(in);
        }

        @Override
        public RetrivePagesData[] newArray(int size) {
            return new RetrivePagesData[size];
        }
    };

    public String getPageNo() {
        return pageNo;
    }

    public void setPageNo(String pageNo) {
        this.pageNo = pageNo;
    }

    public String getPage() {
        return page;
    }

    public void setPage(String page) {
        this.page = page;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(pageNo);
        parcel.writeString(page);
    }
}
