package org.faqrobot.text.entity;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by iyunwen on 2016/9/23.
 */
public class ThirdUrl implements Parcelable {
    private String tabTitle;
    private String url;

    @Override
    public String toString() {
        return "ThirdUrl{" +
                "tabTitle='" + tabTitle + '\'' +
                ", url='" + url + '\'' +
                '}';
    }

    public String getTabTitle() {
        return tabTitle;
    }

    public void setTabTitle(String tabTitle) {
        this.tabTitle = tabTitle;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.tabTitle);
        dest.writeString(this.url);
    }

    public ThirdUrl() {
    }

    protected ThirdUrl(Parcel in) {
        this.tabTitle = in.readString();
        this.url = in.readString();
    }

    public static final Creator<ThirdUrl> CREATOR = new Creator<ThirdUrl>() {
        @Override
        public ThirdUrl createFromParcel(Parcel source) {
            return new ThirdUrl(source);
        }

        @Override
        public ThirdUrl[] newArray(int size) {
            return new ThirdUrl[size];
        }
    };
}
