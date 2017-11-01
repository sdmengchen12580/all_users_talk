package org.faqrobot.text.netapi;


import org.faqrobot.text.entity.Access_Token;
import org.faqrobot.text.entity.AppVersion;
import org.faqrobot.text.entity.GetRobatResult;
import org.faqrobot.text.entity.KeeyRobatOnlion;
import org.faqrobot.text.entity.Robot_Information;

import okhttp3.RequestBody;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.Headers;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Query;
import rx.Observable;

/**
 * Created by 孟晨 on 2017/10/25.
 */

public interface Chat2Server {
    /*
       * 请求参数：
        名称	类型	可选	说明
        appId	字符串	否	第三方用户唯一凭证   AppID的值
        secret	字符串	否	第三方用户唯一凭证密钥，即AppSecret
       * */
    @Headers("Cache-Control:public,max-age=43200")
    @POST("token/getToken")
    Observable<Access_Token> getAccess_Token(@Query("appId") String appId,
                                             @Query("secret") String secret);

    /*获取聊天信息接口
       名称	类型	可选	说明
      s	字符串	否	固定字符串aq
     question	字符串	否	请求的问题(100字以内)
     sysNum	字符串	否	 站点的唯一标识
     clientId	字符串	否	 用户的ID编号（用来识别每一个不同的用户）
    * */
    @FormUrlEncoded
    @POST("servlet/ApiChat")
    Observable<GetRobatResult> getRobatResult(@Query("access_token") String access_token,
                                              @Query("s") String s,
                                              @Field("sourceId") int sourceId,
                                              @Field("question") String question,
                                              @Query("clientId") String clientId);

    /* 设置机器人的一直在线
    名称	类型	可选	说明
    s	字符串	否	kl
    question	字符串	否	请求的问题
    sysNum	字符串	否	站点的唯一标识
    clientId	字符串	否	用户的ID编号（用来识别每一个不同的用户）
    */
    @POST("servlet/ApiChat")
    Observable<KeeyRobatOnlion> setRobotOnlioning(@Query("access_token") String access_token,
                                                  @Query("s") String s,
                                                  @Query("question") String question,
                                                  @Query("clientId") String clientId,
                                                  @Query("sourceId") int sourceId);

    /*
    * 获取网页流程答案接口
    * 请求参数：
    名称	类型	可选	说明
    s	字符串	否	getflw
    fid	整型	否	流程项的id代号
    question	字符串	否	流程引导的引导提示内容（UTF8编码）
    sysNum	字符串	否	 站点的唯一标识
    clientId	字符串	否	用户的ID编号（用来识别每一个不同的用户）
    *
    */
    @FormUrlEncoded
    @POST("servlet/ApiChat")
    Observable<GetRobatResult> getStreamResult(@Query("access_token") String access_token,
                                               @Query("s") String s,
                                               @Field("fid") String fid,
                                               @Field("question") String question,
                                               @Query("clientId") String clientId,
                                               @Query("sourceId") int sourceId);

    /*
     * 获取网页流程答案接口
     * 请求参数：
     名称	类型	可选	说明
     s	字符串	否	getflw
     fid	整型	否	流程项的id代号
     question	字符串	否	流程引导的引导提示内容（UTF8编码）
     sysNum	字符串	否	 站点的唯一标识
     clientId	字符串	否	用户的ID编号（用来识别每一个不同的用户）
     *
     */
    @FormUrlEncoded
    @POST("servlet/ApiChat")
    Observable<GetRobatResult> getStreamRLResult(@Query("access_token") String access_token,
                                                 @Query("s") String s,
                                                 @Query("fid") int fid,
                                                 @Field("question") String question,
                                                 @Query("clientId") String clientId,
                                                 @Query("sourceId") int sourceId);

    /*机器人下线接口
    请求参数：
    名称	类型	可选	说明
    s	字符串	否	offline
    sysNum	字符串	否	站点的唯一标识
    clientId	字符串	否	用户的ID编号（用来识别每一个不同的用户）
    */
    @POST("servlet/ApiChat")
    Observable<KeeyRobatOnlion> getRobotLeaving(@Query("access_token") String access_token,
                                                @Query("s") String s,
                                                @Query("clientId") String clientId,
                                                @Query("sourceId") int sourceId);

    /*转人工接口
    * s	字符串	否	needperson
    sysNum	字符串	否	站点的唯一标识
    clientId	字符串	否	用户的ID编号（用来识别每一个不同的用户）

    * */
    @POST("servlet/ApiChat")
    Observable<KeeyRobatOnlion> changToPersion(@Query("access_token") String access_token,
                                               @Query("s") String s,
                                               @Query("clientId") String clientId,
                                               @Query("sourceId") int sourceId);

    /*
    *上传图片
    */
    @Multipart
    @POST("servlet/ApiChat")
    Observable<Access_Token> downloadPicFile(@Query("access_token") String access_token,
                                             @Query("s") String s,
                                             @Query("clientId") String clientId,
//                                             @Query("md5") String md5,
                                             @Part("uploadFace") String uploadFace,
                                             @Part("file\"; filename=\"avatar.png") RequestBody avatar,
                                             @Query("sourceId") int sourceId);




   /*
    *机器人基本信息
    */

    @Headers("Cache-Control:public,max-age=43200")
    @POST("servlet/ApiChat")
    Observable<Robot_Information> getRobotInformation(@Query("access_token") String access_token,
                                                      @Query("s") String s,
                                                      @Query("clientId") String clientId,
                                                      @Query("sourceId") int sourceId);

    /*
     *版本更新
     */
    @Headers("Cache-Control:public,max-age=43200")
    @POST("CheckVersion/latestVersion")
    Observable<AppVersion> getAppVersion(@Query("type") int type);
}