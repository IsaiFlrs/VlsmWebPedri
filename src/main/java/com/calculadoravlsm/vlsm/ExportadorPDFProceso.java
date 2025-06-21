package com.calculadoravlsm.vlsm;

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.*;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

import java.io.IOException;
import java.util.List;

@WebServlet(name = "ExportadorPDFProceso", urlPatterns = {"/ExportadorPDFProceso"})
public class ExportadorPDFProceso extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        response.setContentType("application/pdf");
        response.setHeader("Content-Disposition", "attachment; filename=\"ProcesoVLSM.pdf\"");

        try {
            Document doc = new Document();
            PdfWriter.getInstance(doc, response.getOutputStream());
            doc.open();

            Font titleFont = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 14);
            Font normalFont = FontFactory.getFont(FontFactory.COURIER, 11); // para alinear como en un esquema

            // Título
            doc.add(new Paragraph("Cálculo VLSM - Proceso Detallado", titleFont));
            doc.add(new Paragraph(" "));

            // Obtener datos de sesión
            HttpSession session = request.getSession();
            String ipBase = (String) session.getAttribute("ipBase");
            String mascara = (String) session.getAttribute("mascara");
            List<Subred> subredes = (List<Subred>) session.getAttribute("resultadoVLSM");

            // Red Padre
            doc.add(new Paragraph("Red Padre: " + ipBase + "/" + mascara, normalFont));
            doc.add(new Paragraph(ipBase + " = " + ipABinario(ipBase), normalFont));
            doc.add(new Paragraph(" "));

            // Subredes
            int num = 0;
            for (Subred s : subredes) {
                doc.add(new Paragraph("Subred " + num++ + ": " + s.getIpDecimal() + "/" + s.getMascara(), normalFont));
                doc.add(new Paragraph("Binario : " + s.getIpBinaria(), normalFont));
                doc.add(new Paragraph("Rango   : " + s.getPrimeraIp() + " - " + s.getUltimaIp(), normalFont));
                doc.add(new Paragraph("Broadcast: " + s.getBroadcast(), normalFont));
                doc.add(new Paragraph(" "));
            }

            doc.close();

        } catch (DocumentException e) {
            throw new IOException("Error al generar PDF: " + e.getMessage());
        }
    }

    private String ipABinario(String ip) {
        String[] partes = ip.split("\\.");
        StringBuilder bin = new StringBuilder();
        for (int i = 0; i < 4; i++) {
            bin.append(String.format("%8s", Integer.toBinaryString(Integer.parseInt(partes[i]))).replace(' ', '0'));
            if (i < 3) bin.append(".");
        }
        return bin.toString();
    }
}
