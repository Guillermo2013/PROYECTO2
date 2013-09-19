
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
        Scanner sc = new Scanner(System.in);
        int menu = 0;
        int respuesta = 0;
        do {
            try {
                System.out.println("MENU\n-----\n 1.Mantenimiento\n 2.Generar Citas \n 3.Cancelar cita \n 4.Atender cita \n 5.Reporte \n 6.salir");
                menu = sc.nextInt();
                switch (menu) {
                    case 1:
                        System.out.println("SUBMENU DE MANTENIMIENTO\n -----\n 1.Agregar doctor \n 2.Dar de alta/baja de doctor \n 3.Agregar paciente  \n 4.Dar de baja a paciente \n 5.Agregar tipo de paciente \n 6.salir a menu principal");
                        respuesta = sc.nextInt();
                        switch (respuesta) {
                            case 1:
                                hpt.añadirDoctor();
                                break;
                            case 2:
                                hpt.cambiaActivo();
                                break;
                            case 3:
                                hpt.agregarPaciente();
                                break;
                            case 4:
                                hpt.cambiaVidaPaciente();
                                break;

                            case 5:
                                hpt.agregarTipoPaciente();
                                break;
                        }
                        break;
                    case 2:
                        hpt.generarCita();
                        break;
                    case 3:
                        hpt.cancelarCita();
                        break;
                    case 4:
                        hpt.atenderCita();
                        break;
                    case 5:
                        System.out.println("SUBMENU DE REPORTE\n -----\n 1.Lista de paciente vivo o mueto \n 2.Lista de doctores activos  \n 3.Lista de tipo  paciente  \n 4.Lista de citas canceladas \n 5.Lista de citas atendidas por doctor \n 6.Ver monto total \n 7.Fechas de citas expiradas \n 8.Salir a menu principal");
                        respuesta = sc.nextInt();
                        switch (respuesta) {
                            case 1:
                                boolean busca = true;
                                System.out.println("DESEA\n1.VIVOS \n2.MUERTOS");
                                int vivo = sc.nextInt();
                                if (vivo == 1) {
                                    busca = true;
                                    hpt.listarPaciente(busca);
                                } else if (vivo == 2) {
                                    busca = false;
                                    hpt.listarPaciente(busca);
                                } else {
                                    System.out.println("OPCION NO VALIDA");
                                }
                                break;
                            case 2:
                                hpt.listarDoctoresActivos();
                                break;
                            case 3:
                                System.out.println("eliga una de lo tipo de paciente que desea elistar ");
                                hpt.imprimirTipoPaciente();
                                int tipo = sc.nextInt();
                                hpt.imprimirXtipoPaciente(tipo);
                                break;
                            case 4:
                                hpt.imprimirCitasCanceladas();
                                break;
                            case 5:
                                System.out.println(" ingrese id del doctor que quiere ver su citas atendidas");
                                int id = sc.nextInt();
                                hpt.imprimirAtendidas(id);
                                break;
                            case 6:
                                hpt.imprimirMonto();
                                break;
                            case 7:
                                hpt.imprimirCitasFechasExpiradas();
                                break;
                        }
                }
            } catch (IOException a) {
                System.out.println("Error:" + a.getMessage());
            }catch(Exception e){
                System.out.println("Erro:"+e.getMessage());
            }
        } while (menu != 6);
    }
}
