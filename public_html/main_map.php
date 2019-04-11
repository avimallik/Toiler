<?php
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

<!DOCTYPE html>
<html>
<body>

<h1>My First Google Map</h1>

<div id="button-layer">
	<button id="btnAction" onClick="locateMax()">My Current Location</button>
</div>

<div id="map-canvas" style="width:100%;height:400px;"></div>

<script>
function myMap() {
var mapProp= {
    center:new google.maps.LatLng(51.508742,-0.120850),
    zoom:5,
};
var map=new google.maps.Map(document.getElementById("map-canvas"),mapProp);
}
</script>

<script src="https://maps.googleapis.com/maps/api/js?key=AIzaSyCQNzgFBUR5Xlaw40mAGDr_XePDSxbIFiQ&callback=myMap"></script>

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
    
//     function locateMax(){
// 	document.getElementById("btnAction").disabled = true;
// 	document.getElementById("btnAction").innerHTML = "Processing...";
// 	if ("geolocation" in navigator){
// 		navigator.geolocation.getCurrentPosition(function(position){ 
// 			var currentLatitude = position.coords.latitude;
// 			var currentLongitude = position.coords.longitude;

// 			var infoWindowHTML = "Latitude: " + currentLatitude + "<br>Longitude: " + currentLongitude;
// 			var infoWindow = new google.maps.InfoWindow({map: map, content: infoWindowHTML});
// 			var currentLocation = { lat: currentLatitude, lng: currentLongitude };
// 			infoWindow.setPosition(currentLocation);
// 			document.getElementById("btnAction").style.display = 'none';
// 		});
// 	}
    
    function locate(marker_id) {
        var myMarker = Markers[marker_id];
        var markerPosition = myMarker.getPosition();
        map.setCenter(markerPosition);
        google.maps.event.trigger(myMarker, 'click');
    }
    

    google.maps.event.addDomListener(window, 'load', initialize);
    </script>
<!--
To use this code on your website, get a free API key from Google.
Read more at: https://www.w3schools.com/graphics/google_maps_basic.asp
-->

</body>
</html>