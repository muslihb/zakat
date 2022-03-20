<?php
require("../koneksi.php");

$response = array();

if ($_SERVER['REQUEST_METHOD'] == 'POST') {
    $kodeevidence = $_POST["kodeevidence"];
    $nama = $_POST["nama"];
    $mb = $_POST["mb"];
    $md = $_POST["md"];
    $kodehipotesa = $_POST["kodehipotesa"];
    $login = "INSERT INTO tbevidence(kodeevidence, mb, md, nama, kodehipotesa) 
    VALUES ('$kodeevidence','$mb','$md','$nama','$kodehipotesa')";
    $execute = mysqli_query($conn, $login);
    $cek1 = mysqli_affected_rows($conn);
    if ($cek1 > 0) {
        $response["kode"] = 1;
        $response["pesan"] = "Akun Berhasil Dibuat";
    } else {
        $response["kode"] = 0;
        $response["pesan"] = "Akun Gagal Dibuat";
    }
} else {
    $response["kode"] = 0;
    $response["pesan"] = "Tidak Ada Post Data";
}

echo json_encode($response);
mysqli_close($conn);
