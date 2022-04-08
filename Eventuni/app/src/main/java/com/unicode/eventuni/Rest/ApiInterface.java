package com.unicode.eventuni.Rest;

import com.unicode.eventuni.Model.GetEvent;
import com.unicode.eventuni.Model.PostPutDelEvent;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.HTTP;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;


public interface ApiInterface {
    @GET("event")
    Call<GetEvent> getEvent();
    @Multipart
    @POST("event")
    Call<PostPutDelEvent> postEvent(@Part MultipartBody.Part image,
                                    @Part("name") RequestBody name,
                                    @Part("description") RequestBody description,
                                    @Part("longdescription") RequestBody longdescription,
                                    @Part("kategori") RequestBody kategori,
                                    @Part("kegiatan") RequestBody kegiatan,
                                    @Part("lokasi") RequestBody lokasi,
                                    @Part("tiket") RequestBody tiket,
                                    @Part("waktu") RequestBody waktu,
                                    @Part("cp") RequestBody cp,
                                    @Part("link") RequestBody link,
                                    @Part("flag") RequestBody flag);
    @Multipart
    @POST("event")
    Call<PostPutDelEvent> postUpdateEvent(@Part MultipartBody.Part image,
                                          @Part("id") RequestBody id,
                                          @Part("name") RequestBody name,
                                          @Part("description") RequestBody description,
                                          @Part("longdescription") RequestBody longdescription,
                                          @Part("kategori") RequestBody kategori,
                                          @Part("kegiatan") RequestBody kegiatan,
                                          @Part("lokasi") RequestBody lokasi,
                                          @Part("tiket") RequestBody tiket,
                                          @Part("waktu") RequestBody waktu,
                                          @Part("cp") RequestBody cp,
                                          @Part("link") RequestBody link,
                                          @Part("flag") RequestBody flag);
    @FormUrlEncoded
    @HTTP(method = "DELETE", path = "event", hasBody = true)
    Call<PostPutDelEvent> deleteEvent(@Field("id") String id);
}
