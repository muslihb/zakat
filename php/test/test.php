<?php
require("../koneksi.php");

$response = array();

if ($_SERVER['REQUEST_METHOD'] == 'POST') {
    $nama = $_POST["nama"];
    $nim = $_POST["nim"];
    $login = "INSERT INTO tbtest(nama, nim) VALUES ('$nama','$nim')";
    $execute = mysqli_query($conn, $login);
    $cek1 = mysqli_affected_rows($conn);
    if ($cek1 > 0) {
        $response["kode"] = 1;
        $response["pesan"] = "Data Berhasil Disimpan";
    } else {
        $response["kode"] = 0;
        $response["pesan"] = "Data gagal Disimpan";
    }
} else {
    $response["kode"] = 0;
    $response["pesan"] = "Tidak Ada Post Data";
}

echo json_encode($response);
mysqli_close($conn);
