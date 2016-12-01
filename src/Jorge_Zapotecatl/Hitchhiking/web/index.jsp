<%-- 
    Document   : index
    Created on : 26/09/2016, 03:47:32 PM
    Author     : Jorge
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
    </head>
    <body>
        <h1>Hitchhiking</h1>
        
        <p>
        Hitchhiking es un sistema web que promueve y facilita la cultura de compartir el auto dentro 
        de comunidades de confianza como lo son las universidades. Es un sistema 
        en línea donde personas que pertenecen a una misma institución comparten autos con la comunidad 
        para reducir el tráfico y aprovechar mejor los recursos.
        </p>
        
        <p><img align=center src=Images/logo-promo.jpg border=0></p>
        
        <form action = "main.do" method="post">
        <table>
        <tr>
        <td>  
        <select id="isOption" name="isOption">
        <option value="Registrarse">Registrarse</option>
        <option value="Visualizacion">Visualizar Usuarios</option>
        </select>
        </td>
        </tr>
        
        <tr>
        <td>
        <input type="submit" name="subVis" value = "Enviar Opciones" />
        </td>
        </tr>
        
        </table>
        </form>
        
    </body>
</html>



