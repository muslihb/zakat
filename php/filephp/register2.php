<?php
require("koneksi.php");

$response = array();

if($_SERVER['REQUEST_METHOD'] =='POST'){
    $namamuzakki = $_POST["namamuzakki"];
    $email = $_POST["email"];
    $telepon = $_POST["nomortelfon"];
    $alamat = $_POST["alamat"];
    $profile = "INSERT INTO tbl_profile(namamuzakki, email, nomortelfon, alamat) VALUES ('$namamuzakki', '$email', '$telepon', '$alamat')";
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
