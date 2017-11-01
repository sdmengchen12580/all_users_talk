package org.faqrobot.text.entity;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

/**
 * Created by iyunwen on 2016/9/1.
 */

public class RobotAnswer implements Parcelable {

    private int aId;  //对应回答答案的id代号
    private String ansCon; //机器人回答的问题内容，如果是引导问题，则此项为空
    private int answerType;  //回答类型
//    private String gusWords; //robat引导话
    private String cluid;    //云的唯一标示
    private List<GusList> gusList;
    private String msgType;//答案内容的格式
    private MyQuestion question;      //问题
    private List<RelateLessList> relateLessList;
    private List<MyQuestion> relateList; //候选的相似答案
    private boolean showQue;   //是否展示问题
    private int relateListStartSelectIndex;
    private ThirdUrl thirdUrl;    //第三方地址

    @Override
    public String toString() {
        return "RobotAnswer{" +
                "aId=" + aId +
                ", ansCon='" + ansCon + '\'' +
                ", answerType=" + answerType +
                ", cluid='" + cluid + '\'' +
                ", gusList=" + gusList +
                ", msgType='" + msgType + '\'' +
                ", question=" + question +
                ", relateLessList=" + relateLessList +
                ", relateList=" + relateList +
                ", showQue=" + showQue +
                ", relateListStartSelectIndex=" + relateListStartSelectIndex +
                ", thirdUrl=" + thirdUrl +
                '}';
    }

    public int getaId() {
        return aId;
    }

    public void setaId(int aId) {
        this.aId = aId;
    }

    public String getAnsCon() {
        return ansCon;
    }

    public void setAnsCon(String ansCon) {
        this.ansCon = ansCon;
    }

    public int getAnswerType() {
        return answerType;
    }

    public void setAnswerType(int answerType) {
        this.answerType = answerType;
    }

    public String getCluid() {
        return cluid;
    }

    public void setCluid(String cluid) {
        this.cluid = cluid;
    }

    public List<GusList> getGusList() {
        return gusList;
    }

    public void setGusList(List<GusList> gusList) {
        this.gusList = gusList;
    }

    public String getMsgType() {
        return msgType;
    }

    public void setMsgType(String msgType) {
        this.msgType = msgType;
    }

    public MyQuestion getQuestion() {
        return question;
    }

    public void setQuestion(MyQuestion question) {
        this.question = question;
    }

    public List<RelateLessList> getRelateLessList() {
        return relateLessList;
    }

    public void setRelateLessList(List<RelateLessList> relateLessList) {
        this.relateLessList = relateLessList;
    }

    public List<MyQuestion> getRelateList() {
        return relateList;
    }

    public void setRelateList(List<MyQuestion> relateList) {
        this.relateList = relateList;
    }

    public boolean isShowQue() {
        return showQue;
    }

    public void setShowQue(boolean showQue) {
        this.showQue = showQue;
    }

    public int getRelateListStartSelectIndex() {
        return relateListStartSelectIndex;
    }

    public void setRelateListStartSelectIndex(int relateListStartSelectIndex) {
        this.relateListStartSelectIndex = relateListStartSelectIndex;
    }

    public ThirdUrl getThirdUrl() {
        return thirdUrl;
    }

    public void setThirdUrl(ThirdUrl thirdUrl) {
        this.thirdUrl = thirdUrl;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.aId);
        dest.writeString(this.ansCon);
        dest.writeInt(this.answerType);
        dest.writeString(this.cluid);
        dest.writeTypedList(this.gusList);
        dest.writeString(this.msgType);
        dest.writeParcelable(this.question, flags);
        dest.writeTypedList(this.relateLessList);
        dest.writeTypedList(this.relateList);
        dest.writeByte(this.showQue ? (byte) 1 : (byte) 0);
        dest.writeInt(this.relateListStartSelectIndex);
        dest.writeParcelable(this.thirdUrl, flags);
    }

    public RobotAnswer() {
    }

    protected RobotAnswer(Parcel in) {
        this.aId = in.readInt();
        this.ansCon = in.readString();
        this.answerType = in.readInt();
        this.cluid = in.readString();
        this.gusList = in.createTypedArrayList(GusList.CREATOR);
        this.msgType = in.readString();
        this.question = in.readParcelable(MyQuestion.class.getClassLoader());
        this.relateLessList = in.createTypedArrayList(RelateLessList.CREATOR);
        this.relateList = in.createTypedArrayList(MyQuestion.CREATOR);
        this.showQue = in.readByte() != 0;
        this.relateListStartSelectIndex = in.readInt();
        this.thirdUrl = in.readParcelable(ThirdUrl.class.getClassLoader());
    }

    public static final Creator<RobotAnswer> CREATOR = new Creator<RobotAnswer>() {
        @Override
        public RobotAnswer createFromParcel(Parcel source) {
            return new RobotAnswer(source);
        }

        @Override
        public RobotAnswer[] newArray(int size) {
            return new RobotAnswer[size];
        }
    };
}
