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

var myIcon_red_car = L.icon({
  iconUrl: myURL + 'images/car_red_24.png',
  iconRetinaUrl: myURL + 'images/car_red_48.png',
  iconSize: [29, 24],
  iconAnchor: [9, 21],
  popupAnchor: [0, -14]
})

var myIcon_green_car = L.icon({
  iconUrl: myURL + 'images/car_green_24.png',
  iconRetinaUrl: myURL + 'images/car_green_48.png',
  iconSize: [29, 24],
  iconAnchor: [9, 21],
  popupAnchor: [0, -14]
})

var myIcon_green_bus = L.icon({
  iconUrl: myURL + 'images/bus_green_24.png',
  iconRetinaUrl: myURL + 'images/bus_green_48.png',
  iconSize: [29, 24],
  iconAnchor: [9, 21],
  popupAnchor: [0, -14]
})

var myIcon_red_bus = L.icon({
  iconUrl: myURL + 'images/bus_red_24.png',
  iconRetinaUrl: myURL + 'images/bus_red_48.png',
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
      marker_origen = event.target;
      var position = marker_origen.getLatLng();
      marker_origen.setLatLng(new L.LatLng(position.lat, position.lng),{draggable:'true'});
      map.panTo(new L.LatLng(position.lat, position.lng))
      //document.getElementById("origen").value = position.toString();
      o_lat=position.lat;
      o_long=position.lng;
      destino = "origen";
      map.fitBounds([[o_lat,o_long]]);
      getReverseGeocodingData(destino,o_lat, o_long);
    });
    marker_origen.setIcon(myIcon_green);
    marker_origen.addTo(map);
    o_lat=e.latlng.lat;
    o_long=e.latlng.lng;
    destino= "origen";
    map.fitBounds([[o_lat,o_long]]);
    getReverseGeocodingData(destino,o_lat, o_long);
  }

  function onLocationError(e) {
    //alert(e.message);
    //startOrigen();
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
    //getReverseGeocodingData(destino,o_lat, o_long);

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
      vmarker_destino = event.target;
      var position = marker_destino.getLatLng();
      marker_destino.setLatLng(new L.LatLng(position.lat, position.lng),{draggable:'true'});
      map.panTo(new L.LatLng(position.lat, position.lng))
      //document.getElementById("destino").value = position.toString();
      d_lat=position.lat;
      d_long=position.lng;
      destino= "destino";
      map.fitBounds([[d_lat,d_long]]);
      getReverseGeocodingData(destino,d_lat, d_long);
    });
    marker_destino.setIcon(myIcon_red);
    marker_destino.addTo(map);
    d_lat=e.latlng.lat;
    d_long=e.latlng.lng;
    destino= "destino";
    map.fitBounds([[d_lat,d_long]]);
    getReverseGeocodingData(destino,d_lat, d_long);
}

map.on('click', onMapClick);

function origenMarcador(lat,lng){
  console.log(lat);
  console.log(lng);
    map.removeLayer(marker_origen);
    //Módulo para obtener la posición del usuario como Origen.
    marker_origen = new L.marker([lat,lng],{draggable:'true'})
    marker_origen.on('dragend', function(event){
      marker_origen = event.target;
      marker_origen.setLatLng(new L.LatLng(lat,lng),{draggable:'true'});
      map.panTo(new L.LatLng(lat,lng))
      o_lat=lat;
      o_long=lng;
      //destino = "origen";
      //getReverseGeocodingData(destino,o_lat, o_long);
    });
    marker_origen.setIcon(myIcon_green);
    marker_origen.addTo(map);
    o_lat=lat;
    o_long=lng;
    map.fitBounds([[o_lat,o_long]]);
    //destino= "origen";
    //getReverseGeocodingData(destino,o_lat, o_long);
    if (clen_pol==true) {
        clearPolylines();
    };
    drawPolyline();
}


function destinoMarcador(lat,lng){
  console.log(lat);
  console.log(lng);

  if(marker_destino){
    console.log("entrando a error");
    map.removeLayer(marker_destino);
  }

    marker_destino = new L.marker([lat,lng],{draggable:'true'})
    marker_destino.on('dragend', function(event){
      marker_destino = event.target;
      marker_destino.setLatLng(new L.LatLng(lat,lng),{draggable:'true'});
      map.panTo(new L.LatLng(lat,lng))
      d_lat=lat;
      d_long=lng;
      //destino= "destino";
      //getReverseGeocodingData(destino,d_lat, d_long);
    });
    marker_destino.setIcon(myIcon_red);
    marker_destino.addTo(map);
    d_lat=lat;
    d_long=lng;
    map.fitBounds([[d_lat,d_long]]);
    //destino= "destino";
    //getReverseGeocodingData(destino,d_lat, d_long);*/
    if (clen_pol==true) {
        clearPolylines();
    };
    drawPolyline();
}

