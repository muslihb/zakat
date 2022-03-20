<?php
require("koneksi.php");

$response = array();

if($_SERVER['REQUEST_METHOD'] =='POST'){
    $id_profile = $_POST["id_profile"];
    $tipe_zakat = $_POST["tipe_zakat"];
    $jenis_zakat = $_POST["jenis_zakat"];
    $total_zakat = $_POST["total_zakat"];
    $tgl_pengambilan = $_POST["tgl_pengambilan"];
    $jam_pengambilan = $_POST["jam_pengambilan"];
    $status_pengambilan = $_POST["status_pengambilan"];
    $profile = "INSERT INTO pembayaran(id_profile, tipe_zakat, jenis_zakat, total_zakat, tgl_pengambilan, jam_pengambilan, status_pengambilan) VALUES 
    ('$id_profile', '$tipe_zakat', '$jenis_zakat','$total_zakat','$tgl_pengambilan','$jam_pengambilan','$status_pengambilan')";
    $execute = mysqli_query($conn, $profile);
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
