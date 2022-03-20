<?php
require("../koneksi.php");

$response = array();

if ($_SERVER['REQUEST_METHOD'] == 'POST') {
    $id = $_POST["kodehipotesa"];
    $nama = $_POST["nama"];
    $perintah = "UPDATE tbhipotesa SET nama = '$nama' WHERE kodehipotesa = '$id'";
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
