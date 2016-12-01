<%-- 
    Document   : mapaUsuarios
    Created on : 26/09/2016, 05:39:33 PM
    Author     : Jorge
--%>
<%@page import="java.sql.ResultSet"%>
<%
    ResultSet   res = (ResultSet) request.getSession().getAttribute("results");
    
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
        <h1>Usuarios</h1>
        <div id="map"></div>  
        
        <%
            out.println("<script>  var map = L.map('map'); var countriesLayer = L.geoJson(countries).addTo(map); map.fitBounds(countriesLayer.getBounds());");
            while (res.next()) {
                
                  float latitude = res.getFloat("latitude");
                  float longitude = res.getFloat("longitude");

                  out.println("var point = [" + latitude + ", " + longitude + "];");
                  out.println("var marker = L.marker(point).addTo(map);");
            }
            out.println("  </script>");
        %>
        
        <p><a href="index.jsp">  Volver al inicio</a></p>
        
    </body>
</html>
