<?php
require("koneksi.php");

$response = array();

if($_SERVER['REQUEST_METHOD'] =='POST'){
    $namaamil = $_POST["namaamil"];
    $email = $_POST["email"];
    $telepon = $_POST["nomortelfon"];
    $profile = "INSERT INTO tbl_profile_amil(namaamil, email, nomortelfon) VALUES ('$namaamil', '$email', '$telepon')";
    $execute = mysqli_query($conn, $profile);
    $cek2 = mysqli_affected_rows($conn);
    if($cek2 > 0){
        $response["kode"] = 1;
        $response["pesan"] = "Simpan Data Berhasil";
        $response["success"] = true;
    }else{
        $response["kode"] = 0;
        $response["pesan"] = "Data Gagal Disimpan";
        $response["success"] = false;
    }
}else{
    $response["kode"] = 0;
    $response["pesan"] = "Tidak Ada Post Data";
}

echo json_encode($response);
mysqli_close($conn);
