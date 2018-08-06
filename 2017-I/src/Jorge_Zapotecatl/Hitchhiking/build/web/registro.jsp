<%-- 
    Document   : registro
    Created on : 26/09/2016, 05:35:28 PM
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
        <h1>Formulario</h1>
                
        <form action = "procesar.do" method="post">
        
        <table>
        
        <tr>
        <td>
        Nombre: <input type="text" name="txtName"/><br />
        </td>
        </tr>
        
        <tr>
        <td>
         Edad: <input type="text" name="txtAge"/><br />
        </td>
        </tr>
             
        <tr>
        <td>
         Latitud: <input type="text" name="txtLatitude"/><br />
        </td>
        <td>
         Longitud: <input type="text" name="txtLongitude"/><br />
        </td>
        </tr>
        
        <tr>
        <td>
        <input type="submit" value = "Enviar datos" />
        </td>
        </tr>
        
                 
        </table>
        </form>
        
        
         <p><img align=center src=Images/hitchhiking.jpg border=0></p>
        
        
    </body>
</html>
