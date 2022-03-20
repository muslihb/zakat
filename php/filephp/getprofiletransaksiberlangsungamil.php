<?php
require("koneksi.php");

$response = array();

if($_SERVER['REQUEST_METHOD'] =='POST'){
    $id_amil = $_POST["id_amil"];
    $status = $_POST["status_pengambilan"];
    $perintah = "SELECT pembayaran.id_transaksi, tbl_profile.id_profile, tbl_profile.namamuzakki, 
    pembayaran.tipe_zakat, pembayaran.jenis_zakat, 
    pembayaran.total_zakat, pembayaran.tgl_pengambilan, 
    pembayaran.jam_pengambilan, pembayaran.status_pengambilan, 
    pembayaran.id_amil FROM pembayaran 
    INNER JOIN tbl_profile ON 
    pembayaran.id_profile = tbl_profile.id_profile 
    where pembayaran.status_pengambilan = '$status' AND pembayaran.id_amil = '$id_amil'";
    $execute = mysqli_query($conn, $perintah);
    $cek = mysqli_affected_rows($conn);
    if($cek > 0){
        $response["kode"] = 1;
        $response["pesan"] = "Data Berhasil didapat";
        $response["datapending"] = array();
        while($ambil = mysqli_fetch_object($execute)){
        $V["pembayaran.id_transaksi"] = $ambil->id_transaksi;
        $V["tbl_profile.id_profile"] = $ambil->id_profile;
        $V["tbl_profile.namamuzakki"] = $ambil->namamuzakki;
        $V["pembayaran.tipe_zakat"] = $ambil->tipe_zakat;
        $V["pembayaran.jenis_zakat"] = $ambil->jenis_zakat;
        $V["pembayaran.total_zakat"] = $ambil->total_zakat;
        $V["pembayaran.tgl_pengambilan"] = $ambil->tgl_pengambilan;
        $V["pembayaran.jam_pengambilan"] = $ambil->jam_pengambilan;
        $V["pembayaran.status_pengambilan"] = $ambil->status_pengambilan;
        $V["pembayaran.id_amil"] = $ambil->id_amil;
        array_push($response["datapending"], $V);
        }
    }else{
        $response["kode"] = 0;
        $response["pesan"] = "Data tidak ditemukan";
    }
}else{
    $response["kode"] = 0;
    $response["pesan"] = "Tidak Ada Post Data";
}

echo json_encode($response);
mysqli_close($conn);
