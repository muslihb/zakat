<?php
require("../koneksi.php");
$perintah = "SELECT * FROM tbhipotesa order by kodehipotesa";
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


echo json_encode($response);
mysqli_close($conn);
