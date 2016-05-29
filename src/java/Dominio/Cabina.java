/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Dominio;

import Interfaces.Auto;
import java.util.ArrayList;

/**
 *
 * @author jorge
 */
public class Cabina {

    private String estado;
    private ArrayList<Auto> colaAutos;
    private int sizeCola;

    public Cabina() {
        this.colaAutos = new ArrayList<>();
        this.estado = "LIBRE";
        this.sizeCola=colaAutos.size();
      
    }

    public int getSizeCola() {
        return sizeCola;
    }

    public void setSizeCola(int sizeCola) {
        this.sizeCola = sizeCola;
    }

    public boolean estaDisponible() {
        return colaAutos.size() < 4;
    }

    public boolean estaLleno() {
        return colaAutos.size() == 4;
    }

    public boolean estaVacio() {
        return colaAutos.isEmpty();
    }

    public int cobrar(){
        return 0;
    }
    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }
    
    public boolean noHagoUnaChota(){
        return estaLibre() && estaVacio();
    }

    public boolean estaLibre() {
        return estado == "LIBRE";
    }

    //el add agrega al ultimo de la lista
    public void añadirAuto(Auto auto) {
        auto.esperar();
        colaAutos.add(auto);

    }
    
    public Auto siguienteAuto(){
        
        return colaAutos.remove(0);
    }

    public void ocupar() {
        this.estado = "OCUPADO";
    }

    //por eso aca borro el primero... seria una pila lifo al reves

    public void liberar() {

        this.estado = "LIBRE";
        //colaAutos.remove(0);
        
    }

    public ArrayList<Auto> getColaAutos() {
        return colaAutos;
    }

    public void setColaAutos(ArrayList<Auto> colaAutos) {
        this.colaAutos = colaAutos;
    }
    


    @Override
    public String toString() {
        return "\n Cabina{" + "estado=" + estado + ", colaAutos=" + colaAutos.toString() + '}';
    }
    
    

}
