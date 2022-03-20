<?php
require("../koneksi.php");

$response = array();

if ($_SERVER['REQUEST_METHOD'] == 'POST') {
    $idrequest = $_POST["id_request"];
    $tgl_pengambilan = $_POST["tgl_pengambilan"];
    $statuspengambilan = $_POST["statuspengambilan"];
    $perintah = "UPDATE tbrequestzakat SET tgl_pengambilan = '$tgl_pengambilan', statuspengambilan = '$statuspengambilan'
      WHERE id_request = '$idrequest'";
    $execute = mysqli_query($conn, $perintah);
    $cek = mysqli_affected_rows($conn);
    if ($cek > 0) {
        $response["kode"] = 1;
        $response["pesan"] = "Data Berhasil diubah";
    } else {
        $response["kode"] = 0;
        $response["pesan"] = "Data tidak diubah";
    }
} else {
    $response["kode"] = 0;
    $response["pesan"] = "Tidak Ada Post Data";
}

echo json_encode($response);
mysqli_close($conn);
