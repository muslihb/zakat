<?php
require("../koneksi.php");

$response = array();

if ($_SERVER['REQUEST_METHOD'] == 'POST') {
    $kodepremis = $_POST["kodepremis"];
    $nama = $_POST["nama"];
    $quest = $_POST["quest"];
    $ifyes = $_POST["ifyes"];
    $ifno = $_POST["ifno"];
    $login = "INSERT INTO tbpremis(kodepremis, nama, quest, ifyes, ifno) 
    VALUES ('$kodepremis','$nama','$quest','$ifyes', '$ifno')";
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
