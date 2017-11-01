package org.faqrobot.text.custom;

/**
 * Created by 孟晨 on 2017/10/25.
 */

public interface ChatView {
    /**
     * 发送文字消息
     */
    void sendText();

    /**
     * 回到监听客户说话
     */
    void beginListener();

    /**
     * 停止监听客户说话，并开启文本模式
     */
    void stopListener();

    /**
     * text改变
     */
    void textChang();

    void isFocus();
}
