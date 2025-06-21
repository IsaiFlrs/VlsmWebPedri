<%-- 
    Document   : resultado
    Created on : 16 jun 2025, 10:40:56
    Author     : Isai Flores
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="com.calculadoravlsm.vlsm.Subred"%>
<%@page import="java.util.List"%>
<!DOCTYPE html>
<html>
    <head>
        <meta charset="UTF-8">
        <title>Resultado VLSM</title>
        <style>
            body {
                font-family: Arial, sans-serif;
                background-color: #ffffff;
                color: #333;
                padding: 20px;
            }
            h1 {
                color: red;
                text-align: center;
            }
            table {
                width: 100%;
                border-collapse: collapse;
                margin-top: 20px;
            }
            th, td {
                border: 1px solid #ccc;
                padding: 10px;
                text-align: center;
            }
            th {
                background-color: #f8d7da;
            }
            .botones {
                margin-top: 20px;
                text-align: center;
            }
            .botones form {
                display: inline-block;
                margin: 0 10px;
            }
            .botones input {
                background-color: red;
                color: white;
                padding: 8px 16px;
                border: none;
                cursor: pointer;
                border-radius: 5px;
            }
            .botones input:hover {
                background-color: darkred;
            }
        </style>
    </head>
    <body>

        <h1>Resultado de Subneteo VLSM</h1>

        <%
            List<Subred> subredes = (List<Subred>) session.getAttribute("resultadoVLSM");
            if (subredes != null && !subredes.isEmpty()) {
        %>
        <table>
            <tr>
                <th>Hosts solicitados</th>
                <th>IP de Red</th>
                <th>Máscara</th>
                <th>Primera IP</th>
                <th>Última IP</th>
                <th>Broadcast</th>  
            </tr>
            <%
                for (Subred s : subredes) {
            %>
            <tr>
                <td><%= s.getHostsRequeridos() %></td>
                <td><%= s.getIpDecimal() %></td>
                <td>/ <%= s.getMascara() %></td>
                <td><%= s.getPrimeraIp() %></td>
                <td><%= s.getUltimaIp() %></td>
                <td><%= s.getBroadcast() %></td>
            </tr>
            <%
                }
            %>
        </table>

        <div class="botones">
            <form action="ExportadorPDFResumen" method="post">
                <input type="submit" value="Exportar PDF Resumen">
            </form>
            <form action="ExportadorPDFProceso" method="post">
                <input type="submit" value="Exportar PDF Proceso">
            </form>
        </div>

        <%
            } else {
        %>
        <p>No hay resultados disponibles.</p>
        <%
            }
        %>

    </body>
</html>
