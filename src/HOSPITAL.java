
import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.Scanner;

/**
 *
 * @author Guillermo Sandoval
 */
public final class HOSPITAL {
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
        initfile();
   }catch(IOException e){
        System.out.println("ERRRO : "+e.getMessage());
    }
    
    }
      public void creardirs() {
        File doct = new File("HOSPITAL/DOCTORES");
        doct.mkdirs();
        File pasci = new File("HOSPITAL/PACIENTES");
        pasci.mkdirs();
        File cita = new File("HOSPITAL/CITAS");
        cita.mkdirs();
    }
      private void initfile() throws IOException{
        if(codigoDoctores.length() == 0&&codigoPacientes.length()==0&&codigoCitas.length()==0){
            codigoDoctores.writeInt(1);
            codigoPacientes.writeInt(1);
            codigoCitas.writeInt(1);
        }
      }
      public int getCodigoDoctores()throws IOException{
        codigoDoctores.seek(0);
        int codigo = codigoDoctores.readInt();
        codigoDoctores.seek(0);
        codigoDoctores.writeInt(codigo+1);
        return codigo;
    }
      public int getCodigoPaciente()throws IOException{
        codigoPacientes.seek(0);
        int codigo = codigoPacientes.readInt();
        codigoPacientes.seek(0);
        codigoPacientes.writeInt(codigo+1);
        return codigo;
    }
      public int getCodigoCita()throws IOException{
        codigoCitas.seek(0);
        int codigo = codigoCitas.readInt();
        codigoCitas.seek(0);
        codigoCitas.writeInt(codigo+1);
        return codigo;
    }   
       
          
          public void añadirDoctor()throws IOException{
              DOCTORES.seek(DOCTORES.length());
              System.out.println("Ingrese el nombre completo:");
              String nom=sc.next();
              System.out.println("Direccion de domicilio:");
              String domicilio=sc.next();
              System.out.println("Ingrese el numero de telefono:");
              int telefono=sc.nextInt();
              System.out.println("Ingrese la expecialidad");
              String especialidad=sc.next();
              boolean actividad=true;
              
              DOCTORES.writeInt(getCodigoDoctores());
              DOCTORES.writeUTF(nom);
              DOCTORES.writeUTF(domicilio);
              DOCTORES.writeInt(telefono);
              DOCTORES.writeUTF(especialidad);
              DOCTORES.writeBoolean(actividad);
             }
          public boolean buscarDoctor(int id)throws IOException{
              DOCTORES.seek(0);
              boolean busqueda=false;
              int codigo;
             while(DOCTORES.getFilePointer()<DOCTORES.length()){             
              
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
                    if (DOCTORES.getFilePointer()==DOCTORES.length())
                         System.out.println("No se a encontrado id");
                 }
             }
                          
              
             return busqueda;
          }

            public void cambiarActivo()throws IOException{
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