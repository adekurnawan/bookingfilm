<?php
	$server		= "localhost"; 
	$user		= "adekur"; 
	$password	= "m1m1n"; 
	$database	= "tixadedb";

	$con = mysqli_connect($server, $user, $password, $database);
	if (mysqli_connect_errno()) {
		echo "Gagal terhubung MySQL: " . mysqli_connect_error();
	}

?>
