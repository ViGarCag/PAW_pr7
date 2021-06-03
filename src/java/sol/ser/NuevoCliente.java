package sol.ser;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.mail.MessagingException;
import javax.mail.internet.InternetAddress;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import paw.bd.GestorBD;
import paw.model.Cliente;
import paw.model.Direccion;
import paw.model.ExcepcionDeAplicacion;
import paw.util.ReCaptchaException;
import paw.util.ReCaptchaValidator;
import paw.util.UtilesString;
import paw.util.Validacion;
import paw.util.mail.DatosCorreo;
import paw.util.mail.GestorCorreo;
import paw.util.mail.conf.ConfiguracionCorreo;
import paw.util.servlet.UtilesServlet;


public class NuevoCliente extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        RequestDispatcher r = request.getRequestDispatcher("/nuevoCliente.jsp");
        r.forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        GestorBD gbd=new GestorBD();
        request.setCharacterEncoding("UTF-8");
        String nombre=request.getParameter("login");
        String contraseña=request.getParameter("pwd");
        String repitaContraseña=request.getParameter("rpwd");
        Cliente cliente = (Cliente) UtilesServlet.populateBean("paw.model.Cliente", request );
        Direccion direccion2=(Direccion) UtilesServlet.populateBean("paw.model.Direccion", request);
        cliente.setDireccion(direccion2);
        try {
            int privacidadOK = 0;
            try {
                privacidadOK = Integer.parseInt(request.getParameter("privacidad"));
            } catch (NumberFormatException e) {
                //e.printStackTrace();
                privacidadOK=0;
            }
            List<String> validacion = valida(cliente, nombre, contraseña, repitaContraseña, privacidadOK, gbd);
            ReCaptchaValidator Captcha = new ReCaptchaValidator("6Le3vM8aAAAAAAOrJqsqzW0Zc3uoVAnGtyOfy9Pg", "6Le3vM8aAAAAAJIeFx4KiEIPvUlPf0Yn-UllIcvQ");
            if(!UtilesString.isVacia(request.getParameter("g-recaptcha-response"))){
            if (!validacion.isEmpty() && Captcha.verifyResponse(request)) {
                               
                request.setAttribute("login", nombre);
                request.setAttribute("privacidad",privacidadOK);
                request.setAttribute("validacion", validacion);
                request.setAttribute("cliente",cliente);
                RequestDispatcher r = request.getRequestDispatcher("/nuevoCliente.jsp");
                
                r.forward(request, response);
                
            }
            else{
                HttpSession sesion=request.getSession();
                Cliente cliente2=gbd.insertaCliente(cliente, nombre, contraseña);
                //TRABAJO
                String mensaje = "Bienvenido a electrosa.com\n" +
                    "Es un placer para nosotros tenerle como cliente. Visite nuestra web en la dirección:\n"+
                        request.getScheme() +"://" +request.getServerName()+":"+ request.getLocalPort() + request.getContextPath();
                String FROM = ConfiguracionCorreo.getDefault().getUser();
                DatosCorreo mail2 = new DatosCorreo(FROM, cliente.getEmail(), "Registro completado", mensaje);
                mail2.setFrom(new InternetAddress(FROM,"Electrosa"));
                mail2.setMimeType("text/html;charset=UTF-8");
                mail2.setCharset("charset=UTF-8");
                
                GestorCorreo.envia(mail2, ConfiguracionCorreo.getDefault());
                //FIN PARTE TRABAJO
                sesion.setAttribute("cliente", cliente2);
                response.sendRedirect("clientes/AreaCliente");
            }
        }else{
                validacion.add("Introduzca la validación del Captcha");
                request.setAttribute("login", nombre);
                request.setAttribute("privacidad",privacidadOK);
                request.setAttribute("validacion", validacion);
                request.setAttribute("cliente",cliente);
                RequestDispatcher r = request.getRequestDispatcher("/nuevoCliente.jsp");
                
                r.forward(request, response);
            }
        }
        catch (ExcepcionDeAplicacion ex) {
            Logger.getLogger(NuevoCliente.class.getName()).log(Level.SEVERE, null, ex);
            request.setAttribute("link", request.getContextPath() + "/index.html");
            throw new ServletException(ex);
        } catch (MessagingException ex) {
            Logger.getLogger(NuevoCliente.class.getName()).log(Level.SEVERE, null, ex);
            request.setAttribute("link", request.getContextPath() + "/index.html");
            throw new ServletException(ex);
        } catch (ReCaptchaException ex) {
            Logger.getLogger(NuevoCliente.class.getName()).log(Level.SEVERE, null, ex);
            request.setAttribute("link", request.getContextPath() + "/index.html");
            throw new ServletException(ex);
        }
        
    }

 /**
   * Realiza las validaciones para los campos del formulario de registro de
   * nuevo cliente
   *
   * @param cli objeto paw.model.Cliente con los datos leídos del formulario
   * @param usr valor del campo "nombre de usuario" del formulario
   * @param pwd valor del campo "contraseña" del formulario
   * @param rpwd valor del campo "Repita contraseña" del formulario
   * @param privacidadOK debe tener valor 1 si la casilla de "Política de
   *                     privacidad" está marcada
   * @param gbd objeto GestorBD para ser usado en las comprobaciones que
   *            requieran de conexión a al BD
   * @return Una lista de String con mensajes de error correspondientes a las
   *         reglas de validación que no se cumplen
   * @throws ExcepcionDeAplicacion
   */
  private List<String> valida(Cliente cli,
          String usr,
          String pwd,
          String rpwd,
          int privacidadOK,
          GestorBD gbd) 
		  throws ExcepcionDeAplicacion, IOException {
		  
    List<String> errores = new ArrayList<String>();

    if (UtilesString.isVacia(cli.getNombre())
            || UtilesString.isVacia(cli.getCif())
            || UtilesString.isVacia(cli.getDireccion().getCalle())
            || UtilesString.isVacia(cli.getDireccion().getCiudad())
            || UtilesString.isVacia(cli.getDireccion().getProvincia())
            || UtilesString.isVacia(cli.getDireccion().getCp())
            || UtilesString.isVacia(cli.getEmail())
            || UtilesString.isVacia(usr)
            || UtilesString.isVacia(pwd)
            || UtilesString.isVacia(rpwd)) {
      errores.add("Debes proporcionar valor para todos los campos requeridos");
    }
    
    if (cli.getNombre() != null && cli.getNombre().length() > 50) {
      errores.add("La longitud máxima del nombre son 50 caracteres");      
    }
    
    if (cli.getCif() != null && cli.getCif().length() > 12) {
      errores.add("La longitud máxima del CIF son 12 caracteres");      
    }
    
    if (cli.getTfno() != null && cli.getTfno().length() > 11) {
      errores.add("La longitud máxima del teléfono son 11 caracteres");      
    }
    
    if (cli.getEmail() != null && cli.getEmail().length() > 100) {
      errores.add("La longitud máxima del email son 100 caracteres");      
    }
    
    if (usr != null && usr.length() > 50) {
      errores.add("La longitud máxima del userName son 50 caracteres");      
    }
    
    if (cli.getDireccion().getCalle() != null && cli.getDireccion().getCalle().length() > 50) {
      errores.add("La longitud máxima de la calle son 50 caracteres");      
    }
    
    if (cli.getDireccion().getCiudad() != null && cli.getDireccion().getCiudad().length() > 20) {
      errores.add("La longitud máxima de la ciudad son 20 caracteres");      
    }

    if (privacidadOK != 1) {
      errores.add("Debes leer la cláusula de privacidad y marcar la casilla correspondiente");
    }

    if (!UtilesString.isVacia(pwd) && !UtilesString.isVacia(rpwd) && !pwd.equals(rpwd)) {
      errores.add("Las constraseñas son diferentes");
    }

    if (!UtilesString.isVacia(usr) && usr.contains(" ")) {
      errores.add("El nombre de usuario tiene espacios en blanco");
    }

    if (!UtilesString.isVacia(usr) && gbd.getClienteByUserName(usr) != null) {
      errores.add("Ya existe un usuario en la BD con ese nombre de usuario");
    }

    if (!UtilesString.isVacia(cli.getCif()) && gbd.getClienteByCIF(cli.getCif()) != null) {
      errores.add("Ya existe un usuario en la BD con ese CIF");
      cli.setCif(null);
    }

    if (!UtilesString.isVacia(cli.getEmail()) && !Validacion.isEmailValido(cli.getEmail())) {
      errores.add("El email no parece una dirección de correo válida");
      cli.setEmail(null);
    }

    if (!UtilesString.isVacia(cli.getDireccion().getCp()) && !Validacion.isCPValido(cli.getDireccion().getCp())) {
      errores.add("El CP no parece un código postal válido");
      cli.getDireccion().setCp(null);
    }
    
    
    
    return errores;
  }

}
