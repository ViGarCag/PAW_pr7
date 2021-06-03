<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8" isErrorPage="true"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" lang="es-ES">
        <title>Electrosa >> Error de aplicación</title>
        <meta name="description" content="Aplicación de prueba de Programación de aplicaciones Web; Grado en informática; Universidad de La Rioja." lang="es-ES">
        <meta name="keywords" content="electrodomesticos" lang="es-ES">
        <meta name="language" content="es-ES">
        <meta name="robots" content="index,follow">

        <link href="${pageContext.request.contextPath}/css/electrosa.css" rel="stylesheet" media="all" type="text/css">
    </head>

    <body >
        <c:if test="${!requestScope['javax.servlet.forward.request_uri'].contains('clientes')}">
            <%@include file="cabecera.html"%>
        </c:if>
        <c:if test="${requestScope['javax.servlet.forward.request_uri'].contains('clientes')}">
            <%@include file="clientes/CabClientesSesion.html"%>
        </c:if>    

        <div class="sombra">
            <div class="nucleo">
                <div id="migas">
                    <a href="index.html" title="Inicio" >Inicio</a><!-- &nbsp; | &nbsp; 
                    <a href="..." title="Otra cosa">Otra cosa</a>   -->	
                </div>

                <div class="contenido">
                    <div style="float:left"><img src="${pageContext.request.contextPath}/img/alerta.png"></div>
                    <div class="error">
                        <div>${empty requestScope['javax.servlet.error.message'] ? 'Error de aplicación' :
requestScope['javax.servlet.error.message']}</div>
                            <c:if test="${link != null}">
                            <div class="errorb"><a href="${link}">Salir de aqui</a></div>      
                        </c:if>
                        <c:if test="${link==null}">
                            <div class="errorb"><a href="index.html">Salir de aqui</a></div>  
                        </c:if>		  </div>
                    <div class="clear"></div>

                </div>
            </div>

            <div class="separa"></div>

            <%@include file="pie.html" %> 


        </div>
    </body>
</html>
