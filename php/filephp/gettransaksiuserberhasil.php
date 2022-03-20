<?php
require("koneksi.php");
try{
$id_profile = $_POST["id_profile"];
$status_pengambilan = "Berhasil";
$perintah = "SELECT * FROM pembayaran where id_profile = '$id_profile' and status_pengambilan = '$status_pengambilan'";
$execute = mysqli_query($conn, $perintah);
$cek = mysqli_affected_rows($conn);
if($cek > 0){
        $response["kode"] = 1;
        $response["pesan"] = "Data Berhasil didapat";
        $response["databayar"] = array();
     while($ambil = mysqli_fetch_object($execute)){
        $V["id_transaksi"] = $ambil->id_transaksi;
        $V["id_profile"] = $ambil->id_profile;
        $V["tipe_zakat"] = $ambil->tipe_zakat;
        $V["jenis_zakat"] = $ambil->jenis_zakat;
        $V["total_zakat"] = $ambil->total_zakat;
        $V["tgl_pengambilan"] = $ambil->tgl_pengambilan;
        $V["jam_pengambilan"] = $ambil->jam_pengambilan;
        $V["status_pengambilan"] = $ambil->status_pengambilan;
        $V["id_amil"] = $ambil->id_amil;
        array_push($response["databayar"], $V);
    }
}else{
        $response["kode"] = 0;
        $response["pesan"] = "Data tidak ditemukan";
    }
}catch (Exception $e) {
    $response["kode"] = 0;
    $response["pesan"] = "Tidak Ada Post Data";
}


echo json_encode($response);
mysqli_close($conn);
