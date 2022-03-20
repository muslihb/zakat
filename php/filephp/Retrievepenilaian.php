<?php
require("koneksi.php");
$perintah = "SELECT * FROM tbl_penilaian";
$execute = mysqli_query($conn, $perintah);
$cek = mysqli_affected_rows($conn);
if($cek > 0){
        $response["kode"] = 1;
        $response["pesan"] = "Data Berhasil didapat";
        $response["datanilai"] = array();
        while($ambil = mysqli_fetch_object($execute)){
        $V["id "] = $ambil->id ;
        $V["id_transaksi "] = $ambil->id_transaksi ;
        $V["id_profile "] = $ambil->id_profile ;
        $V["id_amil"] = $ambil->id_amil ;
        $V["nilai_cf_amil"] = $ambil->nilai_cf_amil;
        $V["nilai_cf_muzakki"] = $ambil->nilai_cf_muzakki;
        array_push($response["datanilai"], $V);
    }
}else{
        $response["kode"] = 0;
        $response["pesan"] = "Data tidak ditemukan";
}


echo json_encode($response);
mysqli_close($conn);
