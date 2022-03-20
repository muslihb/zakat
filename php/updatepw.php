<?php
require("koneksi.php");

$response = array();

if ($_SERVER['REQUEST_METHOD'] == 'POST') {
    $username = $_POST["username"];
    $password = $_POST["password"];
    $perintah = "UPDATE tbmaster SET password = '$password' WHERE username = '$username'";
    $execute = mysqli_query($conn, $perintah);
    $cek = mysqli_affected_rows($conn);
    if ($cek > 0) {
        $response["kode"] = 1;
        $response["pesan"] = "Password Berhasil diubah";
    } else {
        $response["kode"] = 0;
        $response["pesan"] = "Password tidak berhasil diubah";
    }
} else {
    $response["kode"] = 0;
    $response["pesan"] = "Tidak Ada Post Data";
}

echo json_encode($response);
mysqli_close($conn);
