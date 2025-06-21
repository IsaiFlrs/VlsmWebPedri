<%-- 
    Document   : index
    Created on : 16 jun 2025, 10:29:47
    Author     : Isai Flores
--%>

<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
    <head>
        <meta charset="UTF-8">
        <title>Calculadora VLSM</title>
        <style>
            body {
                font-family: Arial, sans-serif;
                background-color: #ffffff;
                color: #333;
                margin: 0;
                padding: 0;
            }

            header {
                background-color: #c0392b;
                color: #fff;
                padding: 1em 0;
                text-align: center;
            }

            .container {
                max-width: 600px;
                margin: 2em auto;
                padding: 2em;
                border: 1px solid #ccc;
                border-radius: 8px;
                background-color: #fdfdfd;
                box-shadow: 0 0 10px rgba(0, 0, 0, 0.1);
            }

            label {
                display: block;
                margin-top: 1em;
                font-weight: bold;
            }

            input[type="text"], textarea {
                width: 100%;
                padding: 0.6em;
                margin-top: 0.3em;
                border: 1px solid #ccc;
                border-radius: 4px;
            }

            button {
                background-color: #e74c3c;
                color: white;
                padding: 0.7em 1.5em;
                border: none;
                border-radius: 4px;
                margin-top: 1.5em;
                cursor: pointer;
                font-size: 1em;
            }

            button:hover {
                background-color: #c0392b;
            }
        </style>
    </head>
    <body>

        <header>
            <h1>Calculadora VLSM</h1>
        </header>

        <div class="container">
            <form action="ControladorVLSM" method="post">
                <label for="ip">Dirección IP Base:</label>
                <input type="text" id="ip" name="ip" placeholder="Ej. 192.168.1.0">

                <label for="mascara">Máscara de Red Base:</label>
                <input type="text" id="mascara" name="mascara" placeholder="Ej. 24">

                <label for="subredes">Tamaños de subredes (una por línea):</label>
                <textarea id="subredes" name="subredes" rows="5" placeholder="Ej. 50&#10;20&#10;10"></textarea>

                <button type="submit">Calcular VLSM</button>
            </form>
        </div>

    </body>
</html>
