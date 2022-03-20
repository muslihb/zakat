<?php
require("koneksi.php");
$perintah = "SELECT * FROM tbl_login";
$execute = mysqli_query($conn, $perintah);
$cek = mysqli_affected_rows($conn);
if($cek > 0){
        $response["kode"] = 1;
        $response["pesan"] = "Data Berhasil didapat";
        $response["datalogin"] = array();
        while($ambil = mysqli_fetch_object($execute)){
        $V["id"] = $ambil->id;
        $V["email"] = $ambil->email;
        $V["password"] = $ambil->password;
        $V["status"] = $ambil->status;
        array_push($response["datalogin"], $V);
    }
}else{
        $response["kode"] = 0;
        $response["pesan"] = "Data tidak ditemukan";
}


echo json_encode($response);
mysqli_close($conn);
