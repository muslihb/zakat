<?php
require("../koneksi.php");

$response = array();

if($_SERVER['REQUEST_METHOD'] =='POST'){
    $jeniszakat = $_POST["jeniszakat"];
    $perintah = "SELECT * FROM tbtersedia
    where jeniszakat = '$jeniszakat'";
    $execute = mysqli_query($conn, $perintah);
    $cek = mysqli_affected_rows($conn);
    if($cek > 0){
        $response["kode"] = 1;
        $response["pesan"] = "Data Berhasil didapat";
        $response["datarequest"] = array();
       while ($ambil = mysqli_fetch_object($execute)) {
            $V["jeniszakat"] = $ambil->jeniszakat;
            $V["jml_zakat"] = $ambil->jml_zakat;
            array_push($response["datarequest"], $V);
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
