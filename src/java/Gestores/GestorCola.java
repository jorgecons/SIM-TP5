/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Gestores;

import Cola.VectorAux;
import Cola.VectorEstado;
import Dominio.AutoCat1;
import Dominio.AutoCat2;
import Dominio.AutoCat3;
import Dominio.AutoCat4;
import Dominio.AutoCat5;
import Dominio.Cabina;
import Interfaces.Auto;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;

/**
 *
 * @author jorge
 */
public class GestorCola {

    private VectorEstado[] vectorEstado;
    private ArrayList<VectorAux> vectorEstadoaux;
    private double tiempoActual;
    private Cabina cabina;
    private ArrayList<Cabina> listadoCabinas;
    private ArrayList<Auto> autos;
    private int maximaCantidadAutos;
    private int numeroCabina;
    private int contador;
    private double tiempoSimulacion;
    private int montoToal;
    private int numeroMaxCabina;
    private int numeroActualDeCabinas;

    public GestorCola(double tiempo) {
        vectorEstado = new VectorEstado[2];
        tiempoActual = 0d;
        cabina = new Cabina();
        vectorEstado[0] = new VectorEstado(tiempoActual);
        contador = 0;
        montoToal = 0;
        numeroCabina = -1;
        numeroMaxCabina = 1;
        tiempoSimulacion = tiempo;
        listadoCabinas = new ArrayList<>();
        autos = new ArrayList<>();
        maximaCantidadAutos = 0;
        vectorEstado[0].lineaCero();
        vectorEstadoaux = new ArrayList<>();
        addAux(vectorEstado[0]);
        System.out.println("INICIALIZO  : " + tiempoActual);
        System.out.println("CABINA:" + cabina.toString());
        System.out.println("--------------------------------");
    }

    public int getMontoToal() {
        return montoToal;
    }

    public void comenzarSimulacion() {
        int i = 1;
        System.out.println("Vuelta: " + i);
        primeraLinea();
        i++;
        while (tiempoActual < tiempoSimulacion) {
            System.out.println("Vuelta: " + i);
            nuevaLinea();
            i++;

        }

//        System.out.println(toString());
    }

    public void primeraLinea() {
        tiempoActual = vectorEstado[0].menorTiempo();

        VectorEstado vec = new VectorEstado(tiempoActual);
        //obtengo una nueva llegada porque es la primera linea
        vec.nuevaLlegadaAuto();
        Auto aut = vectorEstado[0].getAuto();
        // vec.generarTiempoAtencion(aut);

        asignarACabina(aut, vec);
        autos.add(aut);
        maximoTamAutos();
        vec.menorTiempo();
//        System.out.println("TIEMPO ACTUAL: " + tiempoActual);
//        System.out.println("PROXIMA LLEGADA : " + vec.getTiempoProximaLlegada());
//        System.out.println("FIN ATENCION : " + vec.getTiempoFinAtencion().toString());
//        System.out.println("CABINA: " + cabina.toString());
//        System.out.println("AUTO: " + aut.toString());
//        System.out.println("******************************************************");
//        System.out.println(vec.toString());
//        System.out.println("******************************************************");
        vectorEstado[1] = vec;
        addAux(vec);
//        System.out.println("--------------------------------");

    }

