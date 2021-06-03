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
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import paw.model.Cliente;
import paw.model.ExcepcionDeAplicacion;
import paw.bd.GestorBDPedidos;


public class PedidosCliente extends HttpServlet {

    private static GestorBDPedidos gp= new GestorBDPedidos();
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession sesion =request.getSession();
        Cliente cliente=(Cliente) sesion.getAttribute("cliente");
        
        try{
            request.setAttribute("nombre", cliente.getNombre());
            request.setAttribute("pedidosPendiente", gp.getPedidosPendientes(cliente.getCodigo()));
            request.setAttribute("pedidosCompletados", gp.getPedidosCompletados(cliente.getCodigo()));
            request.setAttribute("pedidosAnulados", gp.getPedidosAnulados(cliente.getCodigo()));
            if(sesion.getAttribute("pedido")==null){
                sesion.setAttribute("pedido", gp.getPedidoEnRealizacion(cliente.getCodigo()));
            }
            
            request.getRequestDispatcher("/clientes/pedidosCliente.jsp").forward(request, response);
            
        } catch (ExcepcionDeAplicacion ex) {
            Logger.getLogger(PedidosCliente.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }
}

