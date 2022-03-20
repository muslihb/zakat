<?php
require("../koneksi.php");
$perintah = "SELECT * FROM tbtersedia";
$execute = mysqli_query($conn, $perintah);
$cek = mysqli_affected_rows($conn);
if ($cek > 0) {
        $response["kode"] = 1;
        $response["pesan"] = "Data Berhasil didapat";
        $response["datarequest"] = array();
        while ($ambil = mysqli_fetch_object($execute)) {
            $V["jeniszakat"] = $ambil->jeniszakat;
            $V["jml_zakat"] = $ambil->jml_zakat;
            array_push($response["datarequest"], $V);
        }
} else {
        $response["kode"] = 0;
        $response["pesan"] = "Data tidak ditemukan";
}


echo json_encode($response);
mysqli_close($conn);
