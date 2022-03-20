<?php
require("koneksi.php");

$username = $_POST["username"];
$password = $_POST["password"];
$login = "SELECT * FROM tbmaster where username = '$username' And password = '$password'";
$execute = mysqli_query($conn, $login);
if (mysqli_num_rows($execute) == 0) {
    $response["kode"] = 0;
    $response["pesan"] = "Data tidak tersedia";
    $response["success"] = false;
} else {
    $row = mysqli_fetch_assoc($execute);
    $response["kode"] = 1;
    $response["pesan"] = "Data tersedia";
    $response["success"] = true;
    $response["status"] = $row["status"];
}

echo json_encode($response);
mysqli_close($conn);
