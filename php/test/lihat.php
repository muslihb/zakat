<?php
require("../koneksi.php");
$perintah = "SELECT * FROM tbtest";
$execute = mysqli_query($conn, $perintah);
$cek = mysqli_affected_rows($conn);
if ($cek > 0) {
        $response["kode"] = 1;
        $response["pesan"] = "Data Berhasil didapat";
        $response["datamahasiswa"] = array();
        while ($ambil = mysqli_fetch_object($execute)) {
                $V["iduser"] = $ambil->id;
                $V["nama"] = $ambil->nama;
                $V["nomortelepon"] = $ambil->nim;
                array_push($response["datamahasiswa"], $V);
        }
} else {
        $response["kode"] = 0;
        $response["pesan"] = "Data tidak ditemukan";
}


echo json_encode($response);
mysqli_close($conn);