var transporte = 0;
function drawPolyline() {
  console.log("Entre a dibujar Polyline");
  if(document.getElementById("origen").value!="" && document.getElementById("destino").value!=""){
                        if(array_pol.length!=0) // Borra polilineas en caso de haber cambiado la ruta
                        {
                          clearPolylinesMatch(array_pol.length);
                        }
                      console.log("Dibujando polyline");
                      transporte = medioTransporte();
                      console.log("medioTransporte = " + transporte);
                     $.ajax({
                        url : "http://35.162.215.204:8000/api/lines/", 
                        type : "POST", 
                        dataType: "json", 
                        data : {
                            p_origen :  "(" + d_long + "," + d_lat + ")", //Se voltearon parámetros porque el servicio calcula
                            p_destino : "(" + o_long + "," + o_lat + ")", // el shortest path de Destino a Origen
                            //user_id : 2,
                            tipo_transporte: transporte,
                               },
                        success : function(json) {
                            console.log(json);
                            id_ruta_valid = false;
                            path = json.shortest_path;
                            console.log(path);
                                                      
                            var linePoints=[];
                            console.log(path.length);
                            linePoints.push(new L.LatLng(d_lat,d_long)); // Agrega el punto de Destino a la Polyline
                            for(var i=1;i<path.length;i++) {
                              var point = path[i];
                              linePoints.push(new L.LatLng(point[1],point[0]));
                            }
                            linePoints.push(new L.LatLng(o_lat,o_long)); // Agrega el punto de Origen a la Polyline
                                     
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
                        },
                    });
  }
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


function findMatch(opcion){
  console.log("findMatch");
  console.log("ID_Ruta = " + id_ruta_aux);
  if(!document.getElementById("car").checked && !document.getElementById("bus").checked && !document.getElementById("bike").checked && !document.getElementById("walk").checked){
    alert("Selecciona un medio de transporte");
  }
  else{
  if(document.getElementById("origen").value !="" && document.getElementById("destino").value !=""){
      almacenaRuta(opcion);
  }
  else if(document.getElementById("origen").value == "")
  {
    alert("Indicar un Origen");
  }
  else{
    alert("Indicar un Destino")
  }
}
}


var array_pol =[];
function match(num_ruta,id_ruta)
{
                    $.ajax({
                      url : "http://35.162.215.204:8000/api/matches/", 
                      type : "POST",
                      dataType: "json", 
                      data : { ruta_id : id_ruta, },
                      //data : { ruta_id : 468,} ,
                      success : function(json) {
                      
                      console.log(json);
                      if(json.length == 0)
                      {
                        id_ruta_aux = "";
                        alert("No hay rutas coincidentes");
                      }
                      //Dibuja la polilinea de la ruta 1
                      if(num_ruta==0  && 1 <= json.length){ 
                        if(array_pol.length!=0)
                        {
                          clearPolylinesMatch(array_pol.length);
                        }
                        array_pol[0]=drawRoute(json[0].ruta,'#0000FF',num_ruta);
                      }
                      
                      //Dibuja la polilinea de la ruta 2
                      if(num_ruta==1 && 1 <= json.length){
                        if(array_pol.length!=0)
                        {
                          clearPolylinesMatch(array_pol.length);
                        }
                        array_pol[0]=drawRoute(json[1].ruta,'#FFFF00',num_ruta);
                      }

                      //Dibuja la polilinea de la ruta 3
                      if(num_ruta==2 && 1 <= json.length){
                        if(array_pol.length!=0)
                        {
                          clearPolylinesMatch(array_pol.length);
                        }
                        array_pol[0]=drawRoute(json[2].ruta,'#000000',num_ruta);
                      }

                      //Dibuja la polilinea de la ruta 4
                      if(num_ruta==3 && 1 <= json.length){
                        if(array_pol.length!=0)
                        {
                          clearPolylinesMatch(array_pol.length);
                        }
                        array_pol[0]=drawRoute(json[3].ruta,'#8000FF',num_ruta);
                      }
                      //Dibuja la polilinea de la ruta 5
                      if(num_ruta==4 && 1 <= json.length){
                        if(array_pol.length!=0)
                        {
                          clearPolylinesMatch(array_pol.length);
                        }
                           array_pol[0]=drawRoute(json[4].ruta,'#FF0080',num_ruta);
                      }

                      //Dibuja todas la polilineas de todas las rutas 5
                      if (num_ruta==5) {
                        console.log("Dibuja todas las rutas disponibles");
                        console.log("Num de rutas = " + json.length);
                        
                        if(array_pol.length!=0)
                        {
                          clearPolylinesMatch(array_pol.length);
                        }

                        for(var i=0;i<json.length;i++)
                        {
                          if(i==0){ 
                              array_pol[i]=drawRoute(json[0].ruta,'#0000FF',num_ruta);
                          }
                          if(i==1){
                              array_pol[i]=drawRoute(json[1].ruta,'#FFFF00',num_ruta);
                          }
                          if(i==2){
                              array_pol[i]=drawRoute(json[2].ruta,'#000000',num_ruta);
                          }
                          if(i==3){
                              array_pol[i]=drawRoute(json[3].ruta,'#8000FF',num_ruta);
                          }
                          if(i==4){
                              array_pol[i]=drawRoute(json[4].ruta,'#FF0080',num_ruta);
                          }
                        }
                        console.log("num de pols = " + array_pol.length);
                      }
                      }
                    });
}

function clearPolylinesMatch(num_pol){
  console.log("Borrando polilineas de match");
  for(var i=0;i<num_pol;i++){
        map.removeLayer(array_pol[i]);
  }
  map.removeLayer(marker1);
  map.removeLayer(marker2);
}

var marker1, marker2;
function drawRoute(ruta,color,num_ruta_aux){
                      route = ruta;
                      c=color;
                      console.log(route);

                      var linePoints_route = [];

                      for(var i=0;i<route.length;i++){
                        var point_route = route[i];
                        if(i==0){
                          marker1 = new L.marker([Number(point_route[1]),Number(point_route[0])]);
                        }
                        if (i==route.length - 1){
                          marker2 = new L.marker([Number(point_route[1]),Number(point_route[0])]);
                        } 
                        //console.log(point_route);
                        linePoints_route.push(new L.LatLng(Number(point_route[1]),Number(point_route[0])));
                      }

                      if (num_ruta_aux != 5){
                        if(transporte==1 || transporte==4){
                          marker1.setIcon(myIcon_red_car);
                          marker2.setIcon(myIcon_green_car);
                        }
                        if(transporte==2){
                          marker1.setIcon(myIcon_red_bike);
                          marker2.setIcon(myIcon_green_bike);
                        }
                        if(transporte==3){
                          marker1.setIcon(myIcon_red_bus);
                          marker2.setIcon(myIcon_green_bus);
                        }
                      marker1.addTo(map);
                      marker2.addTo(map);
                      //marker1.bindPopup('Francisco Neri');
                      //marker1.openPopup();
                      }
                      
                      var polylineOptions = {
                               color: c,
                               weight: 6,
                               opacity: 0.6
                      };


                      var polyline_route = new L.Polyline(linePoints_route, polylineOptions);
                            
                      map.addLayer(polyline_route);  
                            

                      // zoom the map to the polyline
                      map.fitBounds(polyline_route.getBounds());
                      return polyline_route;
}

var id_ruta_aux = "";
var id_ruta_valid=false;
function almacenaRuta(num_ruta)
{
  if(num_ruta==5 && id_ruta_valid == false)
  {
    transporte = medioTransporte();
    console.log("almacenaRuta");
    $.ajax({
      url : "http://35.162.215.204:8000/api/lines/", 
      type : "POST", 
      dataType: "json", 
      data : {
        p_origen :  "(" + d_long + "," + d_lat + ")", //Se voltearon parámetros porque el servicio calcula
        p_destino : "(" + o_long + "," + o_lat + ")", // el shortest path de Destino a Origen
        guardar : 1, //Guarda la polilinea en la BD
        //user_id : 2,
        tipo_transporte: transporte,
      },
      success : function(json) {
        console.log(json);
         id_ruta = json.id;
         id_ruta_valid=true;
         id_ruta_aux = id_ruta;
         console.log("id_ruta = " + id_ruta);
         match(num_ruta,id_ruta_aux);
        },
  });
  }
  else if(num_ruta == 5 && id_ruta_valid == true && id_ruta_aux == ""){
    alert("No hay rutas coincidentes");
  }
  else{
    console.log("id_ruta = " + id_ruta_aux);
    console.log("Valid = " + id_ruta_valid);
    if(id_ruta_valid == true && id_ruta_aux != "")
    {
      match(num_ruta,id_ruta_aux);
    }
    else{
      alert("Dar click en Find Match");
    }
  }
}

function medioTransporte(){
  var car = document.getElementById("car").checked;
  var bus = document.getElementById("bus").checked;
  var bike = document.getElementById("bike").checked;
  var walk = document.getElementById("walk").checked;
   
   //Car = 1; Bike = 2; Bus = 3; Walk = 4
  if (car){
    return 1;
  }else if(bike){
    return 2; 
  }else if(bus){
    return 3; 
  }else if(walk){
    return 4;
  }
  else{
    return 1;
  }
}