    public void nuevaLinea() {
        //El vector estado tiene 2 lineas, una actual y otra anterior
        tiempoActual = vectorEstado[1].menorTiempo();

        VectorEstado vec = new VectorEstado(tiempoActual);
        //Determino si llega un nuevo auto o termina una cabina
        //1 = llegada
        //2 = Fin atencion
        if (vectorEstado[1].getSiguienteEvento() == 1) {
            //vec.asignarACabina(vectorEstado[1].getAuto());
            Auto aut = null;
            vec.nuevaLlegadaAuto();
            aut = vectorEstado[1].getAuto();
            //PARA QUE MIERDA ES ESTA VALIDACION
            //COMENTA ALGO HIJO DE MIL
            if (aut != null) {

                vec.setTiempoFinAtencion(vectorEstado[1].getTiempoFinAtencion());

                aut = vec.getAuto();
                asignarACabina(aut, vec);
                autos.add(aut);
                maximoTamAutos();
//                System.out.println("AUTO: " + aut.toString());
            } else {
                vec.setTiempoFinAtencion(vectorEstado[1].getTiempoFinAtencion());
                aut = vec.getAuto();
                asignarACabina(aut, vec);
                //ACA HAY QUE SABER A QUE CABINA LE SACAS EL AUTO
                vec.menorTiempo();

                autos.add(aut);
                maximoTamAutos();
//                System.out.println("AUTO: " + aut.toString());

            }

//            vec.menorTiempo();
//            System.out.println("Hola");
            //            System.out.println("TIEMPO ACTUAL: " + tiempoActual);
//            System.out.println("PROXIMA LLEGADA : " + vec.getTiempoProximaLlegada());
//            System.out.println("FIN ATENCION : " + vec.getTiempoFinAtencion().toString());
//            System.out.println("CABINA: " + cabina.toString());
//            System.out.println("CABINAS: \n" + listadoCabinas.toString());
//            System.out.println("******************************************************");
//            System.out.println(vec.toString());
//            System.out.println("******************************************************");
//            System.out.println("--------------------------------");
        }
//EVENTO 2--------------------------
        //-------------------------------------------------------------------------------
        if (vectorEstado[1].getSiguienteEvento() == 2) {
            
            Auto aut = liberarCabina();

            montoToal += autos.remove(0).costoPeaje();
            //hay un auto en la cola...
            if (aut != null) {
                System.out.println("EXISTE AUTO, ETRO EN LA CONCHA DE TU HERMANA");
                if (vectorEstado[1].getTiempoProximaLlegada() != 0) {
                    vec.setTiempoProximaLlegada(vectorEstado[1].getTiempoProximaLlegada());

                }
                asignarACabina(aut, vec);
                //vec.getTiempoFinAtencion().remove(0);
                maximoTamAutos();
                vec.menorTiempo();
//                System.out.println("TIEMPO ACTUAL: " + tiempoActual);
//                System.out.println("PROXIMA LLEGADA : " + vec.getTiempoProximaLlegada());
//                System.out.println("FIN ATENCION : " + vec.getTiempoFinAtencion().toString());
//                System.out.println("CABINA: " + cabina.toString());
//                System.out.println("AUTO: " + aut.toString());
            } else {

                if (vectorEstado[1].getTiempoProximaLlegada() != 0) {
                    vec.setTiempoProximaLlegada(vectorEstado[1].getTiempoProximaLlegada());

                }
                vec.menorTiempo();
//                System.out.println("TIEMPO ACTUAL: " + tiempoActual);
//                System.out.println("PROXIMA LLEGADA : " + vec.getTiempoProximaLlegada());
//                System.out.println("FIN ATENCION : " + vec.getTiempoFinAtencion());
//                System.out.println("CABINA: " + cabina.toString());
//
            }
//            System.out.println("CABINAS: \n" + listadoCabinas.toString());
//            System.out.println("******************************************************");
//            System.out.println(vec.toString());
//            System.out.println("******************************************************");
//            System.out.println("--------------------------------");
        }
        
        if (numeroActualDeCabinas > numeroMaxCabina) {
            numeroMaxCabina =  numeroActualDeCabinas;
        }

        addVector(vec);
        addAux(vec);
    }

    private void addAux(VectorEstado vec) {

        ArrayList<Cabina> aux = new ArrayList<>();
        ArrayList<Auto> autox = new ArrayList<>();
        Cabina cab = new Cabina();
        int tamañoCola = cabina.getColaAutos().size();
        for (int i = 0; i < autos.size(); i++) {
            Auto tutu = null;
            switch (autos.get(i).getCategoria()) {
                case 1:
                    tutu = new AutoCat1();
                    break;
                case 2:
                    tutu = new AutoCat2();
                    break;
                case 3:
                    tutu = new AutoCat3();
                    break;
                case 4:
                    tutu = new AutoCat4();
                    break;
                case 5:
                    tutu = new AutoCat5();
                    break;
            }
            tutu.setEstado(autos.get(i).getEstado());
            tutu.setTiempoAtencion(autos.get(i).getTiempoAtencion());
            autox.add(tutu);

        }
        ArrayList<Double> taux = new ArrayList<>();
        double d = 0d;
        for (int i = 0; i < vec.getTiempoFinAtencion().size(); i++) {
            d = vec.getTiempoFinAtencion().get(i);
            taux.add(d);

        }
        cab.setEstado(cabina.getEstado());
        cab.setSizeCola(tamañoCola);
        aux.add(cab);
        Cabina caux;
        int tuax;
        for (int i = 0; i < listadoCabinas.size(); i++) {
            caux = new Cabina();
            caux.setEstado(listadoCabinas.get(i).getEstado());
            caux.setSizeCola(listadoCabinas.get(i).getColaAutos().size());
            aux.add(caux);
            System.out.println("Cabia: " + i + 1 + caux.getSizeCola());
        }

        VectorAux vaux = new VectorAux(vec, aux, autox, taux, montoToal);
        vectorEstadoaux.add(vaux);

    }

