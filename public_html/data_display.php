<?php
     include("dbconfig_worker.php");
    
    if($_SERVER['REQUEST_METHOD']=='POST'){
        
              $phone = $_POST['phone'];
            //   $phone_combine = $phone;
              $password = $_POST['password'];
        
              $check_duplicate_data = "SELECT phone, password FROM worker_table WHERE phone = '".$phone."' AND  password = '".$password."'";
              $result = mysqli_query($con, $check_duplicate_data);
              $count = mysqli_num_rows($result);
        
            //   echo $phone;
        
              if($count > 0){
                //   echo "Successfull"."<br>";
                  $all_user_query = "SELECT * FROM worker_table WHERE phone = '$phone'";
                  $result_all = mysqli_query($con, $all_user_query);
                  if (mysqli_num_rows($result_all) > 0) {
                    // output data of each row
                    $rows = array();
                    while($row = mysqli_fetch_assoc($result_all)) {
                        // echo "id : ".$row["id"]."<br>";
                        // echo "Name : ".$row["name"]."<br>";
                        // echo "phone : ".$row["phone"]."<br>";
                        $rows[] = $row;
                    }
                    echo json_encode($rows);
                }
        
              }else{
                  echo "Not Found Any Account ! Enter your correct Phone Number & Password";
              }
        
            //   echo $name;
            //   echo $email;
            //   echo $password;
          }
?>