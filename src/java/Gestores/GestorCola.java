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
    private double tiempoSimulacion;
    private int montoToal;
    private int numeroMaxCabina;
    private int cabinaLiberada;
    private int iaux;

    public GestorCola(double tiempo) {
        tiempoActual = 0d;
        montoToal = 0;
        tiempoSimulacion = tiempo;
        numeroMaxCabina = 1;
        maximaCantidadAutos = 0;

        vectorEstado = new VectorEstado[2];
        vectorEstado[0] = new VectorEstado(tiempoActual);
        vectorEstado[0].lineaCero();
        iaux = 0;
        cabina = new Cabina();
        listadoCabinas = new ArrayList<>();

        autos = new ArrayList<>();

        vectorEstadoaux = new ArrayList<>();
        addAux(vectorEstado[0]);

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
    }

    //La primera linea es la segunda mostrada, donde lo que ocurre es la llegada de un auto si o si
    //ademas pregunta al vectorEstado en la pos 0
    public void primeraLinea() {
        tiempoActual = vectorEstado[0].menorTiempo();

        VectorEstado vec = new VectorEstado(tiempoActual);
        //obtengo una nueva llegada porque es la primera linea
        vec.nuevaLlegadaAuto();
        Auto aut = vectorEstado[0].getAuto();

        asignarACabina(aut, vec);
        autos.add(aut);
        maximoTamAutos();

        vectorEstado[1] = vec;
        addAux(vec);

    }

    public void nuevaLinea() {
        //El vector estado tiene 2 lineas, una actual y otra anterior
        tiempoActual = vectorEstado[1].menorTiempo();

        VectorEstado vec = new VectorEstado(tiempoActual);
        //Determino si llega un nuevo auto o termina una cabina
        //1 = llegada
        //2 = Fin atencion
        if (vectorEstado[1].getSiguienteEvento() == 1) {

            Auto aut;
            //genero una proxima llegada
            //aca ya creo un objeto auto... solo que no lo muestro
            vec.nuevaLlegadaAuto();
            //obtengo el auto que vengo arrastrando(puede ser solo de la linea anterior como de varias antes)
            //es decir, el auto que llega en este momento
            aut = vectorEstado[1].getAuto();
            //obtengo los tiempos fin atencion de la fila anterior y los copio tal cal
            vec.setTiempoFinAtencion(vectorEstado[1].getTiempoFinAtencion());

            //al auto que llega ahora, lo asigno a una cabina
            //lo hace de manera secuencial
            asignarACabina(aut, vec);
            //agrego el auto a la lista
            if (cabinaLiberada + 1 < autos.size()) {
                autos.add(autos.size(), aut);
            } else {
                autos.add(aut);
            }

        }

        //EVENTO 2       
        if (vectorEstado[1].getSiguienteEvento() == 2) {
            //obtengo el auto que sigue en la cola de la cabina que se libera
            //la cabina que se libera es igual a la posicion del menor tiempoFinAtencion del vector
            //a ese valor le resto -1, si es -1 es la cabina sola, sino es una i-esima cabina
            Auto aut = liberarCabina(vectorEstado[1].getNumeroCabina() - 1);
            //elimino el i-esimo auto siendo atendido de la lista de autos
            eliminarAuto(vectorEstado[1].getNumeroCabina() - 1);
            //si hay un auto en la cola de la cabina que se libera
            if (aut != null) {
                //mantengo el tiempo y el auto de la proxima llegada
                vec.setTiempoProximaLlegada(vectorEstado[1].getTiempoProximaLlegada());
                vec.setAuto(vectorEstado[1].getAuto());

                //mantengo los tiempoFinAtencion de la linea anterior, ya que solo afecta a uno solo de los tiempos
                vec.setTiempoFinAtencion(vectorEstado[1].getTiempoFinAtencion());

                //elimino el i-esimo tiempoFinAtencion que coincide con el de la cabina que se libera
                //puede darse el caso de que se libere la cabina pero queden cabinas trabajando
                //por eso el bardo este
                if (cabina.estaLibre()) {
                    //existe una cabina trabajando cuando la cabina sola esta libre
                    if (vectorEstado[1].getTiempoFinAtencion().size() > 1) {
                       //le seteo el tiempoFin de la pos 0, de la cabina sola, en 0
                        vec.getTiempoFinAtencion().set(0, 0d);
                    } else {
                        //si no hay cabinas trabajando, elimino la que corresponde
                        vec.getTiempoFinAtencion().remove(vectorEstado[1].getNumeroCabina());
                    }
                } else {
                    //en caso de que la cabina no este libre, borro el tiempo 0... creo que es lo mismo que arriba, pero no se, asi anda jajaja
                    vec.getTiempoFinAtencion().remove(0);
                }
                //como el auto existe, el auto de la cola se lo "asigno" a la cabina que se libero
                //si es -1, es la cabina sola, sino es la i-esima cabina
                asignarACabina(aut, vec, (vectorEstado[1].getNumeroCabina() - 1));

            } else {
                //no hay auto esperando ser atendido en esa cabina, por lo que se elimina esa cabina
                //lo hace el metodo liberarCabina

                //copio el tiempoProxima llegada de la linea anterior y el auto
                vec.setTiempoProximaLlegada(vectorEstado[1].getTiempoProximaLlegada());
                vec.setAuto(vectorEstado[1].getAuto());
                //copio los fin de atencion de la linea anterior
                vec.setTiempoFinAtencion(vectorEstado[1].getTiempoFinAtencion());

                //borro el i-esimo tiempo fin de atencion
                //puede darse el caso de que se libere la cabina pero queden cabinas trabajando
                //por eso el bardo este
                if (cabina.estaLibre()) {
                    //hay una cabina habilitada
                    if (vectorEstado[1].getTiempoFinAtencion().size() > 1) {
                        
                        //seteo el tiempoFin en la pos 0 en 0 que es de la cabina sola
                        vec.getTiempoFinAtencion().set(0, 0d);
                        //quito el tiempo fin que coincide con el tiempo Actual
                        vec.getTiempoFinAtencion().remove(tiempoActual);

                    } else {
                        //borro el tiempo que corresponode
                        vec.getTiempoFinAtencion().remove(vectorEstado[1].getNumeroCabina());

                    }
                } else {
                    //borro el tiempo que corresponode
                    vec.getTiempoFinAtencion().remove(vectorEstado[1].getNumeroCabina());

                }

            }

        }
//        if (vec.getTiempoFinAtencion().size() == 2 && iaux == 0) {
//            vec.getTiempoFinAtencion().set(1, vectorEstado[1].getTiempoFinAtencion().get(1) + 1000);
//            System.out.println("Entre");
//            System.out.println("numer"+(vectorEstado[1].getTiempoFinAtencion().get(1) + 1000));
//            iaux++;
//        }

        //calculo el tamaño maximo de autos en el sistema 
        maximoTamAutos();
        //añado el vector en el de simulacion y en el de mostrar
        addVector(vec);
        addAux(vec);
    }

    private void addAux(VectorEstado vec) {

        ArrayList<Cabina> aux = new ArrayList<>();
        ArrayList<Auto> autox = new ArrayList<>();

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
                //para que no hinche los huevos netBeans
                default:
                    tutu = new AutoCat1();
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

        Cabina cab = new Cabina();
        int tamañoCola = cabina.getColaAutos().size();
        cab.setEstado(cabina.getEstado());
        cab.setSizeCola(tamañoCola);
        aux.add(cab);
        Cabina caux;
        for (int i = 0; i < listadoCabinas.size(); i++) {
            caux = new Cabina();
            caux.setEstado(listadoCabinas.get(i).getEstado());
            caux.setSizeCola(listadoCabinas.get(i).getColaAutos().size());
            aux.add(caux);

        }

        VectorAux vaux = new VectorAux(vec, aux, autox, taux, montoToal);
        vectorEstadoaux.add(vaux);

    }

    private void addVector(VectorEstado vec) {
        vectorEstado[0] = vectorEstado[1];
        vectorEstado[1] = vec;

    }

    public void eliminarAuto(Auto aut) {
        montoToal += aut.costoPeaje();
        autos.remove(aut);
    }

    public void eliminarAuto(int position) {
        if (cabina.estaLibre() && position == -1 && !listadoCabinas.isEmpty() && !autos.isEmpty()) {

            montoToal += autos.remove(0).costoPeaje();
            return;
        }
        if (position == -1 && !autos.isEmpty()) {
            montoToal += autos.remove(0).costoPeaje();
            return;
        }
        if (position != -1 && !autos.isEmpty()) {
            int bandera = 0;
            for (int i = 1; i < autos.size(); i++) {

                if ("SIENDO ATENDIDO".equals(autos.get(i).getEstado())) {

                    if (bandera == position) {
                        montoToal += autos.remove(i).costoPeaje();
                        break;

                    }
                    bandera++;
                }
            }
        }
    }

    //libero cabina y obtengo si hay autos en la cola
    public Auto liberarCabina(int cabinaALiberar) {
        Auto aut = null;

        if (cabinaALiberar == -1) {
            cabinaLiberada = 0;
            cabina.liberar();
            if (!cabina.estaVacio()) {
                aut = cabina.siguienteAuto();
            }
        } else {

            if (cabinaALiberar > listadoCabinas.size() && !listadoCabinas.isEmpty()) {
                listadoCabinas.get(listadoCabinas.size() - 1).liberar();
                cabinaLiberada = listadoCabinas.size();
                if (!listadoCabinas.get(listadoCabinas.size() - 1).estaVacio()) {
                    aut = listadoCabinas.get(listadoCabinas.size() - 1).siguienteAuto();
                } else {
                    listadoCabinas.remove(listadoCabinas.size() - 1);
                }

            }
            if (cabinaALiberar < listadoCabinas.size()) {
                listadoCabinas.get(cabinaALiberar).liberar();
                cabinaLiberada = listadoCabinas.size() + 1;
                if (!listadoCabinas.get(cabinaALiberar).estaVacio()) {
                    aut = listadoCabinas.get(cabinaALiberar).siguienteAuto();
                } else {
                    listadoCabinas.remove(cabinaALiberar);
                }
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

                vec.generarTiempoAtencion(auto, 0);

                cabina.ocupar();
                return;
            }

            if (cabina.estaDisponible()) {

                cabina.añadirAuto(auto);
            }

        } else {

            listadoCabinas.get(position).ocupar();

            vec.generarTiempoAtencion(auto, position);

        }
    }

    public void asignarACabina(Auto auto, VectorEstado vec) {

        if (cabina.estaLibre()) {

            vec.generarTiempoAtencion(auto, 0);

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

            vec.generarTiempoAtencion(auto, 1);
            cabin.ocupar();

            if (numeroMaxCabina < 2) {
                numeroMaxCabina = 2;

            }

            listadoCabinas.add(cabin);

        } else {

            for (int i = 0; i < listadoCabinas.size(); i++) {
                if (listadoCabinas.get(i).estaLibre()) {
                    listadoCabinas.get(i).ocupar();
                    vec.generarTiempoAtencion(auto, (i + 1));

                    return;
                }
                if (listadoCabinas.get(i).estaDisponible()) {
                    listadoCabinas.get(i).añadirAuto(auto);
                    return;
                }

            }
            if (listadoCabinas.get(listadoCabinas.size() - 1).estaLleno()) {

                Cabina cabin = new Cabina();

                vec.generarTiempoAtencion(auto, listadoCabinas.size() + 1);
                cabin.ocupar();

                listadoCabinas.add(cabin);
                if (numeroMaxCabina < listadoCabinas.size() + 1) {
                    numeroMaxCabina = listadoCabinas.size() + 1;

                }
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
