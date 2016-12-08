// See post: http://asmaloney.com/2014/01/code/creating-an-interactive-map-with-leaflet-and-openstreetmap/

var map = L.map('map').fitWorld();

  L.tileLayer('https://api.tiles.mapbox.com/v4/{id}/{z}/{x}/{y}.png?access_token=pk.eyJ1IjoibWFwYm94IiwiYSI6ImNpandmbXliNDBjZWd2M2x6bDk3c2ZtOTkifQ._QA7i5Mpkd_m30IGElHziw', {
    maxZoom: 18,
    attribution: 'Map data &copy; <a href="http://openstreetmap.org">OpenStreetMap</a> contributors, ' +
      '<a href="http://creativecommons.org/licenses/by-sa/2.0/">CC-BY-SA</a>, ' +
      'Imagery © <a href="http://mapbox.com">Mapbox</a>',
    id: 'mapbox.streets'
  }).addTo(map);

var myURL = jQuery( 'script[src$="leaf-demo.js"]' ).attr( 'src' ).replace( 'leaf-demo.js', '' )

var myIcon = L.icon({
  iconUrl: myURL + 'images/pin24.png',
  iconRetinaUrl: myURL + 'images/pin48.png',
  iconSize: [29, 24],
  iconAnchor: [9, 21],
  popupAnchor: [0, -14]
})

var myIcon_green = L.icon({
  iconUrl: myURL + 'images/pin_green_24.png',
  iconRetinaUrl: myURL + 'images/pin_green_48.png',
  iconSize: [29, 24],
  iconAnchor: [9, 21],
  popupAnchor: [0, -14]
})

var myIcon_red = L.icon({
  iconUrl: myURL + 'images/pin_red_24.png',
  iconRetinaUrl: myURL + 'images/pin_red_48.png',
  iconSize: [29, 24],
  iconAnchor: [9, 21],
  popupAnchor: [0, -14]
})

  var marker_origen;
  var marker_destino;
  
  var o_lat=0;
  var o_long=0;
  var d_lat=0;
  var d_long=0;
  var destino = "";
  var polyline;
  var clen_pol=false;

  function onLocationFound(e) {
    var radius = e.accuracy / 2;
    
    //Módulo para obtener la posición del usuario como Origen.
    marker_origen = new L.marker(e.latlng,{draggable:'true'})
    //document.getElementById("origen").value = e.latlng.toString();
    marker_origen.on('dragend', function(event){
      var marker_origen = event.target;
      var position = marker_origen.getLatLng();
      marker_origen.setLatLng(new L.LatLng(position.lat, position.lng),{draggable:'true'});
      map.panTo(new L.LatLng(position.lat, position.lng))
      //document.getElementById("origen").value = position.toString();
      o_lat=position.lat;
      o_long=position.lng;
      destino = "origen";
      getReverseGeocodingData(destino,o_lat, o_long);
    });
    marker_origen.setIcon(myIcon_green);
    marker_origen.addTo(map);
    o_lat=e.latlng.lat;
    o_long=e.latlng.lng;
    destino= "origen";
    getReverseGeocodingData(destino,o_lat, o_long);
  }

  function onLocationError(e) {
    alert(e.message);
    //Latitud y Longitud de la CDMX
    Lat_Mex= 19.39085896142664;
    Lng_Mex= -99.14361265000002;

    //Módulo para localizar al usuario en la ciudad de México, cuando no haya servicio
    marker_origen = new L.marker(e.latlng,{draggable:'true'})
    marker_origen.setIcon(myIcon_green);
    marker_origen.addTo(map);
    o_lat = Lat_Mex;
    o_long = Lng_Mex; 
    destino = "origen";
    getReverseGeocodingData(destino,o_lat, o_long);

  }

  map.on('locationfound', onLocationFound);
  map.on('locationerror', onLocationError);

  map.locate({setView: true, maxZoom: 18});




function onMapClick(e) {
  if(document.getElementById("destino").value!=""){
    map.removeLayer(marker_destino);
  }

    marker_destino = new L.marker(e.latlng,{draggable:'true'})
    //document.getElementById("destino").value = e.latlng.toString();
    marker_destino.on('dragend', function(event){
      var marker_destino = event.target;
      var position = marker_destino.getLatLng();
      marker_destino.setLatLng(new L.LatLng(position.lat, position.lng),{draggable:'true'});
      map.panTo(new L.LatLng(position.lat, position.lng))
      //document.getElementById("destino").value = position.toString();
      d_lat=position.lat;
      d_long=position.lng;
      destino= "destino";
      getReverseGeocodingData(destino,d_lat, d_long);
    });
    marker_destino.setIcon(myIcon_red);
    marker_destino.addTo(map);
    d_lat=e.latlng.lat;
    d_long=e.latlng.lng;
    destino= "destino";
    getReverseGeocodingData(destino,d_lat, d_long);
}

map.on('click', onMapClick);

