/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller;

import Model.CAccessUser;
import Model.CUser;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author Jorge
 */
public class CProcesar extends HttpServlet {

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
       
        String name = request.getParameter("txtName");
        String age = request.getParameter("txtAge");
        String latitude = request.getParameter("txtLatitude");
        String longitude = request.getParameter("txtLongitude");
        
        if(name.equals("") || age.equals("") || latitude.equals("") || longitude.equals("")) {
            
            request.getRequestDispatcher("errorcampos.jsp").forward(request, response);
        }
        else {
            
            int ag = 0;
            float la = 0;
            float lo = 0;
            
            try {
               ag = Integer.parseInt(age);
               la = Float.parseFloat(latitude);
               lo = Float.parseFloat(longitude);
            }
            catch(NumberFormatException e) {
                
                request.getRequestDispatcher("errorcampos.jsp").forward(request, response);
            }
            
            Random rnd = new Random();
            int r = Math.abs(rnd.nextInt());
            CUser user_model = new CUser(r, name, ag, la, lo);
                             
            try {
              CAccessUser user_database = new CAccessUser();
                        
              user_database.insertRecord(r, name, ag, la, lo);
            
            } catch (Exception ex) {
               Logger.getLogger(CProcesar.class.getName()).log(Level.SEVERE, null, ex);
            }
             
            request.getSession().setAttribute("user", user_model);
            request.getRequestDispatcher("mapa.jsp").forward(request, response);
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
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
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
