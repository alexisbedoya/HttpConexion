/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import com.google.gson.Gson;
import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author alexi
 */
public class Server extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
   
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            /* TODO output your page here. You may use following sample code. */
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Servlet Server</title>");            
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet Server at " + request.getContextPath() + "</h1>");
            out.println("</body>");
            out.println("</html>");
        }
    }

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
  protected void doGet(HttpServletRequest request,
                HttpServletResponse response) throws ServletException, IOException {
		String action = request.getParameter("accion");
                switch (action){
                    case "entrada":
                        PrintWriter out = response.getWriter();
                        registrar_e(request.getParameter("nombre"),request.getParameter("cedula"),request.getParameter("placa"));
                        String json="{"+"mensaje"+":"+"OK"+"}";
                        out.println(json);
                        
                        break;
                    case "salida":
                        registrar_s(request.getParameter("id"));
                        break;
                        
                    case "listar":
                          listar(request,response);
                        break;
                }
               
}
private void registrar_e(String nombre,String cedula, String placa){
    BD bd = new BD();
    bd.registrarEntrada(nombre, cedula, placa, "1");
   
    //
}
private void registrar_s(String id){
    BD bd = new BD();
    bd.registrarSalida(id);
    
}
private void listar(HttpServletRequest request,HttpServletResponse response)throws IOException{
 BD bd = new BD();
 Gson gson = new Gson();
 String json=gson.toJson(bd.ListarRegistros());
 PrintWriter out = response.getWriter();
 out.println(json);
}
    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
