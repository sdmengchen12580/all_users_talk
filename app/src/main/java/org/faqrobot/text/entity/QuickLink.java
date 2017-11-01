package org.faqrobot.text.entity;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by iyunwen on 2016/9/21.
 */
public class QuickLink implements Parcelable {
    private String createtime;
    private String imageUrl;
    private String linkUrl;
    private String name;

    public String getCreatetime() {
        return createtime;
    }

    public void setCreatetime(String createtime) {
        this.createtime = createtime;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getLinkUrl() {
        return linkUrl;
    }

    public void setLinkUrl(String linkUrl) {
        this.linkUrl = linkUrl;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "QuickLink{" +
                "createtime='" + createtime + '\'' +
                ", imageUrl='" + imageUrl + '\'' +
                ", linkUrl='" + linkUrl + '\'' +
                ", name='" + name + '\'' +
                '}';
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.createtime);
        dest.writeString(this.imageUrl);
        dest.writeString(this.linkUrl);
        dest.writeString(this.name);
    }

    public QuickLink() {
    }

    protected QuickLink(Parcel in) {
        this.createtime = in.readString();
        this.imageUrl = in.readString();
        this.linkUrl = in.readString();
        this.name = in.readString();
    }

    public static final Creator<QuickLink> CREATOR = new Creator<QuickLink>() {
        @Override
        public QuickLink createFromParcel(Parcel source) {
            return new QuickLink(source);
        }

        @Override
        public QuickLink[] newArray(int size) {
            return new QuickLink[size];
        }
    };
}
