<?php
require("koneksi.php");

$response = array();

if ($_SERVER['REQUEST_METHOD'] == 'POST') {
    $username = $_POST["username"];
    $id = $_POST["iduser"];
    $nl = $_POST["nama"];
    $nt = $_POST["nomortelepon"];
    $alamat = $_POST["alamat"];
    $perintah = "UPDATE tbmaster SET username = '$username', nama = '$nl', nomortelepon = '$nt', alamat = '$alamat' 
      WHERE iduser = '$id'";
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
