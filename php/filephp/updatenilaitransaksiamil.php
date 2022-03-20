<?php
require("koneksi.php");

$response = array();

if($_SERVER['REQUEST_METHOD'] =='POST'){
    $id = $_POST["id"];
    $id_transaksi = $_POST["id_transaksi"];
    $id_amil = $_POST["id_amil"];
    $perintah = "UPDATE tbl_penilaian SET id_transaksi = '$id_transaksi', id_amil = '$id_amil'
    WHERE id = '$id'";
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
