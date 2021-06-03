<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" lang="es-ES">
        <title>Nuevo art&iacute;culo</title>
        <meta name="description" content="Aplicación de prueba de Programación de aplicaciones Web; Grado en informática; Universidad de La Rioja." lang="es-ES">
        <meta name="keywords" content="electrodomesticos" lang="es-ES">
        <meta name="language" content="es-ES">
        <meta name="robots" content="index,follow">

        <link href="../css/electrosa.css" rel="stylesheet" media="all" type="text/css">
        <link href="../css/formulario.css" rel="stylesheet" media="all" type="text/css">
        <link href="../css/clientes.css" rel="stylesheet" media="all" type="text/css">
        <link href="../css/estilomenu.css" rel="stylesheet" media="all" type="text/css" />
    </head>
    <body>
        <%@include file="cabeceraAdmin.html" %>
        <div class="sombra">
            <div class="nucleo">
                <div id="migas">
                    <a href="index.html" title="Inicio" >Inicio</a>
                </div>

                <div class="clear"></div>

                <div class="contenido">

                    <h1>A&ntilde;adir un art&iacute;culo    </h1>
                    <p>Utilice el siguiente formulario.   </p>

                    <c:if test="${validacion != null}">
                        <div class="alerta">

                            <c:forEach var="val" items="${validacion}">
                                ${val}<br>
                            </c:forEach>  

                        </div>    
                    </c:if>
                    <form id="fArtic" name="fArtic" action="NuevoArticulo" method="POST" enctype="multipart/form-data">
                        <fieldset> 
                            <legend>Datos del art&iacute;culo </legend> 
                            <div class="field">
                                <label for="nombre">Nombre:
                                    <c:if test="${articulo.getNombre()==null}">
                                        <span class='Requerido'>Requerido</span>
                                    </c:if>
                                </label>
                                <input type="text" name="nombre" id="nombre" size="55" value="${articulo.getNombre()}">
                            </div>
                            <div class="field">
                                <label for="pvp">P.V.P:
                                    <c:if test="${articulo.getPvp()==null}">
                                        <span class='Requerido'>Requerido</span>
                                    </c:if>
                                </label>
                                <input type="text" name="pvp" id="pvp" size="15" value="${articulo.getPvp()}">
                            </div>
                            <div>
                                <div class="left">
                                    <div class="field">
                                        <label for="tipo">Tipo:
                                            <c:if test="${articulo.getTipo()==null}">
                                                <span class='Requerido'>Requerido</span>
                                            </c:if>
                                        </label>
                                        <select name="tipo" id="tipo">
                                            <option disabled selected value="">- Elige -</option>                      
                                            <option value="Aspirador">Aspirador</option>                      
                                            <option value="Campana">Campana</option>                      
                                            <option value="Cocina">Cocina</option>                      
                                            <option value="Frigorifico">Frigorifico</option>                      
                                            <option value="Horno">Horno</option>                      
                                            <option value="Lavadora">Lavadora</option>                      
                                            <option value="Lavavajillas">Lavavajillas</option>                      
                                            <option value="Microondas">Microondas</option>                      
                                            <option value="Placa">Placa</option>
                                            <!-- 
                                                <option disabled readonly selected value="">- Elige -</option>                      
                                                <!-- <c:forEach var="t" items="Aspirador,Campana,Cocina,Frigorifico,Horno,Lavadora,Lavavajillas,Microondas,Placa" >
                                                <option value="${t}" ${(t eq articulo.getTipo() ? 'selected' : '')} >${t}</option>
                                                </c:forEach>
                                                
                                                -->
                                                
                                           
                                            
                                        </select>       
                                        <input id="otroTip" type="checkbox" name=" " value="" title="Introduce otro tipo" disabled/>               
                                        Otro 
                                        <div id="otrotipoCont"><!--<label>&nbsp;</label><input class="text" type="text" name="tipo" id="tipo" value="" >--></div>
                                    </div>
                                </div>
                                <div class="right">
                                    <div class="field">
                                        <label for="fabricante">Fabricante:
                                            <span class='Requerido'>Requerido</span>
                                        </label>
                                        <select name="fabricante" id="fabricante">
                                            <option disabled selected value="">- Elige -</option>                       
                                            <option value="Edesa">Edesa</option>                      
                                            <option value="Fagor">Fagor</option>                      
                                            <option value="Miele">Miele</option>                      
                                        </select>
                                        <input id="otroFab" type="checkbox" name=" " value=""  title="Introduce otro fabricante" disabled/>
                                        Otro 
                                        <div id="otrofabricanteCont"><!--<label>&nbsp;</label><input class="text" type="text" name="fabricante" id="fabricante" value="" >--></div>
                                    </div>
                                </div>		
                            </div>

                            <div class="field">
                                <label for="descripcion">
                                    Descripci&oacute;n:
                                </label>
                                <textarea name="descripcion" id="descripcion" cols="70" rows="3"></textarea>
                            </div>

                            <style>
                                .fotoBtn {
                                    width: auto;
                                    background:#5C7E72;
                                    padding:8px 10px 10px 10px;
                                    color:#FFF;
                                }
                            </style>              

                            <div class="field">
                                <label for="fichFoto" class="fotoBtn">
                                    Fichero fotograf&iacute;a
                                </label>
                                <input type="file" name="fichFoto" id="fichFoto" style="display:none">
                                
                                <label for="Foto" class="">
                                    Seleccion:
                                </label>
                                    <input type="File" name="FotoEleccion" id="FotoEleccion">
                                
                            </div>


                        </fieldset>


                        <fieldset class="submit"> 
                            <div class="right">
                                <div class="field">
                                    <input class="submitb" type="submit"  value="Enviar los datos" >  
                                </div>
                            </div>
                        </fieldset>  
                    </form>

                </div>
            </div>

            <div class="separa"></div>
            <%@include file="pieAdmin.html" %>
    </body>
</html>
