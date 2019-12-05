package com.idw.project.notebookstation.rest;

import com.idw.project.notebookstation.response.BatalResponse;
import com.idw.project.notebookstation.response.HapusKeranjangResponse;
import com.idw.project.notebookstation.response.HapusWishlistResponse;
import com.idw.project.notebookstation.response.KeranjangCheckResponse;
import com.idw.project.notebookstation.response.KeranjangCountResponse;
import com.idw.project.notebookstation.response.KeranjangDetailResponse;
import com.idw.project.notebookstation.response.KonsumenDetailResponse;
import com.idw.project.notebookstation.response.KonsumenFotoProfilResponse;
import com.idw.project.notebookstation.response.KonsumenLoginResponse;
import com.idw.project.notebookstation.response.KonsumenRegistrasiResponse;
import com.idw.project.notebookstation.response.KonsumenUbahPasswordResponse;
import com.idw.project.notebookstation.response.MenungguKonfirmasiResponse;
import com.idw.project.notebookstation.response.MenungguPembayaranResponse;
import com.idw.project.notebookstation.response.PesananDiprosesResponse;
import com.idw.project.notebookstation.response.ProdukDetailResponse;
import com.idw.project.notebookstation.response.ProdukGetAllResponse;
import com.idw.project.notebookstation.response.ProdukSearchResponse;
import com.idw.project.notebookstation.response.SampaiTujuanResponse;
import com.idw.project.notebookstation.response.SedangDikirimResponse;
import com.idw.project.notebookstation.response.SelesaiResponse;
import com.idw.project.notebookstation.response.TambahKeranjangResponse;
import com.idw.project.notebookstation.response.TambahWishlistResponse;
import com.idw.project.notebookstation.response.UbahProfilResponse;
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

    //untuk cek id produk pada keranjang
    @GET("keranjang/check")
    Call<KeranjangCheckResponse> checkIdKeranjang(
            @Query("id_produk") String id_produk,
            @Query("id_konsumen") String id_konsumen
    );

    //Untuk tambah keranjang
    @FormUrlEncoded
    @POST("keranjang/tambah-keranjang")
    Call<TambahKeranjangResponse> tambahKeranjang(
            @Field("id_konsumen") String id_konsumen,
            @Field("id_produk") String id_produk
    );

    //Untuk hapus keranjang
    @FormUrlEncoded
    @POST("keranjang/hapus-keranjang")
    Call<HapusKeranjangResponse> hapusKeranjang(
            @Field("id_keranjang") String id_keranjang
    );

    //Untuk ubah profil
    @FormUrlEncoded
    @POST("konsumen/ubah-profil")
    Call<UbahProfilResponse> ubahProfil(
            @Field("id_konsumen") String id_konsumen,
            @Field("username") String username,
            @Field("nama_lengkap") String nama_lengkap,
            @Field("alamat") String alamat,
            @Field("email") String email,
            @Field("nomor_hp") String nomor_hp
    );

    //untuk get detail konsumen
    @GET("konsumen/by-id")
    Call<KonsumenDetailResponse> konsumenById(
            @Query("id_konsumen") String id_konsumen
    );

    //Registrasi Konsumen
    @FormUrlEncoded
    @POST("konsumen/register")
    Call<KonsumenRegistrasiResponse> registrasiKonsumen(
            @Field("username") String username,
            @Field("password") String password,
            @Field("nama_lengkap") String nama_lengkap,
            @Field("alamat") String alamat,
            @Field("email") String email,
            @Field("nomor_hp") String nomor_hp
    );

    //Ubah Password Konsumen
    @FormUrlEncoded
    @POST("konsumen/ubah-password")
    Call<KonsumenUbahPasswordResponse> ubahPassword(
            @Field("id_konsumen") String id_konsumen,
            @Field("password") String password
    );

    //Upload foto Konsumen
    @FormUrlEncoded
    @POST("konsumen/upload-foto")
    Call<KonsumenFotoProfilResponse> uploadFoto(
            @Field("id_konsumen") String id_konsumen,
            @Field("foto") String foto
    );

    //untuk get data pesanan menunggu konfirmasi
    @GET("pesanan/by-id-menunggu-konfirmasi")
    Call<MenungguKonfirmasiResponse> menungguKonfirmasi(
            @Query("id_konsumen") String id_konsumen
    );

    //untuk get data pesanan menunggu pembayaran
    @GET("pesanan/by-id-menunggu-pembayaran")
    Call<MenungguPembayaranResponse> menungguPembayaran(
            @Query("id_konsumen") String id_konsumen
    );

    //untuk get data pesanan diproses
    @GET("pesanan/by-id-pesanan-diproses")
    Call<PesananDiprosesResponse> pesananDiproses(
            @Query("id_konsumen") String id_konsumen
    );

    //untuk get data pesanan sedang dikirim
    @GET("pesanan/by-id-sedang-dikirim")
    Call<SedangDikirimResponse> sedangDikirim(
            @Query("id_konsumen") String id_konsumen
    );

    //untuk get data pesanan sampai tujuan
    @GET("pesanan/by-id-sampai-tujuan")
    Call<SampaiTujuanResponse> sampaiTujuan(
            @Query("id_konsumen") String id_konsumen
    );

    //untuk get data pesanan selesai
    @GET("pesanan/by-id-selesai")
    Call<SelesaiResponse> selesai(
            @Query("id_konsumen") String id_konsumen
    );

    //untuk get data pesanan batal
    @GET("pesanan/by-id-batal")
    Call<BatalResponse> batal(
            @Query("id_konsumen") String id_konsumen
    );

}
