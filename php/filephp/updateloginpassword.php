<?php
require("koneksi.php");

$response = array();

if($_SERVER['REQUEST_METHOD'] =='POST'){
    $id = $_POST["id"];
    $password = $_POST["password"];
    $perintah = " UPDATE tbl_login SET password = '$password' WHERE id = '$id'";
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
