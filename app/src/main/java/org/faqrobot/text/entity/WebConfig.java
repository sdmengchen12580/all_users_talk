package org.faqrobot.text.entity;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by iyunwen on 2016/9/21.
 */
public class WebConfig implements Parcelable {
    private int chatTheme;
    private String chatUrl;
    private String dateTime;
    private int level;
    private String helloWord;
    private int logoType;
    private String logoUrl;
    private String robotName;
    private String sysNum;
    private String unknownWord;
    private String webName;
    private String webSite;
    private String webSubType;
    private String webType;

    @Override
    public String toString() {
        return "WebConfig{" +
                "chatTheme=" + chatTheme +
                ", chatUrl='" + chatUrl + '\'' +
                ", dateTime='" + dateTime + '\'' +
                ", level=" + level +
                ", helloWord='" + helloWord + '\'' +
                ", logoType=" + logoType +
                ", logoUrl='" + logoUrl + '\'' +
                ", robotName='" + robotName + '\'' +
                ", sysNum='" + sysNum + '\'' +
                ", unknownWord='" + unknownWord + '\'' +
                ", webName='" + webName + '\'' +
                ", webSite='" + webSite + '\'' +
                ", webSubType='" + webSubType + '\'' +
                ", webType='" + webType + '\'' +
                '}';
    }

    public int getChatTheme() {
        return chatTheme;
    }

    public void setChatTheme(int chatTheme) {
        this.chatTheme = chatTheme;
    }

    public String getChatUrl() {
        return chatUrl;
    }

    public void setChatUrl(String chatUrl) {
        this.chatUrl = chatUrl;
    }

    public String getDateTime() {
        return dateTime;
    }

    public void setDateTime(String dateTime) {
        this.dateTime = dateTime;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public String getHelloWord() {
        return helloWord;
    }

    public void setHelloWord(String helloWord) {
        this.helloWord = helloWord;
    }

    public int getLogoType() {
        return logoType;
    }

    public void setLogoType(int logoType) {
        this.logoType = logoType;
    }

    public String getLogoUrl() {
        return logoUrl;
    }

    public void setLogoUrl(String logoUrl) {
        this.logoUrl = logoUrl;
    }

    public String getRobotName() {
        return robotName;
    }

    public void setRobotName(String robotName) {
        this.robotName = robotName;
    }

    public String getSysNum() {
        return sysNum;
    }

    public void setSysNum(String sysNum) {
        this.sysNum = sysNum;
    }

    public String getUnknownWord() {
        return unknownWord;
    }

    public void setUnknownWord(String unknownWord) {
        this.unknownWord = unknownWord;
    }

    public String getWebName() {
        return webName;
    }

    public void setWebName(String webName) {
        this.webName = webName;
    }

    public String getWebSite() {
        return webSite;
    }

    public void setWebSite(String webSite) {
        this.webSite = webSite;
    }

    public String getWebSubType() {
        return webSubType;
    }

    public void setWebSubType(String webSubType) {
        this.webSubType = webSubType;
    }

    public String getWebType() {
        return webType;
    }

    public void setWebType(String webType) {
        this.webType = webType;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.chatTheme);
        dest.writeString(this.chatUrl);
        dest.writeString(this.dateTime);
        dest.writeInt(this.level);
        dest.writeString(this.helloWord);
        dest.writeInt(this.logoType);
        dest.writeString(this.logoUrl);
        dest.writeString(this.robotName);
        dest.writeString(this.sysNum);
        dest.writeString(this.unknownWord);
        dest.writeString(this.webName);
        dest.writeString(this.webSite);
        dest.writeString(this.webSubType);
        dest.writeString(this.webType);
    }

    public WebConfig() {
    }

    protected WebConfig(Parcel in) {
        this.chatTheme = in.readInt();
        this.chatUrl = in.readString();
        this.dateTime = in.readString();
        this.level = in.readInt();
        this.helloWord = in.readString();
        this.logoType = in.readInt();
        this.logoUrl = in.readString();
        this.robotName = in.readString();
        this.sysNum = in.readString();
        this.unknownWord = in.readString();
        this.webName = in.readString();
        this.webSite = in.readString();
        this.webSubType = in.readString();
        this.webType = in.readString();
    }

    public static final Creator<WebConfig> CREATOR = new Creator<WebConfig>() {
        @Override
        public WebConfig createFromParcel(Parcel source) {
            return new WebConfig(source);
        }

        @Override
        public WebConfig[] newArray(int size) {
            return new WebConfig[size];
        }
    };
}
