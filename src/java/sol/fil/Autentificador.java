/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sol.fil;

import java.io.IOException;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import paw.model.Cliente;

/**
 *
 * @author ramartc
 */
public class Autentificador implements Filter {

    private static final boolean debug = true;

    private FilterConfig filterConfig = null;

    /* public Autenticador() {
    }    */
    public void doFilter(ServletRequest request, ServletResponse response,
            FilterChain chain)
            throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse resp = (HttpServletResponse) response;
        HttpSession sesion = req.getSession();
        Cliente cliente = (Cliente) sesion.getAttribute("cliente");
        if (cliente == null) {
            String returnURL = req.getRequestURL().toString();
            if (req.getQueryString() != null) {
                returnURL = returnURL + "?" + req.getQueryString();
            }
            sesion.setAttribute("returnURL", returnURL);
            resp.sendRedirect(req.getContextPath() + "/Login");
        } else {
            chain.doFilter(request, response);
        }
    }

    public void destroy() {
    }

    public void init(FilterConfig filterConfig) {

    }

}
