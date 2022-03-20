<?php
require("../koneksi.php");

$response = array();

if ($_SERVER['REQUEST_METHOD'] == 'POST') {
    $kodepremis = $_POST["kodepremis"];
    $nama = $_POST["nama"];
    $quest = $_POST["quest"];
    $ifyes = $_POST["ifyes"];
    $ifno = $_POST["ifno"];
    $perintah = "UPDATE tbpremis SET nama = '$nama', quest = '$quest', ifyes = '$ifyes', ifno = '$ifno'
        WHERE kodepremis = '$kodepremis'";
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
