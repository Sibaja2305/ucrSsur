package ucrssur;

import java.awt.Component;
import java.nio.channels.Selector;
import javax.accessibility.Accessible;
import javax.accessibility.AccessibleContext;
import javax.swing.JFileChooser;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
public class UcrSsur {
    static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    static Student listStudents[] ;
    public static void main(String[] args) throws IOException {
        menu(null);
    }
    public static void menu(File archivo) throws IOException {
        System.out.println("1= Registrar lista de estudiantes\n2= Leer lista de estudiantes\n"
                + "3= Editar lista estudiantes\n"
                + "4= Borrar lista de estudiantes\n5= Crear grupos de la lista de estudiantes  \n"
                + "6= Enviar a correo electronico\n"
                + "7= Manual usuario \n"
                + "0= Salir");
        System.out.println("Seleccione una opción");
        int option = Integer.parseInt(br.readLine());
        switch (option) {
            case 1:
                listRegister(archivo);
                System.out.println(archivo.getAbsolutePath());
                break;
            case 2:
                showFile(archivo);
                menu(archivo);
                break;
            case 3:
                verEstudiantes();
                menu(archivo);
                break;
            case 4:
                menu(archivo);
                break;
            case 5:
                menu(archivo);
                break;
            case 6:
                menu(archivo);
                break;
            case 0:
                break;
            default:
                System.out.println("opcion invalida, por favor digite");
                menu(archivo);
                break;
        }
    }
    public static void listRegister(File archivo) throws IOException {
        JFileChooser chooser = new JFileChooser();
        Component parent = null;
        int returnVal = chooser.showOpenDialog(parent);
        if (returnVal == JFileChooser.APPROVE_OPTION) {
            System.out.println("Se subio el registro: "
                    + chooser.getSelectedFile().getName());
        }
        menu(chooser.getSelectedFile());
    }
    public static void showFile(File archivo) throws IOException {
        BufferedReader objReader = null;
        objReader = new BufferedReader(new FileReader(archivo.getAbsolutePath()));
        String strCurrentLine;
         int i =0;
        while ((strCurrentLine = objReader.readLine()) != null) {
           // System.out.println("strCurrentLine: "+strCurrentLine);
            String datos[] = strCurrentLine.split(",");
            Student student = new Student(datos[0], datos[1], datos[2], datos[3], datos[4]);
            listStudents  = nuevoVector(student, i);
             System.out.println("vec: "+listStudents[i].getName());
             i++;
        }
    }
    private static Student[] nuevoVector(Student student,  int i) {
            Student listStudend [] = new Student[i + 1];
            listStudend [i] = student;
            if (i>=1) {
                for (int j = 0; j < listStudents.length; j++) {
                     listStudend[j] = listStudents[j];
                }
                listStudend [i] = student;
             }
            return listStudend;
    }
    private static void verEstudiantes() {
         for (int i = 0; i < listStudents.length; i++) {
             System.out.println("Estudiante: "+ listStudents[i].getGender());
        }
    }
}
