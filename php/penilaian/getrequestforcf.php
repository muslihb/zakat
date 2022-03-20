<?php
require("../koneksi.php");

$response = array();

if ($_SERVER['REQUEST_METHOD'] == 'POST') {
    $id = $_POST["id_request"];
    $perintah = "SELECT * FROM tbpenilaian where id_request = '$id'";
    $execute = mysqli_query($conn, $perintah);
    $cek = mysqli_affected_rows($conn);
    if ($cek > 0) {
        $response["kode"] = 1;
        $response["pesan"] = "Data Berhasil didapat";
        $response["datapenilaian"] = array();
        while ($ambil = mysqli_fetch_object($execute)) {
                $V["id_nilai_amil"] = $ambil->id_nilai_amil;
                $V["id_request"] = $ambil->id_request;
                $V["nilai_cf"] = $ambil->nilai_cf;
                array_push($response["datapenilaian"], $V);
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