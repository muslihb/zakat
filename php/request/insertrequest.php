<?php
require("../koneksi.php");

$response = array();

if($_SERVER['REQUEST_METHOD'] =='POST'){
    $id_muzakki = $_POST["id_muzakki"];
    $namamuzakki = $_POST["namamuzakki"];
    $nomortelepon = $_POST["nomortelepon"];
    $alamat = $_POST["alamat"];
    $tipe_zakat = $_POST["tipe_zakat"];
    $jenis_zakat = $_POST["jenis_zakat"];
    $nishob = $_POST["nishob"];
    $total_zakat = $_POST["total_zakat"];
    $tgl_request = $_POST["tgl_request"];
    $statuspengambilan = $_POST["statuspengambilan"];
    $transaksi = "INSERT INTO tbrequestzakat(id_muzakki, namamuzakki, nomortelepon, alamat, tipe_zakat, 
    jenis_zakat, nishob, total_zakat, tgl_request, statuspengambilan) VALUES 
    ('$id_muzakki', '$namamuzakki', '$nomortelepon', '$alamat', 
    '$tipe_zakat', '$jenis_zakat', '$nishob', '$total_zakat',
    '$tgl_request', '$statuspengambilan')";
    $execute = mysqli_query($conn, $transaksi);
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
