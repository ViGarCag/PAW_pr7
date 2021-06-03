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
import paw.bd.GestorBD;
import paw.model.Cliente;
import paw.model.ExcepcionDeAplicacion;

public class Login extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {                   
        request.getRequestDispatcher("login.jsp").forward(request, response);
        return;
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        GestorBD gbd = new GestorBD();
        request.setCharacterEncoding("UTF-8");
        String nombre = request.getParameter("usr");
        String contraseña = request.getParameter("pwd");
        try {
            if (nombre != null && nombre.trim().length() > 0
                    && contraseña != null && contraseña.trim().length() > 0) {
                if (gbd.comprobarLogin(nombre, contraseña)) {
                    HttpSession sesion = request.getSession();
                    Cliente cliente = gbd.getClienteByUserName(nombre);
                    sesion.setAttribute("cliente", cliente);
                    String returnURL = (String) sesion.getAttribute("returnURL");
                    if (returnURL != null) {
                        sesion.removeAttribute("returnURL");
                        response.sendRedirect(returnURL);
                    }else{
                        response.sendRedirect("clientes/AreaCliente");
                    }                  
                    return;
                } else {
                    request.setAttribute("aviso", "usuario o contraseña incorrecta");
                    request.setAttribute("usr", nombre);
                    RequestDispatcher rd = request.getRequestDispatcher(("login.jsp"));
                    rd.forward(request, response);
                }
            } else {
                request.setAttribute("aviso", "usuario o contraseña no introducidos");
                request.setAttribute("usr", nombre);
                RequestDispatcher rd = request.getRequestDispatcher(("/login.jsp"));
                rd.forward(request, response);
            }
        } catch (ExcepcionDeAplicacion ex) {
            Logger.getLogger(BuscarArticulos.class.getName()).log(Level.SEVERE, null, ex);
            request.setAttribute("link", "index.html");
            throw new ServletException(ex);
        }

    }
}
