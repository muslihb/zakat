<?php
require("koneksi.php");
$perintah = "SELECT pembayaran.id_transaksi, tbl_profile_amil.namaamil, pembayaran.nilai_cf_amil FROM pembayaran
INNER JOIN tbl_profile_amil ON tbl_profile_amil.id_amil = pembayaran.id_amil";
$execute = mysqli_query($conn, $perintah);
$cek = mysqli_affected_rows($conn);
if($cek > 0){
        $response["kode"] = 1;
        $response["pesan"] = "Data Berhasil didapat";
        $response["datanilai"] = array();
        while($ambil = mysqli_fetch_object($execute)){
        $V["pembayaran.id_transaksi"] = $ambil->id_transaksi;
        $V["tbl_profile_amil.namaamil"] = $ambil->namaamil;
        $V["pembayaran.nilai_cf_amil"] = $ambil->nilai_cf_amil;
        array_push($response["datanilai"], $V);
        }
}else{
        $response["kode"] = 0;
        $response["pesan"] = "Data tidak ditemukan";
}


echo json_encode($response);
mysqli_close($conn);