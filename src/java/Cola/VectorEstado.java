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
    //para el tp6, agrego el evento 2- log Sistema;
    private int eventoActual;
    private Auto auto;
    private int numeroCabina;
    //para el tp6
    private double rndLog;
    private int porcentajeLlenado;
    private double tiempoHastaLog;
    private double tiempoLog;
    private double tiempoFinLog;

    public VectorEstado(double tiempoActual) {
        this.tiempoActual = tiempoActual;
        tiempoFinAtencion = new ArrayList<>();

    }

    public void lineaCero() {
        this.tiempoActual = 0d;
        nuevaLlegadaAuto();
        generarLogSistema();
        siguienteEvento = 1;
        eventoActual = 0;
    }

    public void generarLogSistema() {
        this.rndLog = Math.random();
        if (rndLog < 0.50d) {
            this.porcentajeLlenado = 100;
            this.tiempoHastaLog = 4501d;
        } else if (rndLog < 0.80d) {
            this.porcentajeLlenado = 80;
            this.tiempoHastaLog = 4166d;
        } else {
            this.porcentajeLlenado = 50;
            this.tiempoHastaLog = 3459.5d;
        }
        this.tiempoLog = tiempoActual + tiempoHastaLog;

    }

    public double getTiempoHastaLog() {
        return tiempoHastaLog;
    }

    public void setTiempoHastaLog(double tiempoHastaLog) {
        this.tiempoHastaLog = tiempoHastaLog;
    }

    public double getTiempoFinLog() {
        return tiempoFinLog;
    }

    public void setTiempoFinLog(double tiempoFinLog) {
        this.tiempoFinLog = tiempoFinLog;
    }

    public double getTiempoLog() {
        return tiempoLog;
    }

    public void setTiempoLog(double tiempoLog) {
        this.tiempoLog = tiempoLog;
    }

    public double getRndLog() {
        return rndLog;
    }

    public void setRndLog(double rndLog) {
        this.rndLog = rndLog;
    }

    public int getPorcentajeLlenado() {
        return porcentajeLlenado;
    }

    public void setPorcentajeLlenado(int porcentajeLlenado) {
        this.porcentajeLlenado = porcentajeLlenado;
    }

    public void setTiempoActual(double tiempoActual) {
        this.tiempoActual = tiempoActual;
    }

    public void nuevaLlegadaAuto() {
        do {
            rndLlegadaAuto = Math.random();
        } while (rndLlegadaAuto == 0d);
        tiempoEntreLlegada = -120 * Math.log(1 - rndLlegadaAuto);

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

    public int getEventoActual() {
        return eventoActual;
    }

    public void setEventoActual(int eventoActual) {
        this.eventoActual = eventoActual;
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

    public void generarTiempoAtencion(Auto auto, int i) {
        rndAtencion = Math.random();
        auto.atender();

        auto.tiempoAtencion(rndAtencion);

        tiempoDemoraAtencion = auto.getTiempoAtencion();
        if (tiempoFinAtencion.isEmpty()) {
            tiempoFinAtencion.add(tiempoActual + tiempoDemoraAtencion);
            return;
        }
        if (i >= tiempoFinAtencion.size()) {
            tiempoFinAtencion.add(tiempoActual + tiempoDemoraAtencion);

        } else {
            if (tiempoFinAtencion.get(i) == 0) {
                tiempoFinAtencion.set(i, tiempoActual + tiempoDemoraAtencion);
            } else {
                tiempoFinAtencion.add(i, tiempoActual + tiempoDemoraAtencion);
            }
        }
    }

    public double menorTiempo() {
        if (tiempoFinLog != 0) {
            if (!tiempoFinAtencion.isEmpty()) {
                double menorT = menorTiempoAtencion();
                if (tiempoProximaLlegada < tiempoLog && tiempoProximaLlegada < tiempoFinLog && tiempoProximaLlegada < menorT) {
                    siguienteEvento = 1;
                    return tiempoProximaLlegada;
                } else if (menorT < tiempoProximaLlegada && menorT < tiempoLog && menorT < tiempoFinLog) {
                    siguienteEvento = 2;
                    return menorT;
                } else if (tiempoLog < tiempoProximaLlegada && tiempoLog < tiempoFinLog && tiempoLog < menorT) {
                    siguienteEvento = 3;
                    return tiempoLog;
                } else if (tiempoFinLog < tiempoProximaLlegada && tiempoFinLog < tiempoLog && tiempoFinLog < menorT) {
                    siguienteEvento = 4;
                    return tiempoFinLog;
                }
            } else {
                if (tiempoProximaLlegada < tiempoLog && tiempoProximaLlegada < tiempoFinLog) {
                    siguienteEvento = 1;
                    return tiempoProximaLlegada;
                } else if (tiempoLog < tiempoProximaLlegada && tiempoLog < tiempoFinLog) {
                    siguienteEvento = 3;
                    return tiempoLog;
                } else if (tiempoFinLog < tiempoProximaLlegada && tiempoFinLog < tiempoLog) {
                    siguienteEvento = 4;
                    return tiempoFinLog;
                }
            }
        } else {
            if (!tiempoFinAtencion.isEmpty()) {
                double menorT = menorTiempoAtencion();
                if (tiempoProximaLlegada < tiempoLog && tiempoProximaLlegada < menorT) {
                    siguienteEvento = 1;
                    return tiempoProximaLlegada;
                } else if (menorT < tiempoProximaLlegada && menorT < tiempoLog) {
                    siguienteEvento = 2;
                    return menorT;
                } else if (tiempoLog < tiempoProximaLlegada && tiempoLog < menorT) {
                    siguienteEvento = 3;
                    return tiempoLog;
                }
            } else {
                if (tiempoProximaLlegada < tiempoLog) {
                    siguienteEvento = 1;
                    return tiempoProximaLlegada;
                } else if (tiempoLog < tiempoProximaLlegada) {
                    siguienteEvento = 3;
                    return tiempoLog;

                }
            }
        }
        return 0;

    }
    //        if (tiempoProximaLlegada != 0) {
    //            if (!tiempoFinAtencion.isEmpty()) {
    //                double menorT = menorTiempoAtencion();
    //                if (tiempoProximaLlegada < menorT) {
    //                    //veo que tiempo es menor, si el que se clava o proxima llegada
    //                    if (tiempoProximaLlegada < tiempoLog) {
    //                        siguienteEvento = 1;
    //
    //                        return tiempoProximaLlegada;
    //
    //                    } else {//se me clava el sistema...
    //                        siguienteEvento = 3;
    //
    //                        return tiempoLog;
    //
    //                    }
    //                } else if (menorT < tiempoLog) {
    //                    siguienteEvento = 2;
    //
    //                    return menorT;
    //                }else{//se me clava el sistema...
    //                    siguienteEvento = 3;
    //
    //                    return tiempoLog;
    //                }
    //            } else {
    //                //veo que tiempo es menor, si el que se clava o proxima llegada
    //                if (tiempoProximaLlegada < tiempoLog) {
    //                    
    //                    siguienteEvento = 1;
    //
    //                    return tiempoProximaLlegada;
    //
    //                } else {//se me clava el sistema...
    //                    siguienteEvento = 3;
    //
    //                    return tiempoLog;
    //
    //                }
    //            }
    //        }

    public double menorTiempoAtencion() {
        double menorT = 0d;
        menorT = 500000d;
        numeroCabina = 0;
        for (int i = 0; i < tiempoFinAtencion.size(); i++) {
            if (menorT > tiempoFinAtencion.get(i) && tiempoFinAtencion.get(i) != 0) {
                menorT = tiempoFinAtencion.get(i);
                numeroCabina = i;
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
