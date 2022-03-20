<?php
require("koneksi.php");

$response = array();

if($_SERVER['REQUEST_METHOD'] =='POST'){
    $id_amil = $_POST['id_amil'];
    $namaamil = $_POST["namaamil"];
    $nomortelfon = $_POST["nomortelfon"];
    $perintah = "UPDATE tbl_profile_amil SET namaamil = '$namaamil', nomortelfon = '$nomortelfon'
    WHERE id_amil = '$id_amil'";
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
