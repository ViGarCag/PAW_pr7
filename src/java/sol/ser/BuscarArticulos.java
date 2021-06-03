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
import paw.bd.CriteriosArticulo;
import paw.bd.GestorBD;
import paw.bd.Paginador;
import paw.model.ExcepcionDeAplicacion;

public class BuscarArticulos extends HttpServlet {

    private static int tamanioPagina = 15;
    private static GestorBD gd= new GestorBD();

    @Override
    public void init() throws ServletException {
        super.init();
        try {
            tamanioPagina = Integer.parseInt(this.getInitParameter("tamanioPagina"));
        } catch (Exception ex) {
            Logger.getLogger(BuscarArticulos.class.getName()).log(Level.WARNING, null, ex);
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String tipo = request.getParameter("tipo");
        String fabricante = request.getParameter("fabricante");
        String precio = request.getParameter("precio");
        //TRABAJO BUSQUEDA POR NOMBRE Y CÓDIGO
        String nombre = request.getParameter("nombre");
        String codigo = request.getParameter("codigo");
        try {
            int pagina = 0;
            if (tipo != null && tipo.trim().length() > 0
                    || fabricante != null && fabricante.trim().length() > 0
                    || precio != null && precio.trim().length() > 0
                    || nombre != null && nombre.trim().length() > 0
                    || codigo != null && codigo.trim().length() > 0) {
                CriteriosArticulo criterios = new CriteriosArticulo();
                criterios.setTipo(tipo);
                criterios.setFabricante(fabricante);
                criterios.setPrecio(precio);
                criterios.setNombre(nombre);
                criterios.setCodigo(codigo);
                Paginador paginador = this.gd.getPaginadorArticulos(criterios, tamanioPagina);
                if (request.getParameter("p") != null && request.getParameter("p").trim().length() != 0) {
                    try {
                        pagina = Integer.parseInt(request.getParameter("p"));
                    } catch (NumberFormatException e) {
                        e.printStackTrace();
                    }
                    if (paginador.getNumPaginas() > 0) {
                        if (pagina > paginador.getNumPaginas()) {
                            pagina = paginador.getNumPaginas();
                        }else{
                            if (pagina < 1) {
                                pagina = 1;
                            }
                        }
                    }

                } else {
                    pagina = 1;
                }
                //TRABAJO
                if (gd.getArticulos(criterios, pagina, tamanioPagina).size() == 1) {
                    RequestDispatcher rd = request.getRequestDispatcher(("FichaArticulo?cart=" + gd.getArticulos(criterios, pagina, tamanioPagina).get(0).getCodigo()));
                    rd.forward(request, response);
                }
                //FIN PARTE TRABAJO
                request.setAttribute("tipo", tipo);
                request.setAttribute("tiposArticulos", gd.getTiposArticulos());
                request.setAttribute("fabricante", fabricante);
                request.setAttribute("fabricantes", gd.getFabricantes());
                request.setAttribute("precio", precio);
                request.setAttribute("criterios", criterios);
                request.setAttribute("pgdr", paginador);
                request.setAttribute("p", pagina);
                request.setAttribute("articulos", gd.getArticulos(criterios, pagina, tamanioPagina));
                RequestDispatcher r = request.getRequestDispatcher("/catalogo.jsp");
                r.forward(request, response);
            } else {
                request.setAttribute("tiposArticulos", gd.getTiposArticulos());
                request.setAttribute("fabricantes", gd.getFabricantes());
                RequestDispatcher r = request.getRequestDispatcher("catalogo.jsp");
                r.forward(request, response);
            }

        } catch (ExcepcionDeAplicacion ex) {
            Logger.getLogger(BuscarArticulos.class.getName()).log(Level.SEVERE, null, ex);
            request.setAttribute("enlaceSalir", "index.html");
            throw new ServletException(ex);
        }

    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

    }

}
