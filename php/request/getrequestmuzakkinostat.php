<?php
require("../koneksi.php");

$response = array();

if($_SERVER['REQUEST_METHOD'] =='POST'){
    $id_muzakki = $_POST["id_muzakki"];
    $perintah = "SELECT * FROM tbrequestzakat INNER JOIN tbtersedia ON 
    tbrequestzakat.jenis_zakat = tbtersedia.jeniszakat WHERE 
    tbrequestzakat.id_muzakki = '$id_muzakki' ORDER by tbrequestzakat.tgl_request DESC";
    $execute = mysqli_query($conn, $perintah);
    $cek = mysqli_affected_rows($conn);
    if($cek > 0){
        $response["kode"] = 1;
        $response["pesan"] = "Data Berhasil didapat";
        $response["datareq"] = array();
        while ($ambil = mysqli_fetch_object($execute)) {
            $V["tbrequestzakat.id_request"] = $ambil->id_request;
            $V["tbrequestzakat.id_muzakki"] = $ambil->id_muzakki;
            $V["tbrequestzakat.namamuzakki"] = $ambil->namamuzakki;
            $V["tbrequestzakat.nomortelepon"] = $ambil->nomortelepon;
            $V["tbrequestzakat.alamat"] = $ambil->alamat;
            $V["tbrequestzakat.tipe_zakat"] = $ambil->tipe_zakat;
            $V["tbrequestzakat.jenis_zakat"] = $ambil->jenis_zakat;
            $V["tbrequestzakat.nishob"] = $ambil->nishob;
            $V["tbrequestzakat.total_zakat"] = $ambil->total_zakat;
            $V["tbrequestzakat.tgl_request"] = $ambil->tgl_request;
            $V["tbrequestzakat.tgl_pengambilan"] = $ambil->tgl_pengambilan;
            $V["tbrequestzakat.statuspengambilan"] = $ambil->statuspengambilan;
            $V["tbrequestzakat.otp"] = $ambil->otp;
            $V["tbrequestzakat.id_amil"] = $ambil->id_amil;
            $V["tbtersedia.jeniszakat"] = $ambil->jeniszakat;
            $V["tbtersedia.jml_zakat"] = $ambil->jml_zakat;
            array_push($response["datareq"], $V);
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
