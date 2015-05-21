<%@page pageEncoding="UTF-8" contentType="text/html" %>
<html>
    <head>
        <title>Print</title>
        <script type="text/javascript">
            /**
             * llamar para imprimir..
             *
             **/
            function newContent(){
                window.print();
                window.close();
            }
        </script>
    </head>

    <body>
        <p><%=request.getParameter("m") %></p>
        <script type="text/javascript">newContent();</script>
    </body>
</html>