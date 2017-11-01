package org.faqrobot.text.entity;

import android.graphics.PointF;
import android.os.Parcel;
import android.os.Parcelable;

/**
 * 视图的坐标点
 * <p>
 * Created by housh on 2017/4/19.
 */

public class ViewCoordinate implements Parcelable {
    private PointF coordinatae;

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeParcelable(this.coordinatae, flags);
    }

    public ViewCoordinate() {
    }

    protected ViewCoordinate(Parcel in) {
        this.coordinatae = in.readParcelable(PointF.class.getClassLoader());
    }

    public static final Creator<ViewCoordinate> CREATOR = new Creator<ViewCoordinate>() {
        @Override
        public ViewCoordinate createFromParcel(Parcel source) {
            return new ViewCoordinate(source);
        }

        @Override
        public ViewCoordinate[] newArray(int size) {
            return new ViewCoordinate[size];
        }
    };
}
