<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" lang="es-ES">
        <title>Electrosa >> Pedido en realización</title>
        <meta name="description" content="Aplicación de prueba de Programación de aplicaciones Web; Grado en informática; Universidad de La Rioja." lang="es-ES">
        <meta name="keywords" content="electrodomesticos" lang="es-ES">
        <meta name="language" content="es-ES">
        <meta name="robots" content="index,follow">

        <link href="../css/electrosa.css" rel="stylesheet" media="all" type="text/css">
        <link href="../css/clientes.css" rel="stylesheet" media="all" type="text/css">
        <link href="../css/pedidoR.css" rel="stylesheet" media="all" type="text/css">
    </head>

    <body >
        <%@include file="CabClientesSesion.html" %>
        <jsp:useBean id="now" class="java.util.Date" />

        <div class="sombra">
            <div class="nucleo">
                <div id="migas">
                    <a href="../index.html" title="Inicio" >Inicio</a> &nbsp; | &nbsp; 
                    <a href="AreaCliente" title="Área de cliente">Área de cliente</a>
                </div>
                <div id="cliente">
                    Bienvenido, ${cliente.nombre}
                </div>
                <div class="clear"></div>
                <div class="contenido">
                    <c:if test="${pedido!=null}">

                        <h1>Contenido de su  pedido</h1>
                        <p>Pedido iniciado el ${now}.</p>
                        <form action="GestionaPedido" method="post">
                            <table width="95%" cellspacing="0">
                                <tr>
                                    <td colspan="2"><img src="${pageContext.request.contextPath}/img/AddCartb.png" style="vertical-align:middle;margin-bottom:3px; margin-left:15px">&nbsp; Art&iacute;culos del pedido</td>
                                    <td width="10%">P.V.P.</td>
                                    <td width="10%">Cantidad</td>
                                    <td width="16%">Fecha de entrega (dd/mm/yyyy)</td>
                                </tr>
                                <c:forEach var="pedidolinea" items="${pedido.getLineas()}">
                                    <tr >
                                        <td width="6%" style="text-align:center"><a href="GestionaPedido?accion=Quitar&cl=${pedidolinea.getCodigo()}"><img src="${pageContext.request.contextPath}/img/cancel.png" alt="Quitar del pedido" border="0" title="Quitar del pedido"></a></td>
                                        <td width="58%"><span class="codigo">${pedidolinea.getArticulo().getCodigo()}</span> - <br/><span class="descr">${pedidolinea.getArticulo().getNombre()}</span></td>
                                        <td>${pedidolinea.getPrecio()} &euro;</td>
                                        <td>
                                            <input class="cantidad" type="text" name="C_${pedidolinea.getCodigo()}" size="3" value="${pedidolinea.getCantidad()}">
                                        </td>
                                        <td>
                                            <input type="text" name="F_${pedidolinea.getCodigo()}" size="10" value="<fmt:formatDate value="${pedidolinea.getFechaEntregaDeseada().getTime()}" pattern="dd/MM/yyyy"/>">			  
                                        </td>
                                    </tr>
                                </c:forEach>

                            </table>
                            <input class="submitb" type="submit" name="accion" value="Seguir comprando">
                            <input class="submitb" type="submit" name="accion" value="Guardar pedido">
                            <input class="submitb cerrar" type="submit" name="accion" value="Cerrar pedido">	
                            <input class="submitb cancelar" type="submit" name="accion" value="Cancelar">
                        </form>
                    </c:if>
                </div>
                <div class="clear"></div>
            </div>

            <div class="separa"></div>

            <div class="pie">
                <span class="pie_izda">
                    <a href="mailto:francisco.garcia@unirioja.es">Contacto</a> &nbsp; | &nbsp; <a href="../mapa.html">Mapa</a> </span>
                <span class="pie_dcha">
                    &copy; 2012 Francisco J. García Izquierdo  </span>
                <div class="clear"></div>  
            </div>

        </div>
    </body>
</html>