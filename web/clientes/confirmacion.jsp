<!DOCTYPE html>
<html>
  <head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" lang="es-ES">
    <title>Electrosa >> Confirmación</title>
    <meta name="description" content="Aplicación de prueba de Programación de aplicaciones Web; Grado en informática; Universidad de La Rioja." lang="es-ES">
    <meta name="keywords" content="electrodomesticos" lang="es-ES">
    <meta name="language" content="es-ES">
    <meta name="robots" content="index,follow">

    <link href="../css/electrosa.css" rel="stylesheet" media="all" type="text/css">
    <link href="../css/confirmacion.css" rel="stylesheet" media="all" type="text/css">
  </head>

  <body >
     
      <%@include file="CabClientesSesion.html" %>

    <div class="sombra">
      <div class="nucleo">
        <div id="migas">
          <a href="index.html" title="Inicio" >Inicio</a><!-- &nbsp; | &nbsp; 
          <a href="..." title="Otra cosa">Otra cosa</a>   -->	
        </div>

        <div class="contenido">
		  <div style="float:left; width:20%"><img src="../img/pregunta.png"></div>
		  <div class="confirm">
		    <div>${msg}</div>
		    <div class="NOb dcha"><a href="${noLink}">NO</a></div>
		    <div class="SIb izda"><a href="${siLink}">SI</a></div>
		    <div class="clear"></div>
		  </div>
		  <div class="clear"></div>
    </div>
          <%@include file="pie.html" %>
  </body>
</html>