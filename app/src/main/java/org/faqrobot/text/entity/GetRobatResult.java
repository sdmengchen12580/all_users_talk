package org.faqrobot.text.entity;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

/**
 * 返回的 android返回的结构集合
 * Created by iyunwen on 2016/9/1.
 */

public class GetRobatResult implements Parcelable {

    private String message;
    private String nowState;
    private List<RobotAnswer> robotAnswer;
    private int status;
    private String tspan;

    //描述是发送方还是接收方
    private int direct;
    //设置不同类型的不同数据(富文本使用)
    private List<String> urlRichLink;
    private List<String> urlRichImage;
    private List<String> urlRichText;
    private String urlVoice;
    private String urlImage;
    //设置类型
    private String msgType;
    private String content;  //富文本中的内容
    private boolean isPlaying;
    private String time;
    private int currentPosition;
    private int totalTime;

    @Override
    public String toString() {
        return "GetRobatResult{" +
                "message='" + message + '\'' +
                ", nowState='" + nowState + '\'' +
                ", robotAnswer=" + robotAnswer +
                ", status=" + status +
                ", tspan='" + tspan + '\'' +
                ", direct=" + direct +
                ", urlRichLink=" + urlRichLink +
                ", urlRichImage=" + urlRichImage +
                ", urlRichText=" + urlRichText +
                ", urlVoice='" + urlVoice + '\'' +
                ", urlImage='" + urlImage + '\'' +
                ", msgType='" + msgType + '\'' +
                ", content='" + content + '\'' +
                ", isPlaying=" + isPlaying +
                ", time='" + time + '\'' +
                ", currentPosition=" + currentPosition +
                ", totalTime=" + totalTime +
                '}';
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getNowState() {
        return nowState;
    }

    public void setNowState(String nowState) {
        this.nowState = nowState;
    }

    public List<RobotAnswer> getRobotAnswer() {
        return robotAnswer;
    }

    public void setRobotAnswer(List<RobotAnswer> robotAnswer) {
        this.robotAnswer = robotAnswer;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getTspan() {
        return tspan;
    }

    public void setTspan(String tspan) {
        this.tspan = tspan;
    }

    public int getDirect() {
        return direct;
    }

    public void setDirect(int direct) {
        this.direct = direct;
    }

    public List<String> getUrlRichLink() {
        return urlRichLink;
    }

    public void setUrlRichLink(List<String> urlRichLink) {
        this.urlRichLink = urlRichLink;
    }

    public List<String> getUrlRichImage() {
        return urlRichImage;
    }

    public void setUrlRichImage(List<String> urlRichImage) {
        this.urlRichImage = urlRichImage;
    }

    public List<String> getUrlRichText() {
        return urlRichText;
    }

    public void setUrlRichText(List<String> urlRichText) {
        this.urlRichText = urlRichText;
    }

    public String getUrlVoice() {
        return urlVoice;
    }

    public void setUrlVoice(String urlVoice) {
        this.urlVoice = urlVoice;
    }

    public String getUrlImage() {
        return urlImage;
    }

    public void setUrlImage(String urlImage) {
        this.urlImage = urlImage;
    }

    public String getMsgType() {
        return msgType;
    }

    public void setMsgType(String msgType) {
        this.msgType = msgType;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public boolean isPlaying() {
        return isPlaying;
    }

    public void setPlaying(boolean playing) {
        isPlaying = playing;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public int getCurrentPosition() {
        return currentPosition;
    }

    public void setCurrentPosition(int currentPosition) {
        this.currentPosition = currentPosition;
    }

    public int getTotalTime() {
        return totalTime;
    }

    public void setTotalTime(int totalTime) {
        this.totalTime = totalTime;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.message);
        dest.writeString(this.nowState);
        dest.writeTypedList(this.robotAnswer);
        dest.writeInt(this.status);
        dest.writeString(this.tspan);
        dest.writeInt(this.direct);
        dest.writeStringList(this.urlRichLink);
        dest.writeStringList(this.urlRichImage);
        dest.writeStringList(this.urlRichText);
        dest.writeString(this.urlVoice);
        dest.writeString(this.urlImage);
        dest.writeString(this.msgType);
        dest.writeString(this.content);
        dest.writeByte(this.isPlaying ? (byte) 1 : (byte) 0);
        dest.writeString(this.time);
        dest.writeInt(this.currentPosition);
        dest.writeInt(this.totalTime);
    }

    public GetRobatResult() {
    }

    protected GetRobatResult(Parcel in) {
        this.message = in.readString();
        this.nowState = in.readString();
        this.robotAnswer = in.createTypedArrayList(RobotAnswer.CREATOR);
        this.status = in.readInt();
        this.tspan = in.readString();
        this.direct = in.readInt();
        this.urlRichLink = in.createStringArrayList();
        this.urlRichImage = in.createStringArrayList();
        this.urlRichText = in.createStringArrayList();
        this.urlVoice = in.readString();
        this.urlImage = in.readString();
        this.msgType = in.readString();
        this.content = in.readString();
        this.isPlaying = in.readByte() != 0;
        this.time = in.readString();
        this.currentPosition = in.readInt();
        this.totalTime = in.readInt();
    }

    public static final Creator<GetRobatResult> CREATOR = new Creator<GetRobatResult>() {
        @Override
        public GetRobatResult createFromParcel(Parcel source) {
            return new GetRobatResult(source);
        }

        @Override
        public GetRobatResult[] newArray(int size) {
            return new GetRobatResult[size];
        }
    };
}
