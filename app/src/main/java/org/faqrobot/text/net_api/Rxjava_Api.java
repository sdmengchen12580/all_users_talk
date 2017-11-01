package org.faqrobot.text.net_api;

import org.faqrobot.text.entity.Access_Token;
import org.faqrobot.text.entity.GetRobatResult;
import org.faqrobot.text.entity.KeeyRobatOnlion;

import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Query;
import rx.Observable;

/**
 * Created by yunwen on 2017/10/9.
 */

public interface Rxjava_Api {

    /**
     * 1.获取accesstoken
     */
    @Headers("Cache-Control:public,max-age=43200")
    @POST("token/getToken")
    Observable<Access_Token> getAccess_Token(@Query("appId") String appId,
                                             @Query("secret") String secret);

    /**
     * 2.获取聊天信息接口
     * s=固定字符串aq
     * sourceId=9
     * question为请求的问题(100字以内)
     * clientId=一串数字
     */
    @FormUrlEncoded
    @POST("servlet/ApiChat")
    Observable<GetRobatResult> getRobatResult(@Query("access_token") String access_token,
                                              @Query("s") String s,
                                              @Field("sourceId") int sourceId,
                                              @Field("question") String question,
                                              @Query("clientId") String clientId);

    /**
     *  3.设置机器人的一直在线
     *  s=kl
     *  sourceId=9
     *  question为请求的问题(100字以内)
     *  clientId=一串数字
     */
    @POST("servlet/ApiChat")
    Observable<KeeyRobatOnlion> setRobotOnlioning(@Query("access_token") String access_token,
                                                  @Query("s") String s,
                                                  @Query("question") String question,
                                                  @Query("clientId") String clientId,
                                                  @Query("sourceId") int sourceId);



    /**
     * 4.获取网页流程答案接口
     * s=getflw
     * fid=流程项的id代号
     *  question为请求的问题(100字以内)
     *  sourceId=9
     *  clientId=一串数字
     */
    @FormUrlEncoded
    @POST("servlet/ApiChat")
    Observable<GetRobatResult> getStreamRLResult(@Query("access_token") String access_token,
                                                 @Query("s") String s,
                                                 @Query("fid") int fid,
                                                 @Field("question") String question,
                                                 @Query("clientId") String clientId,
                                                 @Query("sourceId") int sourceId);


}
