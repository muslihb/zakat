package com.example.zakat.API;
import com.example.zakat.model.ModelLogin;
import com.example.zakat.model.ResponseData;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface APIAdapter {

    //Akses Login
    @FormUrlEncoded
    @POST("login.php")
    Call<ModelLogin> ardlogin(
            @Field("username") String username,
            @Field("password") String password);

    //Dapatkan Data Pengguna
    @FormUrlEncoded
    @POST("getlogin.php")
    Call<ResponseData> ardgetDataLogin(
            @Field("username") String username);

    //Dapatkan Data Amil
    @FormUrlEncoded
    @POST("request/viewamil.php")
    Call<ResponseData> ardgetDataAmil(
            @Field("iduser") String iduser);

    //Buat Akun Pengguna
    @FormUrlEncoded
    @POST("register.php")
    Call<ResponseData> registerakun(
        @Field("nama") String nama,
        @Field("nomortelepon") String nomortelepon,
        @Field("alamat") String alamat,
        @Field("username") String username,
        @Field("password") String password,
        @Field("status") String status
    );

    //Ubah Akun Pengguna
    @FormUrlEncoded
    @POST("updatedatauser.php")
    Call<ResponseData> updateakun(
            @Field("iduser") String iduser,
            @Field("username") String username,
            @Field("nama") String nama,
            @Field("nomortelepon") String nomortelepon,
            @Field("alamat") String alamat
    );
    //Ubah Akun Lewat Admin
    @FormUrlEncoded
    @POST("updatedatabyadmin.php")
    Call<ResponseData> updateakunadmin(
            @Field("iduser") String iduser,
            @Field("nama") String nama,
            @Field("nomortelepon") String nomortelepon,
            @Field("alamat") String alamat,
            @Field("username") String username,
            @Field("password") String password,
            @Field("status") String status
    );

    //Verifikasi Password lama
    @FormUrlEncoded
    @POST("cekpw.php")
    Call<ResponseData> cekpw(
            @Field("username") String username,
            @Field("password") String password
    );

    //Verifikasi Username
    @FormUrlEncoded
    @POST("cekusername.php")
    Call<ResponseData> cekusername(
            @Field("username") String username
    );

    //Ubah password akun Pengguna
    @FormUrlEncoded
    @POST("updatepw.php")
    Call<ResponseData> updatepw(
            @Field("username") String username,
            @Field("password") String password
    );

    //List Akun
    @GET("retrieveakun.php")
    Call<ResponseData> ardRetrieveakun(
    );

    //List Akun
    @GET("request/retrievezakatall.php")
    Call<ResponseData> retrieveZakatall(
    );

    //List nilai
    @GET("penilaian/listreview.php")
    Call<ResponseData> retrievenilai(
    );

    //List Info Zakat
    @GET("penyaluran/retrieveinfozakat.php")
    Call<ResponseData> ardRetrieveinfoZakat(
    );

    //List Akun By Status
    @FormUrlEncoded
    @POST("retrieveakunbystatus.php")
    Call<ResponseData> retrievestatusakun(
                @Field("status") String  status
    );

    //List Akun by Search
    @FormUrlEncoded
    @POST("searchakun.php")
    Call<ResponseData> search(@Field("username") String username);

    //Hapus Akun
    @FormUrlEncoded
    @POST("deletelogin.php")
    Call<ResponseData> ardDeleteLogin(
            @Field("iduser") String iduser);

    //Insert Premis Zakat
    @FormUrlEncoded
    @POST("premis/insertpremisfc.php")
    Call<ResponseData> insertpremiszakat(
            @Field("kodepremis") String kodepremis,
            @Field("nama") String nama,
            @Field("quest") String quest,
            @Field("ifyes") String ifyes,
            @Field("ifno") String ifno
    );

    //Update Premis Zakat
    @FormUrlEncoded
    @POST("premis/updatefcpremiszakat.php")
    Call<ResponseData> updatepremiszakat(
            @Field("kodepremis") String kodepremis,
            @Field("nama") String nama,
            @Field("quest") String quest,
            @Field("ifyes") String ifyes,
            @Field("ifno") String ifno
    );

    //FC data Premis FC
    @FormUrlEncoded
    @POST("premis/getfcpremis.php")
    Call<ResponseData> ardgetfc(
            @Field("kodepremis") String kodepremis
    );

    //Insert Premis Zakat
    @FormUrlEncoded
    @POST("evidence/insertevidencecf.php")
    Call<ResponseData> insertevidencepenilaian(
            @Field("kodeevidence") String kodeevidence,
            @Field("mb") String mb,
            @Field("md") String md,
            @Field("nama") String nama,
            @Field("kodehipotesa") String kodehipotesa
    );

    //Update Premis Zakat
    @FormUrlEncoded
    @POST("evidence/updatecfevidencepenilaian.php")
    Call<ResponseData> updateevidencepenilaian(
            @Field("kodeevidence") String kodeevidence,
            @Field("mb") String mb,
            @Field("md") String md,
            @Field("nama") String nama,
            @Field("kodehipotesa") String kodehipotesa
    );

    //Hapus Premis Zakat
    @FormUrlEncoded
    @POST("evidence/deleteevidencepenilaian.php")
    Call<ResponseData> hapusevidencepenilaian(
            @Field("kodeevidence") String kodeevidence
    );

    //List Premis Zakat FC
    @GET("premis/fczakat.php")
    Call<ResponseData> retrievefcpremiszakat();

    //List Evidence
    @GET("evidence/cfpenilaianevidence.php")
    Call<ResponseData> retrievecfevidencepenilaian();

    //List Hipotesa
    @GET("hipotesa/cfpenilaianhipotesa.php")
    Call<ResponseData> retrievecfhipotesapenilaian();

    //Hapus data Premis FC
    @FormUrlEncoded
    @POST("premis/deletepremiszakat.php")
    Call<ResponseData> ardDeletepzakat(
            @Field("kodepremis") String kodepremis
    );

    //Insert Konklusi Zakat
    @FormUrlEncoded
    @POST("konklusi/insertkonklusizakat.php")
    Call<ResponseData> insertkonklusizakat(
            @Field("kodekonklusi") String kodekonklusi,
            @Field("nama") String nama
    );

    //Insert Hipotesa
    @FormUrlEncoded
    @POST("hipotesa/inserthipotesapenilaian.php")
    Call<ResponseData> inserthipotesa(
            @Field("kodehipotesa") String kodehipotesa,
            @Field("nama") String nama
    );

    //Update Hipotesa
    @FormUrlEncoded
    @POST("hipotesa/updatecfhipotesapenilaian.php")
    Call<ResponseData> updatehipotesa(
            @Field("kodehipotesa") String kodehipotesa,
            @Field("nama") String nama
    );

    //Delete Hipotesa
    @FormUrlEncoded
    @POST("hipotesa/deletehipotesapenilaian.php")
    Call<ResponseData> deletehipotesa(
            @Field("kodehipotesa") String kodehipotesa
    );

    //FC Konklusi Zakat
    @FormUrlEncoded
    @POST("konklusi/getfckonklusi.php")
    Call<ResponseData> getkonklusizakat(
            @Field("kodekonklusi") String kodekonklusi
    );

    //Update konklusi Zakat
    @FormUrlEncoded
    @POST("konklusi/updatefckonklusizakat.php")
    Call<ResponseData> updatekonklusizakat(
            @Field("kodekonklusi") String kodekonklusi,
            @Field("nama") String nama
    );

    //List konklusi Zakat FC
    @GET("konklusi/fczakatkonklusi.php")
    Call<ResponseData> retrievefckonklusizakat();

    //List Penyaluran Zakat
    @GET("mutashik/retrieveinfomutashik.php")
    Call<ResponseData> retrievepenyaluranzakat();

    //List Penyaluran Zakat
    @FormUrlEncoded
    @POST("mutashik/getretrieveinfomutashik.php")
    Call<ResponseData> getretrievepenyaluranzakat(
            @Field("golonganmutashik") String golonganmutashik
    );

    //Hapus data Konklusi FC
    @FormUrlEncoded
    @POST("konklusi/deletekonklusizakat.php")
    Call<ResponseData> ardDeletekzakat(
            @Field("kodekonklusi") String kodekonklusi
    );

    //Insert Request Zakat
    @FormUrlEncoded
    @POST("request/insertrequest.php")
    Call<ResponseData> insertrequest(
            @Field("id_muzakki") String idMuzakki,
            @Field("namamuzakki") String namamuzakki,
            @Field("nomortelepon") String nomortelepon,
            @Field("alamat") String alamat,
            @Field("tipe_zakat") String tipeZakat,
            @Field("jenis_zakat") String jenisZakat,
            @Field("nishob") String nishob,
            @Field("total_zakat") String totalZakat,
            @Field("tgl_request") String tgl_request,
            @Field("statuspengambilan") String statuspengambilan
    );
    //RequestZakat Muzakki
    @FormUrlEncoded
    @POST("request/getrequestmuzakki.php")
    Call<ResponseData> ardgetRequestMuzakkistatus(
            @Field("id_muzakki") String idMuzakki,
            @Field("statuspengambilan") String statuspengambilan
    );

    //RequestZakat Admin
    @FormUrlEncoded
    @POST("request/getrequestadmin.php")
    Call<ResponseData> ardgetRequestAdmintatus(
            @Field("statuspengambilan") String statuspengambilan
    );

    //Insert Nilai
    @FormUrlEncoded
    @POST("penilaian/insertnilai.php")
    Call<ResponseData> insertnilai(
            @Field("id_request") String id_request,
            @Field("nilai_cf") String nilai_cf
    );

    //Insert Nilai
    @FormUrlEncoded
    @POST("penilaian/getrequestforcf.php")
    Call<ResponseData> getnilai(
            @Field("id_request") String id_request
    );

    //RequestZakat Muzakki no stat
    @FormUrlEncoded
    @POST("request/getrequestmuzakkinostat.php")
    Call<ResponseData> ardgetRequestMuzakki(
            @Field("id_muzakki") String idMuzakki
    );

    //UpdateZakat Pending Amil
    @FormUrlEncoded
    @POST("request/updatependingamil.php")
    Call<ResponseData> UpdatePendingAmil(
            @Field("id_request") String idRequest,
            @Field("id_amil") String idAmil,
            @Field("statuspengambilan") String statuspengambilan
    );

    //UpdateZakat Pending Amil
    @FormUrlEncoded
    @POST("request/cekotp.php")
    Call<ResponseData> cekotp(
            @Field("id_request") String idRequest,
            @Field("id_amil") String idAmil,
            @Field("statuspengambilan") String statuspengambilan,
            @Field("otp") String otp
    );

    //UpdateZakat Berlangsung Muzakki
    @FormUrlEncoded
    @POST("request/updatekonfirmasiMuzakki.php")
    Call<ResponseData> UpdateBerlangsungMuzzaki(
            @Field("id_request") String idRequest,
            @Field("tgl_pengambilan") String tglPengambilan,
            @Field("statuspengambilan") String statuspengambilan
    );

    //UpdateZakat Nishob dan total
    @FormUrlEncoded
    @POST("request/UpdateNishob.php")
    Call<ResponseData> updateNishob(
            @Field("id_request") String idRequest,
            @Field("nishob") String nishob,
            @Field("total_zakat") String totalZakat
    );

    //Request pengambilan Amil
    @FormUrlEncoded
    @POST("request/retrieverequestzakat.php")
    Call<ResponseData> RequestPengambilanAmil(
            @Field("statuspengambilan") String statuspengambilan
    );

    //List pengambilan Amil idAmil
    @FormUrlEncoded
    @POST("request/retrieverequestzakatstatus.php")
    Call<ResponseData> RequestPengambilanAmilBerlangsung(
            @Field("id_amil") String idAmil,
            @Field("statuspengambilan") String statuspengambilan
    );

    //cekZakat Nishob dan total
    @FormUrlEncoded
    @POST("penyaluran/updateconfirmmuzakki.php")
    Call<ResponseData> updatezakat(
            @Field("jeniszakat") String jeniszakat,
            @Field("jml_zakat") String jmlZakat
    );

    //UpdateZakat Nishob dan total
    @FormUrlEncoded
    @POST("penyaluran/getforupdate.php")
    Call<ResponseData> cektersedia(
            @Field("jeniszakat") String jeniszakat
    );

    //Penerima Zakat
    @FormUrlEncoded
    @POST("mutashik/insertmutashik.php")
    Call<ResponseData> insertmutashik(
            @Field("idamil") String idamil,
            @Field("jenis_zakat") String jenis_zakat,
            @Field("jumlah") String jumlah,
            @Field("namamutashik") String namamutashik,
            @Field("golonganmutashik") String golonganmutashik,
            @Field("tglmenerima") String tglmenerima

    );

    //Perubahan Penerima Zakat
    @FormUrlEncoded
    @POST("mutashik/updatemutashik.php")
    Call<ResponseData> ubahmutashik(
            @Field("idmutashik") String idmutashik,
            @Field("idamil") String idamil,
            @Field("jenis_zakat") String jenis_zakat,
            @Field("jumlah") String jumlah,
            @Field("namamutashik") String namamutashik,
            @Field("golonganmutashik") String golonganmutashik,
            @Field("tglmenerima") String tglmenerima
    );

    //Hapus Penerima Zakat
    @FormUrlEncoded
    @POST("mutashik/deletemutashik.php")
    Call<ResponseData> hapusmutashik(
            @Field("idmutashik") String idmutashik
    );

    //penemuan data nilai
    @FormUrlEncoded
    @POST("penilaian/getrequestforcf.php")
    Call<ResponseData> getrequestforcf(
            @Field("id_request") String id_request
    );
}
