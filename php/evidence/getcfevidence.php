<?php
require("../koneksi.php");

$response = array();

if ($_SERVER['REQUEST_METHOD'] == 'POST') {
    $id = $_POST["kodeevidence"];
    $perintah = "SELECT * FROM tbevidence where kodeevidence = '$id'";
    $execute = mysqli_query($conn, $perintah);
    $cek = mysqli_affected_rows($conn);
    if ($cek > 0) {
        $response["kode"] = 1;
        $response["pesan"] = "Data Berhasil didapat";
        $response["dataevidencecf"] = array();
        while ($ambil = mysqli_fetch_object($execute)) {
                $V["kodeevidence"] = $ambil->kodeevidence;
                $V["mb"] = $ambil->mb;
                $V["md"] = $ambil->md;
                $V["nama"] = $ambil->nama;
                $V["kodehipotesa"] = $ambil->kodehipotesa;
                array_push($response["dataevidencecf"], $V);
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