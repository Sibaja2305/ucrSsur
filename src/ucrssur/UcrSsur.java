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
     * este codigo contiene un menu
     *
     * @param args
     * @throws IOException
     */
    public static void main(String[] args) throws IOException {
        menu(null, null);
    }

    public static void menu(File file, File h) throws IOException {
        System.out.println("1= Registrar lista de estudiantes\n2= Leer lista de estudiantes\n"
                + "3= Editar lista estudiantes\n"
                + "4= Borrar lista de estudiantes\n5= Crear grupos de la lista de estudiantes  \n"
                + "6= Enviar a correo electronico\n"
                + "7= Manual usuario \n"
                + "0= Salir");
        System.out.println("-------------------------------------------");;
        System.out.println("Seleccione una opción");
        int option = Integer.parseInt(br.readLine());

        switch (option) {
            case 1:
                System.out.println("-------------------------------------------");
                listRegister(file);
                System.out.println("-------------------------------------------");
                break;
            case 2:
                System.out.println("-------------------------------------------");
                seeStudent(listStudents, 0);
                System.out.println("-------------------------------------------");
                menu(file, null);
                System.out.println("-------------------------------------------");
                break;
            case 3:

                edStudent(listStudents, 0, editStudent());
                System.out.println("-------------------------------------------");
                menu(file, null);
                System.out.println("-------------------------------------------");
                break;
            case 4:
                System.out.println("-------------------------------------------");

                System.out.println("-------------------------------------------");
                menu(file, null);
                System.out.println("-------------------------------------------");
                break;
            case 5:
                System.out.println("-------------------------------------------");
                createGroups();
                menu(file, null);
                System.out.println("-------------------------------------------");
                break;
            case 6:
                email(h);
                System.out.println("-------------------------------------------");
                menu(file, null);
                System.out.println("-------------------------------------------");
                break;
            case 7:
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
                System.out.println("opcion invalida, por favor digite solamente una de las opciones dadas");
                menu(file, null);
                break;
        }
    }

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

    public static void showFile(File file) throws IOException {

        BufferedReader objReader = null;
        objReader = new BufferedReader(new FileReader(file.getAbsolutePath()));
        String strCurrentLine;
        int i = 0;
        while ((strCurrentLine = objReader.readLine()) != null) {
            String datos[] = strCurrentLine.split(",");
            Student student = new Student(datos[0], datos[1], datos[2], datos[3], datos[4], false);
            listStudents = newVector(student, i);
            i++;
        }
        menu(file, null);
    }

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

    private static void seeStudent(Student listStudents[], int i) {

        if (i != listStudents.length) {
            System.out.println("Estudiante: " + listStudents[i].getName());
            seeStudent(listStudents, i + 1);
        }

    }

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
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
    }

    public static void createEmail(File h) throws IOException {

        System.out.println("email a que quiere enviar");
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

    public static void sendEmail(File h) {

        try {
            Transport transport = session.getTransport("smtp");
            transport.connect(emailFrom, passwordFrom);
            transport.sendMessage(mimeMessage, mimeMessage.getRecipients(Message.RecipientType.TO));
            transport.close();
            JOptionPane.showMessageDialog(null, "correo enviado");
        } catch (NoSuchProviderException ex) {
            Logger.getLogger(UcrSsur.class.getName()).log(Level.SEVERE, null, ex);
        } catch (MessagingException ex) {
            Logger.getLogger(UcrSsur.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public static String editStudent() throws IOException {
        boolean found = false;
        System.out.println("-------------------------------------------");
        System.out.println("Carnet del estudiante a editar: ");
        String compareCard = br.readLine();
        return compareCard;
    }

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

    public static void createGroups() throws IOException {
        Student[] copy = new Student[listStudents.length];
        int j = 0;
        while (check()) {

            int ramdom = (int) (Math.random() * copy.length);
            if (listStudents[ramdom].isSelected() == false) {
                listStudents[ramdom].setSelected(true);

                copy[j] = listStudents[ramdom];

                j++;
            }
        }
        System.out.println("Digite cuantos grupos desea");
        int n = Integer.parseInt(br.readLine());
        int init = 0;
        int end = copy.length / n;
        int x = copy.length%n;
        for (int i = 0; i < n; i++) {
            System.out.println("Grupo" + (i+1));
              if ((i+1)==1) {
                for (int r = copy.length-x; r < copy.length; r++) {
                    System.out.println(copy[r].getName());
                }
            }
            for (int k = init; k < end; k++) {
              
                System.out.println(copy[k].getName());
            }
            init += copy.length/n;
            end += copy.length/n;
            
        }
    }

    public static boolean check() {
        boolean found = false;
        for (int i = 0; i < listStudents.length; i++) {
            if (listStudents[i].isSelected() == false) {
                found = true;
            }

        }

        return found;
    }

    public static void edStudent(Student listStudents[], int i, String compareCard) throws IOException {
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
                System.out.println("Nuevo correo electronico: ");
                listStudents[i].setEmail(br.readLine());

            }
            edStudent(listStudents, i + 1, compareCard);

        }
        if (!found) {
            System.out.println("-------------------------------------------");
            System.out.println("El estudiante no fue encontrado");
        }
    }

}
