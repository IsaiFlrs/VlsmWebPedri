package com.calculadoravlsm.vlsm;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@WebServlet("/ControladorVLSM") // Versión simplificada
public class ControladorVLSM extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        try {
            // 1. Obtener y validar parámetros
            String ipBase = validarParametro(request, "ip");
            String mascara = validarParametro(request, "mascara");
            String subredesParam = validarParametro(request, "subredes");

            // 2. Procesar subredes
            List<Integer> tamanosSubredes = procesarSubredes(subredesParam);

            // 3. Calcular VLSM
            List<Subred> resultado = new CalculadoraVLSM()
                .calcular(ipBase, Integer.parseInt(mascara), tamanosSubredes);

            // 4. Preparar atributos para la vista
            HttpSession session = request.getSession();
            session.setAttribute("resultadoVLSM", resultado);
            session.setAttribute("ipBase", ipBase);
            session.setAttribute("mascara", mascara);

            // 5. Redirección definitiva (solución para Render)
            response.sendRedirect(request.getContextPath() + "/resultado.jsp");

        } catch (Exception e) {
            request.setAttribute("error", "Error: " + e.getMessage());
            request.getRequestDispatcher("/index.jsp").forward(request, response);
        }
    }

    private String validarParametro(HttpServletRequest request, String paramName) 
            throws ServletException {
        String value = request.getParameter(paramName);
        if (value == null || value.trim().isEmpty()) {
            throw new ServletException("El campo " + paramName + " es requerido");
        }
        return value.trim();
    }

    private List<Integer> procesarSubredes(String subredesParam) 
            throws ServletException {
        String[] lineas = subredesParam.split("\\r?\\n");
        List<Integer> resultados = new ArrayList<>();
        
        for (String linea : lineas) {
            try {
                resultados.add(Integer.parseInt(linea.trim()));
            } catch (NumberFormatException e) {
                throw new ServletException("Valor inválido: " + linea);
            }
        }
        
        return resultados;
    }
}
