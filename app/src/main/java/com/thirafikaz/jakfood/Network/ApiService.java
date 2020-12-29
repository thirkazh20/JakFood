package com.thirafikaz.jakfood.Network;

import com.thirafikaz.jakfood.Model.Response;
import com.thirafikaz.jakfood.Model.ResponseKategoriItem;
import com.thirafikaz.jakfood.Model.ResponseMakanan;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface ApiService {
    @FormUrlEncoded
    @POST("registeruser.php")
    Call<Response> requestRegister(
            @Field("vsnama") String nama,
            @Field("vsalamat") String alamat,
            @Field("vsjenkel") String jenkel,
            @Field("vsnotelp") String notelp,
            @Field("vsusername") String username,
            @Field("vslevel") String level,
            @Field("vspassword") String password
    );

    @FormUrlEncoded
    @POST("loginuser.php")
    Call<Response>requestLogin(
            @Field("edtusername") String username,
            @Field("edtpassword") String password,
            @Field("vslevel") String level
    );

    @FormUrlEncoded
    @POST("getdatamakanan.php")
    Call<ResponseMakanan> getDataMakanan(
            @Field("vsiduser") String idUser,
            @Field("vsidkastrkategorimakanan") String KategoriMakanan
    );

    @GET("kategorimakanan.php")
    Call<ResponseKategoriItem> getkategorimakanan();

    @FormUrlEncoded
    @POST("deletedatamakanan.php")
    Call<Response> deleteData(
            @Field("vsidmakanan") String idMakanan
    );
}
