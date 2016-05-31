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
//    private int numeroCabina;
    private int contador;
    private double tiempoSimulacion;
    private int montoToal;
    private int numeroMaxCabina;
//    private int numeroActualDeCabinas;

    public GestorCola(double tiempo) {
        vectorEstado = new VectorEstado[2];
        tiempoActual = 0d;
        cabina = new Cabina();
        vectorEstado[0] = new VectorEstado(tiempoActual);
        contador = 0;
        montoToal = 0;
//        numeroCabina = -1;
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
            if (i == 50) {
                break;
            }
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
//                vec.getTiempoFinAtencion().remove(vectorEstado[1].getNumeroCabina());
                aut = vectorEstado[1].getAuto();
                asignarACabina(aut, vec);
                autos.add(aut);
                maximoTamAutos();

//
            }

            vec.menorTiempo();
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

        //    System.out.println("Cabina a Liberar: " + (vectorEstado[1].getNumeroCabina() - 1));
        //    System.out.println("Numero de cabinas: " + listadoCabinas.size());
            Auto aut = liberarCabina(vectorEstado[1].getNumeroCabina() - 1);
        //    System.out.println("-------------------------------------");
            if (aut != null) {
        //        System.out.println("Auto:" + aut.toString());
            }
         //   System.out.println("-------------------------------------");

            eliminarAuto(vectorEstado[1].getNumeroCabina() - 1);
            //hay un auto en la cola...
            if (aut != null) {
                System.out.println("Me meto aca... hay auto");
                if (vectorEstado[1].getTiempoProximaLlegada() != 0) {
                    vec.setTiempoProximaLlegada(vectorEstado[1].getTiempoProximaLlegada());

                }
                vec.setTiempoFinAtencion(vectorEstado[1].getTiempoFinAtencion());
         //       System.out.println("Teimpos del anterior:" + vec.getTiempoFinAtencion().toString());
                vec.getTiempoFinAtencion().remove(vectorEstado[1].getNumeroCabina());
           //     System.out.println("Teimpos eliminando :" + vec.getTiempoFinAtencion().toString());
             //   System.out.println("---------------------------------------------");

                asignarACabina(aut, vec, (vectorEstado[1].getNumeroCabina() - 1));
               // System.out.println("Teimpos agregando:" + vec.getTiempoFinAtencion().toString());
                //vec.getTiempoFinAtencion().remove(0);
                maximoTamAutos();
                vec.menorTiempo();
                vec.setAuto(vectorEstado[1].getAuto());
//                System.out.println("TIEMPO ACTUAL: " + tiempoActual);
//                System.out.println("PROXIMA LLEGADA : " + vec.getTiempoProximaLlegada());
//                System.out.println("FIN ATENCION : " + vec.getTiempoFinAtencion().toString());
//                System.out.println("CABINA: " + cabina.toString());
//                System.out.println("AUTO: " + aut.toString());
            } else {
                System.out.println("Me meto aca en el else");
                if (vectorEstado[1].getTiempoProximaLlegada() != 0) {
                    vec.setTiempoProximaLlegada(vectorEstado[1].getTiempoProximaLlegada());

                }
                vec.setTiempoFinAtencion(vectorEstado[1].getTiempoFinAtencion());
                vec.getTiempoFinAtencion().remove(vectorEstado[1].getNumeroCabina());
                vec.menorTiempo();
                vec.setAuto(vectorEstado[1].getAuto());
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

//        if (numeroActualDeCabinas > numeroMaxCabina) {
//            numeroMaxCabina =  numeroActualDeCabinas;
//        }
      //  System.out.println("Cabinas:\n-----------------------------------\n" + listadoCabinas.toString());
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
            //   System.out.println("Cabia: " + i + 1 + caux.getSizeCola());
        }

        VectorAux vaux = new VectorAux(vec, aux, autox, taux, montoToal);
        vectorEstadoaux.add(vaux);

    }

    private void addVector(VectorEstado vec) {
        vectorEstado[0] = vectorEstado[1];
        vectorEstado[1] = vec;

    }

    public void eliminarAuto(int position) {
        if (position == -1) {
            montoToal += autos.remove(0).costoPeaje();
            
        } else {
            for (int i = position*4 +1; i < autos.size(); i++) {
             //   System.out.println("Position autos: "+i);
                if("SIENDO ATENDIDO".equals(autos.get(i).getEstado())){
                   montoToal+= autos.remove(i).costoPeaje();
                   break;
                }
            }
        }
    }

    @Override
    public String toString() {
        return "GestorCola{" + "vectorEstadoaux=\n" + vectorEstadoaux.toString() + '}';
    }

    //libero cabina y obtengo si hay autos en la cola
    public Auto liberarCabina(int cabinaALiberar) {
        Auto aut = null;

        if (cabinaALiberar == -1) {
            cabina.liberar();
            if (!cabina.estaVacio()) {
                aut = cabina.siguienteAuto();
            }
        } else {

            listadoCabinas.get(cabinaALiberar).liberar();
           // System.out.println("Numero cabina: " + cabinaALiberar);
            if (!listadoCabinas.get(cabinaALiberar).estaVacio()) {
                aut = listadoCabinas.get(cabinaALiberar).siguienteAuto();
            } else {
                listadoCabinas.remove(cabinaALiberar);
            }
        }

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

    public void asignarACabina(Auto auto, VectorEstado vec, int position) {
        if (position == -1) {
            if (cabina.estaLibre()) {
               // System.out.println("CABINA LIBRE");
                vec.generarTiempoAtencion(auto, 0);

                cabina.ocupar();
                return;
            }
            if (cabina.estaDisponible()) {
              //  System.out.println("CABINA DISPONIBLE");
                cabina.añadirAuto(auto);
            }
        } else {
           // System.out.println("VIENDO QUE PUTA CABINA ANDA");
            //System.out.println("CABINAS ACA ESTOY: " + listadoCabinas.toString());
            listadoCabinas.get(position).ocupar();

            vec.generarTiempoAtencion(auto, (position + 1));
            //System.out.println("ACA ESTOY: " + (position + 1));

        }
    }

    //DEBERIA SABER A QUE CABINA VOLVER A OCUPAR..... ESO ES LO QUE PASA
    public void asignarACabina(Auto auto, VectorEstado vec) {

        if (cabina.estaLibre()) {
           // System.out.println("CABINA LIBRE");
            vec.generarTiempoAtencion(auto, 0);

            cabina.ocupar();
            return;
        }
        if (cabina.estaDisponible()) {
          //  System.out.println("CABINA DISPONIBLE");
            cabina.añadirAuto(auto);
            return;
        }
        //en caso de que no este disponible
        //habilito nuevas cabinas
        if (listadoCabinas.isEmpty()) {

            Cabina cabin = new Cabina();

            vec.generarTiempoAtencion(auto, 1);
            cabin.ocupar();

            //esta mal echo las forma de guardar el maximo
            numeroMaxCabina = 2;
//            
//            numeroCabina = 0;

            listadoCabinas.add(cabin);
          //  System.out.println("Agrego la primer CABINA");

        } else {
         //   System.out.println("VIENDO QUE PUTA CABINA ANDA");
         //   System.out.println("CABINAS ACA ESTOY: " + listadoCabinas.toString());
            for (int i = 0; i < listadoCabinas.size(); i++) {
                if (listadoCabinas.get(i).estaLibre()) {
                    listadoCabinas.get(i).ocupar();
                    vec.generarTiempoAtencion(auto, (i + 1));
            //        System.out.println("ACA ESTOY: " + (i + 1));
                    return;
                }
                if (listadoCabinas.get(i).estaDisponible()) {
                    listadoCabinas.get(i).añadirAuto(auto);
                    return;
                }

            }
            if (listadoCabinas.get(listadoCabinas.size() - 1).estaLleno()) {
                //    System.out.println("Agrego una NUEVA CABINA");
                Cabina cabin = new Cabina();
                numeroMaxCabina++;
                vec.generarTiempoAtencion(auto, listadoCabinas.size() + 1);
                cabin.ocupar();

                //numeroCabina = listadoCabinas.size();
                listadoCabinas.add(cabin);
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
