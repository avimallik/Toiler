<?php
    include("dbconfig_worker.php");
    
    if($_SERVER['REQUEST_METHOD']=='POST'){
        $id = $_POST['id'];
        $id_helper = (int)$id;
        $image = $_POST['image'];

        unlink($_SERVER['DOCUMENT_ROOT']."/"."upload_image"."/".$image);
        
        $sql_delete_worker = "DELETE FROM worker_table WHERE id = $id_helper";

        if(mysqli_query($con,$sql_delete_worker)){
            echo "Your information is successfully deleted from Toiler, we don't wanna let you go, see you next time Toilarian";
        }else{
            echo "Database Connection Error !";
        } 
	}
?>