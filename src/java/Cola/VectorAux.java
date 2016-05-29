    /*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Cola;

import Dominio.Cabina;
import Interfaces.Auto;
import java.util.ArrayList;


/**
 *
 * @author jorge
 */
public class VectorAux{
    private int montoTotal;
    private VectorEstado vec;
    private ArrayList<Cabina> cabinas;
    private ArrayList<Auto> autos;
    private ArrayList<Double> tiempos;
    

    public VectorAux(VectorEstado vec, ArrayList<Cabina> cabinas, ArrayList<Auto> autos,ArrayList<Double> tiempos, int montoToal) {
        this.vec = vec;
        this.cabinas = cabinas;
        this.autos = autos;
        this.montoTotal= montoToal;
        this.tiempos= tiempos;
        
        
    }

    @Override
    public String toString() {
    //    return "VectorAux{" + "montoTotal=" + montoTotal + ", vec=" + vec + ", cabinas=" + cabinas + ", autos=" + autos + '}';
        return "VectorAux{" + "montoTotal=" + montoTotal + ", vec=" + vec + ", cabinas=" + cabinas + ", autos=" + autos + '}';
    
    }

    public int getMontoTotal() {
        return montoTotal;
    }

    public void setMontoTotal(int montoTotal) {
        this.montoTotal = montoTotal;
    }

    public ArrayList<Double> getTiempos() {
        return tiempos;
    }

    public void setTiempos(ArrayList<Double> tiempos) {
        this.tiempos = tiempos;
    }

    



    public VectorEstado getVec() {
        return vec;
    }

    public void setVec(VectorEstado vec) {
        this.vec = vec;
    }

    public ArrayList<Cabina> getCabinas() {
        return cabinas;
    }
    
    

    public void setCabinas(ArrayList<Cabina> cabinas) {
        this.cabinas = cabinas;
    }

    public ArrayList<Auto> getAutos() {
        return autos;
    }

    public void setAutos(ArrayList<Auto> autos) {
        this.autos = autos;
    }



    
    
    
    
    
    
    
}
