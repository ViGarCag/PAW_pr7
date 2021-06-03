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
import paw.bd.GestorBDPedidos;
import paw.model.Cliente;
import paw.model.ExcepcionDeAplicacion;
import paw.model.Pedido;
import paw.util.UtilesString;


public class VerPedido extends HttpServlet {
    
    private static GestorBDPedidos gbd = new GestorBDPedidos();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        GestorBDPedidos gbd= new GestorBDPedidos();
        HttpSession sesion=request.getSession();
        Cliente cliente=(Cliente) sesion.getAttribute("cliente");
        String cp=(String)request.getParameter("cp");
        try{
            if(cp != null && cp.trim().length() > 0){
                if(gbd.getPedido(cp)!=null){
                    if(gbd.getPedido(cp).getCliente().getCodigo().equals(cliente.getCodigo())){
                        request.setAttribute("p", gbd.getPedido(cp));
                        RequestDispatcher rd = request.getRequestDispatcher("/clientes/verPedido.jsp");
                        rd.forward(request, response);
                    }else{
                        request.setAttribute("link", "../Salir");
                        response.sendError(403,"Usted no está autorizado para consultar esta información");
                    }
                }else{
                    request.setAttribute("link", "index.html");
                    response.sendError(404,"Codigo de pedido inválido");
                }
            }
            else{
                response.sendRedirect("/clientes/PedidosCliente");
            }
        } catch (ExcepcionDeAplicacion ex) {
            Logger.getLogger(VerPedido.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}


  
