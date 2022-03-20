<?php
require("../koneksi.php");
$perintah = "SELECT * FROM tbpremis order by kodepremis";
$execute = mysqli_query($conn, $perintah);
$cek = mysqli_affected_rows($conn);
if ($cek > 0) {
        $response["kode"] = 1;
        $response["pesan"] = "Data Berhasil didapat";
        $response["datapremisfc"] = array();
        while ($ambil = mysqli_fetch_object($execute)) {
                $V["kodepremis"] = $ambil->kodepremis;
                $V["nama"] = $ambil->nama;
                $V["quest"] = $ambil->quest;
                $V["ifyes"] = $ambil->ifyes;
                $V["ifno"] = $ambil->ifno;
                array_push($response["datapremisfc"], $V);
        }
} else {
        $response["kode"] = 0;
        $response["pesan"] = "Data tidak ditemukan";
}


echo json_encode($response);
mysqli_close($conn);
