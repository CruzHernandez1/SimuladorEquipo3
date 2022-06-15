package simulador;

import java.text.DecimalFormat;
import java.util.InputMismatchException;
import java.util.LinkedList;

import java.util.Scanner;

public class Simulacion {
    
     public static void main(String[] args) {
        
        Auto auto = new Auto();Auto bomba = new Auto();Auto auxiliar = new Auto();
        LinkedList cola = new LinkedList();
        double riAutos[] = new double[500]; //Números aleatorios para el tiempo de llegada
        double riServicio[] = new double[500]; //Números aleatorios para el tiempo de servicio
        double tiempoLlegada[] = new double[riAutos.length]; //Vector que guarda los tiempos de llegada
        double tiempoServicio[] = new double[riServicio.length]; //Vector que guarda los tiempos de servicio
        double sumaLlegada = 0, sumaServicio = 0, sumaEspera = 0, horaSalida = 0, espera, aux, tiempoPolicia = 15;
        double tiempoSalida = 0;
        double tiempoEspera = 0;
        double tiempoEntrada = 0;
        
        int contRegular = 0, contPolicia = 0, contEspera = 0;
        double tiempo = 0;
        //Valores para el generador
        Scanner leer = new Scanner(System.in);
        int Xo = 0, a = 0, b = 0;

       boolean error;
        do {
            error = true;
        try{ 
            System.out.println("Ingrese el numero del generador a usar, 1-10");
        int op = leer.nextInt();
         //Llama al método generador para generar los números aleatorios y llenar los vectores
        switch (op) {
            case 1:
                Xo = 15; a = 21; b = 19;
                generador(Xo, a, b, riAutos, riServicio);
                break;
            case 2:
                Xo = 13; a = 13; b = 20;
                generador(Xo, a, b, riAutos, riServicio);
                break;
            case 3:
                Xo = 17; a = 19; b = 12;
                generador(Xo, a, b, riAutos, riServicio);
                break;
            case 4:
                Xo = 17; a = 19; b = 20;
                generador(Xo, a, b, riAutos, riServicio);
                break;
            case 5:
                Xo = 11; a = 13; b = 14;
                generador(Xo, a, b, riAutos, riServicio);
                break;
            case 6:
                Xo = 15; a = 19; b = 16;
                generador(Xo, a, b, riAutos, riServicio);
                break;
            case 7:
                Xo = 23; a = 27; b = 20;
                generador(Xo, a, b, riAutos, riServicio);
                break;
            case 8:
                Xo = 43; a = 19; b = 14;
                generador(Xo, a, b, riAutos, riServicio);
                break;
            case 9:
                Xo = 23; a = 43; b = 20;
                generador(Xo, a, b, riAutos, riServicio);
                break;
            case 10:
                Xo = 15; a = 21; b = 19;
                generador(Xo, a, b, riAutos, riServicio);
                break;
            default:
                System.out.println("Ingrese sólamente números del 1 al 10");
                error = false;
        }
        } catch(InputMismatchException e) {
            System.out.println("Ingrese sólamente números del 1 al 10");
            leer.nextLine();
            error = false;
        } 
        } while (error == false);
        //Llama al método generador para generar los números aleatorios y llenar los vectores
        generador(Xo, a, b, riAutos, riServicio);
        
        DecimalFormat df = new DecimalFormat("0.00000");

        
        for (int i = 0; i < riAutos.length; i++) {
            tiempoLlegada[i] = (-5.6) * (Math.log(riAutos[i])); //Tiempos de llegada exponencial de 5.6 min
            tiempoServicio[i] = (-4.8) * (Math.log(riServicio[i])); //Tiempos de servicio exponencial de 4.8 min

        }
        int i = 0;
        while(i < 500){
            
            if(tiempo == 0){
                cola.add(auto.GenerarAuto(tiempo, tiempoServicio[i]));
                contRegular ++;
            }
            else if(tiempo >= tiempoPolicia){
                tiempo = tiempoPolicia;
                tiempoPolicia += 30;
                if(cola.isEmpty()){
                    cola.add(auto.GenerarAuto(tiempo, tiempoServicio[i]));
                }else{
                    cola.add(1,auto.GenerarAuto(tiempo, tiempoServicio[i]));
                    
                }
                contPolicia ++;
                
            }else{
                cola.add(auto.GenerarAuto(tiempo, tiempoServicio[i]));
                contRegular ++;
            }
            
            bomba = (Auto) cola.get(0);
            
            bomba.tiempoEntrada = Math.max(tiempoSalida, bomba.tiempoLlegada);
            if(bomba.tiempoEntrada + bomba.tiempoServicio >= tiempo){
                
                tiempoSalida = bomba.tiempoEntrada + bomba.tiempoServicio;
                tiempoEspera = tiempoSalida - bomba.tiempoLlegada;
                cola.remove(0);
                sumaEspera += tiempoEspera;
                
            }
            sumaServicio += tiempoServicio[i];
            
            tiempo = tiempo + tiempoLlegada[i];
            i++;
        }
         System.out.println("Tiempo total: " +tiempo);
         System.out.println("Autos normales: " +contRegular);
         System.out.println("Autos de policia: " + contPolicia);
         System.out.println("Autos totales: " + (contPolicia + contRegular));
         System.out.println("Promedio de servicio: " +sumaServicio/500);
         System.out.println("Promedio de espera: " +sumaEspera/500);
         System.out.println("Puede cerrar el programa");
         int salite = leer.nextInt();
    }

    public static void generador(int Xo, int a, int b, double riAutos[], double riServicio[]) {
        int m, auxg;
        m = (int) Math.pow(2, b);
        double numeros[] = new double[m / 4];

        //Generador de números pseudoaleatorios
        for (int i = 1; i <= m / 4; i++) {
            auxg = (a * Xo) % m;
            numeros[i - 1] = ((float) Xo / m);
            Xo = auxg;
        }

        //Obtener números aleatorios para generar el tiempo de llegada y de servicio
        for (int i = 0; i < 500; i++) {
            riAutos[i] = numeros[i];
            riServicio[i] = numeros[i + 500];
        }
    }
}