function drawPolyline() {
  console.log("Entre a dibujar Polyline");
  if(document.getElementById("origen").value!="" && document.getElementById("destino").value!=""){
                      console.log("Dibujando polyline");
                     $.ajax({
                        url : "http://35.164.20.251:8000/api/lines/", 
                        type : "POST",
                        dataType: "json", 
                        data : {
                            p_origen :  "(" + d_long + "," + d_lat + ")", //Se voltearon parámetros porque el servicio calcula
                            p_destino : "(" + o_long + "," + o_lat + ")", // el shortest path de Destino a Origen
                               },
                        success : function(json) {
                            console.log(json);

                            path = json.shortest_path;
                            console.log(path);
                                                      
                            var linePoints=[];

                            for(var i=0;i<path.length;i++) {
                              var point = path[i];
                              linePoints.push(new L.LatLng(point[1],point[0]));
                            }
                                     
                            var polylineOptions = {
                               color: '#f50909',
                               weight: 6,
                               opacity: 0.6
                            };

                            polyline = new L.Polyline(linePoints, polylineOptions);
                            
                            map.addLayer(polyline);  
                            

                            // zoom the map to the polyline
                            map.fitBounds(polyline.getBounds());
                            clen_pol=true;
                            //findMatch();
                        },
                    });
  }
}
function findMatch(){
  console.log("findMatch");
                    $.ajax({
                      url : "http://35.164.20.251:8000/api/matches/", 
                      type : "POST",
                      dataType: "json", 
                      data : { ruta_id : 1024, },
                      success : function(json) {
                      
                      console.log(json);
                      
                      route = json[0].ruta;
                      console.log(route);

                      var linePoints_route = [];

                      for(var i=0;i<route.length;i++){
                        var point_route = route[i];
                        //console.log(point_route);
                        linePoints_route.push(new L.LatLng(Number(point_route[1]),Number(point_route[0])));
                      }
                      
                      var polylineOptions = {
                               color: '#f50099',
                               weight: 6,
                               opacity: 0.6
                      };

                      var polyline_route = new L.Polyline(linePoints_route, polylineOptions);
                            
                      map.addLayer(polyline_route);  
                            

                      // zoom the map to the polyline
                      map.fitBounds(polyline_route.getBounds());
                      },
                    });
}

function getReverseGeocodingData(des, lat, lng) {
    var latlng = new google.maps.LatLng(lat, lng);
    
    // This is making the Geocode request
    var geocoder = new google.maps.Geocoder();
    geocoder.geocode({ 'latLng': latlng }, function (results, status) {
        if (status !== google.maps.GeocoderStatus.OK) {
            alert(status);
        }
        // This is checking to see if the Geoeode Status is OK before proceeding
        if (status == google.maps.GeocoderStatus.OK) {
            console.log(results);
            var address = (results[0].formatted_address);
            if (des == "origen")
            {
              document.getElementById("origen").value = address.toString();
              console.log("Origen");
              console.log(address);
            }
            else
            {
              document.getElementById("destino").value = address.toString();
              console.log("Destino");
              console.log(address);
            }
            if (clen_pol==true) {
              clearPolylines();
            };
            drawPolyline();
        }
    });
    return this;
}

// clear polylines   
function clearPolylines() {
    map.removeLayer(polyline);
}

function findMatch(num_ruta){
  console.log("findMatch");
  console.log("fdgfdgd"+origenLocation);
                    $.ajax({
                      url : "http://35.164.20.251:8000/api/matches/", 
                      type : "POST",
                      dataType: "json", 
                      data : { ruta_id : 1078, },
                      success : function(json) {
                     
                      //Dibuja la polilinea de la ruta 1
                      if(num_ruta==0  && num_ruta<=json.length){ 
                           drawRoute(json[0].ruta,'#071019');
                      }

                      //Dibuja la polilinea de la ruta 2
                      if(num_ruta==1 && num_ruta<=json.length){
                           drawRoute(json[1].ruta,'#0000FF');
                      }

                      //Dibuja la polilinea de la ruta 3
                      if(num_ruta==2 && num_ruta<=json.length){
                           drawRoute(json[2].ruta,'#F7FE2E');
                      }

                      //Dibuja la polilinea de la ruta 4
                      if(num_ruta==3 && num_ruta<=json.length){
                           drawRoute(json[3].ruta,'#FF8000');
                      }
                      //Dibuja la polilinea de la ruta 5
                      if(num_ruta==4 && num_ruta<=json.length){
                           drawRoute(json[4].ruta,'#00FF00');
                      }

                      //Dibuja todas la polilineas de todas las rutas 5
                      if (num_ruta==5) {
                        for(var i=0;i<json.length;i++)
                        {
                          if(id_ruta==0){ 
                              drawRoute(json[0].ruta,'#071019');
                          }
                          if(id_ruta==1){
                              drawRoute(json[1].ruta,'#0000FF');
                          }
                          if(id_ruta==2){
                              drawRoute(json[2].ruta,'#F7FE2E');
                          }
                          if(id_ruta==3){
                              drawRoute(json[3].ruta,'#FF8000');
                          }
                          if(id_ruta==4){
                              drawRoute(json[4].ruta,'#00FF00');
                          }
                        }
                      }
                      }
                    });
}