package org.faqrobot.text.entity;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by iyunwen on 2016/9/21.
 */
public class NewAdd implements Parcelable {
    private int aId;
    private int groupId;
    private String groupName;
    private int hits;
    private int level;
    private String question;
    private String requestRegex;
    private int solutionId;
    private String time;
    private String url;

    @Override
    public String toString() {
        return "NewAdd{" +
                "aId=" + aId +
                ", groupId=" + groupId +
                ", groupName='" + groupName + '\'' +
                ", hits=" + hits +
                ", level=" + level +
                ", question='" + question + '\'' +
                ", requestRegex='" + requestRegex + '\'' +
                ", solutionId=" + solutionId +
                ", time='" + time + '\'' +
                ", url='" + url + '\'' +
                '}';
    }

    public int getaId() {
        return aId;
    }

    public void setaId(int aId) {
        this.aId = aId;
    }

    public int getGroupId() {
        return groupId;
    }

    public void setGroupId(int groupId) {
        this.groupId = groupId;
    }

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    public int getHits() {
        return hits;
    }

    public void setHits(int hits) {
        this.hits = hits;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public String getRequestRegex() {
        return requestRegex;
    }

    public void setRequestRegex(String requestRegex) {
        this.requestRegex = requestRegex;
    }

    public int getSolutionId() {
        return solutionId;
    }

    public void setSolutionId(int solutionId) {
        this.solutionId = solutionId;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
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
        dest.writeInt(this.aId);
        dest.writeInt(this.groupId);
        dest.writeString(this.groupName);
        dest.writeInt(this.hits);
        dest.writeInt(this.level);
        dest.writeString(this.question);
        dest.writeString(this.requestRegex);
        dest.writeInt(this.solutionId);
        dest.writeString(this.time);
        dest.writeString(this.url);
    }

    public NewAdd() {
    }

    protected NewAdd(Parcel in) {
        this.aId = in.readInt();
        this.groupId = in.readInt();
        this.groupName = in.readString();
        this.hits = in.readInt();
        this.level = in.readInt();
        this.question = in.readString();
        this.requestRegex = in.readString();
        this.solutionId = in.readInt();
        this.time = in.readString();
        this.url = in.readString();
    }

    public static final Creator<NewAdd> CREATOR = new Creator<NewAdd>() {
        @Override
        public NewAdd createFromParcel(Parcel source) {
            return new NewAdd(source);
        }

        @Override
        public NewAdd[] newArray(int size) {
            return new NewAdd[size];
        }
    };
}
