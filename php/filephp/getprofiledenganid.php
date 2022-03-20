<?php
require("koneksi.php");

$response = array();

if($_SERVER['REQUEST_METHOD'] =='POST'){
    $id_profile = $_POST["id_profile"];
    $perintah = "SELECT * FROM tbl_profile where id_profile = '$id_profile'";
    $execute = mysqli_query($conn, $perintah);
    $cek = mysqli_affected_rows($conn);
    if($cek > 0){
        $response["kode"] = 1;
        $response["pesan"] = "Data Berhasil didapat";
        $response["data"] = array();
        while($ambil = mysqli_fetch_object($execute)){
        $V["id_profile"] = $ambil->id_profile;
        $V["namamuzakki"] = $ambil->namamuzakki;
        $V["email"] = $ambil->email;
        $V["alamat"] = $ambil->alamat;
        $V["nomortelfon"] = $ambil->nomortelfon;
        array_push($response["data"], $V);
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
