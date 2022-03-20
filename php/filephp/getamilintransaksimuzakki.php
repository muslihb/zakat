<?php
require("koneksi.php");

$response = array();

if($_SERVER['REQUEST_METHOD'] =='POST'){
    $id_amil = $_POST["id_amil"];
    $perintah = "SELECT * FROM tbl_profile_amil where id_amil = '$id_amil'";
    $execute = mysqli_query($conn, $perintah);
    $cek = mysqli_affected_rows($conn);
    if($cek > 0){
        $response["kode"] = 1;
        $response["pesan"] = "Data Berhasil didapat";
        $response["dataamil"] = array();
        while($ambil = mysqli_fetch_object($execute)){
        $V["id_amil"] = $ambil->id_amil;
        $V["namaamil"] = $ambil->namaamil;
        $V["email"] = $ambil->email;
        $V["nomortelfon"] = $ambil->nomortelfon;
        array_push($response["dataamil"], $V);
    }
    }else{
        $response["kode"] = 0;
        $response["pesan"] = "Data tidak ditemukan";
    }
}else{
    $response["kode"] = 0;
    $response["pesan"] = "Tidak Ada Post Data";
}

echo json_encode($response);
mysqli_close($conn);
