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
import paw.model.ExcepcionDeAplicacion;
import paw.model.PedidoEnRealizacion;

public class Salir extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession sesion = request.getSession(false);
        PedidoEnRealizacion carrito = (PedidoEnRealizacion) sesion.getAttribute("pedido");
        GestorBDPedidos gd = new GestorBDPedidos();
        if (sesion != null) {
            if (carrito != null) {
                try {
                    gd.grabaPedidoEnRealizacion(carrito);
                } catch (ExcepcionDeAplicacion ex) {
                    Logger.getLogger(Salir.class.getName()).log(Level.SEVERE, null, ex);
                }
                sesion.invalidate();
            }else{
                sesion.invalidate();
            }
        }
        response.sendRedirect("index.html");
        return;
    }
}
