<?php
require("../koneksi.php");
$perintah = "SELECT * FROM tbmaster INNER JOIN tbmutashik ON tbmaster.iduser = tbmutashik.idamil";
$execute = mysqli_query($conn, $perintah);
$cek = mysqli_affected_rows($conn);
if ($cek > 0) {
        $response["kode"] = 1;
        $response["pesan"] = "Data Berhasil didapat";
        $response["datamamut"] = array();
        while ($ambil = mysqli_fetch_object($execute)) {
            $V["tbmutashik.idmutashik"] = $ambil->idmutashik;
            $V["tbmutashik.idamil"] = $ambil->idamil;
            $V["tbmutashik.jenis_zakat"] = $ambil->jenis_zakat;
            $V["tbmutashik.jumlah"] = $ambil->jumlah;
            $V["tbmutashik.namamutashik"] = $ambil->namamutashik;
            $V["tbmutashik.golonganmutashik"] = $ambil->golonganmutashik;
            $V["tbmutashik.tglmenerima"] = $ambil->tglmenerima;
            $V["tbmaster.iduser"] = $ambil->iduser;
            $V["tbmaster.nama"] = $ambil->nama;
            $V["tbmaster.nomortelepon"] = $ambil->nomortelepon;
            $V["tbmaster.alamat"] = $ambil->alamat;
            $V["tbmaster.username"] = $ambil->username;
            $V["tbmaster.password"] = $ambil->password;
            $V["tbmaster.status"] = $ambil->status;
            array_push($response["datamamut"], $V);
        }
} else {
        $response["kode"] = 0;
        $response["pesan"] = "Data tidak ditemukan";
}


echo json_encode($response);
mysqli_close($conn);
