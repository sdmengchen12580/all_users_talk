package org.faqrobot.text.constant;


public class Config {

	public static final int NUMNER_ONE = 1;
	public static final int NUMNER_TWO = 2;
	public static final int NUMNER_THREE = 3;
	public static final int NUMNER_FOUR = 4;
	public static final int NUMNER_FIVE = 5;
	public static final int NUMNER_SIX = 6;

	//云知声
	public static final String appKey = "4qqq4qrxw6mvccxttl7fffuhh23atwubogdvcmyg";
	public static final String secret = "0f34fb0811bb9b884cde6317e7c8bad8";

	//主机地址（可变更）
	public static final String HOSTNAME = "http://v4.faqrobot.net/";
	//设置客户clientId
	public static final String CLIENDID = "5255525611";
	//设置客户sourceId
	public static final int SOURCEID = 9;
	//设置机器人问题类型
	public static final String QUESTION = "aq";
	//设置机器人appid
//	public static final String APPID = "v6vJUtwosZoVOgez3D";
	public static final String APPID = "yYpPVnoDpvibxwQYHM";
	//设置机器人sercet
//	public static final String SECRET = "iPh2SDkwxoC76D6A33E8";
	public static final String SECRET = "wrbpCSrvgl390F56AC93";
	//设置机器人常在线类型
	public static final String ROBAT_ONLIONING = "kl";
	//设置机器人常用信息
	public static final String ROBAT_INFORMATION = "p";
	//设置机器人获取流程答案接口
	public static final String ROBAT_GWTFLOW = "getflw";
	//设置转人工
	public static final String ROBAT_TOPERSION = "needperson";
	//设置机器人离线类型
	public static final String ROBAT_NOONLION = "offline";

	//发送
	public static final int SENDTXT = 0x1;
	public static final int SENDIMAGE = 0x2;
	public static final int SENDVIDEO = 0x3;
	public static final int SENDAUDIO = 0x4;
	public static final int SENDMORE = 0x5;
	//接受
	public static final int FROMIMAGE = 0x6;
	public static final int FROMVIDEO = 0x7;
	public static final int FROMAUDIO = 0x8;
	public static final int FROMMORE = 0x9;
	public static final int FROMTXT = 0x10;
	public static final int FROMTXTMORE = 0x11;

	//类型值
	public static class Direct {
		//发送方
		public static final int SEND = 0;
		//接收方
		public static final int RECEIVE = 1;
	}

	public static class Type {
		public static final String TXT = "text";
		public static final String IMAGE = "image";
		//视频类型
		public static final String VIDEO = "video";
		//声音类型
		public static final String VOICE = "voice";
		public static final String RICHTEXT = "richtext";
	}
}
