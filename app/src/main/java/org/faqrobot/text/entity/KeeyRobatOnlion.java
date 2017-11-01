package org.faqrobot.text.entity;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * @param{KeeyRobatOnlion}
 * 返回结果包括
 * ①保持机器人一直在线   tspan=2
 * ②给机器人留言的     tspan=2
 * ③对问题有效性进行反馈    tspan=2
 * ④转人工         tspan=2
 * ⑤会话满意度反馈   （移动端不需要）
 * ⑥会话问题有无帮助  （移动端不需要）
 * ⑦机器人下线     tspan=2000
 *
 * Created by iyunwen on 2016/9/1.
 */

public class KeeyRobatOnlion implements Parcelable {
    private int status;
    private String message;
    private String tspan;

    @Override
    public String toString() {
        return "KeeyRobatOnlion{" +
                "status=" + status +
                ", message='" + message + '\'' +
                ", tspan='" + tspan + '\'' +
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

    public String getTspan() {
        return tspan;
    }

    public void setTspan(String tspan) {
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
        dest.writeString(this.tspan);
    }

    public KeeyRobatOnlion() {
    }

    protected KeeyRobatOnlion(Parcel in) {
        this.status = in.readInt();
        this.message = in.readString();
        this.tspan = in.readString();
    }

    public static final Creator<KeeyRobatOnlion> CREATOR = new Creator<KeeyRobatOnlion>() {
        @Override
        public KeeyRobatOnlion createFromParcel(Parcel source) {
            return new KeeyRobatOnlion(source);
        }

        @Override
        public KeeyRobatOnlion[] newArray(int size) {
            return new KeeyRobatOnlion[size];
        }
    };
}
