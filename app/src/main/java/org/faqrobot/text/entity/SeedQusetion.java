package org.faqrobot.text.entity;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * 返回的问题数据
 * Created by iyunwen on 2016/9/5.
 */
public class SeedQusetion implements Parcelable {
    private String addTime;   //添加的时间
    private String answer;   //问题
    private int hits;  //
    private String keyword;   //问题
    private int level;    //等级
    private String question; //答案
    private int similarCount;    //
    private int solutionId;    //解决ID
    private int solutionType;    //解决ID
    private int status;   //状态
    private int suggestMode;
    private String suggestModes;//建议方式
    private String thirUrl;//第三方连接的网址
    private String updateTime;//第三方连接的网址
    private int usefull;
    private int useless;
    private String userName;//用户名称
    private int webid; //webid的 唯一标示符

    @Override
    public String toString() {
        return "SeedQusetion{" +
                "addTime='" + addTime + '\'' +
                ", answer='" + answer + '\'' +
                ", hits=" + hits +
                ", keyword='" + keyword + '\'' +
                ", level=" + level +
                ", question='" + question + '\'' +
                ", similarCount=" + similarCount +
                ", solutionId=" + solutionId +
                ", solutionType=" + solutionType +
                ", status=" + status +
                ", suggestMode=" + suggestMode +
                ", suggestModes='" + suggestModes + '\'' +
                ", thirUrl='" + thirUrl + '\'' +
                ", updateTime='" + updateTime + '\'' +
                ", usefull=" + usefull +
                ", useless=" + useless +
                ", userName='" + userName + '\'' +
                ", webid=" + webid +
                '}';
    }

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }

    public int getHits() {
        return hits;
    }

    public void setHits(int hits) {
        this.hits = hits;
    }

    public String getKeyword() {
        return keyword;
    }

    public void setKeyword(String keyword) {
        this.keyword = keyword;
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

    public int getSimilarCount() {
        return similarCount;
    }

    public void setSimilarCount(int similarCount) {
        this.similarCount = similarCount;
    }

    public int getSolutionId() {
        return solutionId;
    }

    public void setSolutionId(int solutionId) {
        this.solutionId = solutionId;
    }

    public int getSolutionType() {
        return solutionType;
    }

    public void setSolutionType(int solutionType) {
        this.solutionType = solutionType;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public int getSuggestMode() {
        return suggestMode;
    }

    public void setSuggestMode(int suggestMode) {
        this.suggestMode = suggestMode;
    }

    public String getSuggestModes() {
        return suggestModes;
    }

    public void setSuggestModes(String suggestModes) {
        this.suggestModes = suggestModes;
    }

    public String getThirUrl() {
        return thirUrl;
    }

    public void setThirUrl(String thirUrl) {
        this.thirUrl = thirUrl;
    }

    public String getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(String updateTime) {
        this.updateTime = updateTime;
    }

    public int getUsefull() {
        return usefull;
    }

    public void setUsefull(int usefull) {
        this.usefull = usefull;
    }

    public int getUseless() {
        return useless;
    }

    public void setUseless(int useless) {
        this.useless = useless;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public int getWebid() {
        return webid;
    }

    public void setWebid(int webid) {
        this.webid = webid;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.addTime);
        dest.writeString(this.answer);
        dest.writeInt(this.hits);
        dest.writeString(this.keyword);
        dest.writeInt(this.level);
        dest.writeString(this.question);
        dest.writeInt(this.similarCount);
        dest.writeInt(this.solutionId);
        dest.writeInt(this.solutionType);
        dest.writeInt(this.status);
        dest.writeInt(this.suggestMode);
        dest.writeString(this.suggestModes);
        dest.writeString(this.thirUrl);
        dest.writeString(this.updateTime);
        dest.writeInt(this.usefull);
        dest.writeInt(this.useless);
        dest.writeString(this.userName);
        dest.writeInt(this.webid);
    }

    public SeedQusetion() {
    }

    protected SeedQusetion(Parcel in) {
        this.addTime = in.readString();
        this.answer = in.readString();
        this.hits = in.readInt();
        this.keyword = in.readString();
        this.level = in.readInt();
        this.question = in.readString();
        this.similarCount = in.readInt();
        this.solutionId = in.readInt();
        this.solutionType = in.readInt();
        this.status = in.readInt();
        this.suggestMode = in.readInt();
        this.suggestModes = in.readString();
        this.thirUrl = in.readString();
        this.updateTime = in.readString();
        this.usefull = in.readInt();
        this.useless = in.readInt();
        this.userName = in.readString();
        this.webid = in.readInt();
    }

    public static final Creator<SeedQusetion> CREATOR = new Creator<SeedQusetion>() {
        @Override
        public SeedQusetion createFromParcel(Parcel source) {
            return new SeedQusetion(source);
        }

        @Override
        public SeedQusetion[] newArray(int size) {
            return new SeedQusetion[size];
        }
    };
}
