<?php
    include("dbconfig_worker.php");
    
    if($_SERVER['REQUEST_METHOD']=='POST'){
		$name = $_POST['name'];
		$phone = $_POST['phone'];
		$address = $_POST['address']; 
		$designation = $_POST['designation'];
		$area_name = $_POST['area_name'];
		$city = $_POST['city'];
		$password = $_POST['password'];
		$latitude = $_POST['latitude'];
		$longitude = $_POST['longitude'];
		$gender = $_POST['gender'];
		$experience = $_POST['experience'];
		$description = $_POST['description'];
		$postcode = $_POST['postcode'];
		$account_type = $_POST['account_type'];
		$search_key = $_POST['search_key'];
		$image = $_POST['image'];
		
		
		//Image Upload Experiement
		$sql_id_selector = "SELECT id FROM worker_table ORDER BY id ASC";
		$id_selector_exec = mysqli_query($con,$sql_id_selector);
		$id = 0;
		 while($row = mysqli_fetch_array($id_selector_exec)){
		     $id = $row['id'];
		 }
		 
		$id_phone_combiner = $id.''.$phone;
		$path = "upload_image/$id_phone_combiner.png";
		$mainpath = "http://www.armapprise.com/$path";
		
		
        
        $sql = "INSERT INTO worker_table (name,phone,address,designation,area_name,city,password,latitude,longitude,gender,experience,description,postcode,account_type,search_key,image) VALUES ('$name','$phone','$address','$designation','$area_name','$city','$password','$latitude','$longitude','$gender','$experience','$description','$postcode','$account_type','$search_key','$mainpath')";
        $check_duplicate_data = "SELECT phone FROM worker_table WHERE phone = '$phone' ";
        $result = mysqli_query($con, $check_duplicate_data);
        $count = mysqli_num_rows($result);

        if($count > 0){
            echo "This phone number is already exists! Sorry, choose another phone number for joining to Toiler";
        }else if(mysqli_query($con,$sql)){
            echo "Congrats , You successfully registered to Toiler, Thank You for joining Toiler";
            file_put_contents($path,base64_decode($image));
        }else{
            echo "Database Connection Error !";
        }

        
        
        // $dupesql = "SELECT * FROM worker_table where (phone = '$phone')";
		// $duperaw = mysqli_query($con,$dupesql);
		
		// if(mysqli_query($con,$sql)){
		// 	echo "Information Successfully Added in Server";
		// }else if (mysql_num_rows($duperaw) > 0) {
        //     echo "Duplicate Data";
        //   }
		// else{
		// 	echo "Server Connection Establishing Error";
		// }
	}
?>