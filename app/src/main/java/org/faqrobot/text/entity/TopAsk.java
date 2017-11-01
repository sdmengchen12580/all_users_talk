package org.faqrobot.text.entity;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by iyunwen on 2016/9/21.
 */
public class TopAsk implements Parcelable {
    private int aId;
    private int groupId;
    private int hits;
    private int level;
    private String question;
    private int solutionId;
    private String time;
    private String url;

    @Override
    public String toString() {
        return "TopAsk{" +
                "aId=" + aId +
                ", groupId=" + groupId +
                ", hits=" + hits +
                ", level=" + level +
                ", question='" + question + '\'' +
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
        dest.writeInt(this.hits);
        dest.writeInt(this.level);
        dest.writeString(this.question);
        dest.writeInt(this.solutionId);
        dest.writeString(this.time);
        dest.writeString(this.url);
    }

    public TopAsk() {
    }

    protected TopAsk(Parcel in) {
        this.aId = in.readInt();
        this.groupId = in.readInt();
        this.hits = in.readInt();
        this.level = in.readInt();
        this.question = in.readString();
        this.solutionId = in.readInt();
        this.time = in.readString();
        this.url = in.readString();
    }

    public static final Creator<TopAsk> CREATOR = new Creator<TopAsk>() {
        @Override
        public TopAsk createFromParcel(Parcel source) {
            return new TopAsk(source);
        }

        @Override
        public TopAsk[] newArray(int size) {
            return new TopAsk[size];
        }
    };
}
