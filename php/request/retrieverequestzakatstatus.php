<?php
require("../koneksi.php");

$response = array();

if($_SERVER['REQUEST_METHOD'] =='POST'){
    $id = $_POST["id_amil"];
    $statuspengambilan = $_POST["statuspengambilan"];
    $perintah = "SELECT * FROM tbrequestzakat
    where id_amil = '$id' and statuspengambilan = '$statuspengambilan' order by tgl_request ASC";
    $execute = mysqli_query($conn, $perintah);
    $cek = mysqli_affected_rows($conn);
    if($cek > 0){
        $response["kode"] = 1;
        $response["pesan"] = "Data Berhasil didapat";
        $response["datarequest"] = array();
        while ($ambil = mysqli_fetch_object($execute)) {
            $V["id_request"] = $ambil->id_request;
            $V["id_muzakki"] = $ambil->id_muzakki;
            $V["namamuzakki"] = $ambil->namamuzakki;
            $V["nomortelepon"] = $ambil->nomortelepon;
            $V["alamat"] = $ambil->alamat;
            $V["tipe_zakat"] = $ambil->tipe_zakat;
            $V["jenis_zakat"] = $ambil->jenis_zakat;
            $V["nishob"] = $ambil->nishob;
            $V["total_zakat"] = $ambil->total_zakat;
            $V["tgl_request"] = $ambil->tgl_request;
            $V["tgl_pengambilan"] = $ambil->tgl_pengambilan;
            $V["statuspengambilan"] = $ambil->statuspengambilan;
            $V["id_amil"] = $ambil->id_amil;
            array_push($response["datarequest"], $V);
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