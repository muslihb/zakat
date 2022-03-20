<?php
require("../koneksi.php");

$response = array();

if ($_SERVER['REQUEST_METHOD'] == 'POST') {
    $kodehipotesa = $_POST["kodehipotesa"];
    $nama = $_POST["nama"];
    $login = "INSERT INTO tbhipotesa(kodehipotesa, nama) 
    VALUES ('$kodehipotesa','$nama')";
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
