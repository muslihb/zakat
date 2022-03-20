<?php
require("koneksi.php");

$response = array();

if($_SERVER['REQUEST_METHOD'] =='POST'){
    $id_transaksi = $_POST["id_transaksi"];
    $nilai_cf_muzakki = $_POST["nilai_cf_muzakki"];
    $perintah = "UPDATE pembayaran SET nilai_cf_muzakki = '$nilai_cf_muzakki'
    WHERE id_transaksi = '$id_transaksi'";
    $execute = mysqli_query($conn, $perintah);
    $cek = mysqli_affected_rows($conn);
    if($cek > 0){
        $response["kode"] = 1;
        $response["pesan"] = "Data Berhasil diubah";
    }else{
        $response["kode"] = 0;
        $response["pesan"] = "Data tidak diubah";
    }
}else{
    $response["kode"] = 0;
    $response["pesan"] = "Tidak Ada Post Data";
}

echo json_encode($response);
mysqli_close($conn);
