
package simulador;


public class Auto {
    double tiempoLlegada;
    double tiempoServicio;
    double tiempoEntrada = 0;
    double tiempoSalida;
    double tiempoEspera;
    
    public Auto GenerarAuto(double tiempoLlegada,double tiempoServicio){
        Auto auto = new Auto();
        auto.tiempoLlegada = tiempoLlegada;
        auto.tiempoServicio = tiempoServicio;
        return auto;
    }
}
