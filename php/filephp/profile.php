<?php
require("koneksi.php");

$login = "SELECT * FROM tbl_login";
$execute = mysqli_query($conn, $login);
if (mysqli_num_rows($execute) == 0){
    $response["kode"] = 0;
    $response["pesan"] = "Data tidak tersedia";
    $response["success"] = false;
}else{
   $row = mysqli_fetch_assoc($execute);
    $response["kode"] = 1;
    $response["pesan"] = "Data tersedia";
    $response["success"] = true;
    $response["id"] = $row["id"];
    $response["email"] = $row["email"];
    $response["password"] = $row["password"];
    $response["status"] = $row["status"];
}

echo json_encode($response);
mysqli_close($conn);