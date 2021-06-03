package sol.ser;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Calendar;
import java.util.Enumeration;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import paw.bd.GestorBD;
import paw.bd.GestorBDPedidos;
import paw.model.Articulo;
import paw.model.Cliente;
import paw.model.ExcepcionDeAplicacion;
import paw.model.Linea;
import paw.model.LineaEnRealizacion;
import paw.model.PedidoEnRealizacion;
import paw.util.servlet.ParameterParser;

public class GestionaPedido extends HttpServlet {

    private static GestorBDPedidos gd = new GestorBDPedidos();
    private static GestorBD gb = new GestorBD();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession sesion = request.getSession();
        Cliente cliente = (Cliente) sesion.getAttribute("cliente");
        PedidoEnRealizacion carrito = (PedidoEnRealizacion) sesion.getAttribute("pedido");

        String accion = request.getParameter("accion");

        try {
            if (carrito == null) {
                carrito = gd.getPedidoEnRealizacion(cliente.getCodigo());
                if (carrito == null) {
                    carrito = new PedidoEnRealizacion(cliente);
                }
            }

            sesion.setAttribute("pedido", carrito);

            if (accion != null && accion.trim().length() > 0 && (accion.equals("Comprar") || accion.equals("Seguir")
                    || accion.equals("Guardar pedido") || accion.equals("Quitar") || accion.equals("Cerrar pedido")
                    || accion.equals("Cancelar"))) {

                switch (accion) {
                    case "Comprar":
                        if (request.getParameter("ca") != null && request.getParameter("ca").trim().length() > 0
                                && gb.getArticulo(request.getParameter("ca")) != null) {
                            try {
                                carrito.addLinea(gb.getArticulo(request.getParameter("ca")));
                            } catch (ExcepcionDeAplicacion ex) {
                                Logger.getLogger(GestionaPedido.class.getName()).log(Level.SEVERE, null, ex);
                            }
                            //MIRARLO PERO NO FUNCIONA; AÑADE CONTINUAMENTE COSAS AL CARRITO
                            //RequestDispatcher rd = request.getRequestDispatcher("/clientes/Carrito");
                            //rd.forward(request, response);
                            //MIRARLO
                            response.sendRedirect(request.getContextPath() + "/clientes/Carrito");
                            break;
                        } else {
                            if (gb.getArticulo(request.getParameter("ca")) == null) {
                                response.sendError(HttpServletResponse.SC_NOT_FOUND, "El artículo " + gb.getArticulo(request.getParameter("ca")) + " no ha sido encontrado");
                                return;
                            }
                        }
                    case "Seguir comprando":
                        procesaParams(carrito, request);
                        response.sendRedirect("../BuscarArticulos");
                        break;
                    case "Guardar pedido":
                        procesaParams(carrito, request);
                        try {
                            gd.grabaPedidoEnRealizacion(carrito);
                            response.sendRedirect(request.getContextPath() + "/clientes/AreaCliente");
                        } catch (ExcepcionDeAplicacion ex) {
                            Logger.getLogger(GestionaPedido.class.getName()).log(Level.SEVERE, null, ex);
                        }
                        break;
                    //TRABAJO
                    case "Quitar":
                        if (request.getParameter("cl") != null && request.getParameter("cl").trim().length() > 0) {
                            carrito.removeLinea(request.getParameter("cl"));
                            response.sendRedirect(request.getContextPath() + "/clientes/carrito.jsp");
                            return;
                        }
                        break;
                    //FIN PARTE TRABAJO
                    case "Cerrar pedido":
                        procesaParams(carrito, request);
                        sesion.setAttribute("pedidoACerrar", carrito.getCodigo());
                        sesion.setAttribute("msg", "Se va a proceder a cerrar su pedido en realización. ¿Está usted seguro?");
                        sesion.setAttribute("siLink", "CierraPedido?accion=cerrar");
                        sesion.setAttribute("noLink", "CierraPedido?accion=cancelar");
                        RequestDispatcher rd = request.getRequestDispatcher(("/clientes/confirmacion.jsp"));
                        rd.forward(request, response);
                        break;
                    //TRABAJO        
                    case "Cancelar":
                        sesion.setAttribute("pedidoACancelar", carrito.getCodigo());
                        sesion.setAttribute("msg", "Se va a proceder a eliminar su pedido en realización. ¿Está usted seguro?");
                        sesion.setAttribute("siLink", "AnulaCarrito?accion=anular");
                        sesion.setAttribute("noLink", "AnulaCarrito?accion=cancelar");
                        RequestDispatcher rd2 = request.getRequestDispatcher("/clientes/confirmacion.jsp");
                        rd2.forward(request, response);
                        break;
                    //FIN PARTE TRABAJO
                }
            } else {
                response.sendRedirect(request.getContextPath() + "/clientes/Carrito");
            }
        } catch (ExcepcionDeAplicacion ex) {
            Logger.getLogger(GestionaPedido.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession sesion = request.getSession();
        Cliente cliente = (Cliente) sesion.getAttribute("cliente");
        PedidoEnRealizacion carrito = (PedidoEnRealizacion) sesion.getAttribute("pedido");

        String accion = request.getParameter("accion");

        try {
            if (carrito == null) {
                carrito = gd.getPedidoEnRealizacion(cliente.getCodigo());
                if (carrito == null) {
                    carrito = new PedidoEnRealizacion(cliente);
                }
            }

            sesion.setAttribute("pedido", carrito);

            if (accion != null && accion.trim().length() > 0 && (accion.equals("Comprar") || accion.equals("Seguir")
                    || accion.equals("Guardar pedido") || accion.equals("Quitar") || accion.equals("Cerrar pedido")
                    || accion.equals("Cancelar"))) {

                switch (accion) {
                    case "Comprar":
                        if (request.getParameter("ca") != null && request.getParameter("ca").trim().length() > 0
                                && gb.getArticulo(request.getParameter("ca")) != null) {
                            try {
                                carrito.addLinea(gb.getArticulo(request.getParameter("ca")));
                            } catch (ExcepcionDeAplicacion ex) {
                                Logger.getLogger(GestionaPedido.class.getName()).log(Level.SEVERE, null, ex);
                            }
                            //MIRARLO PERO NO FUNCIONA; AÑADE CONTINUAMENTE COSAS AL CARRITO
                            //RequestDispatcher rd = request.getRequestDispatcher("/clientes/Carrito");
                            //rd.forward(request, response);
                            //MIRARLO
                            response.sendRedirect(request.getContextPath() + "/clientes/Carrito");
                            break;
                        } else {
                            if (gb.getArticulo(request.getParameter("ca")) == null) {
                                response.sendError(HttpServletResponse.SC_NOT_FOUND, "El artículo " + gb.getArticulo(request.getParameter("ca")) + " no ha sido encontrado");
                                return;
                            }
                        }
                    case "Seguir comprando":
                        procesaParams(carrito, request);
                        response.sendRedirect("../BuscarArticulos");
                        break;
                    case "Guardar pedido":
                        procesaParams(carrito, request);
                        try {
                            gd.grabaPedidoEnRealizacion(carrito);
                            response.sendRedirect(request.getContextPath() + "/clientes/AreaCliente");
                        } catch (ExcepcionDeAplicacion ex) {
                            Logger.getLogger(GestionaPedido.class.getName()).log(Level.SEVERE, null, ex);
                        }
                        break;
                    //TRABAJO
                    case "Quitar":
                        if (request.getParameter("cl") != null && request.getParameter("cl").trim().length() > 0) {
                            carrito.removeLinea(request.getParameter("cl"));
                            response.sendRedirect(request.getContextPath() + "/clientes/carrito.jsp");
                            return;
                        }
                        break;
                    //FIN PARTE TRABAJO
                    case "Cerrar pedido":
                        procesaParams(carrito, request);
                        sesion.setAttribute("pedidoACerrar", carrito.getCodigo());
                        sesion.setAttribute("msg", "Se va a proceder a cerrar su pedido en realización. ¿Está usted seguro?");
                        sesion.setAttribute("siLink", "CierraPedido?accion=cerrar");
                        sesion.setAttribute("noLink", "CierraPedido?accion=cancelar");
                        RequestDispatcher rd = request.getRequestDispatcher(("/clientes/confirmacion.jsp"));
                        rd.forward(request, response);
                        break;
                    //TRABAJO        
                    case "Cancelar":
                        sesion.setAttribute("pedidoACancelar", carrito.getCodigo());
                        sesion.setAttribute("msg", "Se va a proceder a eliminar su pedido en realización. ¿Está usted seguro?");
                        sesion.setAttribute("siLink", "AnulaCarrito?accion=anular");
                        sesion.setAttribute("noLink", "AnulaCarrito?accion=cancelar");
                        RequestDispatcher rd2 = request.getRequestDispatcher("/clientes/confirmacion.jsp");
                        rd2.forward(request, response);
                        break;
                    //FIN PARTE TRABAJO
                }
            } else {
                response.sendRedirect(request.getContextPath() + "/clientes/Carrito");
            }
        } catch (ExcepcionDeAplicacion ex) {
            Logger.getLogger(GestionaPedido.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    private void procesaParams(PedidoEnRealizacion pedido, HttpServletRequest req) {
        ParameterParser pp = new ParameterParser(req);
        Enumeration<String> pnames = req.getParameterNames();
        while (pnames.hasMoreElements()) {
            String paramName = pnames.nextElement();
            if (paramName.startsWith("C_")) {
                String codLinea = paramName.substring(2);
                LineaEnRealizacion linea = pedido.getLinea(codLinea);
                int cantidad = pp.getIntParameter(paramName, 1);
                linea.setCantidad(cantidad);
            } else if (paramName.startsWith("F_")) {
                String codLinea = paramName.substring(2);
                LineaEnRealizacion linea = pedido.getLinea(codLinea);
                Calendar fe = pp.getCalendarParameter(paramName, "dd/MM/yyyy", Calendar.getInstance());
                linea.setFechaEntregaDeseada(fe);
            }
        }
    }

}
