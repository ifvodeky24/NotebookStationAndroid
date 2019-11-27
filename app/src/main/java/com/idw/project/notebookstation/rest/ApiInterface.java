package com.idw.project.notebookstation.rest;

import com.idw.project.notebookstation.response.HapusWishlistResponse;
import com.idw.project.notebookstation.response.KeranjangCountResponse;
import com.idw.project.notebookstation.response.KeranjangDetailResponse;
import com.idw.project.notebookstation.response.KonsumenLoginResponse;
import com.idw.project.notebookstation.response.KonsumenRegisterResponse;
import com.idw.project.notebookstation.response.ProdukDetailResponse;
import com.idw.project.notebookstation.response.ProdukGetAllResponse;
import com.idw.project.notebookstation.response.ProdukSearchResponse;
import com.idw.project.notebookstation.response.TambahWishlistResponse;
import com.idw.project.notebookstation.response.WishlistCheckResponse;
import com.idw.project.notebookstation.response.WishlistDetailResponse;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface ApiInterface {

    //Untuk Login
    @FormUrlEncoded
    @POST("konsumen/login")
    Call<KonsumenLoginResponse> loginKonsumen(
            @Field("email") String email,
            @Field("password") String password
    );

    //UNTUK REGISTER
    @FormUrlEncoded
    @POST("konsumen/register")
    Call<KonsumenRegisterResponse> registerKonsumen(
            @Field("username") String username,
            @Field("password") String password,
            @Field("email") String email,
            @Field("nama_lengkap") String nama_lengkap,
            @Field("nomor_hp") String nomor_hp,
            @Field("alamat") String alamat,
            @Field("foto") String foto
    );

    //UNTUK GETALL PRODUK
    @GET("produk/get-all")
    Call<ProdukGetAllResponse> produkGetAll(

    );

    //untuk get detail produk
    @GET("produk/by-id")
    Call<ProdukDetailResponse> produkById(
            @Query("id_produk") String id_produk
    );

    //untuk cari data produk
    @GET("produk/search")
    Call<ProdukSearchResponse> searchProduk(
            @Query("query") String query
    );

    //untuk cek id produk pada wishlist
    @GET("wishlist/check")
    Call<WishlistCheckResponse> checkId(
            @Query("id_produk") String id_produk,
            @Query("id_konsumen") String id_konsumen
    );

    //untuk get detail wishlist berdasarkan id konsumen
    @GET("wishlist/by-id")
    Call<WishlistDetailResponse> wishlistDetail(
            @Query("id_konsumen") String id_konsumen
    );

    //Untuk tambah wishlist
    @FormUrlEncoded
    @POST("wishlist/tambah-wishlist")
    Call<TambahWishlistResponse> tambahWishlist(
            @Field("id_konsumen") String id_konsumen,
            @Field("id_produk") String id_produk
    );

    //Untuk hapus wishlist
    @FormUrlEncoded
    @POST("wishlist/hapus-wishlist")
    Call<HapusWishlistResponse> hapusWishlist(
            @Field("id_wishlist") String id_wishlist
    );

    //untuk get detail keranjang berdasarkan id konsumen
    @GET("keranjang/by-id")
    Call<KeranjangDetailResponse> keranjangDetail(
            @Query("id_konsumen") String id_konsumen
    );

    //untuk get detail keranjang berdasarkan id konsumen
    @GET("keranjang/count-keranjang")
    Call<KeranjangCountResponse> keranjangCount(
            @Query("id_konsumen") String id_konsumen
    );

}
