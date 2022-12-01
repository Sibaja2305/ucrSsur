package ucrssur;

import java.awt.Component;
import java.nio.channels.Selector;
import javax.accessibility.Accessible;
import javax.accessibility.AccessibleContext;
import javax.swing.JFileChooser;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Comparator;
import java.util.Properties;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.activation.DataHandler;
import javax.activation.FileDataSource;
import javax.mail.BodyPart;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.NoSuchProviderException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import javax.swing.JOptionPane;

/**
 * En este programa los docentes podran crear grupos al azar de listas de
 * estudiantes que ellos contegan y podran enviar al correo eletronico y tambien
 * podran guardalo en sus computadoras.
 *
 * @author Kevin Sibaja Granados
 * @author Yordany Navarro Hernandez
 * @author Jonathan Alfaro Herrera
 */
public class UcrSsur {

    static JFileChooser chooser = new JFileChooser();
    static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    static Student listStudents[];
    static Student aux[];
    static String compareCard;

    private static String emailFrom = "kevinsibajah@gmail.com";
    private static String passwordFrom = "ccunjmdfqajcxeyi";
    private static String emailTo;
    private static String subject;
    private static String content;
    private static Properties properties = new Properties();
    private static Session session;
    private static MimeMessage mimeMessage;

    /**
     * este codigo contiene un menu donde el usuario va a entrar y puede
     * utilizar las diferentes opciones que contiene para la creacion de grupo
     * de una lista de estudiantes.
     *
     * @param args
     * @throws IOException
     */
    public static void main(String[] args) throws IOException {
        JOptionPane.showMessageDialog(null, "Bienvenido a Groups Create");
        File file = null;
        listRegister(file);

    }

    /**
     * Este metodo llamado menu contiene diferentes opciones que el usuario
     * puede utilizar, como leer la lista, editar algun estudiantes de las
     * listas y otra opciones que va a necesitar
     *
     * @param file
     * @param h
     * @throws IOException
     */
    public static void menu(File file, File h) throws IOException {
        System.out.println("1= Registrar lista de estudiantes\n2= Leer lista de estudiantes\n"
                + "3= Editar lista estudiantes\n"
                + "4= Crear grupos de la lista de estudiantes\n5= Enviar a correo electronico \n"
                + "6= Manual usuario \n"
                + "0= Salir");
        int option = 0;
        try {
            System.out.println("-------------------------------------------");;
            System.out.println("Seleccione una opción");
            option = Integer.parseInt(br.readLine());
        } catch (NumberFormatException numberFormatException) {
            System.out.println("Caracter invalido, por favor utilice solo numericos");
            menu(file, null);
        }

        switch (option) {
            case 1:
                System.out.println("-------------------------------------------");
                listRegister(file);
                System.out.println("-------------------------------------------");
                break;
            case 2:
                System.out.println("-------------------------------------------");

                seeStudent(alphabetically(), 0);

                System.out.println("-------------------------------------------");
                menu(file, null);
                System.out.println("-------------------------------------------");
                break;
            case 3:

                editStudent(listStudents, 0, searchStudent());
                System.out.println("-------------------------------------------");
                menu(file, null);
                System.out.println("-------------------------------------------");
                break;
            case 4:
                System.out.println("-------------------------------------------");

                System.out.println("1= Crear por cantidad de grupos\n"
                        + "2= Crear grupos por cantidad de personas\n"
                        + "3= Regresar al menu principal");
                int response = 0;
                try {
                    System.out.println("Seleccione una opcion");
                    response = Integer.parseInt(br.readLine());
                } catch (Exception e) {
                    System.out.println("Caracter invalido, por favor utilice solo numericos");

                }
                switch (response) {
                    case 1:
                        System.out.println("-------------------------------------------");
                        groupQuantity(createGroups());
                        System.out.println("-------------------------------------------");
                        menu(file, null);
                        break;
                    case 2:
                        System.out.println("-------------------------------------------");
                        personGroups(createGroups());

                        System.out.println("-------------------------------------------");
                        menu(file, null);
                        break;
                    case 3:
                        menu(file, null);
                        break;
                    default:
                        System.out.println("Opcion invalida, por favor digite solamente una de las opciones dadas");
                        System.out.println("-------------------------------------------");
                        menu(file, null);
                        break;
                }
                break;
            case 5:
                email(h);
                System.out.println("-------------------------------------------");
                menu(file, null);
                System.out.println("-------------------------------------------");
                break;

            case 6:
                System.out.println("-------------------------------------------");
                readUserManual();
                System.out.println("-------------------------------------------");
                menu(file, null);
                System.out.println("-------------------------------------------");
                break;
            case 0:
                System.out.println("-------------------------------------------");
                System.out.println("Feliz Navidad y prospero año nuevo");
                System.out.println("-------------------------------------------");

                break;
            default:
                System.out.println("Opcion invalida, por favor digite solamente una de las opciones dadas");
                menu(file, null);
                break;
        }
    }

