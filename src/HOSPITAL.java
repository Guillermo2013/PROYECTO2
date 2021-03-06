
import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.Calendar;
import java.util.Scanner;

public final class HOSPITAL {

    private RandomAccessFile DOCTORES;
    private RandomAccessFile codigoDoctores;
    private RandomAccessFile PACIENTES;
    private RandomAccessFile codigoPacientes;
    private RandomAccessFile tipoPaciente;
    private RandomAccessFile codTipoPaciente;
    private RandomAccessFile citas;
    private RandomAccessFile codigoCitas;
    private RandomAccessFile atendidas;
    private Scanner sc = new Scanner(System.in);

    public HOSPITAL() {
        creardirs();
        try {
            DOCTORES = new RandomAccessFile("HOSPITAL/DOCTORES/Doctores.mnt", "rw");
            PACIENTES = new RandomAccessFile("HOSPITAL/PACIENTES/Pacientes.mnt", "rw");
            codigoDoctores = new RandomAccessFile("HOSPITAL/DOCTORES/codidosDoctores.mnt", "rw");
            codigoPacientes = new RandomAccessFile("HOSPITAL/PACIENTES/codigoPacientes.mnt", "rw");
            codTipoPaciente = new RandomAccessFile("HOSPITAL/PACIENTES/codTipoPacientes.mnt", "rw");
            tipoPaciente = new RandomAccessFile("HOSPITAL/PACIENTES/tipoPacientes.mnt", "rw");
            citas = new RandomAccessFile("HOSPITAL/CITAS/citas.jhp", "rw");
            codigoCitas = new RandomAccessFile("HOSPITAL/CITAS/codigoCitas.jhp", "rw");
            atendidas = new RandomAccessFile("HOSPITAL/CITAS/atendidas.jhp", "rw");
            initfile();
        } catch (IOException e) {
            System.out.println("ERRRO : " + e.getMessage());
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

    private void initfile() throws IOException {
        codigoDoctores.writeInt(1);
        codigoPacientes.writeInt(1);
        codigoCitas.writeInt(1);
        codTipoPaciente.writeInt(1);
    }

    public int getCodigoDoctores() throws IOException {
        codigoDoctores.seek(0);
        int codigo = codigoDoctores.readInt();
        codigoDoctores.seek(0);
        codigoDoctores.writeInt(codigo + 1);
        return codigo;
    }

    public int getCodigoTipoPaciente() throws IOException {
        codTipoPaciente.seek(0);
        int codigo = codTipoPaciente.readInt();
        codTipoPaciente.seek(0);
        codTipoPaciente.writeInt(codigo + 1);
        return codigo;
    }

    public int getCodigoPaciente() throws IOException {
        codigoPacientes.seek(0);
        int codigo = codigoPacientes.readInt();
        codigoPacientes.seek(0);
        codigoPacientes.writeInt(codigo + 1);
        return codigo;
    }

    public int getCodigoCita() throws IOException {
        codigoCitas.seek(0);
        int codigo = codigoCitas.readInt();
        codigoCitas.seek(0);
        codigoCitas.writeInt(codigo + 1);
        return codigo;
    }

    public void añadirDoctor() throws IOException {
        DOCTORES.seek(DOCTORES.length());
        System.out.println("Ingrese el nombre completo:");
        String nom = sc.next();

        System.out.println("Direccion de domicilio:");
        String domicilio = sc.next();
        System.out.println("Ingrese el numero de telefono:");
        int telefono = sc.nextInt();
        System.out.println("Ingrese la expecialidad");
        String especialidad = sc.next();
        boolean actividad = true;

        DOCTORES.writeInt(getCodigoDoctores());
        DOCTORES.writeUTF(nom);
        DOCTORES.writeUTF(domicilio);
        DOCTORES.writeInt(telefono);
        DOCTORES.writeUTF(especialidad);
        DOCTORES.writeBoolean(actividad);
        System.out.println("Se guardo con exito");
        sc.reset();
    }

    public void listarDoctoresActivos() throws IOException {
        DOCTORES.seek(0);
        while (DOCTORES.getFilePointer() < DOCTORES.length()) {
            int cod = DOCTORES.readInt();
            String nom = DOCTORES.readUTF();
            String domicilio = DOCTORES.readUTF();
            int telefon = DOCTORES.readInt();
            String especialidad = DOCTORES.readUTF();
            boolean activo = DOCTORES.readBoolean();
            if (activo) {
                System.out.println(" Codigo:" + cod + " Nombre:" + nom + " Domicilio:" + domicilio + "  Numero de telefono:" + telefon + " Espeialidad:" + especialidad + "  Esta activo");
            }
        }
        System.out.println("Operacion realizada con exito");
    }

    public boolean buscar(int id) throws IOException {
        DOCTORES.seek(0);
        boolean busqueda = false;
        while (DOCTORES.getFilePointer() < DOCTORES.length()) {
            int cod = DOCTORES.readInt();
            if (cod == id) {
                busqueda = true;
                break;
            } else if (cod != id) {
                DOCTORES.readUTF();
                DOCTORES.readUTF();
                DOCTORES.readInt();
                DOCTORES.readUTF();
                DOCTORES.readBoolean();
                if (DOCTORES.getFilePointer() == DOCTORES.length()) {
                    System.out.println("No se encontro el id ");
                    return false;
                }
                busqueda = false;

            }
        }
        return busqueda;
    }

    public void cambiaActivo() throws IOException {
        System.out.println("Que id es el que busca");
        int id = sc.nextInt();
        String repuesta = "";
        if (buscar(id)) {
            DOCTORES.readUTF();
            DOCTORES.readUTF();
            DOCTORES.readInt();
            DOCTORES.readUTF();
            boolean actividad = DOCTORES.readBoolean();
            if (actividad == true) {
                System.out.println("El doctor esta de alta");
                System.out.println("Seguro que dese dalo de baja SI/NO");
                repuesta = sc.next().toUpperCase();
                if (repuesta.equals("SI")) {
                    DOCTORES.seek(DOCTORES.getFilePointer() - 1);
                    DOCTORES.writeBoolean(false);
                }
            } else if (actividad == false) {
                System.out.println("El doctor esta de baja");
                System.out.println("Seguro que dese dalo de alta SI/NO");
                repuesta = sc.next().toUpperCase();
                if (repuesta.equals("SI")) {
                    DOCTORES.seek(DOCTORES.getFilePointer() - 1);
                    DOCTORES.writeBoolean(true);
                }
            }
            System.out.println("Operacion realizada con exito");
        }
    }

    public void agregarTipoPaciente() throws IOException {
        String repuesta;
        do {
            tipoPaciente.seek(tipoPaciente.length());
            tipoPaciente.writeInt(getCodigoTipoPaciente());
            System.out.println("ingrese el tipo de paciente ");
            String tipoPaci = sc.next();
            tipoPaciente.writeUTF(tipoPaci);
            System.out.println("desea otro tipo de paciente SI/NO:");
            repuesta = sc.next().toUpperCase();
        } while (repuesta.equals("SI"));
        System.out.println("Operacion realizada con exito");
    }

    public void imprimirTipoPaciente() throws IOException {
        tipoPaciente.seek(0);
        System.out.println("Tipo de paciente\nOpciones\n-------");
        while (tipoPaciente.getFilePointer() < tipoPaciente.length()) {
            int codigo = tipoPaciente.readInt();
            String tipo = tipoPaciente.readUTF();
            System.out.println(codigo + " " + tipo);
        }
    }

    public boolean buscarPaciente(int id) throws IOException {
        PACIENTES.seek(0);
        boolean busqueda = false;
        while (PACIENTES.getFilePointer() < PACIENTES.length()) {
            int cod = PACIENTES.readInt();
            if (cod == id) {
                return true;

            } else if (cod != id) {
                PACIENTES.readUTF();
                PACIENTES.seek(PACIENTES.getFilePointer() + 12);
                PACIENTES.readBoolean();
                if (PACIENTES.getFilePointer() == PACIENTES.length()) {
                    System.out.println("No se encontro el id ");

                }
                busqueda = false;

            }
        }
        return busqueda;
    }

    public void cambiaVidaPaciente() throws IOException {
        System.out.println("Que id es el que busca");
        int id = sc.nextInt();
        String repuesta = "";
        if (buscarPaciente(id)) {
            String nombre = PACIENTES.readUTF();
            int telefono = PACIENTES.readInt();
            int edad = PACIENTES.readInt();
            int tipo = PACIENTES.readInt();
            boolean actividad = PACIENTES.readBoolean();
            if (actividad == true) {
                System.out.println("El pasiente :" + nombre + " con telefono " + telefono + " con edad " + edad + " y con codigo de tipo de pasiente " + tipo);
                System.out.println("El pasiente esta vivo seguro que se murio SI/NO");
                repuesta = sc.next().toUpperCase();
                if (repuesta.equals("SI")) {
                    PACIENTES.seek(PACIENTES.getFilePointer() - 1);
                    PACIENTES.writeBoolean(false);
                }
            }
        }
    }

    public void agregarPaciente() throws IOException {
        PACIENTES.seek(PACIENTES.length());
        boolean busqueda;
        System.out.println("Ingrese el nombre del paciente ");
        String nombre = sc.next();
        System.out.println("Ingrese el telefono del pasiente ");
        int telefono = sc.nextInt();
        System.out.println("Ingrese la edad ");
        int edad = sc.nextInt();
        if (tipoPaciente.length() == 0) {
            agregarTipoPaciente();
        } else {
        }
        int tipoCodigo;
        imprimirTipoPaciente();
        do {

            System.out.println("ingrese el codigo del tipo de paciente");
            tipoCodigo = sc.nextInt();
            if (tipoCodigo > -1) {
                busqueda = bucarTipoPciente(tipoCodigo);
                if (busqueda == true) {
                    System.out.println("descripcion: " + tipoPaciente.readUTF());
                }
            } else {
                busqueda = false;
            }
        } while (busqueda == false);
        if (busqueda != false && tipoCodigo > -1) {
            PACIENTES.writeInt(getCodigoPaciente());
            PACIENTES.writeUTF(nombre);
            PACIENTES.writeInt(telefono);
            PACIENTES.writeInt(edad);
            PACIENTES.writeInt(tipoCodigo);
            PACIENTES.writeBoolean(true);


            System.out.println("Operacion realizada con exito");
        } else {
            System.out.println("Operacion cancelada");
        }

    }

    private boolean bucarTipoPciente(int tipo) throws IOException {
        tipoPaciente.seek(0);
        boolean enBusqueda = false;
        while (tipoPaciente.getFilePointer() < tipoPaciente.length()) {
            int cod = tipoPaciente.readInt();

            if (cod == tipo) {

                enBusqueda = true;
                return enBusqueda;
            } else {
                tipoPaciente.readUTF();
                enBusqueda = false;

            }
        }
        return enBusqueda;
    }

    public void listarPaciente(boolean estado) throws IOException {
        PACIENTES.seek(0);
        while (PACIENTES.getFilePointer() < PACIENTES.length()) {
            int codigo = PACIENTES.readInt();
            String nombre = PACIENTES.readUTF();
            int telefono = PACIENTES.readInt();
            int edad = PACIENTES.readInt();
            int tipo = PACIENTES.readInt();
            String nombreTipo = "";
            tipoPaciente.seek(0);
            while (tipoPaciente.getFilePointer() < tipoPaciente.length()) {
                int cod = tipoPaciente.readInt();
                if (tipo == cod) {
                    nombreTipo = tipoPaciente.readUTF();
                } else {
                    tipoPaciente.readUTF();
                }
            }
            boolean vivo = PACIENTES.readBoolean();
            if (vivo == estado) {
                System.out.println("Codigo " + codigo + " Nombre " + nombre + " Telefono " + telefono + " Edad " + edad + " Tipo de paciente " + nombreTipo);
            }
        }
        System.out.println("Operacion realizada con exito");
    }

    public void generarCita() throws IOException {
        System.out.println("Ingrese el id del paciente");
        int id = sc.nextInt();
        if (id != -1) {
            if (buscarPaciente(id)) {
                String nombre = PACIENTES.readUTF();
                int telefono = PACIENTES.readInt();
                int edad = PACIENTES.readInt();
                int tipo = PACIENTES.readInt();
                System.out.println("El pasiente :" + nombre + " con telefono " + telefono + " con edad " + edad + " y con codigo de tipo de pasiente " + tipo);

                System.out.println("Ingrese el id del doctor");
                int idoct = sc.nextInt();
                if (idoct != -1) {
                    if (buscar(idoct)) {
                        String nombreD = DOCTORES.readUTF();
                        String dirre = DOCTORES.readUTF();
                        int telef = DOCTORES.readInt();
                        String espe = DOCTORES.readUTF();
                        boolean estadodoc = DOCTORES.readBoolean();
                        if (estadodoc == true) {
                            System.out.println("El doctor :" + nombreD + " con telefono " + telef + " con dirrecion " + dirre + " y especialidad " + espe);

                            Calendar c = Calendar.getInstance();
                            System.out.println("INGRESE LA DIA  QUE DESEA");
                            int dia = sc.nextInt();
                            System.out.println("INGRESE LA MES  QUE DESEA 1.ENERO...12.DICIEMBRE");
                            int mese = sc.nextInt() - 1;

                            System.out.println("INGRESE LA AÑO  QUE DESEA ");
                            int año = sc.nextInt();
                            c.set(año, mese, dia);
                            long fecha = c.getTimeInMillis();
                            String estado = Estado.PENDIE.toString();
                            System.out.println("DECRIPCION DE POR QUE ASE LA CITA ");
                            String descip = sc.next();
                            citas.writeInt(getCodigoCita());
                            citas.writeInt(id);
                            citas.writeInt(idoct);
                            citas.writeLong(fecha);
                            citas.writeUTF(descip);
                            citas.writeUTF(estado);
                            System.out.println("Operacion ralizada con exito");
                        } else {
                            System.out.println("el doctor esta inactivo");
                        }
                    } else {
                        System.out.println("EL ID DEL DOCTOR NO EXISTE");
                    }

                } else {
                    System.out.println("Ha decidido no seguir generando la cita");
                }
            } else {
                System.out.println("EL IDE DE PASIENTE NO EXISTE");
            }
        } else {
            System.out.println("Ha decidido no seguir generando la cita");
        }
    }

    public void cancelarCita() throws IOException {
        citas.seek(0);
        System.out.println("Ingres el id que esta buscando");
        int busqueda = sc.nextInt();
        while (citas.getFilePointer() < citas.length()) {
            int cod = citas.readInt();
            citas.seek(citas.getFilePointer() + 8);
            citas.readLong();
            citas.readUTF();
            if (cod == busqueda) {
                System.out.println("Seguro que desea cancelar la cita SI/NO");
                String respuesta = sc.next().toUpperCase();
                if (respuesta.equals("SI")) {
                    String estado = Estado.CANCEL.toString();
                    citas.writeUTF(estado);
                    System.out.println("Operacion realizada con exito");
                    break;
                }
            } else {
                citas.readUTF();
                if (citas.getFilePointer() == citas.length()) {
                    System.out.println("no se a encotrado");
                }
            }

        }
    }

    public void imprimirCitasCanceladas() throws IOException {
        citas.seek(0);
        while (citas.getFilePointer() < citas.length()) {
            int id = citas.readInt();
            int idp = citas.readInt();
            int idd = citas.readInt();
            long fecha = citas.readLong();
            Calendar c = Calendar.getInstance();
            c.setTimeInMillis(fecha);
            String des = citas.readUTF();
            String esta = citas.readUTF();
            if (esta.equals(Estado.CANCEL.toString())) {
                buscarPaciente(idp);
                buscar(idd);
                System.out.println("id citas:" + id + " id de paciente:" + PACIENTES.readUTF() + " id del doctor:" + DOCTORES.readUTF() + " la fecha de:" + c.getTime() + " la descripcion es:" + des + " esta cancelada");
            }
        }
    }

    public void imprimirXtipoPaciente(int cod) throws IOException {
        PACIENTES.seek(0);
        while (PACIENTES.getFilePointer() < PACIENTES.length()) {
            int codigo = PACIENTES.readInt();
            String nombre = PACIENTES.readUTF();
            int telefono = PACIENTES.readInt();
            int edad = PACIENTES.readInt();
            int tipo = PACIENTES.readInt();
            boolean estado = PACIENTES.readBoolean();
            if (tipo == cod && bucarTipoPciente(tipo)) {
                System.out.println("Codigo " + codigo + " Nombre " + nombre + " Telefono " + telefono + " Edad " + edad + " Tipo de paciente " + tipoPaciente.readUTF() + " estado :" + (estado == true ? "vivo" : "muerto"));
            }
        }
    }

    public boolean cita(int cita) throws IOException {
        citas.seek(0);
        boolean puntero = false;
        while (citas.getFilePointer() < citas.length()) {
            int iide = citas.readInt();
            if (iide == cita) {
                return true;
            } else {
                citas.readInt();
                citas.readInt();
                citas.readLong();
                citas.readUTF();
                citas.readUTF();
                puntero = false;
            }
        }
        return puntero;

    }

    public void imprimirAtendidas(int id) throws IOException {
        atendidas.seek(0);
        double montoTotal = 0;
        if (buscar(id)) {
            while (atendidas.getFilePointer() < atendidas.length()) {
                int cod = atendidas.readInt();
                int buscado = atendidas.readInt();
                int idpas = atendidas.readInt();
                buscarPaciente(idpas);
                String nombrePaci = PACIENTES.readUTF();
                String sintomas = atendidas.readUTF();
                String dianos = atendidas.readUTF();
                double monto = atendidas.readDouble();
                Calendar c = Calendar.getInstance();
                long fecha = atendidas.readLong();
                c.setTimeInMillis(fecha);
                if (buscado == id) {
                    montoTotal += monto;
                    System.out.println(+cod + " con el paciente:" + nombrePaci + " con sintomas " + sintomas + " con el dinostico " + dianos + " el la fecha " + c.getTime());
                }
            }
            System.out.println("El total ganado por ese doctor es de " + montoTotal);
        } else {
            System.out.println("el id de doctor no existe");

        }

    }

    public void imprimirMonto() throws IOException {
        atendidas.seek(0);
        double monto = 0;
        while (atendidas.getFilePointer() < atendidas.length()) {
            atendidas.readInt();
            atendidas.readInt();
            atendidas.readInt();
            atendidas.readUTF();
            atendidas.readUTF();
            monto += atendidas.readDouble();
            atendidas.readLong();
        }
        System.out.println("el total ganado por el hospital es de " + monto);
    }

    public void imprimirCitasFechasExpiradas() throws IOException {
        citas.seek(0);
        while (citas.getFilePointer() < citas.length()) {
            int id = citas.readInt();
            int idPas = citas.readInt();
            citas.readInt();
            long fecha = citas.readLong();
            String decrip = citas.readUTF();
            String estado = citas.readUTF();
            if (estado.equals(Estado.PENDIE.toString())) {
                Calendar c = Calendar.getInstance();
                long fechaActual = c.getTimeInMillis();
                if (fechaActual > fecha) {
                    c.setTimeInMillis(fecha);
                    buscarPaciente(idPas);
                    System.out.println(id + " el pasiente: " + PACIENTES.readUTF() + " el la fecha " + c.getTime() + " con la descripcion:" + decrip);
                }
            }

        }

    }

    public void atenderCita() throws IOException {
        boolean busca = false;
        do {
            System.out.println("Ingrese el codigo de Doctor");
            int codoct = sc.nextInt();
            busca = buscar(codoct);
            if (codoct != -1) {
                if (busca == true) {
                    String nombreD = DOCTORES.readUTF();
                    String dirre = DOCTORES.readUTF();
                    int telef = DOCTORES.readInt();
                    String espe = DOCTORES.readUTF();
                    boolean estadodoc = DOCTORES.readBoolean();
                    System.out.println("El doctor :" + nombreD + " con telefono " + telef + " con dirrecion " + dirre + " y especialidad " + espe);

                    int cod = validar(codoct);
                    if (cod > 0) {
                        int idpas = citas.readInt();
                        System.out.println("Ingrese los sintomas:");
                        String sintomas = sc.next();
                        System.out.println("Ingrese el diagnostico:");
                        String diagnostico = sc.next();
                        System.out.println("Ingrese el monto:");
                        double monto = sc.nextInt();
                        Calendar c = Calendar.getInstance();
                        long fecha = c.getTimeInMillis();
                        atendidas.writeInt(cod);
                        atendidas.writeInt(codoct);
                        atendidas.writeInt(idpas);
                        atendidas.writeUTF(sintomas);
                        atendidas.writeUTF(diagnostico);
                        atendidas.writeDouble(monto);
                        atendidas.writeLong(fecha);
                        citas.readLong();
                        citas.readUTF();
                        citas.writeUTF(Estado.ATENDI.toString());
                        System.out.println("Operacion realizada con exito");

                    } else {
                        System.out.println("El codigo de cita no no concuerda con el de docto o no existe");
                    }

                } else {
                    System.out.println("no se escontro el id del doctor");
                    busca = false;
                }
            } else {
                System.out.println("ha decidido salir al menu");
                busca = true;
            }
        } while (busca == false);

    }

    private int validar(int codDoct) throws IOException {
        int cantidadOp = 0;
        System.out.println("OPCIONES DE ATENDER\n------");
        citas.seek(0);
        while (citas.getFilePointer() < citas.length()) {
            int cod = citas.readInt();
            int codP = citas.readInt();
            int codd = citas.readInt();
            long fecha = citas.readLong();
            String descrip = citas.readUTF();
            String estado = citas.readUTF();
            if (codDoct == codd) {
                if (estado.equals(Estado.PENDIE.toString())) {
                    if (buscarPaciente(codP)) {
                        Calendar c = Calendar.getInstance();
                        c.setTimeInMillis(fecha);
                        String paciente = PACIENTES.readUTF();
                        System.out.println(cod + " paciente:" + paciente + " fecha:" + c.getTime() + " con la descripcion:" + descrip);
                        cantidadOp = 1;
                    }
                }
            }
        }

        if (cantidadOp > 0) {
            System.out.println("Ingrese una cita que antender");
            int cita = sc.nextInt();
            if (cita(cita)) {
                long pos = citas.getFilePointer();
                int codCom = citas.readInt();
                if (codCom == codDoct) {

                    return cita;
                }
            }
        }
        return cantidadOp;

    }
}
