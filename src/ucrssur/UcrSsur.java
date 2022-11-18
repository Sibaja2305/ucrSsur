package ucrssur;

import java.awt.Component;
import java.nio.channels.Selector;
import javax.accessibility.Accessible;
import javax.accessibility.AccessibleContext;
import javax.swing.JFileChooser;
import javax.swing.filechooser.FileNameExtensionFilter;
import ucrssur.Person;
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 *
 * @author Kevin Sibaja Granados
 * @author Yordany Navarro Hernandez
 *
 */
public class UcrSsur {

    /**
     * @param args the command line arguments
     */
    static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

    public static void main(String[] args) throws IOException {

        listRegister();

    }

    public static void menu() throws IOException {

        System.out.println("1= Registrar lista de estudiantes\n2= Leer lista de estudiantes\n"
                + "3= Editar lista estudiantes\n"
                + "4= Explosiones de cantera\n5= Borrar lista de estudiantes \n"
                + "6= Crear grupos de la lista de estudiantes\n"
                + "7= Manual usuario \n"
                + "0= Salir");
        System.out.println("Seleccione una opción");
        int option = Integer.parseInt(br.readLine());
        switch (option) {
            case 1:

                menu();
                break;
            case 2:

                menu();
                break;
            case 3:
                menu();
                break;
            case 4:
                menu();
                break;
            case 5:
                menu();
                break;
            case 6:
                menu();
                break;
            case 0:

                break;
            default:
                System.out.println("opcion invalida, por favor digite");
                menu();
                break;

        }
    }

    public static void listRegister() throws IOException {
        JFileChooser chooser = new JFileChooser();
        FileNameExtensionFilter filter = new FileNameExtensionFilter(
                ".txt", ".txt");
        chooser.setFileFilter(filter);
        Component parent = null;
        int returnVal = chooser.showOpenDialog(parent);
        if (returnVal == JFileChooser.APPROVE_OPTION) {
            System.out.println("Se subio el registro: "
                    + chooser.getSelectedFile().getName());
        }
        menu();
    }

}
