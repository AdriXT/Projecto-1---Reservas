import java.util.Scanner;
public class Main {
    //Metodo del menu
    public static void menu(){
        Scanner in = new Scanner(System.in);

        //Centinela que nos permitirá salir
        boolean salir = false;

        System.out.println("Bienvenido a la aplicación \n ¿Como desea acceder?");
        while(!salir){
            String opcion;
            System.out.println("1. Login de administrador");
            System.out.println("2. Login de departamento");
            System.out.println("3. Salir del programa");

            opcion = in.nextLine();

            switch(opcion){
                case "1":
                    String passwd;
                    System.out.println("Indicame la contraseña de administrador:");
                    passwd = in.nextLine();
                    if (passwd.matches(Administrador.contraseña)){
                        Administrador.menuadmin();
                    }else{
                        System.out.println("Contraseña incorrecta");
                    }
                    break;
                case "2":
                    String codigoDep;
                    System.out.println("Indicame el codigo del departamento:");
                    codigoDep = in.nextLine();
                    Departamento d2 = Administrador.buscarCodDep(codigoDep);
                    if (d2!=null){
                        Departamento.codMenuDep=codigoDep;
                        Departamento.menuDep();
                    }else{
                        System.out.println("Codigo de departamento no existente");
                    }
                    break;
                case "3":
                    System.out.println("...\nSaliendo");
                    salir=true;
                    break;
                default:
                System.out.println("No has indicado una opcion correcta");
                    break;
            }
        }
    }
    public static void main(String[] args) {
        //Programa principal
            Main.menu();
    }
}
