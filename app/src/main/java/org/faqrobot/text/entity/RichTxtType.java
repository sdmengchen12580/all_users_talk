package org.faqrobot.text.entity;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by iyunwen on 2016/9/8.
 */
public class RichTxtType implements Parcelable {

    private String url;
    private int height;
    private int weight;
    private int type;


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.url);
        dest.writeInt(this.height);
        dest.writeInt(this.weight);
        dest.writeInt(this.type);
    }


    protected RichTxtType(Parcel in) {
        this.url = in.readString();
        this.height = in.readInt();
        this.weight = in.readInt();
        this.type = in.readInt();
    }

    public static final Creator<RichTxtType> CREATOR = new Creator<RichTxtType>() {
        @Override
        public RichTxtType createFromParcel(Parcel source) {
            return new RichTxtType(source);
        }

        @Override
        public RichTxtType[] newArray(int size) {
            return new RichTxtType[size];
        }
    };

    @Override
    public String toString() {
        return "RichTxtType{" +
                "url='" + url + '\'' +
                ", height=" + height +
                ", weight=" + weight +
                ", type=" + type +
                '}';
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public int getWeight() {
        return weight;
    }

    public void setWeight(int weight) {
        this.weight = weight;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public static Creator<RichTxtType> getCREATOR() {
        return CREATOR;
    }
}