    /**
     * Este metodo funciona para registrar la lista de estudiantes, ya que
     * contiene el JFileChooser y podra buscar el registro en diferentes partes
     * de la computadora
     *
     * @param file
     * @throws IOException
     */
    public static void listRegister(File file) throws IOException {

        Component parent = null;
        int returnVal = chooser.showOpenDialog(parent);
        if (returnVal == JFileChooser.APPROVE_OPTION) {
            System.out.println("Se subio el registro: "
                    + chooser.getSelectedFile().getName());
            System.out.println("-------------------------------------------");
        }
        showFile(chooser.getSelectedFile());
    }

    /**
     * Este metodo funciona para que cuando usuario registra la lista de
     * estudiantes esta lista se va a guardar en un arreglo y en el constructor
     * de la clase Student que contiene los diferentes datos para guardar parte
     * por parte y poder trabajarlo de una mejor manera.
     *
     * @param file
     * @throws IOException
     */
    public static void showFile(File file) throws IOException {

        BufferedReader objReader = null;
        objReader = new BufferedReader(new FileReader(file.getAbsolutePath()));
        String strCurrentLine;
        int i = 0;
        while ((strCurrentLine = objReader.readLine()) != null) {
            String datos[] = strCurrentLine.split(",");
            Student student = new Student(datos[0], datos[1], datos[2], datos[3], false);
            listStudents = newVector(student, i);
            i++;
        }

        menu(file, null);
    }

    /**
     * Este metodo crea un vector del tamaño de la lista para poder utilizar
     * diferentes lista de diferentes tamaños y no utilizar una un tamaño
     * definido por el programa.
     *
     * @param student
     * @param i
     * @return
     */
    private static Student[] newVector(Student student, int i) {

        Student listStudend[] = new Student[i + 1];
        listStudend[i] = student;
        if (i >= 1) {
            for (int j = 0; j < listStudents.length; j++) {
                listStudend[j] = listStudents[j];
            }
            listStudend[i] = student;
        }
        return listStudend;
    }

    /**
     * Este metodo lee la lista de estudiantes que se registro al principio
     *
     * @param listStudents
     * @param i
     */
    private static void seeStudent(Student alphaNames[], int i) {

        if (i != alphaNames.length) {
            System.out.println("Estudiante: " + alphaNames[i].getName());
            seeStudent(alphaNames, i + 1);
        }

    }

    /**
     * Este metodo llama al manual de usuario por medio de la ruta donde el esta
     * guardado y el usuario podra leerlo para ver cmo funciona el programa.
     */
    public static void readUserManual() {

        try {
            File myObj = new File("C:\\Users\\Hp EliteBook\\OneDrive\\Documentos\\Manual de usuario.txt");
            Scanner myReader = new Scanner(myObj);
            while (myReader.hasNextLine()) {
                String data = myReader.nextLine();
                System.out.println(data);
            }
            myReader.close();
        } catch (FileNotFoundException e) {
            System.out.println("Hubo un error en la ruta");
            e.printStackTrace();
        }
    }

