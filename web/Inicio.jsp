<%-- 
    Document   : Inicio
    Created on : 24-may-2016, 18:43:58
    Author     : jorge
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>TP5 - Simulacion</title>
    </head>
    <body>
    <center><h1>TP5 - Simulación</h1></center>
        
        <h3>Ejercicio 51</h3>
        <p>A un peaje llegan vehículos respetando una distribución exponencial negativa con media = 2’ y desviación estándar 1’,  
            con la siguiente probabilidad de tipo de vehículo, de tiempo de atención y costo del peaje: </p>
        <table border="1 px"  cellpadding="6px" style="text-align: center">
            <tr>

                <td style="background-color: khaki"><strong>Categoria</strong></td>
               
                <td>1</td>
                <td>2</td>
                <td>3</td>
                <td>4</td>
                <td>5</td>
                
            </tr> 
            <tr>
                <td style="background-color: khaki"><strong>Probabilidad de Vehiculo</strong></td>        
                <td>0,10</td>
                <td>0,50</td>
                <td>0,15</td>
                <td>0,15</td>
                <td>0,10</td>
                
            </tr>
            <tr>
                <td style="background-color: khaki"><strong>Tiempo Atencion</strong></td>        
                <td>30''</td>
                <td>50'' ± 5''</td>
                <td>1' 10'' ± 15''</td>
                <td>1' 50'' ± 20''</td>
                <td>3' ± 30''</td>
                
            </tr>
            <tr>
                <td style="background-color: khaki"><strong>Costo Peaje</strong></td>        
                <td>0</td>
                <td>3</td>
                <td>6</td>
                <td>9</td>
                <td>12</td>
                
            </tr>
            
        </table>
        
        <p>Si la cola supera los 4 vehículos se habilita una nueva cabina de cobro, 
            cuando no hay cola se la deshabilita (siempre hay por lo menos una cabina habilitada), 
            no hay límite en la posibilidad de habilitar cabinas. </p>
<p>Determine: </p>
<p>a)	Número máximo de cabinas habilitadas </p>
<p>b)	Monto recaudado  </p>
<p>c)	Numero máximo de autos en el sistema  </p>



<form method="post" action="${pageContext.request.contextPath}/ServidorCola" name="datos">
    <p>Simular hasta el tiempo: <input name="tiempoSimulacion" value="" type="number" required></p>



    <input align="middle" style="width:100px; height:50px; font-size: 20px;" type="submit" value="Simular" />

</form> 
</body>
</html>

</body>
</html>
