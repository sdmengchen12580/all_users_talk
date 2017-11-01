package org.faqrobot.text.entity;

/**
 * Created by housh on 2017/4/25.
 */

public class NetState {

    private static NetState mNetState = null;

    public NetState() {
    }

    public static NetState getInstant() {
        if (null == mNetState) {
            synchronized (NetState.class) {
                mNetState = new NetState();
            }
        }
        return mNetState;
    }


    /**
     * 对网络状态的管理AA
     */
    private boolean WifiAble;
    private boolean MobleNetble;

    public boolean isWifiAble() {
        return WifiAble;
    }

    public void setWifiAble(boolean wifiAble) {
        WifiAble = wifiAble;
    }

    public boolean isMobleNetble() {
        return MobleNetble;
    }

    public void setMobleNetble(boolean mobleNetble) {
        MobleNetble = mobleNetble;
    }
}
