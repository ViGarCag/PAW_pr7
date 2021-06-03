package sol.ser;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import paw.bd.GestorBDPedidos;
import paw.model.Cliente;
import paw.model.ExcepcionDeAplicacion;
import paw.model.PedidoEnRealizacion;

public class Carrito extends HttpServlet {

   
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        GestorBDPedidos gb= new GestorBDPedidos();
        HttpSession sesion=request.getSession();
        Cliente cliente=(Cliente) sesion.getAttribute("cliente");
        PedidoEnRealizacion pedido=(PedidoEnRealizacion) sesion.getAttribute("pedido");
        try{
           if(pedido!=null){
               RequestDispatcher rd = request.getRequestDispatcher("/clientes/carrito.jsp");
               rd.forward(request, response);           
           } 
           else{
               pedido=gb.getPedidoEnRealizacion(cliente.getCodigo());
               sesion.setAttribute("pedido", pedido);
               RequestDispatcher rd = request.getRequestDispatcher("/clientes/carrito.jsp");
               rd.forward(request, response);
           }
        } catch (ExcepcionDeAplicacion ex) {
            Logger.getLogger(Carrito.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }
}