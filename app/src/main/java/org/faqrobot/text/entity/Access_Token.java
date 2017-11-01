package org.faqrobot.text.entity;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * 获取access_token
 *
 * Created by iyunwen on 2016/9/1.
 */

public class Access_Token implements Parcelable {
    private String message;
    private int status;
    private String access_token;

    @Override
    public String toString() {
        return "Access_Token{" +
                "message='" + message + '\'' +
                ", status=" + status +
                ", access_token='" + access_token + '\'' +
                '}';
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getAccess_token() {
        return access_token;
    }

    public void setAccess_token(String access_token) {
        this.access_token = access_token;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.message);
        dest.writeInt(this.status);
        dest.writeString(this.access_token);
    }

    public Access_Token() {
    }

    protected Access_Token(Parcel in) {
        this.message = in.readString();
        this.status = in.readInt();
        this.access_token = in.readString();
    }

    public static final Creator<Access_Token> CREATOR = new Creator<Access_Token>() {
        @Override
        public Access_Token createFromParcel(Parcel source) {
            return new Access_Token(source);
        }

        @Override
        public Access_Token[] newArray(int size) {
            return new Access_Token[size];
        }
    };
}
