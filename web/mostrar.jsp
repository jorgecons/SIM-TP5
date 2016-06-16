<%-- 
    Document   : mostrar
    Created on : 25-may-2016, 22:12:09
    Author     : jorge
--%>

<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Resultado</title>
    </head>
    <body>
    <center><h1 >TP5 - Simulación de Colas</h1></center>

    <h3>El monto total recaudado en ${tiempo} segundos, es de $${monto}.</h3>
    <h3>Se habilitaron un total de ${cabinas} cabinas.</h3>
    <h3>La maxima cantidad de autos en el sistema es de ${maximoTam}.</h3>


    <%-- INICIO TABLA--%>      
    <table  class="table" border="1" style="width:100% ; text-align: center">
        <%-- INICIO CABECERA TABLA--%>  
        <thead style="background-color: #D8D8D8" >
        <th>Vuelta</th>
        <th>Evento Actual</th>
        <th>Tiempo</th>

        <th>rnd LLegada Auto</th> 
        <th>Llegada entre Autos</th> 
        <th style="background-color: chartreuse">Tiempo proxima Llegada</th> 
        <th>rnd Categoria Auto</th> 
        <th>Categoria Auto</th> 
        <th>rnd Tiempo Atencion</th> 
        <th>Tiempo Atencion</th> 
            <c:forEach  var="c" begin ="1" end="${cabinas}">
            <th style="background-color: chartreuse">Tiempo Fin Atencion</th>
            </c:forEach>
        <th>rnd Log Sistema</th> 
        <th>Porcentaje Llenado</th>
        <th>Tiempo hasta Log</th> 
        <th style="background-color: chartreuse">Tiempo Log Sistema</th>
        <th>Tiempo Fin Log</th> 
            <c:forEach  var="c" begin ="1" end="${cabinas}">
            <th>Cabina - Estado</th> 
            <th>Cola</th>
            </c:forEach>

        <th>Monto Total</th>
        <th>Numero de Cabinas</th>


        <th colspan="${maximoTam*3}">Autos</th>


    </thead>
    <%-- INICIO CUERPO/FILA TABLA--%>  

    <c:forEach items="${vectorEstadoAux}" var="p"  varStatus="loop">
        <tbody>
        <td>${loop.index}</td>
        <c:if test="${p.vec.eventoActual == 0}">
            <td>Inicializo</td>

        </c:if>
        <c:if test="${p.vec.eventoActual == 1}">
            <td>Llegada Auto</td>

        </c:if>
        <c:if test="${p.vec.eventoActual == 2}">
            <td>Fin Atencion</td>

        </c:if>

        <c:if test="${p.vec.eventoActual == 3}">
            <td>Log Sistema</td>

        </c:if>
        <c:if test="${p.vec.eventoActual == 4}">
            <td>Fin Log Sistema</td>

        </c:if>

        <td> <fmt:formatNumber value="${p.vec.tiempoActual}" maxFractionDigits="3"></fmt:formatNumber> </td>
        <c:if test="${p.vec.rndLlegadaAuto==0}">
            <td></td>
            <td></td>
        </c:if>
        <c:if test="${p.vec.rndLlegadaAuto!=0}">
            <td><fmt:formatNumber value="${p.vec.rndLlegadaAuto}" maxFractionDigits="3"></fmt:formatNumber> </td>
            <td> <fmt:formatNumber value="${p.vec.tiempoEntreLlegada}" maxFractionDigits="3"></fmt:formatNumber></td>
        </c:if>
        <c:if test="${p.vec.siguienteEvento==1}">
            <td style="background-color: chartreuse"> <fmt:formatNumber value="${p.vec.tiempoProximaLlegada}" maxFractionDigits="3"></fmt:formatNumber></td>
        </c:if>
        <c:if test="${p.vec.siguienteEvento!=1}">
            <td> <fmt:formatNumber value="${p.vec.tiempoProximaLlegada}" maxFractionDigits="3"></fmt:formatNumber></td>
        </c:if>
        <c:if test="${p.vec.rndCatAuto==0}">
            <td></td>
        </c:if>
        <c:if test="${p.vec.rndCatAuto!=0}">
            <td> <fmt:formatNumber value="${p.vec.rndCatAuto}" maxFractionDigits="3"></fmt:formatNumber></td>
        </c:if>

        <td> ${p.vec.auto.categoria}</td>

        <c:if test="${p.vec.rndAtencion==0}">
            <td></td>
            <td></td>
        </c:if>
        <c:if test="${p.vec.tiempoDemoraAtencion==30}">
            <c:if test="${p.vec.rndAtencion!=0}">
                <td></td>
            </c:if>
            <td> <fmt:formatNumber value="${p.vec.tiempoDemoraAtencion}" maxFractionDigits="3"></fmt:formatNumber></td>
        </c:if>
        <c:if test="${p.vec.tiempoDemoraAtencion!=30}">
            <c:if test="${p.vec.rndAtencion!=0}">
                <td> <fmt:formatNumber value="${p.vec.rndAtencion}" maxFractionDigits="3"></fmt:formatNumber></td>
                <td> <fmt:formatNumber value="${p.vec.tiempoDemoraAtencion}" maxFractionDigits="3"></fmt:formatNumber></td>
            </c:if>
        </c:if>


        <c:forEach items="${p.tiempos}" var="t" varStatus="loop">

            <c:set var="tiempoFin" scope="session" value="${loop.index+1}"/>


            <c:if test="${p.vec.numeroCabina==loop.index}">
                <c:if test="${p.vec.siguienteEvento==2}">
                    <td style="background-color: chartreuse"> 
                        <fmt:formatNumber value="${t}" maxFractionDigits="3"></fmt:formatNumber>
                        </td>
                </c:if>
                <c:if test="${p.vec.siguienteEvento!=2}">
                    <td> 
                        <fmt:formatNumber value="${t}" maxFractionDigits="3"></fmt:formatNumber>
                        </td>
                </c:if>
            </c:if>
            <c:if test="${p.vec.numeroCabina!=loop.index}">
                <td><fmt:formatNumber value="${t}" maxFractionDigits="3"></fmt:formatNumber></td>
            </c:if>


        </c:forEach>
        <%--COMPLETO CON LO QUE FALTA los tiempos fin de Atencion --%>
        <c:forEach  var="t" begin="${tiempoFin}" end="${cabinas-1}" varStatus="loop">
            <c:if test="${tiempoFin != cabinas}">
                <td style="background-color: yellow">Vacio</td>

            </c:if>
        </c:forEach> 



        <%-- SETEO EN CERO --%>        
        <c:set var="CabinaActual" scope="session" value="${0}"/>
        <c:set var="tiempoFin" scope="session" value="${0}"/>

        <%-- TP N6 --%>
        <c:if test="${p.vec.rndLog == 0}">
            <td></td>
            <td></td>
            <td></td>
        </c:if>
        <c:if test="${p.vec.rndLog != 0}">
            <td> <fmt:formatNumber value="${p.vec.rndLog}" maxFractionDigits="3"></fmt:formatNumber></td>
            <td> ${p.vec.porcentajeLlenado}</td>
            <td> ${p.vec.tiempoHastaLog}</td>
        </c:if>
        <c:if test="${p.vec.siguienteEvento == 3}">
            <td style="background-color: chartreuse"> ${p.vec.tiempoLog}</td>

        </c:if>
        <c:if test="${p.vec.siguienteEvento != 3}">
            <td> ${p.vec.tiempoLog}</td>

        </c:if>
        <c:if test="${p.vec.tiempoFinLog == 0}">
            <td></td>
        </c:if>
        <c:if test="${p.vec.tiempoFinLog != 0}">


            <c:if test="${p.vec.siguienteEvento == 4}">
                <td style="background-color: chartreuse"> ${p.vec.tiempoFinLog}</td>

            </c:if>
            <c:if test="${p.vec.siguienteEvento != 4}">
                <td> ${p.vec.tiempoFinLog}</td>

            </c:if>
        </c:if>









        <%-- ESTADO Y COLA DE LA CABINA --%>
        <c:forEach items="${p.cabinas}" var="t" varStatus="loop">
            <c:set var="CabinaActual" scope="session" value="${loop.index+1}"/>

            <c:if test="${t !=null}">
                <c:if test="${t.estado == 'LIBRE'}">
                    <td style="background-color: lightgreen"> ${t.estado}</td>
                </c:if>
                <c:if test="${t.estado == 'OCUPADO'}">
                    <td style="background-color: lightcoral"> ${t.estado}</td>
                </c:if>
                <c:if test="${t.estado == 'BLOQUEADO'}">
                    <td style="background-color: dodgerblue"> ${t.estado}</td>
                </c:if>


                <td> ${t.sizeCola}</td>
            </c:if>

        </c:forEach>

        <%--COMPLETO CON LO QUE FALTA estado y colas --%>       
        <c:forEach  var="t" begin="${CabinaActual}" end="${cabinas-1}" varStatus="loop">
            <c:if test="${tiempoFin != cabinas}">
                <td style="background-color: red">Deshabilitado</td>
                <td>Sin cola</td>


            </c:if>
        </c:forEach>     
        <td>${p.montoTotal}</td>
        <td>${p.cabinas.size()}</td>

        <c:forEach items="${p.autos}" var="t" varStatus="loop">

            <c:if test="${t.estado == 'SIENDO ATENDIDO'}"> 
                <td> ${t.categoria}</td>
                <td style="background-color: lightblue"> ${t.estado}</td>
                <td><fmt:formatNumber value=" ${t.tiempoAtencion}" maxFractionDigits="3"></fmt:formatNumber></td> 
            </c:if>
            <c:if test="${t.estado != 'SIENDO ATENDIDO'}"> 
                <td> ${t.categoria}</td>
                <td > ${t.estado}</td>
                <td><fmt:formatNumber value=" ${t.tiempoAtencion}" maxFractionDigits="3"></fmt:formatNumber></td> 
            </c:if>


        </c:forEach>
















    </tbody>
</c:forEach>
</table>
</body>
</html>
