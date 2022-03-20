<?php
require("koneksi.php");

$response = array();

if ($_SERVER['REQUEST_METHOD'] == 'POST') {
    $id = $_POST["iduser"];
    $ids = str_replace('.', '.', $id);
    $perintah = "DELETE FROM tbmaster where iduser in ($ids)";
    $execute = mysqli_query($conn, $perintah);
    $cek = mysqli_affected_rows($conn);
    if ($cek > 0) {
        $response["kode"] = 1;
        $response["pesan"] = "Hapus Data Berhasil";
    } else {
        $response["kode"] = 0;
        $response["pesan"] = "Data Gagal Dihapus";
    }
} else {
    $response["kode"] = 0;
    $response["pesan"] = "Tidak Ada Post Data";
}

echo json_encode($response);
mysqli_close($conn);
