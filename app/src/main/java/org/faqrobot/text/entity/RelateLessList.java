package org.faqrobot.text.entity;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * 后选列表
 * Created by iyunwen on 2016/9/5.
 */
public class RelateLessList implements Parcelable {

    private int qId;
    private String question;
    private SeedQusetion seedQuestion;
    private boolean showQues;
    private int solutionId;

    @Override
    public String toString() {
        return "RelateLessList{" +
                "qId=" + qId +
                ", question='" + question + '\'' +
                ", seedQuestion=" + seedQuestion +
                ", showQues=" + showQues +
                ", solutionId=" + solutionId +
                '}';
    }

    public int getqId() {
        return qId;
    }

    public void setqId(int qId) {
        this.qId = qId;
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public SeedQusetion getSeedQuestion() {
        return seedQuestion;
    }

    public void setSeedQuestion(SeedQusetion seedQuestion) {
        this.seedQuestion = seedQuestion;
    }

    public boolean isShowQues() {
        return showQues;
    }

    public void setShowQues(boolean showQues) {
        this.showQues = showQues;
    }

    public int getSolutionId() {
        return solutionId;
    }

    public void setSolutionId(int solutionId) {
        this.solutionId = solutionId;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.qId);
        dest.writeString(this.question);
        dest.writeParcelable(this.seedQuestion, flags);
        dest.writeByte(this.showQues ? (byte) 1 : (byte) 0);
        dest.writeInt(this.solutionId);
    }

    public RelateLessList() {
    }

    protected RelateLessList(Parcel in) {
        this.qId = in.readInt();
        this.question = in.readString();
        this.seedQuestion = in.readParcelable(SeedQusetion.class.getClassLoader());
        this.showQues = in.readByte() != 0;
        this.solutionId = in.readInt();
    }

    public static final Creator<RelateLessList> CREATOR = new Creator<RelateLessList>() {
        @Override
        public RelateLessList createFromParcel(Parcel source) {
            return new RelateLessList(source);
        }

        @Override
        public RelateLessList[] newArray(int size) {
            return new RelateLessList[size];
        }
    };
}
