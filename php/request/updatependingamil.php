<?php
require("../koneksi.php");

$response = array();

if ($_SERVER['REQUEST_METHOD'] == 'POST') {
    $idrequest = $_POST["id_request"];
    $id_amil = $_POST["id_amil"];
    $otp = $_POST["otp"];
    $statuspengambilan = $_POST["statuspengambilan"];
    $angka = '0123456789';
    $arr = array();
    $panjang=strlen($angka) - 2;
    for ($i=0; $i <4 ; $i++) { 
        $x =rand(0, $panjang);
        $arr[]=$angka[$x];
    }
    $pick = implode($arr);
    
    $perintah = "UPDATE tbrequestzakat SET id_amil = '$id_amil', statuspengambilan = '$statuspengambilan', otp = '$pick'
      WHERE id_request = '$idrequest'";
    $execute = mysqli_query($conn, $perintah);
    $cek = mysqli_affected_rows($conn);
    if ($cek > 0) {
        $response["kode"] = 1;
        $response["pesan"] = "Data Berhasil diubah";
    } else {
        $response["kode"] = 0;
        $response["pesan"] = "Data tidak diubah";
    }
} else {
    $response["kode"] = 0;
    $response["pesan"] = "Tidak Ada Post Data";
}

echo json_encode($response);
mysqli_close($conn);
