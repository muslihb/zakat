<?php
require("koneksi.php");

$response = array();

if($_SERVER['REQUEST_METHOD'] =='POST'){
    $id_profile = $_POST["id_profile"];
    $profile = "INSERT INTO tbl_penilaian(id_profile) VALUES 
    ('$id_profile')";
    $execute = mysqli_query($conn, $profile);
    $cek2 = mysqli_affected_rows($conn);
    if($cek2 > 0){
        $response["kode"] = 1;
        $response["pesan"] = "Simpan Data Berhasil";
    }else{
        $response["kode"] = 0;
        $response["pesan"] = "Data Gagal Disimpan";
    }
}else{
    $response["kode"] = 0;
    $response["pesan"] = "Tidak Ada Post Data";
}

echo json_encode($response);
mysqli_close($conn);
