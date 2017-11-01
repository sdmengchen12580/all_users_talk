package org.faqrobot.text.entity;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * APP版本号
 * Created by iyunwen on 2016/10/13.
 */

public class AppVersion implements Parcelable {
    private Integer id;
    private Integer versionNumber;
    private String peojectName;
    private Integer type;
    private String fileName;
    private String fileUrl;
    private Integer del;
    private String createUser;

    @Override
    public String toString() {
        return "AppVersion{" +
                "id=" + id +
                ", versionNumber=" + versionNumber +
                ", peojectName='" + peojectName + '\'' +
                ", type=" + type +
                ", fileName='" + fileName + '\'' +
                ", fileUrl='" + fileUrl + '\'' +
                ", del=" + del +
                ", createUser='" + createUser + '\'' +
                '}';
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getVersionNumber() {
        return versionNumber;
    }

    public void setVersionNumber(Integer versionNumber) {
        this.versionNumber = versionNumber;
    }

    public String getPeojectName() {
        return peojectName;
    }

    public void setPeojectName(String peojectName) {
        this.peojectName = peojectName;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getFileUrl() {
        return fileUrl;
    }

    public void setFileUrl(String fileUrl) {
        this.fileUrl = fileUrl;
    }

    public Integer getDel() {
        return del;
    }

    public void setDel(Integer del) {
        this.del = del;
    }

    public String getCreateUser() {
        return createUser;
    }

    public void setCreateUser(String createUser) {
        this.createUser = createUser;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(this.id);
        dest.writeValue(this.versionNumber);
        dest.writeString(this.peojectName);
        dest.writeValue(this.type);
        dest.writeString(this.fileName);
        dest.writeString(this.fileUrl);
        dest.writeValue(this.del);
        dest.writeString(this.createUser);
    }

    public AppVersion() {
    }

    protected AppVersion(Parcel in) {
        this.id = (Integer) in.readValue(Integer.class.getClassLoader());
        this.versionNumber = (Integer) in.readValue(Integer.class.getClassLoader());
        this.peojectName = in.readString();
        this.type = (Integer) in.readValue(Integer.class.getClassLoader());
        this.fileName = in.readString();
        this.fileUrl = in.readString();
        this.del = (Integer) in.readValue(Integer.class.getClassLoader());
        this.createUser = in.readString();
    }

    public static final Creator<AppVersion> CREATOR = new Creator<AppVersion>() {
        @Override
        public AppVersion createFromParcel(Parcel source) {
            return new AppVersion(source);
        }

        @Override
        public AppVersion[] newArray(int size) {
            return new AppVersion[size];
        }
    };
}
