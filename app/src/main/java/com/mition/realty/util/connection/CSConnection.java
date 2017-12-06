package com.mition.realty.util.connection;

import com.mition.realty.util.model.Transaction;
import com.mition.realty.util.model.User;

import java.util.Map;

import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;
import rx.Observable;

/**
 * Created by kksd0900 on 16. 9. 1..
 */
public interface CSConnection {
    @POST("user")
    Observable<User> createUser(@Body Map<String, Object> field);

    @GET("user/{id}")
    Observable<User> getUser(@Path("id") String id);

    @POST("user}")
    Observable<User> login(@Body Map<String, Object> field);

    @PUT("user/{id}")
    Observable<User> modifyUser(@Path("id") String id);

    @DELETE("user/{id}")
    Observable<User> deleteUser(@Path("id") String id);



    @GET("transaction/sender/{id}")
    Observable<Transaction> getSenderTransaction(String id);

    @GET("transaction/recipient/{id}")
    Observable<Transaction> getRecipientTransaction(String id);

    @POST("transaction/accept/{id}")
    Observable<Transaction> acceptTransaction(String id);
//
//    @POST("v1/account/{account_no}")
//    Observable<List<Account>> doesTheAccountExist(@Path("account_no") String account_no);
//
//    @GET("v1/image_password/{phone_no}")
//    Observable<List<ImagePassword>> getImagePassword(@Path("phone_no") String phone_no);
//
//    @Multipart
//    @POST("v1/image_password/{phone_no}/{category}/{name}")
//    Observable<GlobalResponse> postImagePassword(@Part("post_image\"; filename=\"android_post_image_file") RequestBody file,
//                                                 @Path("phone_no") String phone_no,
//                                                 @Path("category") String category,
//                                                 @Path("name") String name);
//
//    @GET("v1/membership/{phone_no}")
//    Observable<List<Membership>> getMembership(@Path("phone_no") String phone_no);
//
//    @POST("v1/membership")
//    Observable<Membership> createMembership(@Body Map<String, Object> fields);
//
//    @POST("v1/transaction")
//    Observable<GlobalResponse> executeAccountTransfer(@Body Map<String, Object> fields);
}