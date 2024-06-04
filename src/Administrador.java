import java.util.Scanner;
import java.util.ArrayList;
import java.util.Iterator;
public class Administrador {
    public static final String contraseña = "1234A";
    public static ArrayList departamentos = new ArrayList();
    public static ArrayList salas = new ArrayList();

    //Menu de Administrador
    public static void menuadmin() {
        Scanner in = new Scanner(System.in);
        //Centinela de salida
        boolean logout = false;
        String opcionAdmin;
        do {
            System.out.println("Acceso: Administrador");
            System.out.println("1. \t Añadir Departamento");
            System.out.println("2. \t Eliminar Departamento");
            System.out.println("3. \t Listar Departamentos");
            System.out.println("4. \t Crear nueva sala");
            System.out.println("5. \t Eliminar sala");
            System.out.println("6. \t Listar Salas");
            System.out.println("7. \t Listar Reservas");
            System.out.println("8. \t Cerrar sesion");
            opcionAdmin = in.nextLine();
            //Switch con opciones
            switch (opcionAdmin) {
                case "1":
                    //Declaracion de variables y texto por consola del usuario
                    String nombre;
                    String codigo;
                    String represent;
                    System.out.println("Bienvenido a la creacion de departamentos");
                    System.out.println("Nombre de departamento a crear:");
                    nombre = in.nextLine();
                    System.out.println("Codigo de departamento que tendrá");
                    codigo = in.nextLine();
                    System.out.println("Representante de departamento");
                    represent = in.nextLine();
                    anadirDep(nombre, codigo, represent);
                    break;
                case "2":
                    String codigopru;
                    System.out.println("Dime el codigo del departamento a eliminar");
                    codigopru = in.nextLine();

                    if (buscarCodDep(codigopru) != null) {
                        Departamento d1 = buscarCodDep(codigopru);
                        departamentos.remove(d1);
                        System.out.println("Borrado de departamento exitoso");
                        Reserva r1;
                        Reserva r2 = null;
                        
                        ArrayList reservasAEliminar = null;
                        Iterator iteradorSalas = salas.iterator();
                        
                        while (iteradorSalas.hasNext()) {
                            Sala s1 = (Sala) iteradorSalas.next();
                            if (s1 != null) {
                                Iterator iteradorReser = s1.reservas.iterator();
                                while (iteradorReser.hasNext()){
                                    reservasAEliminar= new ArrayList();
                                    r1 = (Reserva) iteradorReser.next();
                                    if (r1!=null){
                                        if (r1.getCodDepReservador().equalsIgnoreCase(codigopru)){
                                            reservasAEliminar.add(r1);
                                        }
                                    }
                                }
                                if (reservasAEliminar!=null){
                                    s1.reservas.removeAll(reservasAEliminar);
                                }

                            }
                        }
                        System.out.println("Sus reservas se han eliminado correctamente");
                    } else {
                        System.out.println("No se a encontrado el departamento");
                    }
                    break;
                case "3":
                    listarDep();
                    break;
                case "4":
                    String nombresala;
                    String codSala;
                    System.out.println("Bienvenido a la creacion de Salas");
                    System.out.println("Indicame el nombre de la sala a crear");
                    nombresala = in.nextLine();
                    System.out.println("Indicame el codigo de la sala");
                    codSala = in.nextLine();
                    if (!buscarSalaTrue(nombresala)){
                        Sala s1 = new Sala(nombresala,codSala);
                        salas.add(s1);
                    }else{
                        System.out.println("Error, sala ya existente");
                    }
                    break;
                case "5":
                    String salaAEliminar;
                    System.out.println("Indicame el nombre de la sala a eliminar");
                    salaAEliminar = in.nextLine();
                    if (buscarSalaTrue(salaAEliminar)){
                        salas.remove(buscarSalaID(salaAEliminar));
                    }else{
                        System.out.println("La sala indicada no existe");
                    }
                    break;
                case "6":
                    listarSalas();
                    break;
                case "7":
                    listarSalasConReservas();
                    break;
                case "8":
                    System.out.println("Cerrando sesion...");
                    logout = true;
                    
                    break;
                default:
                    System.out.println("Opcion de administrador no correcta \n Introduzca una opcion valida");
                    break;
            }
        } while (!logout);
        //in.close();
    }
    //Metodo que nos pemritira añadir departamento, indicando un nombre, codigo y representante
    public static void anadirDep(String nombre, String codigo, String representante) {
        //Si esta añadido de antes el metodo buscarNomDep devolverá true, lo mismo con buscarCodDepTrue
        //Por ello si las dos opciones devuelven false, significará que no hay ningun departamento creado
        if (!buscarNomDep(nombre)&&!buscarCodDepTrue(codigo)) {
            //Con los valores dados crearemos el departamento y lo añadiremos
            Departamento d1 = new Departamento(nombre, codigo, representante);
            //Al ser una lista lo añadiremos con .add()
            departamentos.add(d1);
        } else {
            //Si no se cumple la opcion anterior significara que hay algun departamento igual ya creado
            System.out.println("Nombre/Codigo ya existente");
        }
    }
    //metodo para listar Departamentos usando el toString de Departamento
    public static void listarDep() {
        for (Object o : departamentos) {
            System.out.println(o);
        }
    }
    //metodo de buscar Departamento usando su codigo
    public static Departamento buscarCodDep(String codigo) {
        //Utilizamos un iterador para recorrer el departamento
        Iterator iterador = departamentos.iterator();
        //Mientras ese iterador continue:
        while (iterador.hasNext()){
            //Crearemos un departamento auxiliar pasandole el iterador que señala el departamento siguiente en la lista
            Departamento d1 = (Departamento) iterador.next();
            //Si ese departamento, su codigo es igual que el codigo pasado, significara que esta
            if (d1.getCodigo().equalsIgnoreCase(codigo)) {
                return d1;
            }
        }
        //Si cualquier opcion anterior no se cumple, devolvera null (Como si fuera false en boolean)
        return null;
    }
    //Realiza la misma busqueda anterior, solo que devulve true o false
    public static boolean buscarCodDepTrue(String codigo) {
        Iterator iterador = departamentos.iterator();
        while (iterador.hasNext()){
            Departamento d1 = (Departamento) iterador.next();
            if (d1.getCodigo().equalsIgnoreCase(codigo)) {
                return true;
            }
        }
        return false;
    }

