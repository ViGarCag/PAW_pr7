<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" lang="es-ES">
        <title>Bienvenido a Electrosa</title>
        <meta name="description" content="Aplicación de prueba de Programación de aplicaciones Web; Grado en informática; Universidad de La Rioja." lang="es-ES">
        <meta name="keywords" content="electrodomesticos" lang="es-ES">
        <meta name="language" content="es-ES">
        <meta name="robots" content="index,follow">

        <link href="css/electrosa.css" rel="stylesheet" media="all" type="text/css">
    </head>

    <body >
        <div class="logo"><a href="index.html"><img src="img/LogoElectrosa200.png" border="0"></a></div>

        <c:if test="${not empty param.lang}" >
        <fmt:setLocale value="${param.lang}" scope="session"/>
        </c:if>
        <fmt:setBundle  basename="electrosaMsg"  scope="session"></fmt:setBundle>
    <div class="sombra">
        <div class="nucleo">
            <div id="lang">
                <a href="index.jsp?lang=es_ES">Español</a> &nbsp; | &nbsp; <a href="index.jsp?lang=en">English</a>
            </div>
        </div>
    </div>

    <div class="barra_menus">	
        <div class="pestanias">
            <div class="grupoPestanias">
                <div class="pestaniaSel"><fmt:message key="pest.usr"/> Para usuarios</div>
                <div class="pestaniaNoSel"><a href="admin/index.html">Intranet</a></div>
            </div>
        </div>

        <div id="menu" >
            <ul>
                <li>
                    <a href="index.html"><fmt:message key="pest.usr.sobre"/>Sobre electrosa<br/><img src="img/Home4.png"/></a>
                </li>
                <li>
                    <a href="index.html"><fmt:message key="pest.usr.donde"/>Dónde estamos<br/><img src="img/map.png"/></a>
                </li>
                <li>
                    <a href="BuscarArticulos"><fmt:message key="pest.usr.catalogo"/>Hojear catálogo<br/><img src="img/catalog.png"/></a>
                </li>
                <li>
                    <a href="clientes/AreaCliente"><fmt:message key="pest.usr.reg"/>Usuario registrado<br/><img src="img/registrado.png"/></a>
                </li>
            </ul>
            <div style="clear: left;"></div>
        </div>
    </div> 

    <div class="sombra">
        <div class="nucleo">
            <div id="migas">
                <a href="index.html" title="Inicio" >Inicio</a><!-- &nbsp; | &nbsp; 
                <a href="..." title="Otra cosa">Otra cosa</a>   -->	
            </div>
            <div class="contenido">
                <h1><fmt:message key="home.tit"/>Bienvenido a Electrosa </h1>
                <p><fmt:message key="home.txt"/>Electrosa ha venido acompañando a los alumnos de informática de la Universidad de la Rioja desde 2003. Con el fin de prestarle el mejor servicio, ponemos a su disposición toda la información que necesite sobre Eletrosa.</p>
            </div>
            <div class="centro">
                <ul>
                    <li class="columna">  
                        <ul>  
                            <li class="celda">
                                <div class="parteCelda">
                                    <a href="generica.html"><img src="img/motif_b2c_1_194.jpg" alt="" title=""></a>
                                </div>
                                <div class="parteCelda2">
                                    <h3><a href="generica.html"><fmt:message key="home.secc.sat.tit"/>El mejor Servicio de Mantenimiento</a></h3>
                                    <fmt:message key="home.secc.sat.txt"/>Quién mejor que Electrosa para cuidar&nbsp;sus electrodomésticos
                                    <div style="float:right; padding:10px 5px">
                                        <a style="padding:5px 8px 5px 10px;background:#5C7E72;color:#FFF" href="generica.html" ><span><fmt:message key="home.secc.sat.btn"/>Sepa por qu&eacute; ...</span> </a>
                                    </div>
                                </div>
                                <div class="clear"></div>
                            </li> 
                            <li class="celda">
                                <div class="parteCelda">
                                    <a class="parteCelda" href="generica.html"><img src="img/imag_teaser_encuentre-solucion.jpg" alt="" title="" ></a>
                                </div>
                                <div class="parteCelda2">
                                    <h3><a href="generica.html"><fmt:message key="home.secc.sol.tit"/>Encuentre la solución más adecuada</a></h3>
                                    <fmt:message key="home.secc.sol.txt"/>Descubra&nbsp;el producto que necesita&nbsp;
                                    <div style="float:right; padding:10px 5px">
                                        <a style="padding:5px 8px 5px 10px;background:#5C7E72;color:#FFF" href="generica.html" ><span><fmt:message key="home.secc.sol.btn"/>Comenzar ...</span> </a>
                                    </div>
                                </div>
                                <div class="clear"></div>
                            </li> 
                        </ul>
                    </li>

                    <li class="columna"> 
                        <ul>  
                            <li class="celda">
                                <div class="parteCelda">
                                    <a href="generica.html"><img src="img/registre-garantia.jpg" alt="" title=""></a>
                                </div>
                                <div class="parteCelda2">
                                    <h3 ><a href="generica.html"><fmt:message key="home.secc.garant.txt"/>Garantía y Puesta en Marcha</a></h3>
                                    <fmt:message key="home.secc.garant.btn"/>Solicite su Garantía y Puesta en Marcha gratuita
                                    <div style="float:right; padding:10px 5px">
                                        <a style="padding:5px 8px 5px 10px;background:#5C7E72;color:#FFF" href="generica.html" ><span>Registrar ...</span> </a>
                                    </div>
                                </div>
                                <div class="clear"></div>
                            </li> 
                            <li class="celda">
                                <div class="parteCelda">
                                    <a href="generica.html"><img src="img/atencion-cliente.jpg" alt="" title=""></a>
                                </div>
                                <div class="parteCelda2">
                                    <h3><a href="generica.html"><fmt:message key="home.secc.atn.tit"/>Atención al Cliente</a></h3>
                                    <fmt:message key="home.secc.atn.txt"/>Contacte con Electrosa<br><strong>999 99 99 99</strong>
                                    <div style="float:right; padding:10px 5px">
                                        <a style="padding:5px 8px 5px 10px;background:#5C7E72;color:#FFF" href="generica.html" ><span>M&aacute;s ...</span> </a>
                                    </div>
                                </div>
                                <div class="clear"></div>
                            </li> 			
                        </ul>
                    </li>
                </ul>

            </div>
            <div class="clear"></div>
        </div>

        <div class="separa"></div>

        <div class="pie">
            <span class="pie_izda">
                <a href="mailto:francisco.garcia@unirioja.es">Contacto</a> &nbsp; | &nbsp; <a href="mapa.html">Mapa</a> </span>
            <span class="pie_dcha">
                &copy; 2012 Francisco J. García Izquierdo  </span>
            <div class="clear"></div>  
        </div>

    </div>
</body>
</html>