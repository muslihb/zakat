<?php
require("../koneksi.php");

$response = array();

if ($_SERVER['REQUEST_METHOD'] == 'POST') {
    $id = $_POST["kodekonklusi"];
    $perintah = "SELECT * FROM tbkonklusi where kodekonklusi = '$id'";
    $execute = mysqli_query($conn, $perintah);
    $cek = mysqli_affected_rows($conn);
    if ($cek > 0) {
        $response["kode"] = 1;
        $response["pesan"] = "Data Berhasil didapat";
        $response["datakonklusifc"] = array();
        while ($ambil = mysqli_fetch_object($execute)) {
            $V["kodekonklusi"] = $ambil->kodekonklusi;
            $V["nama"] = $ambil->nama;
            array_push($response["datakonklusifc"], $V);
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
