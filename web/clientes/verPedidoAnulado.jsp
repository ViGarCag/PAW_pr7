<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page import="paw.model.Cliente"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
  <head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" lang="es-ES">
    <title>Electrosa >> Hoja de pedido</title>
    <meta name="description" content="Aplicación de prueba de Programación de aplicaciones Web; Grado en informática; Universidad de La Rioja." lang="es-ES">
    <meta name="keywords" content="electrodomesticos" lang="es-ES">
    <meta name="language" content="es-ES">
    <meta name="robots" content="index,follow">

    <link href="${pageContext.request.contextPath}/css/electrosa.css" rel="stylesheet" media="all" type="text/css">
    <link href="${pageContext.request.contextPath}/css/clientes.css" rel="stylesheet" media="all" type="text/css">
    <link href="${pageContext.request.contextPath}/css/pedido.css" rel="stylesheet" media="all" type="text/css">
    <link href="${pageContext.request.contextPath}/css/listado.css" rel="stylesheet" media="all" type="text/css">
  </head>

  <body >
      <%@include file="CabClientesSesion.html" %>

    <div class="sombra">
      <div class="nucleo">
        <div id="migas">
          <a href="../index.html" title="Inicio" >Inicio</a> &nbsp; | &nbsp; 
          <a href="AreaCliente" title="Área de cliente">Área de cliente</a>&nbsp; | &nbsp; 
          <a href="PedidosCliente" title="Mis pedidos">Mis pedidos</a>
        </div>
           <% HttpSession sesion= request.getSession();%>
          <% Cliente cliente= (Cliente) sesion.getAttribute("cliente");%>
        <div id="cliente">
          Bienvenido <%=cliente.getNombre()%>
        </div>
        <div class="clear"></div>
        <div class="contenido">
          <h1>Hoja de pedido    </h1>
          <div class="cabPedido"> <span class="izda">ELECTROSA - Hoja de pedido</span> <span class="dcha">Ref. Pedido: ${p.getCodigo()}</span>
            <div class="clear"></div>
          </div>
          <div class="cabIdCliente">Identificación del cliente</div>
          <div class="detIdCliente">
            <div class="izda">Cliente: ${p.getCliente().getCodigo()}</div>
            <div class="izda">${p.getCliente().getNombre()}</div>
            <div class="dcha">Fecha: ${p.fechaCierre.time}</div>
            <div class="dcha"> CIF: ${p.getCliente().getCif()}</div>
            <div class="dcha">Fecha Anulacion: ${p.getFechaAnulacion().time}</div>
            
            <div class="clear"></div>
          </div>

     

          <table width="95%">
            <colgroup>
              <col width="9%">
              <col width="49%">
              <col width="11%">
              <col width="11%">
              <col width="10%">
              <col width="10%">
            </colgroup>
            <tr>
              <td colspan="6">Detalle del pedido</td>
            </tr>
            <tr>
              <td>Cantidad</td>
              <td>Art&iacute;culo</td>
              <td>P.V.P.</td>
             
            </tr>
            
            <c:forEach var="pe" items="${p.getLineas()}" varStatus="i">
            <tr <c:if test="${i.index%2==0}"> class="par"</c:if>>
              <td style="text-align:center">${pe.getCantidad()}</td>
              <td>${pe.getArticulo().getCodigo()} / ${pe.getArticulo().getCodigo()}</td>
              <td style="text-align: right"><fmt:formatNumber type="currency" value="${pe.getArticulo().getPvp()}"/></td>

            </tr>
            </c:forEach>
          
          </table>

           

          <div>
            * S.D.: sin disponibilidad. Recibirá una notificación de entrega en el momento en que podamos atender su petición.
          </div>   
        </div>
           
          <div  class="NOanulab">
              <a href="${pageContext.request.contextPath}/clientes/PedidosCliente">Volver a mis pedidos</a>
          </div> 
          <div class="clear"></div>
      </div>

      <div class="separa"></div>

      <%@include file="pie.html" %>

    </div>
  </body>
</html>