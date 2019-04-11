<?php
    include("dbconfig_worker.php");
    
    if($_SERVER['REQUEST_METHOD']=='POST'){
        $id = $_POST['id'];
        $phone = $_POST['phone'];
        $id_helper = (int)$id;
        $sql_update_worker = "UPDATE worker_table SET phone ='$phone' WHERE id = $id_helper";

        $check_duplicate_data = "SELECT phone FROM worker_table WHERE phone = '$phone' ";
        $result = mysqli_query($con, $check_duplicate_data);
        $count = mysqli_num_rows($result);

        if($count > 0){
            echo "This phone number is already exists! Sorry, choose another phone number for Update your information";
        }else if(mysqli_query($con,$sql_update_worker)){
            echo "Congrats , Your Phone number is successfully updated";
        }else{
            echo "Database Connection Error !";
        } 
	}
?>