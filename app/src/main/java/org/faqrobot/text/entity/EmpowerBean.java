package org.faqrobot.text.entity;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * 授权状态的判断Bean
 * <p>
 * Created by housh on 2017/4/19.
 */

public class EmpowerBean implements Parcelable {
    private int status;
    private String message;
    private String data;
    private String time;
    private int tspan;

    @Override
    public String toString() {
        return "EmpowerBean{" +
                "status=" + status +
                ", message='" + message + '\'' +
                ", data='" + data + '\'' +
                ", time='" + time + '\'' +
                ", tspan=" + tspan +
                '}';
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public int getTspan() {
        return tspan;
    }

    public void setTspan(int tspan) {
        this.tspan = tspan;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.status);
        dest.writeString(this.message);
        dest.writeString(this.data);
        dest.writeString(this.time);
        dest.writeInt(this.tspan);
    }

    public EmpowerBean() {
    }

    protected EmpowerBean(Parcel in) {
        this.status = in.readInt();
        this.message = in.readString();
        this.data = in.readString();
        this.time = in.readString();
        this.tspan = in.readInt();
    }

    public static final Creator<EmpowerBean> CREATOR = new Creator<EmpowerBean>() {
        @Override
        public EmpowerBean createFromParcel(Parcel source) {
            return new EmpowerBean(source);
        }

        @Override
        public EmpowerBean[] newArray(int size) {
            return new EmpowerBean[size];
        }
    };
}
