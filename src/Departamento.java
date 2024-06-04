import java.time.DateTimeException;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.Scanner;
public class Departamento {
    private String nombre;
    private String codigo;
    private String representante;
    public static String codMenuDep;

    public Departamento(String nombre, String codigo, String representante) {
        this.nombre = nombre;
        this.codigo = codigo;
        this.representante = representante;
    }

    public static void menuDep() {
        Scanner in = new Scanner(System.in);
        boolean logout =false;
        String opcionDep;
        do {
            System.out.println("\n Menú del Departamento: --" + codMenuDep.toUpperCase()+"--");
            System.out.println("1. Añadir reserva");
            System.out.println("2. Cancelar reserva");
            System.out.println("3. Listar todas las reservas");
            System.out.println("4. Cerrar sesion");
            System.out.print("Seleccione una opción: ");
            opcionDep = in.nextLine();
            switch (opcionDep) {
                case "1": //En este apartado elegimo la hora qeu queremos
                    try {
                        String codSalaAReservar;
                        LocalDateTime fechaDeReserva;
                        int mes;
                        int dia;
                        int horaR;
                        int horaD;
                        Duration durationDeReserva;
                        System.out.println("Dime la sala en la cual quieras realizar una reserva:");
                        codSalaAReservar = in.nextLine();

                        if (Administrador.buscarCodSalaTrue(codSalaAReservar)) {
                            Sala s1 = Administrador.buscarCodSalaID(codSalaAReservar);
                            //si la sala existe sigues con la reserva y si no existe te devolvera al menu

                            System.out.println("Datos de Reserva");
                            System.out.println("Mes: (Febrero = 2,etc):");
                            mes = Integer.parseInt(in.nextLine());
                            System.out.println("Dia: (Numerico)");
                            dia = Integer.parseInt(in.nextLine());
                            System.out.println("Hora: (Disponible desde las 9 hasta las 14 horas)");
                            horaR = Integer.parseInt(in.nextLine());
                            while (horaR < 9 || horaR > 14) {
                                System.out.println("Hora de trabajo no disponible");
                                System.out.println("Hora: (Disponible desde las 9 hasta las 14 horas)");
                                horaR = Integer.parseInt(in.nextLine());
                            }
                            System.out.println("Duracion de la reserva: ");

                            horaD = Integer.parseInt(in.nextLine());
                            durationDeReserva = Duration.ofHours(horaD);
                            fechaDeReserva = LocalDateTime.of(2024, mes, dia, horaR, 0);
                            if (horaD + horaR <= 14) {
                                //aqui en caso de que las salas existan te la reserva y si no pone que no existe
                                if (!Sala.compararSiEstaFechaTrue(Administrador.buscarCodSalaID(codSalaAReservar).getNombre(), fechaDeReserva)) {
                                    s1.reservar(Administrador.buscarCodSalaID(codSalaAReservar).getNombre(), fechaDeReserva, durationDeReserva, codMenuDep);
                                } else {
                                    System.out.println("Reserva igual ya existente");
                                }
                            } else {
                                System.out.println("La hora de reserva y su duracion ha excedido el tiempo disponible de la sala");
                            }
                        } else {
                            System.out.println("La sala a reservar no existe");
                        }
                    }catch (NumberFormatException nfe){
                        System.out.println("Formato de fecha/hora no valido (Uso de texto)");
                    }catch (DateTimeException dte){
                        System.out.println("La fecha a reservar no existe ");
                    }
                    break;
                case "2":
                    try {
                        String salaACancelar;
                        LocalDateTime fechaDeReserva;
                        int mes;
                        int dia;
                        int horaR;
                        int horaD;
                        System.out.println("Datos de Cancelacion");
                        System.out.println("Dime la sala en la cual quieras cancelar la resrva:");
                        salaACancelar = in.nextLine();
                        if (Administrador.buscarSalaTrue(salaACancelar)) {
                            System.out.println("Mes de la reserva a cancelar: (Junio = 6,etc):");
                            mes = Integer.parseInt(in.nextLine());
                            System.out.println("Dia de la reserva a cancelar: (Numerico)");
                            dia = Integer.parseInt(in.nextLine());
                            System.out.println("Hora de la reserva a cancelar");
                            horaR = Integer.parseInt(in.nextLine());
                            fechaDeReserva = LocalDateTime.of(2024,mes,dia,horaR,0);
                            //La cancelacion te pregunta los datos de la reserva que has realizado previamente
                            if (codMenuDep.equalsIgnoreCase(Sala.buscarResIgualID(salaACancelar,fechaDeReserva).getCodDepReservador())){
                                Sala s1= Administrador.buscarSalaID(salaACancelar);
                                Reserva r1 = Sala.buscarResIgualID(salaACancelar,fechaDeReserva);
                                s1.reservas.remove(r1);
                            }else{
                                System.out.println("El departamento que quiere retirar esta reserva no es el mismo que la reservo");
                            }
                        }else{
                            System.out.println("la sala para cancelar la reserva no existe");
                        }
                    }catch (NumberFormatException nfe){
                        System.out.println("Formato de fecha/hora no valido (Uso de texto)");
                    }catch (DateTimeException dte){
                        System.out.println("La fecha a reservar no existe ");
                        //si no existe no puedes cancelar nada
                    }
                    break;
                case "3":
                        Administrador.listarSalasConReservas();
                    break;
                case "4":
                    logout=true;
                    break;
                default:
                    System.out.println("Opción no válida. Por favor, seleccione una opción válida.");
            }
        } while (!logout);
    }


    public String getCodigo() {
        return codigo;
    }

    public String getNombre() {
        return nombre;
    }

    //metodo redefinido del toString padre objeto
    @Override
    public String toString() {
        String DepartaCom = "Nombre de departamento: "+ nombre +
                            "\n\t Codigo de departamento: "+ codigo.toUpperCase() +
                            "\n\t Representante: " + representante;
        return DepartaCom;
    }


}
