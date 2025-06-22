package com.calculadoravlsm.vlsm;

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.*;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;

import java.io.IOException;
import java.util.List;

@WebServlet(name = "ExportadorPDFResumen", urlPatterns = {"/ExportadorPDFResumen"})
public class ExportadorPDFResumen extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        response.setContentType("application/pdf");
        response.setHeader("Content-Disposition", "attachment; filename=\"ResumenVLSM.pdf\"");

        try {
            Document documento = new Document();
            PdfWriter.getInstance(documento, response.getOutputStream());
            documento.open();

            Font tituloFont = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 14);
            Font cabeceraFont = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 11);
            Font normalFont = FontFactory.getFont(FontFactory.HELVETICA, 11);

            // Título
            Paragraph titulo = new Paragraph("Resumen de Subredes VLSM", tituloFont);
            titulo.setAlignment(Element.ALIGN_CENTER);
            titulo.setSpacingAfter(15);
            documento.add(titulo);

            // Obtener lista de subredes
            HttpSession session = request.getSession();
            List<Subred> subredes = (List<Subred>) session.getAttribute("resultadoVLSM");

            // Tabla de resultados
            PdfPTable tabla = new PdfPTable(5);
            tabla.setWidthPercentage(100);
            tabla.setSpacingBefore(10);
            tabla.setWidths(new float[]{3, 2, 3, 3, 3});

            tabla.addCell(crearCelda("IP de Red", cabeceraFont));
            tabla.addCell(crearCelda("Máscara", cabeceraFont));
            tabla.addCell(crearCelda("Primera IP", cabeceraFont));
            tabla.addCell(crearCelda("Última IP", cabeceraFont));
            tabla.addCell(crearCelda("Broadcast", cabeceraFont));

            for (Subred s : subredes) {
                tabla.addCell(crearCelda(s.getIpDecimal(), normalFont));
                tabla.addCell(crearCelda("/" + s.getMascara(), normalFont));
                tabla.addCell(crearCelda(s.getPrimeraIp(), normalFont));
                tabla.addCell(crearCelda(s.getUltimaIp(), normalFont));
                tabla.addCell(crearCelda(s.getBroadcast(), normalFont));
            }

            documento.add(tabla);
            documento.close();

        } catch (DocumentException e) {
            throw new IOException("Error al generar PDF: " + e.getMessage());
        }
    }

    private PdfPCell crearCelda(String texto, Font fuente) {
        PdfPCell celda = new PdfPCell(new Phrase(texto, fuente));
        celda.setHorizontalAlignment(Element.ALIGN_CENTER);
        celda.setVerticalAlignment(Element.ALIGN_MIDDLE);
        celda.setPadding(6);
        return celda;
    }
}