    //Funciona igual que buscarCodDep solo que utilizando el nombre
    public static boolean buscarNomDep(String nombre) {
        Iterator iterador = departamentos.iterator();
        while (iterador.hasNext()) {
            Departamento d1 = (Departamento) iterador.next();
            if (d1.getNombre().equalsIgnoreCase(nombre)) {
                return true;
            }
        }
        return false;
    }

    //Es una funcion parecida a las anteriores, si el nombre de la sala esta ya creada, devuelve true
    //Sino, devuelve false
    public static boolean buscarSalaTrue(String nombre) {
        Iterator iterador = salas.iterator();
        while (iterador.hasNext()) {
            Sala s1 = (Sala) iterador.next();
            if (s1.getNombre().equalsIgnoreCase(nombre)) {
                return true;
            }
        }
        return false;
    }
    //Lo mismo que lo anterior solo que en este caso devuelve la sala
    public static Sala buscarSalaID(String nombre) {
        Iterator iterador = salas.iterator();
        do {
            Sala s1 = (Sala) iterador.next();
            if (s1.getNombre().equalsIgnoreCase(nombre)) {
                return s1;
            }
        } while (iterador.hasNext());
        return null;
    }
    //Lo mismo que los anteriores metodos pero este caso devuelve la true o false segun el codigo de la sala
    public static boolean buscarCodSalaTrue(String codigo) {
        Iterator iterador = salas.iterator();
        while (iterador.hasNext()) {
            Sala s1 = (Sala) iterador.next();
            if (s1.getCodSala().equalsIgnoreCase(codigo)) {
                return true;
            }
        }
        return false;
    }
    //Lo mismo que el metodo de codigo anterior pero este caso devuelve la sala
    public static Sala buscarCodSalaID(String codigo) {
        Iterator iterador = salas.iterator();
        do {
            Sala s1 = (Sala) iterador.next();
            if (s1.getCodSala().equalsIgnoreCase(codigo)) {
                return s1;
            }
        } while (iterador.hasNext());
        return null;
    }
    //metodo que nos permite recorrer las salas utilizando el toString de las salas
    public static void listarSalas(){
        for (Object o : salas) {
            System.out.println(o);
        }
    }
    //metodo que nos permite recorrer las salas utilizando el toString de las salas y sus reservas
    public static void listarSalasConReservas(){
            Iterator iterator = salas.iterator();
            while (iterator.hasNext()) {
                Sala s1 = (Sala) iterator.next();
                if (s1 != null) {
                    s1.listarReservas();
                }
            }
    }
}


