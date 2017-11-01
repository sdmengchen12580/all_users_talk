package org.faqrobot.text.entity;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by iyunwen on 2016/9/21.
 */

public class Robot_Information implements Parcelable {
    private List<QuickLink> quickLink;
    private List<TopAsk> topAsk;
    private List<NewAdd> newAdd;
    private WebConfig webConfig;

    @Override
    public String toString() {
        return "Robot_Information{" +
                "quickLink=" + quickLink +
                ", topAsk=" + topAsk +
                ", newAdd=" + newAdd +
                ", webConfig=" + webConfig +
                '}';
    }

    public List<QuickLink> getQuickLink() {
        return quickLink;
    }

    public void setQuickLink(List<QuickLink> quickLink) {
        this.quickLink = quickLink;
    }

    public List<TopAsk> getTopAsk() {
        return topAsk;
    }

    public void setTopAsk(List<TopAsk> topAsk) {
        this.topAsk = topAsk;
    }

    public List<NewAdd> getNewAdd() {
        return newAdd;
    }

    public void setNewAdd(List<NewAdd> newAdd) {
        this.newAdd = newAdd;
    }

    public WebConfig getWebConfig() {
        return webConfig;
    }

    public void setWebConfig(WebConfig webConfig) {
        this.webConfig = webConfig;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeList(this.quickLink);
        dest.writeList(this.topAsk);
        dest.writeList(this.newAdd);
        dest.writeParcelable(this.webConfig, flags);
    }

    public Robot_Information() {
    }

    protected Robot_Information(Parcel in) {
        this.quickLink = new ArrayList<QuickLink>();
        in.readList(this.quickLink, QuickLink.class.getClassLoader());
        this.topAsk = new ArrayList<TopAsk>();
        in.readList(this.topAsk, TopAsk.class.getClassLoader());
        this.newAdd = new ArrayList<NewAdd>();
        in.readList(this.newAdd, NewAdd.class.getClassLoader());
        this.webConfig = in.readParcelable(WebConfig.class.getClassLoader());
    }

    public static final Creator<Robot_Information> CREATOR = new Creator<Robot_Information>() {
        @Override
        public Robot_Information createFromParcel(Parcel source) {
            return new Robot_Information(source);
        }

        @Override
        public Robot_Information[] newArray(int size) {
            return new Robot_Information[size];
        }
    };
}
