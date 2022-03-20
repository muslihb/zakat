<?php
require("koneksi.php");

$response = array();

if($_SERVER['REQUEST_METHOD'] =='POST'){
    $id_profile = $_POST['id_profile'];
    $namamuzakki = $_POST["namamuzakki"];
    $nomortelfon = $_POST["nomortelfon"];
    $alamat = $_POST["alamat"];
    $perintah = "UPDATE tbl_profile SET namamuzakki = '$namamuzakki', nomortelfon = '$nomortelfon', alamat = '$alamat'
    WHERE id_profile = '$id_profile'";
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
