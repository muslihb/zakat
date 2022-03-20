<?php
require("../koneksi.php");

$response = array();

if ($_SERVER['REQUEST_METHOD'] == 'POST') {
    $kodeevidence = $_POST["kodeevidence"];
    $nama = $_POST["nama"];
    $mb = $_POST["mb"];
    $md = $_POST["md"];
    $kodehipotesa = $_POST["kodehipotesa"];
    $perintah = "UPDATE tbevidence SET mb = '$mb', md = '$md', nama = '$nama', kodehipotesa = '$kodehipotesa'
        WHERE kodeevidence = '$kodeevidence'";
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
