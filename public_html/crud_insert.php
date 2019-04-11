<?php
 include("crud_dbconfig.php");

 if($_SERVER['REQUEST_METHOD']=='POST'){
     $name = $_POST['name'];
     $designation = $_POST['designation'];

     $sql_insert = "INSERT INTO crud (name,designation) VALUES ('$name','$designation')";

     if(mysqli_query($con,$sql_insert)){
        echo "Congrats , Data successfully insert ";
     }else{
        echo "Database Connection Error !";
     }
     
 }

?>