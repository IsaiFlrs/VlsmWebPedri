package com.calculadoravlsm.vlsm;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@WebServlet(name = "ControladorVLSM", urlPatterns = {"/ControladorVLSM"})
public class ControladorVLSM extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            // 1. Obtener parámetros del formulario
            String ipBase = request.getParameter("ip");
            String mascara = request.getParameter("mascara");
            String subredesParam = request.getParameter("subredes");

            // Validación básica
            if (ipBase == null || ipBase.isEmpty() || 
                mascara == null || mascara.isEmpty() ||
                subredesParam == null || subredesParam.isEmpty()) {
                throw new ServletException("Todos los campos son requeridos");
            }

            // 2. Procesar subredes
            String[] subredesTexto = subredesParam.split("\\r?\\n");
            List<Integer> tamanosSubredes = new ArrayList<>();

            for (String linea : subredesTexto) {
                try {
                    tamanosSubredes.add(Integer.parseInt(linea.trim()));
                } catch (NumberFormatException e) {
                    throw new ServletException("Formato inválido en tamaños de subred: " + linea);
                }
            }

            // 3. Realizar cálculo VLSM
            CalculadoraVLSM calculadora = new CalculadoraVLSM();
            List<Subred> resultado = calculadora.calcular(
                ipBase, 
                Integer.parseInt(mascara), 
                tamanosSubredes
            );

            // 4. Preparar respuesta
            request.setAttribute("resultadoVLSM", resultado);
            request.setAttribute("ipBase", ipBase);
            request.setAttribute("mascara", mascara);

            // 5. Redirección definitiva para Render
            String contextPath = request.getContextPath();
            
            // Opción A: Redirección HTTP (recomendada para Render)
            response.sendRedirect(contextPath + "/resultado.jsp");
            
        } catch (Exception e) {
            // Manejo de errores mejorado
            request.setAttribute("error", e.getMessage());
            request.getRequestDispatcher("/error.jsp").forward(request, response);
        }
    }
}
