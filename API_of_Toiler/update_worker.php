<?php
    include("dbconfig_worker.php");
    
    if($_SERVER['REQUEST_METHOD']=='POST'){
        $id = $_POST['id'];
		$name = $_POST['name'];
        // $phone = $_POST['phone'];
        $address = $_POST['address'];
        $area_name = $_POST['area_name'];
        $city = $_POST['city'];
        $experience = $_POST['experience'];
        $description = $_POST['description'];
        $postcode = $_POST['postcode'];
        $latitude = $_POST['latitude'];
        $longitude = $_POST['longitude'];
        $password = $_POST['password'];
        
        $id_helper = (int)$id;
        $sql_update_worker = "UPDATE worker_table SET name ='$name', address = '$address', area_name = '$area_name', city = '$city', experience = '$experience', description = '$description', postcode = '$postcode', latitude = '$latitude', longitude = '$longitude', password = '$password' WHERE id = $id_helper";
        
        // $check_duplicate_data = "SELECT phone FROM worker_table WHERE phone = '$phone' ";
        // $result = mysqli_query($con, $check_duplicate_data);
        // $count = mysqli_num_rows($result);

        if(mysqli_query($con,$sql_update_worker)){
            echo "Congrats , Your Information is successfully updated";
        }else{
            echo "Database Connection Error !";
        } 
	}
?>