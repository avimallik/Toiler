<?php
    include("dbconfig_worker.php");
    
    if($_SERVER['REQUEST_METHOD']=='POST'){
        $id = $_POST['id'];
        $status = $_POST['status'];
        $id_helper = (int)$id;
        $sql_update_worker_status = "UPDATE worker_table SET status ='$status' WHERE id = $id_helper";

        if(mysqli_query($con,$sql_update_worker_status)){
            echo "Status Updated";
        }else{
            echo "Database Connection Error !";
        } 
	}
?>