    private void addVector(VectorEstado vec) {
        vectorEstado[0] = vectorEstado[1];
        vectorEstado[1] = vec;

    }

    @Override
    public String toString() {
        return "GestorCola{" + "vectorEstadoaux=\n" + vectorEstadoaux.toString() + '}';
    }

    //libero cabina y obtengo si hay autos en la cola
    public Auto liberarCabina() {
        Auto aut = null;

//        if (numeroCabina == -1) {
        //prgunto si hay una listado de cabinas
        //en caso de no estar vacio, entro
        if (!listadoCabinas.isEmpty()) {
            //recorrro todas las cabinas
            for (Iterator<Cabina> iterator = listadoCabinas.iterator(); iterator.hasNext();) {
                Cabina next = iterator.next();
                next.liberar();
                System.out.println("LIBERO LA CABINA DEL LISTADO: " + next.toString());

                if (!next.estaVacio()) {

                    aut = next.siguienteAuto();

                }else{
                    //en caso de estar vacio no puede estar libre, para eso tengo la 1.
                    //encotnce la deshabilito sacandola del listado de cabinas
                    System.out.println("DESHABILITO");
                    //aca daba errro xq no restaba al contador de cabinas
                    numeroActualDeCabinas--;
                    listadoCabinas.remove(next);
                    System.out.println("Esta vacio ?" + listadoCabinas.isEmpty());
                    return aut;
                    
                }

            }

        } else {
            //en caso de no existir mas cabinas hsabilitadas, voy a la primera
             //System.out.println("LIBERO LA CABINA 1: " + cabina.toString());
            cabina.liberar();

            if (!cabina.estaVacio()) {

                aut = cabina.siguienteAuto();

            }
        }
//        }else{
//            Cabina cab=listadoCabinas.get(numeroCabina);
//            listadoCabinas.get(numeroCabina).liberar();
//            if(!cab.estaVacio()){
//                aut= listadoCabinas.get(numeroCabina).siguienteAuto();
//                if(cab.estaVacio()){
//                    listadoCabinas.remove(cab);
//                }
//            }
//        }

        return aut;
    }

    public int getNumeroMaxCabina() {
        return numeroMaxCabina;
    }

    public void setNumeroMaxCabina(int numeroMaxCabina) {
        this.numeroMaxCabina = numeroMaxCabina;
    }

    public ArrayList<VectorAux> getVectorEstadoaux() {
        return vectorEstadoaux;
    }

    public void asignarACabina(Auto auto, VectorEstado vec) {

        if (cabina.estaLibre()) {

            vec.generarTiempoAtencion(auto);

            cabina.ocupar();
            return;
        }
        if (cabina.estaDisponible()) {

            cabina.añadirAuto(auto);
            return;
        }
        //en caso de que no este disponible
        //habilito nuevas cabinas
        if (listadoCabinas.isEmpty()) {

            Cabina cabin = new Cabina();
            
            vec.generarTiempoAtencion(auto);
            cabin.ocupar();
            //esta mal echo las forma de guardar el maximo
            
            numeroActualDeCabinas=2;
            
            numeroCabina = 0;
            listadoCabinas.add(cabin);
            System.out.println("Agrego la primer CABINA");

        } else {

            for (int i = 0; i < listadoCabinas.size(); i++) {
                if (listadoCabinas.get(i).estaLibre()) {
                    listadoCabinas.get(i).ocupar();
                    vec.generarTiempoAtencion(auto);
                    return;
                }
                if (listadoCabinas.get(i).estaDisponible()) {
                    listadoCabinas.get(i).añadirAuto(auto);
                    return;
                }

            }
            if (listadoCabinas.get(listadoCabinas.size() - 1).estaLleno()) {
                System.out.println("Agrego una NUEVA CABINA");
                Cabina cabin = new Cabina();
                numeroActualDeCabinas++;
                vec.generarTiempoAtencion(auto);
                cabin.ocupar();

                numeroCabina = 0;
                listadoCabinas.add(cabin);
                return;
            }
        }

    }

    public int getMaximaCantidadAutos() {
        return maximaCantidadAutos;
    }

    public void setMaximaCantidadAutos(int maximaCantidadAutos) {
        this.maximaCantidadAutos = maximaCantidadAutos;
    }

    public void maximoTamAutos() {
        if (autos.size() > maximaCantidadAutos) {
            maximaCantidadAutos = autos.size();
        }
    }

}
