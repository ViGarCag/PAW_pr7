package sol.ser;

import java.io.IOException;
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

public class ConfirmaAnulacion extends HttpServlet {

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        GestorBDPedidos gbd= new GestorBDPedidos();
        HttpSession sesion=request.getSession();
        Cliente cliente=(Cliente) sesion.getAttribute("cliente");
        String cp=request.getParameter("cp");
        System.out.println("hola");
        try{
            Pedido p= gbd.getPedido(cp);
            if(cp != null && cp.trim().length() > 0){
             if(p!=null){
                    if(p.getCliente().getCodigo().equals(cliente.getCodigo())){
                        
                    }else{
                        request.setAttribute("link", "../Salir");
                        response.sendError(403,"no tiene permiso para anular ese pedido");
                    }
                }else{
                    request.setAttribute("link", "index.html");
                    response.sendError(404,"Codigo de pedido invalido");
                }
             if(p.isCursado()){
                request.setAttribute("link", "index.html");
                response.sendError(400,"El pedido esta cursado.No puede anularlo");
             }
             gbd.anulaPedido(p); 
             response.sendRedirect(request.getContextPath()+"/clientes/PedidosCliente");
                         
            }
            else{
               request.setAttribute("link", "index.html");
               response.sendError(404,"Codigo de pedido invalido");
            }
        } catch (ExcepcionDeAplicacion ex) {
            Logger.getLogger(ConfirmaAnulacion.class.getName()).log(Level.SEVERE, null, ex);
        }   
            
    }

   

}