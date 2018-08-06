<%-- 
    Document   : exito
    Created on : 12/09/2016, 06:23:39 PM
    Author     : Jorge
--%>

<%@page import="modelo.CPersona"%>
<%
    CPersona  p1 = (CPersona) request.getSession().getAttribute("persona1");
%>
    
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
    </head>
    <body>
                
        <h1>Datos recibidos exitosamente</h1>
        <p> Nombre: <%= p1.getNombre()%></p>
        <p> Edad:  <%= p1.getEdad()%></p>
    </body>
</html>
