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


  function onLocationFound(e) {
    var radius = e.accuracy / 2;

      //document.getElementById("origen").value = e.latlng.toString();
      //L.marker(e.latlng,{icon:myIcon})
      //.bindPopup('<b> Usted esta aqui </b>')
      //.addTo(map);
      //L.circle(e.latlng, radius).addTo(map);
  }

  function onLocationError(e) {
    alert(e.message);
  }

  map.on('locationfound', onLocationFound);
  map.on('locationerror', onLocationError);

  map.locate({setView: true, maxZoom: 18});


var o_lat=0;
var o_long=0;
var d_lat=0;
var d_long=0;

var popup = L.popup();

  function onMapClick(e) {

    popup
      .setLatLng(e.latlng)
      .setContent("Usted dió clic en " + e.latlng.toString())
      .openOn(map);
         
         if(document.getElementById("origen").value==""){
            document.getElementById("origen").value = e.latlng.toString();
                      o_lat=e.latlng.lat;
                      o_long=e.latlng.lng;
                      L.marker([o_lat,o_long],{icon:myIcon})
                      .bindPopup('<b> Origen </b>')
                      .addTo( map );
            }
          else{
          if(document.getElementById("destino").value==""){
             document.getElementById("destino").value = e.latlng.toString();             
                      d_lat=e.latlng.lat;
                      d_long=e.latlng.lng;
                      L.marker([d_lat,d_long],{icon:myIcon})
                      .bindPopup('<b> Destino </b>')
                      .addTo( map );
                   }
         }

       if(document.getElementById("origen").value!="" && document.getElementById("destino").value!=""){
                     $.ajax({
                        url : "http://35.160.229.64:8000/api/lines/", 
                        type : "POST",
                        dataType: "json", 
                        data : {
                            p_origen :  "(" + o_long + "," + o_lat + ")",
                            p_destino : "(" + d_long + "," + d_lat + ")",
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

                            var polyline = new L.Polyline(linePoints, polylineOptions);

                            map.addLayer(polyline);  

                            // zoom the map to the polyline
                            map.fitBounds(polyline.getBounds());


                        },
                    });
         }
  }
  map.on('click', onMapClick);