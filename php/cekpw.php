<?php
require("koneksi.php");

$response = array();

if ($_SERVER['REQUEST_METHOD'] == 'POST') {
    $username = $_POST["username"];
    $password = $_POST["password"];
    $perintah = "SELECT * FROM tbmaster where username = '$username' and password = '$password'";
    $execute = mysqli_query($conn, $perintah);
    $cek = mysqli_affected_rows($conn);
    if ($cek > 0) {
        $response["kode"] = 1;
        $response["pesan"] = "Data Berhasil didapat";
        $response["datalogin"] = array();
        while ($ambil = mysqli_fetch_object($execute)) {
            $V["iduser"] = $ambil->iduser;
            $V["nama"] = $ambil->nama;
            $V["nomortelepon"] = $ambil->nomortelepon;
            $V["alamat"] = $ambil->alamat;
            $V["username"] = $ambil->username;
            $V["password"] = $ambil->password;
            $V["status"] = $ambil->status;
            array_push($response["datalogin"], $V);
        }
    } else {
        $response["kode"] = 0;
        $response["pesan"] = "password lama salah";
    }
} else {
    $response["kode"] = 0;
    $response["pesan"] = "Tidak Ada Post Data";
}

echo json_encode($response);
mysqli_close($conn);