    /**
     * Este metodo funciona para que la persona pueda mandar la lista de
     * estudiantes en un correo a una persona en especifico y podra un mensaje y
     * el asunto del correo electronico con el archivo. Enviar correos desde
     * Java 📧 | Gmail | 2022. (2022, 19 septiembre). [Vídeo]. YouTube.
     * https://www.youtube.com/watch?v=ZggjlwLzrxg&feature=youtu.be
     *
     * @param h
     * @throws IOException
     */
    public static void createEmail(File h) throws IOException {

        System.out.println("Email a que quiere enviar");
        emailTo = br.readLine();
        System.out.println("Asunto del correo");
        subject = br.readLine();
        System.out.println("Mensaje");
        content = br.readLine();
        System.out.println("Espere un momento...");

        properties.put("mail.smtp.host", "smtp.gmail.com");
        properties.put("mail.smtp.ssl.trust", "smtp.gmail.com");
        properties.setProperty("mail.smtp.starttls.enable", "true");
        properties.setProperty("mail.smtp.port", "587");
        properties.setProperty("mail.smtp.user", emailFrom);
        properties.setProperty("mail.smtp.ssl.protocols", "TLSv1.2");
        properties.setProperty("mail.smtp.auth", "true");

        session = Session.getDefaultInstance(properties);
        mimeMessage = new MimeMessage(session);
        try {
            BodyPart texto = new MimeBodyPart();

            texto.setText(content);

            BodyPart adjunto = new MimeBodyPart();

            adjunto.setDataHandler(new DataHandler(new FileDataSource(h.getAbsolutePath())));
            adjunto.setFileName(h.getName());
            MimeMultipart m = new MimeMultipart();
            m.addBodyPart(texto);
            m.addBodyPart(adjunto);

            mimeMessage.setFrom(new InternetAddress(emailFrom));
            mimeMessage.setRecipient(Message.RecipientType.TO, new InternetAddress(emailTo));
            mimeMessage.setSubject(subject);
            mimeMessage.setContent(m);
            sendEmail(h);

        } catch (MessagingException ex) {
            Logger.getLogger(UcrSsur.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    /**
     * Crea los protocolos para poder mandar el correo electronico a la persona
     * designada.
     *
     * @param h
     */
    public static void sendEmail(File h) {

        try {
            Transport transport = session.getTransport("smtp");
            transport.connect(emailFrom, passwordFrom);
            transport.sendMessage(mimeMessage, mimeMessage.getRecipients(Message.RecipientType.TO));
            transport.close();
            JOptionPane.showMessageDialog(null, "Correo enviado");
        } catch (NoSuchProviderException ex) {
            Logger.getLogger(UcrSsur.class.getName()).log(Level.SEVERE, null, ex);
        } catch (MessagingException ex) {
            Logger.getLogger(UcrSsur.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Este metodo pide el numero de carnet y compara en el arreglo y lo
     * encuentra puede editar al estudiante.
     *
     * @return
     * @throws IOException
     */
    public static String searchStudent() throws IOException {
        boolean found = false;
        System.out.println("-------------------------------------------");
        System.out.println("Carnet del estudiante a editar: ");
        String compareCard = br.readLine();
        return compareCard;
    }

    /**
     * Este metodo contiene un JFileChooser para que el usuarion pueda buscar el
     * archivo que quiere enviar por correo electronico
     *
     * @param h
     * @throws IOException
     */
    public static void email(File h) throws IOException {
        Component pare = null;
        int returnVal = chooser.showOpenDialog(pare);
        if (returnVal == JFileChooser.APPROVE_OPTION) {
            System.out.println("Se subio el registro: "
                    + chooser.getSelectedFile().getName());
            System.out.println("-------------------------------------------");
        }
        createEmail(chooser.getSelectedFile());

    }

    /**
     * Este metodo funciona para que los estudiantes queden revueltos y asi
     * crear grupos y se guardada en un arreglo llamado copy
     *
     * @return
     * @throws IOException
     */
    public static Student[] createGroups() throws IOException {
        Student[] copy = new Student[listStudents.length];
        int j = 0;
        for (int i = 0; i < listStudents.length; i++) {
            listStudents[i].setSelected(false);
        }

        while (check()) {

            int ramdom = (int) (Math.random() * copy.length);
            if (listStudents[ramdom].isSelected() == false) {
                listStudents[ramdom].setSelected(true);

                copy[j] = listStudents[ramdom];

                j++;
            }
        }
        return copy;

    }

    /**
     * Este metodo funciona para que el usuario digitar cuantos grupos quiere
     * con la lista de estudiantes.
     *
     * @param copy
     * @throws IOException
     */
    public static void groupQuantity(Student copy[]) throws IOException {

        System.out.println("Digite cuantos grupos desea");
        int n = Integer.parseInt(br.readLine());
        int init = 0;
        int end = copy.length / n;
        int x = copy.length % n;
        Component parent = null;
        int returnVal = chooser.showSaveDialog(parent);
        if (returnVal == JFileChooser.APPROVE_OPTION) {

            try {
                File h = chooser.getSelectedFile();
                FileWriter write = new FileWriter(h, true);
                for (int i = 0; i < n; i++) {
                    write.write("Grupo: " + (i + 1) + "\n");
                    if ((i + 1) == 1) {
                        for (int r = copy.length - x; r < copy.length; r++) {
                            write.write(copy[r].getName() + "\n");
                        }
                    }
                    for (int k = init; k < end; k++) {

                        write.write(copy[k].getName() + "\n");
                    }
                    init += copy.length / n;
                    end += copy.length / n;
                    write.write("\n");

                }
                write.close();
                JOptionPane.showMessageDialog(null, "Grupo creado en la direccion : " + h.getAbsolutePath());
            } catch (Exception e) {
                System.out.println(e.getMessage());
                System.out.println("-------------------------------------------");
            }
        }
    }

    /**
     * Este metodo crea grupos de la lista registrada basandose en la cantidad
     * de estudiantes que se quiere por grupo
     *
     * @param copy
     * @throws IOException
     */
    public static void personGroups(Student copy[]) throws IOException {
        System.out.println("Digite la cantidad de personas por grupo");
        int n = Integer.parseInt(br.readLine());
        int init = 0;
        int end = n;
        int count = 1;
        int y = copy.length / n;
        Component parent = null;
        int returnVal = chooser.showSaveDialog(parent);
        if (returnVal == JFileChooser.APPROVE_OPTION) {

            try {
                File h = chooser.getSelectedFile();
                FileWriter write = new FileWriter(h, true);

                for (int i = 0; i < copy.length; i++) {
                    copy[i].setSelected(false);
                }
                while (checkGroup(copy)) {
                    write.write("Grupo: " + count + "\n");

                    if (copy.length >= end) {
                        if (count == 1 && copy.length % n != 0) {
                            for (int i = copy.length - y; i < copy.length; i++) {
                                write.write(copy[i].getName() + "\n");
                                copy[i].setSelected(true);
                            }
                        }
                        for (int j = init; j < end; j++) {
                            write.write(copy[j].getName() + "\n");
                            copy[j].setSelected(true);
                        }

                    }
                    init += n;
                    end += n;
                    count++;
                }
                write.close();
                JOptionPane.showMessageDialog(null, "Grupo creado en la direccion : " + h.getAbsolutePath());
            } catch (Exception e) {
                System.out.println(e.getMessage());
                System.out.println("-------------------------------------------");
            }
        }

    }

    /**
     * Este metodo verifica si los estudiantes guardados en el arreglo copy
     * estan asignados a un grupo o no.
     *
     * @param copy
     * @return
     */
    public static boolean checkGroup(Student copy[]) {
        boolean found = false;

        for (int i = 0; i < copy.length; i++) {
            if (copy[i].isSelected() == false) {
                found = true;
            }

        }

        return found;
    }

    /**
     * Este metodo funciona para saber si el math ramdon pueda escoger los que
     * no estan seleccionados y pueda utilizar todos los estudiantes.
     *
     * @return
     */
    public static boolean check() {
        boolean found = false;

        for (int i = 0; i < listStudents.length; i++) {
            if (listStudents[i].isSelected() == false) {
                found = true;
            }

        }

        return found;
    }

    /**
     * cuando se compara el carnet para editar entra a este metodo y el usuario
     * puede editar algun estudiante de la lista.
     *
     * @param listStudents
     * @param i
     * @param compareCard
     * @throws IOException
     */
    public static void editStudent(Student listStudents[], int i, String compareCard) throws IOException {
        boolean found = true;
        if (i != listStudents.length) {

            if (compareCard.equals(listStudents[i].getIdStudent())) {
                found = true;
                System.out.println("-------------------------------------------");
                System.out.println("Nuevo carnet: ");
                listStudents[i].setIdStudent(br.readLine());
                System.out.println("-------------------------------------------");
                System.out.println("Nombre: ");
                listStudents[i].setName(br.readLine());
                System.out.println("-------------------------------------------");
                System.out.println("Sexo biologico: ");
                listStudents[i].setGender(br.readLine());
                System.out.println("-------------------------------------------");
                System.out.println("Nuevo lugar de residencia: ");
                listStudents[i].setResidence(br.readLine());
                System.out.println("-------------------------------------------");

            }
            editStudent(listStudents, i + 1, compareCard);

        }
        if (!found) {
            System.out.println("-------------------------------------------");
            System.out.println("El estudiante no fue encontrado");

        }
    }

    /**
     * Este metodo ordena alfabeticamente la lista de estudiantes que registra
     * el usuario.
     */

    public static Student[] alphabetically() {
        Student alphaNames[] = new Student[listStudents.length];
        for (int i = 0; i < alphaNames.length; i++) {
            alphaNames[i] = listStudents[i];
        }

        for (int i = 0; i < alphaNames.length; i++) {

            for (int j = 0; j < alphaNames.length - 1; j++) {
                String actualElement = alphaNames[j].getName();
                String nextElement = alphaNames[j + 1].getName();
                if (actualElement.compareTo(nextElement) > 0) {
                    // Intercambiar
                    alphaNames[j].setName(nextElement);
                    alphaNames[j + 1].setName(actualElement);

                }
            }
        }
        return alphaNames;
    }

}
