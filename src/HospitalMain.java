
import java.io.IOException;
import java.util.Scanner;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Guillermo Sandoval
 */
public class HospitalMain {
 public static void main(String[] args) {
        HOSPITAL hpt = new HOSPITAL();
        
        Scanner sc=new Scanner(System.in);
        
        int menu=0;
        
        try{
            System.out.println("MENU\n-----\n 1.Mantenimiento\n 2.Generar Citas \n 3.Cancelar cita \n 4.Atender cita \n 5.Reporte \n 6.salir");
            menu=sc.nextInt();

            switch(menu) {
                case 1 :
                         System.out.println("SUBMENU DE MANTENIMIENTO\n -----\n 1.Agregar doctor \n 2.Dar de alta/baja de doctor \n 3.Agregar paciente  \n 4.Dar de baja a paciente \n 5.Agregar tipo de paciente \n 6.salir a menu principal");
                         int respuesta=sc.nextInt();
                         switch(respuesta){
                             case 1:
                                 hpt.añadirDoctor();
                                 break;
                         }

                        break;

           }
       }catch(IOException a){
            System.out.println("Error:"+a.getMessage());
       }catch(Exception e){
            System.out.println("Error:");
       }
 
 }   
}
