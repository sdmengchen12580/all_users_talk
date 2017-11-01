package org.faqrobot.text.utils;


import android.text.TextUtils;

import org.faqrobot.text.constant.Config;
import org.faqrobot.text.entity.GetRobatResult;
import org.faqrobot.text.entity.MyQuestion;
import org.faqrobot.text.entity.RobotAnswer;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * 用来解析Html中想要的数据
 * Created by iyunwen on 2016/9/6.
 */

public class HtmlParser {

    private static HashMap<String, String> mMap;

    /**
     * 从HTML中解析出图片需要的内容
     */
    public static GetRobatResult getImageContent(String initalQuestion, GetRobatResult getRobatResult) {
        Document parse = Jsoup.parse(initalQuestion);
        Elements div = parse.select("div");
        List<String> urlLink = new ArrayList<>();
        List<String> urlTxt = new ArrayList<>();
        List<String> urlImage = new ArrayList<>();
        Elements a = div.select("a");
        for (Element link : a
                ) {
            urlLink.add(link.attr("href"));
        }
        Elements elementsByClass = parse.getElementsByClass("i-title");
        for (Element link : elementsByClass
                ) {
            urlTxt.add(link.text());
        }
        Elements image = div.select("img");
        for (Element link : image
                ) {
            urlImage.add(link.attr("src"));
        }
        getRobatResult.setUrlRichImage(urlImage);
        getRobatResult.setUrlRichText(urlTxt);
        getRobatResult.setUrlRichLink(urlLink);
        return getRobatResult;
    }

    /**
     * l
     * 从HTML中解析出音频需要的内容
     */
    public static GetRobatResult getVideoContent(String initalQuestion, GetRobatResult getRobatResult) {
        Document parse = Jsoup.parse(initalQuestion);
        Elements object = parse.select("object");
        Elements param = object.select("embed");
        for (Element link : param
                ) {
            analysis(link.attr("src"));
        }
        String file = getParam("file");
        getRobatResult.setUrlVoice(file);
        return getRobatResult;
    }

    /**
     * 从HTML中解析出富文本需要的内容
     */
    public static String getMoreContent(String initalQuestion) {
        Document parse = Jsoup.parse(initalQuestion, "UTF-8");
        Elements object = parse.select("p");
        int size = object.size();
        StringBuilder content = new StringBuilder();
        for (int i = 0; i < size; i++) {
            Elements img = object.get(i).select("img");
            Elements a = object.get(i).select("a");
            String text = object.get(i).text();
            content.append(text);
        }
        return content.toString().trim();
    }

    /**
     * 从HTML中解析出富文本中多问题需要的内容
     */
    public static GetRobatResult getMoreQuestionContent(String initalQuestion, GetRobatResult getRobatResult) {
        Document parse = Jsoup.parse(initalQuestion, "UTF-8");
        Elements object = parse.select("p");
        Elements wflink = parse.getElementsByClass("wflink");
        List<MyQuestion> questionLink = new ArrayList<>();
        StringBuilder content = new StringBuilder();
        for (Element question :
                wflink) {
            String text = question.text();
            if (!TextUtils.isEmpty(text)) {
                String rel = question.attr("rel");
                MyQuestion seedQusetion = new MyQuestion();
                seedQusetion.setQuestion(text.trim());
                seedQusetion.setRel(rel.trim());
                questionLink.add(seedQusetion);
            }
        }
        for (Element title :
                object) {
            String text = title.text();
            content.append(text);
        }
        String result = content.toString();
        String[] split = result.split("->");
        List<RobotAnswer> mRobotAnswer = new ArrayList<>();
        RobotAnswer robotAnswer = new RobotAnswer();
        String s = split[0];
        String[] split1 = s.split("：");
        robotAnswer.setAnsCon(split1[0].trim());
        robotAnswer.setRelateList(questionLink);
        robotAnswer.setMsgType(Config.Type.TXT);
        mRobotAnswer.add(robotAnswer);
        getRobatResult.setRobotAnswer(mRobotAnswer);
        return getRobatResult;
    }

    /**
     * 从HTML中解析出多选文本的需要的内容
     */
    public static String getMoreSelectContent(String initalQuestion) {
        Document parse = Jsoup.parse(initalQuestion, "UTF-8");
        Elements object = parse.select("body");
        int size = object.size();
        StringBuilder content = new StringBuilder();
        for (int i = 0; i < size; i++) {
            String text = object.get(i).text();
            content.append(text.trim());
        }
        return content.toString();
    }


    private static void analysis(String url) {
        mMap = new HashMap<>();
        mMap.clear();
        if (!"".equals(url)) {// 如果URL不是空字符串
            url = url.substring(url.indexOf('?') + 1);
            String paramaters[] = url.split("&");
            for (String param : paramaters) {
                String values[] = param.split("=");
                mMap.put(values[0], values[1]);
            }
        }
    }

    private static String getParam(String name) {
        return mMap.get(name);
    }
}
