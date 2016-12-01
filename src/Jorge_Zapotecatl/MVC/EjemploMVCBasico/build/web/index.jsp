<%-- 
    Document   : index
    Created on : 12/09/2016, 04:34:09 PM
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
        <h1>formulario</h1>
        <form action = "procesar.do" method="post">
            Nombre: <input type="text" name="txtNombre"/><br />
            Edad: <input type="text" name="txtEdad"/><br />
            <input type="submit" value = "Enviar datos" />
            
            </form>
    </body>
</html>
