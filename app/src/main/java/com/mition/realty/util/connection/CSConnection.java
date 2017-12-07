package com.mition.realty.util.connection;

import com.mition.realty.util.model.PDFFile;
import com.mition.realty.util.model.Transaction;
import com.mition.realty.util.model.User;

import java.util.List;
import java.util.Map;

import okhttp3.RequestBody;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.PartMap;
import retrofit2.http.Path;
import rx.Observable;

/**
 * Created by kksd0900 on 16. 9. 1..
 */
public interface CSConnection {
    @POST("user")
    Observable<User> createUser(@Body Map<String, Object> field);

    @POST("user/login")
    Observable<User> login(@Body Map<String, Object> field);

    @GET("user/{id}")
    Observable<User> getUser(@Path("id") String id);

    @PUT("user/{id}")
    Observable<User> modifyUser(@Path("id") String id, @Body Map<String, Object> field);

    @DELETE("user/{id}")
    Observable<User> deleteUser(@Path("id") String id);

    @Multipart
    @POST("file/upload")
    Observable<PDFFile> uploadFile(@PartMap Map<String, RequestBody> params);

    @POST("transaction")
    Observable<Transaction> createTransaction(@Body Map<String, Object> field);

    @POST("transaction/accept/{id}")
    Observable<Transaction> acceptTransaction(@Path("id") String id);


    @GET("transaction/sender/{id}")
    Observable<Transaction> getSenderTransaction(@Path("id") String id);

    @GET("transaction/recipient/{id}")
    Observable<Transaction> getRecipientTransaction(@Path("id") String id);

    @GET("transaction/contract/user/{email}")
    Observable<List<PDFFile>> getRegisteredContract(@Path("email") String email);

//
//    @POST("v1/account/{account_no}")
//    Observable<List<Account>> doesTheAccountExist(@Path("account_no") String account_no);
//
//    @GET("v1/image_password/{phone_no}")
//    Observable<List<ImagePassword>> getImagePassword(@Path("phone_no") String phone_no);
//
//    @Multipart
//    @POST("v1/image_password/{phone_no}/{category}/{name}")
//    Observable<Tran> postImagePassword(@Part("contract_file") RequestBody file,
//                                                 @Path("phone_no") String phone_no,
//                                                 @Path("category") String category,
//                                                 @Path("name") String name);
//
}