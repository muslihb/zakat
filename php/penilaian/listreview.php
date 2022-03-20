<?php
require("../koneksi.php");
$perintah = "SELECT tbpenilaian.id_nilai_amil, tbpenilaian.nilai_cf, tbpenilaian.id_request, tbmaster.nama FROM tbrequestzakat INNER JOIN tbpenilaian ON tbpenilaian.id_request = tbrequestzakat.id_request JOIN tbmaster ON tbrequestzakat.id_amil = tbmaster.iduser";
$execute = mysqli_query($conn, $perintah);
$cek = mysqli_affected_rows($conn);
if ($cek > 0) {
        $response["kode"] = 1;
        $response["pesan"] = "Data Berhasil didapat";
        $response["datapenilaian"] = array();
        while ($ambil = mysqli_fetch_object($execute)) {
                $V["tbpenilaian.id_nilai_amil"] = $ambil->id_nilai_amil;
                $V["tbpenilaian.id_request"] = $ambil->id_request;
                $V["tbpenilaian.nilai_cf"] = $ambil->nilai_cf;
                $V["tbmaster.nama"] = $ambil->nama;
                array_push($response["datapenilaian"], $V);
        }
} else {
        $response["kode"] = 0;
        $response["pesan"] = "Data tidak ditemukan";
}


echo json_encode($response);
mysqli_close($conn);
