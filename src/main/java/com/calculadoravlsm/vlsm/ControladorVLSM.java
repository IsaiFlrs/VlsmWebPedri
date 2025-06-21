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
        
        String ipBase = request.getParameter("ip");
        String mascara = request.getParameter("mascara");
        String[] subredesTexto = request.getParameter("subredes").split("\\r?\\n");

        List<Integer> tamamosSubredes = new ArrayList<>();
        for (String linea : subredesTexto) {
            try {
                tamamosSubredes.add(Integer.parseInt(linea.trim()));
            } catch (NumberFormatException e) {
                // Ignora líneas que no son números
            }
        }

        // Procesar el cálculo VLSM
        CalculadoraVLSM calculadora = new CalculadoraVLSM();
        List<Subred> resultado = calculadora.calcular(ipBase, Integer.parseInt(mascara), tamamosSubredes);

        // Guardar en la sesión para usar en resultado.jsp
        HttpSession sesion = request.getSession();
        sesion.setAttribute("resultadoVLSM", resultado);
        sesion.setAttribute("ipBase", ipBase);
        sesion.setAttribute("mascara", mascara);

        // Redirigir a la vista de resultados
        request.getRequestDispatcher("resultado.jsp").forward(request, response);
    }
}
