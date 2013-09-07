
import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.Scanner;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Guillermo Sandoval
 */
public class HOSPITAL {
    private RandomAccessFile DOCTORES;
    private RandomAccessFile codigoDoctores;
    private RandomAccessFile PACIENTES;
    private RandomAccessFile codigoPacientes;
    private RandomAccessFile tipoPaciente;
    private RandomAccessFile citas;
    private RandomAccessFile codigoCitas;
    private Scanner sc = new Scanner(System.in);   
    
    public HOSPITAL() {
    try{
    
        creardirs();
        DOCTORES=new RandomAccessFile("HOSPITAL/DOCTORES/Doctores.mnt", "rw");
        PACIENTES=new RandomAccessFile("HOSPITAL/PACIENTES/Pacientes.mnt", "rw");
        codigoDoctores=new RandomAccessFile("HOSPITAL/DOCTORES/codidosDoctores.mnt", "rw");
        codigoPacientes=new RandomAccessFile("HOSPITAL/PACIENTES/codigoPacientes.mnt","rw");
        tipoPaciente = new RandomAccessFile("HOSPITAL/PACIENTES/tipoPacientes.mnt", "rw");
        citas=new RandomAccessFile("HOSPITAL/CITAS/citas.jhp", "rw");
        codigoCitas=new RandomAccessFile("HOSPITAL/CITAS/codigoCitas.jhp", "rw");
    }catch(IOException e){
        System.out.println("ERRRO : "+e.getMessage());
    }
    
    }
      private void creardirs() {
        File doct = new File("HOSPITAL/DOCTORES");
        doct.mkdirs();
        File pasci = new File("HOSPITAL/PACIENTES");
        pasci.mkdirs();
        File cita = new File("HOSPITAL/CITAS");
        cita.mkdirs();
    }
      private void initfile() throws IOException{
        if(codigoDoctores.length() == 0&&codigoPacientes.length()==0){
            codigoDoctores.writeInt(1);
            codigoPacientes.writeInt(1);
            codigoCitas.writeInt(1);
        }
      }
      private int getCodigoDoctores()throws IOException{
        codigoDoctores.seek(0);
        int codigo = codigoDoctores.readInt();
        codigoDoctores.seek(0);
        codigoDoctores.writeInt(codigo+1);
        return codigo;
    }
          private int getCodigoPaciente()throws IOException{
        codigoPacientes.seek(0);
        int codigo = codigoPacientes.readInt();
        codigoPacientes.seek(0);
        codigoPacientes.writeInt(codigo+1);
        return codigo;
    }
            private int getCodigoCita()throws IOException{
        codigoCitas.seek(0);
        int codigo = codigoCitas.readInt();
        codigoCitas.seek(0);
        codigoCitas.writeInt(codigo+1);
        return codigo;
    }   
          private void añadirPaciente()throws IOException{
              PACIENTES.seek(PACIENTES.length());
              System.out.println("Ingrese el nombre completo:");
              String nom=sc.next();
              System.out.println("Direccion de telefono:");
              int telefono=sc.nextInt();
              System.out.println("Ingrese el numero de edad:");
              int edad=sc.nextInt();
              
              
              
              PACIENTES.writeInt(getCodigoDoctores());
              PACIENTES.writeUTF(nom);
              PACIENTES.writeInt(telefono);
              PACIENTES.writeInt(edad);
              PACIENTES.writeUTF(especialidad);
              PACIENTES.writeBoolean(true);
              
          
          }
          private void añadirDoctor()throws IOException{
              DOCTORES.seek(DOCTORES.length());
              System.out.println("Ingrese el nombre completo:");
              String nom=sc.next();
              System.out.println("Direccion de domicilio:");
              String domicilio=sc.next();
              System.out.println("Ingrese el numero de telefono:");
              int telefono=sc.nextInt();
              System.out.println("Ingrese la expecialidad");
              String especialidad=sc.next();
              
              
              DOCTORES.writeInt(getCodigoDoctores());
              DOCTORES.writeUTF(nom);
              DOCTORES.writeUTF(domicilio);
              DOCTORES.writeInt(telefono);
              DOCTORES.writeUTF(especialidad);
              DOCTORES.writeBoolean(true);
             }
          private boolean buscarDoctor(int id)throws IOException{
              DOCTORES.seek(0);
              boolean busqueda;
              int codigo;
             do{
                  codigo=DOCTORES.readInt();
                 if (codigo==id){
                    busqueda=true;
                 }else{
                    busqueda=false;
                 DOCTORES.readUTF();
                 DOCTORES.readUTF();
                 DOCTORES.readInt();
                 DOCTORES.readUTF();
                 DOCTORES.readBoolean();
                    if (DOCTORES.getFilePointer()>=DOCTORES.length())
                         System.out.println("No se a encontrado id");
                 }
                 
             
             }while(codigo!=id&&DOCTORES.getFilePointer()>=DOCTORES.length());             
              
             return busqueda;
          }

            private void cambiarActivo()throws IOException{
                System.out.println("Ingrese el id que desea:");
                int id=sc.nextInt();
                if (buscarDoctor(id)){
                 DOCTORES.readUTF();
                 DOCTORES.readUTF();
                 DOCTORES.readInt();
                 DOCTORES.readUTF(); 
                   if(DOCTORES.readBoolean()){
                       System.out.println("Seguro que desea dar de baja s/n:");
                       char des=sc.next().charAt(0);
                       if (des=='s'){
                         DOCTORES.seek(DOCTORES.getFilePointer()-1);
                       DOCTORES.writeBoolean(false);
                       }
                   }else if(DOCTORES.readBoolean()==false){
                       System.out.println("Seguro que desea dar de alta s/n:");
                       char des=sc.next().charAt(0);
                       if (des=='s'){
                         DOCTORES.seek(DOCTORES.getFilePointer()-1);
                       DOCTORES.writeBoolean(true);
                       }
                   }
                
                }
            }
}
