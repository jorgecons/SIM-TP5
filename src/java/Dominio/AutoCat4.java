/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Dominio;

import Interfaces.Auto;

/**
 *
 * @author jorge
 */
public class AutoCat4 extends Auto {

    private double tiempoAtencion;
    private String estado;

    @Override
    public void atender() {
        estado = "SIENDO ATENDIDO";
    }
    @Override
    public void setTiempoAtencion(double tiempoAtencion) {
        this.tiempoAtencion = tiempoAtencion;
    }
    @Override
    public void setEstado(String estado) {
        this.estado = estado;
    }

    @Override
    public void esperar() {
        estado = "ESPERANDO ATENCION";
    }

    @Override
    public String getEstado() {
        return estado;
    }

    @Override
    public int getCategoria() {
        return 4;
    }

    @Override
    public double tiempoAtencion(double RND) {
        tiempoAtencion=(double)RND * (double)40 +(double) 90;
        return tiempoAtencion;
    }

    @Override
    public int costoPeaje() {
        return 9;
    }

    @Override
    public double getTiempoAtencion() {
        return tiempoAtencion;
    }

}
