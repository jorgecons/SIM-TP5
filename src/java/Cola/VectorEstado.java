/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Cola;

import Dominio.*;
import Interfaces.Auto;
import java.util.ArrayList;

/**
 *
 * @author jorge
 */
public class VectorEstado {

    //todas las variables del vector...
    private double tiempoActual, tiempoProximaLlegada;
    private ArrayList<Double> tiempoFinAtencion;
    private double tiempoEntreLlegada, tiempoDemoraAtencion;
    private double rndLlegadaAuto, rndCatAuto, rndAtencion;
    private int siguienteEvento;//para saber que evento es el que sigue; 1- llegada, 2- fin atencion;
    private Auto auto;
    private int numeroCabina;

    public VectorEstado(double tiempoActual) {
        this.tiempoActual = tiempoActual;
        tiempoFinAtencion=new ArrayList<>();

    }

    public void lineaCero() {
        this.tiempoActual = 0d;
        nuevaLlegadaAuto();
        siguienteEvento = 1;
    }

    public void setTiempoActual(double tiempoActual) {
        this.tiempoActual = tiempoActual;
    }

    public void nuevaLlegadaAuto() {
        do {
            rndLlegadaAuto = Math.random();
        } while (rndLlegadaAuto == 0d);
        tiempoEntreLlegada = -50 * Math.log(1 - rndLlegadaAuto);

        tiempoProximaLlegada = tiempoActual + tiempoEntreLlegada;
        generarCatAuto();
        // asignarACabina();
    }

    private void generarCatAuto() {
        rndCatAuto = Math.random();
        if (rndCatAuto < 0.1d) {
            auto = new AutoCat1();

        } else if (rndCatAuto < 0.6d) {
            auto = new AutoCat2();

        } else if (rndCatAuto < 0.75d) {
            auto = new AutoCat3();

        } else if (rndCatAuto < 0.9d) {
            auto = new AutoCat4();

        } else {
            auto = new AutoCat5();

        }

    }

    public double getTiempoActual() {
        return tiempoActual;
    }

    public void setTiempoDemoraAtencion(double tiempoDemoraAtencion) {
        this.tiempoDemoraAtencion = tiempoDemoraAtencion;
    }

    public void setSiguienteEvento(int siguienteEvento) {
        this.siguienteEvento = siguienteEvento;
    }

    public void setAuto(Auto auto) {
        this.auto = auto;
    }

    public void setNumeroCabina(int numeroCabina) {
        this.numeroCabina = numeroCabina;
    }

    public Auto getAuto() {
        return auto;
    }

    public double getTiempoDemoraAtencion() {
        return tiempoDemoraAtencion;
    }

    public double getTiempoProximaLlegada() {
        return tiempoProximaLlegada;
    }

    public int getSiguienteEvento() {
        return siguienteEvento;
    }

    public void eventoLlegada() {
        siguienteEvento = 1;
    }

    public void eventoFin() {
        siguienteEvento = 2;
    }

    public void generarTiempoAtencion(Auto auto) {
        rndAtencion = Math.random();
        auto.atender();
        auto.tiempoAtencion(rndCatAuto);

        tiempoDemoraAtencion = auto.getTiempoAtencion();
        tiempoFinAtencion.add(tiempoActual + tiempoDemoraAtencion);
//        System.out.println("TIEMPOS FIN ATENCION:"+tiempoFinAtencion.toString());
    }

    public double menorTiempo() {
        if (tiempoProximaLlegada != 0) {
            if (!tiempoFinAtencion.isEmpty()) {
                double menorT= menorTiempoAtencion();
                if (tiempoProximaLlegada < menorT) {
                    siguienteEvento = 1;
                  

                    return tiempoProximaLlegada;
                } else {
                    siguienteEvento = 2;
                   
                    return menorT;
                }
            } else {
                siguienteEvento = 1;
                   
                return tiempoProximaLlegada;
            }
        }
        return 0;
    }

    public double menorTiempoAtencion() {
        double menorT = 0d;
        menorT = tiempoFinAtencion.get(0);

        for (int i = 0; i < tiempoFinAtencion.size(); i++) {
            if(menorT>tiempoFinAtencion.get(i)){
                menorT=tiempoFinAtencion.get(i);
            }
        }
        return menorT;
    }

    public void setTiempoProximaLlegada(double tiempoProximaLlegada) {
        this.tiempoProximaLlegada = tiempoProximaLlegada;
    }

    public int getNumeroCabina() {
        return numeroCabina;
    }

    public ArrayList<Double> getTiempoFinAtencion() {
        return tiempoFinAtencion;
    }

    public void setTiempoFinAtencion(ArrayList<Double> tiempoFinAtencion) {
        this.tiempoFinAtencion = tiempoFinAtencion;
    }

    

//    
    public double getTiempoEntreLlegada() {
        return tiempoEntreLlegada;
    }

    public void setTiempoEntreLlegada(double tiempoEntreLlegada) {
        this.tiempoEntreLlegada = tiempoEntreLlegada;
    }

    public double getRndLlegadaAuto() {
        return rndLlegadaAuto;
    }

    public void setRndLlegadaAuto(double rndLlegadaAuto) {
        this.rndLlegadaAuto = rndLlegadaAuto;
    }

    public double getRndCatAuto() {
        return rndCatAuto;
    }

    public void setRndCatAuto(double rndCatAuto) {
        this.rndCatAuto = rndCatAuto;
    }

    public double getRndAtencion() {
        return rndAtencion;
    }

    public void setRndAtencion(double rndAtencion) {
        this.rndAtencion = rndAtencion;
    }

    @Override
    public String toString() {
        return "\n--------------------------------\n"
                + " Vector Estado A Añadir{"
                + " \n tiempoActual=" + tiempoActual
                + " \n tiempoProximaLlegada=" + tiempoProximaLlegada
                + " \n tiempoEntreLlegada=" + tiempoEntreLlegada
                + " \n tiempoDemoraAtencion=" + tiempoFinAtencion
                + " \n rndLlegadaAuto=" + rndLlegadaAuto
                + " \n rndCatAuto=" + rndCatAuto
                + " \n rndAtencion=" + rndAtencion
                + " \n siguienteEvento=" + siguienteEvento;

    }

}
