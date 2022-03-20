<?php
require("koneksi.php");

$response = array();

if ($_SERVER['REQUEST_METHOD'] == 'POST') {
    $nama = $_POST["nama"];
    $nomortelepon = $_POST["nomortelepon"];
    $alamat = $_POST["alamat"];
    $username = $_POST["username"];
    $password = $_POST["password"];
    $status = $_POST["status"];
    $login = "INSERT INTO tbmaster(nama, nomortelepon, username, password, status, alamat) 
    VALUES ('$nama','$nomortelepon','$username', '$password', '$status', '$alamat')";
    $execute = mysqli_query($conn, $login);
    $cek1 = mysqli_affected_rows($conn);
    if ($cek1 > 0) {
        $response["kode"] = 1;
        $response["pesan"] = "Akun Berhasil Dibuat";
    } else {
        $response["kode"] = 0;
        $response["pesan"] = "Akun Gagal Dibuat || Username sudah terdaftar";
    }
} else {
    $response["kode"] = 0;
    $response["pesan"] = "Tidak Ada Post Data";
}

echo json_encode($response);
mysqli_close($conn);
