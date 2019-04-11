<?php
    echo "<table>";
     echo "<tr>";
     echo "<td>Name</td>";
     echo "<td>".$name."</td>";
     echo "</tr>";
     echo "</table>";
        $locations=array(); 
        // $uname="root";
        // $pass="";
        // $servername="localhost";
        // $dbname="bcremote";
        // $db=new mysqli($servername,$uname,$pass,$dbname);
        include("dbconfig_worker.php");
        $query =  $con->query("SELECT * FROM worker_table");
        //$number_of_rows = mysql_num_rows($db);  
        //echo $number_of_rows;
        while( $row = $query->fetch_assoc() ){
            $name = $row['name'];
            $longitude = $row['longitude'];                              
            $latitude = $row['latitude'];
            $link=$row['link'];
            /* Each row is added as a new array */
            $locations[]=array( 'name'=>$name, 'lat'=>$latitude, 'lng'=>$longitude, 'name'=>$name );
        }
        //echo $locations[0]['name'].": In stock: ".$locations[0]['lat'].", sold: ".$locations[0]['lng'].".<br>";
        //echo $locations[1]['name'].": In stock: ".$locations[1]['lat'].", sold: ".$locations[1]['lng'].".<br>";
    ?>
    <script type="text/javascript" src="http://maps.googleapis.com/maps/api/js?key=AIzaSyCQNzgFBUR5Xlaw40mAGDr_XePDSxbIFiQ"></script> 
    <script src="https://cdn.klokantech.com/maptilerlayer/v1/index.js"></script>
    <script type="text/javascript">
    var map;
    var Markers = {};
    var infowindow;
    var locations = [
        <?php for($i=0;$i<sizeof($locations);$i++){ $j=$i+1;?>
        [
            'AMC Service',
            '<p><?php echo $locations[$i]['name'];?></p>',
            <?php echo $locations[$i]['lat'];?>,
            <?php echo $locations[$i]['lng'];?>,
            0
        ]<?php if($j!=sizeof($locations))echo ","; }?>
    ];
    var origin = new google.maps.LatLng(locations[0][2], locations[0][3]);
    function initialize() {
        
      var mapOptions = {
        zoom: 9,
        center: origin
      };
      map = new google.maps.Map(document.getElementById('map-canvas'), mapOptions);
        infowindow = new google.maps.InfoWindow();
        
        for(i=0; i<locations.length; i++) {
            var position = new google.maps.LatLng(locations[i][2], locations[i][3]);
            var marker = new google.maps.Marker({
                position: position,
                map: map,
            });
            
            google.maps.event.addListener(marker, 'click', (function(marker, i) {
                return function() {
                    infowindow.setContent(locations[i][1]);
                    infowindow.setOptions({maxWidth: 200});
                    infowindow.open(map, marker);
                }
            }) (marker, i));
            Markers[locations[i][4]] = marker;
        }
        locate(0);
        
           var loc = {};
        var geocoder = new google.maps.Geocoder();
        if(google.loader.ClientLocation) {
            loc.lat = google.loader.ClientLocation.latitude;
            loc.lng = google.loader.ClientLocation.longitude;

            var latlng = new google.maps.LatLng(loc.lat, loc.lng);
            geocoder.geocode({'latLng': latlng}, function(results, status) {
                if(status == google.maps.GeocoderStatus.OK) {
                    alert(results[0]['formatted_address']);
                };
            });
        }
        
        google.load("maps", "3.x", {other_params: "sensor=false", callback:initialize});
    }
    
    
    function locate(marker_id) {
        var myMarker = Markers[marker_id];
        var markerPosition = myMarker.getPosition();
        map.setCenter(markerPosition);
        google.maps.event.trigger(myMarker, 'click');
    }
    
    function getLocation() {
    if (navigator.geolocation) {
        navigator.geolocation.getCurrentPosition(showPosition);
    } else { 
        x.innerHTML = "Geolocation is not supported by this browser.";
    }
    }

    google.maps.event.addDomListener(window, 'load', initialize);
    </script>
    <body id="map-canvas">
    </body>
    