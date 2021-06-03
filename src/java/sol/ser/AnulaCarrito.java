package sol.ser;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import paw.bd.GestorBDPedidos;
import paw.model.Cliente;
import paw.model.ExcepcionDeAplicacion;
import paw.model.Pedido;
import paw.model.PedidoEnRealizacion;

public class AnulaCarrito extends HttpServlet {

    private static GestorBDPedidos gd = new GestorBDPedidos();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession sesion = request.getSession();
        paw.model.Cliente cliente = (paw.model.Cliente) sesion.getAttribute("cliente");
        PedidoEnRealizacion carrito = (PedidoEnRealizacion) sesion.getAttribute("pedido");
        String accion = request.getParameter("accion");
        if (sesion.getAttribute("pedidoACancelar") != null) {
            if (accion != null && accion.trim().length() > 0 && (accion.equals("cancelar")) || (accion.equals("anular"))) {
                if (accion.equals("anular")) {
                    try {
                        gd.anulaPedido(carrito);
                        sesion.removeAttribute("pedidoACancelar");
                        sesion.removeAttribute("pedido");
                        response.sendRedirect(request.getContextPath() + "/clientes/AreaCliente");
                    } catch (ExcepcionDeAplicacion ex) {
                        Logger.getLogger(CierraPedido.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
                if (accion.equals("cancelar")) {
                    sesion.removeAttribute("pedidoACancelar");
                    response.sendRedirect(request.getContextPath() + "/clientes/carrito.jsp");
                }
                
            }else{
                sesion.removeAttribute("pedidoACancelar");
                response.sendRedirect(request.getContextPath() + "/clientes/carrito.jsp");
            }
        } else {
            request.setAttribute("msg", "La aplicacion no pude determinar el pedido a anular");
            request.setAttribute("link", "/clientes/AreaCliente");
            response.sendError(400, "salir de aquí");
        }
    }

}
