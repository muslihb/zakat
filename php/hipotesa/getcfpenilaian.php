<?php
require("../koneksi.php");

$response = array();

if ($_SERVER['REQUEST_METHOD'] == 'POST') {
    $id = $_POST["kodehipotesa"];
    $perintah = "SELECT * FROM tbhipotesa where kodehipotesa = '$id'";
    $execute = mysqli_query($conn, $perintah);
    $cek = mysqli_affected_rows($conn);
    if ($cek > 0) {
        $response["kode"] = 1;
        $response["pesan"] = "Data Berhasil didapat";
        $response["datahipotesacf"] = array();
        while ($ambil = mysqli_fetch_object($execute)) {
            $V["kodehipotesa"] = $ambil->kodehipotesa;
            $V["nama"] = $ambil->nama;
            array_push($response["datahipotesacf"], $V);
        }
    } else {
        $response["kode"] = 0;
        $response["pesan"] = "Data tidak ditemukan";
    }
} else {
    $response["kode"] = 0;
    $response["pesan"] = "Tidak Ada Post Data";
}

echo json_encode($response);
mysqli_close($conn);
