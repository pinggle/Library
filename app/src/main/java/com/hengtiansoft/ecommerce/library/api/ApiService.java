package com.hengtiansoft.ecommerce.library.api;


import com.hengtiansoft.ecommerce.library.data.CreatedResult;
import com.hengtiansoft.ecommerce.library.data.Data;
import com.hengtiansoft.ecommerce.library.data.entity.Comment;
import com.hengtiansoft.ecommerce.library.data.entity.CommentInfo;
import com.hengtiansoft.ecommerce.library.data.entity.Image;
import com.hengtiansoft.ecommerce.library.data.entity._User;
import com.hengtiansoft.ecommerce.library.ui.user.UserModel;

import okhttp3.RequestBody;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;
import retrofit2.http.Query;
import rx.Observable;

/**
 * ProjectName：Library
 * PackageName: com.hengtiansoft.ecommerce.library.api
 * Description：retrofit请求接口的封装
 *
 * @author liminghuang
 * @time 6/14/2016 11:03
 * Modifier：liminghuang
 * ModifyTime：6/14/2016 11:03
 * Comment：
 */
public interface ApiService {
    @GET("login")
    Observable<_User> login(@Query("username") String username, @Query("password") String password);

    @POST("users")
    Observable<CreatedResult> createUser(@Body _User user);


    @GET("users")
    Observable<Data<_User>> getAllUser(@Query("skip") int skip, @Query("limit") int limit);

    @GET("classes/Image")
    Observable<Data<Image>> getAllImages(@Query("where") String where, @Query("order") String order, @Query("skip")
    int skip, @Query("limit") int limit);


    @GET("classes/Comment")
    Observable<Data<CommentInfo>> getCommentList(@Query("include") String include, @Query("where") String where,
                                                 @Query("skip") int skip, @Query("limit") int limit);


    @POST("classes/Comment")
    Observable<CreatedResult> createComment(@Body Comment mComment);


    @Headers("Content-Type: image/png")
    @POST("files/{name}")
    Observable<CreatedResult> upFile(@Path("name") String name, @Body RequestBody body);


    @PUT("users/{uid}")
    Observable<CreatedResult> upUser(@Header("X-LC-Session") String sesssion, @Path("uid") String uid, @Body
    UserModel.Face face);
}
