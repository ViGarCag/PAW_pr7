/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sol.ser;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import paw.bd.GestorBDPedidos;
import paw.model.ExcepcionDeAplicacion;
import paw.model.Pedido;
import paw.model.PedidoEnRealizacion;

public class CierraPedido extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        GestorBDPedidos gd = new GestorBDPedidos();
        javax.servlet.http.HttpSession sesion = request.getSession();
        paw.model.Cliente cliente = (paw.model.Cliente) sesion.getAttribute("cliente");
        PedidoEnRealizacion carrito = (PedidoEnRealizacion) sesion.getAttribute("pedido");
        String accion = request.getParameter("accion");
        if (sesion.getAttribute("pedidoACerrar") != null) {
            if (accion != null && accion.trim().length() > 0 && (accion.equals("cerrar")) || (accion.equals("cancelar"))) {
                if (accion.equals("cerrar")) {
                    try {
                        Pedido aux = gd.cierraPedido(carrito, cliente.getDireccion());
                        request.setAttribute(carrito.getCodigo(), aux);
                        sesion.removeAttribute("pedidoACerrar");
                        sesion.removeAttribute("pedido");
                        response.sendRedirect(request.getContextPath() + "/clientes/VerPedido?cp=" + carrito.getCodigo());

                    } catch (ExcepcionDeAplicacion ex) {
                        Logger.getLogger(CierraPedido.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    sesion.removeAttribute("pedido");
                }

                if (accion.equals("cancelar")) {
                    sesion.removeAttribute("pedidoACerrar");
                    response.sendRedirect(request.getContextPath() + "/clientes/carrito.jsp");
                }

            } else {
                sesion.removeAttribute("pedidoACerrar");
                response.sendRedirect(request.getContextPath() + "/clientes/carrito.jsp");
            }
        } else {
            request.setAttribute("msg", "La aplicacion no puede determinar el pedido a cerrar");
            request.setAttribute("link", request.getContextPath() + "/clientes/AreaCliente");
            response.sendError(400, "salir de aquí");
        }
    }
}
