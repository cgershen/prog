<%-- 
    Document   : mapa
    Created on : 26/09/2016, 07:50:56 PM
    Author     : Jorge
--%>


<%@page import="Model.CUser"%>
<%
    CUser  u1 = (CUser) request.getSession().getAttribute("user");
%>


<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta charset=UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1"/>
        <title>Mapa</title>
        <link rel="stylesheet" href="leaflet/lib/leaflet.css"/>
        <script src="leaflet/lib/leaflet.js"></script>
        <script src="leaflet/data/countries.geojson"></script>
        <style type="text/css">
        #map {height: 400px;}
        </style>
    </head>
    <body>
       
        <h1>Nuevo usuario registrado</h1>
        <div id="map"></div>
         <script>
          var map = L.map('map').setView([<%= u1.getLatitude()%>, <%= u1.getLongitude()%>], 25);
          var countriesLayer = L.geoJson(countries).addTo(map);
                            
          map.fitBounds(countriesLayer.getBounds());
                   
          var point = [<%= u1.getLatitude()%>, <%= u1.getLongitude()%>];
          var marker = L.marker(point).addTo(map);
          
        </script>
            
        
        <h1>¡Gracias por unirte a la comunidad Hitchhiking!</h1>
        <p> ID: <%= u1.getIdU()%></p>
        <p> Nombre: <%= u1.getName()%></p>
        <p> Edad:  <%= u1.getAge()%></p>
        <p> Nombre: <%= u1.getLatitude()%></p>
        <p> Edad:  <%= u1.getLongitude()%></p>
        
        <p><a href="index.jsp">  Volver al inicio</a></p>
        
    </body>
</html>
