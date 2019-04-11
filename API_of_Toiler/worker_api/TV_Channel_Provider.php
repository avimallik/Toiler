<?php

  include("dbconfig_worker.php");
   $sql = "SELECT id,name,phone,address,designation,area_name,city,latitude,longitude,gender,experience,description,postcode,account_type,image,status FROM worker_table WHERE designation='TV Channel Provider'";
    $result = mysqli_query($con, $sql);

    if (mysqli_num_rows($result) > 0) {
        // output data of each row
      $rows = array();
       while($r = mysqli_fetch_assoc($result)) {
          $rows[] = $r; // with result object
        //  $rows[] = $r; // only array
       }
      echo json_encode($rows);

    } else {
        echo '{"result": "No data found"}';
    }
  ?>