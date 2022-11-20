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

    static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    static Student listStudents[];
    private static String emailFrom = "kevinsibajah@gmail.com";
    private static String passwordFrom = "ccunjmdfqajcxeyi";
    private static String emailTo;
    private static String subject;
    private static String content;
     private static Properties properties = new Properties();
    private static Session session;
    private static MimeMessage mimeMessage;

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
        System.out.println("-------------------------------------------");;
        System.out.println("Seleccione una opción");
        int option = Integer.parseInt(br.readLine());

        switch (option) {
            case 1:
                System.out.println("-------------------------------------------");
                listRegister(archivo);
                System.out.println("-------------------------------------------");

                break;
            case 2:
                System.out.println("-------------------------------------------");
                showFile(archivo);
                System.out.println("-------------------------------------------");
                menu(archivo);
                System.out.println("-------------------------------------------");

                break;
            case 3:
                System.out.println("-------------------------------------------");
                menu(archivo);
                System.out.println("-------------------------------------------");
                break;
            case 4:
                System.out.println("-------------------------------------------");
                menu(archivo);
                System.out.println("-------------------------------------------");
                break;
            case 5:
                System.out.println("-------------------------------------------");
                menu(archivo);
                System.out.println("-------------------------------------------");
                break;
            case 6:
                createEmail();
                

                System.out.println("-------------------------------------------");
                menu(archivo);
                System.out.println("-------------------------------------------");
                break;
            case 7:
                System.out.println("-------------------------------------------");
                readUserManual();
                System.out.println("-------------------------------------------");
                menu(archivo);
                System.out.println("-------------------------------------------");
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
            System.out.println("-------------------------------------------");
        }
        menu(chooser.getSelectedFile());
    }

    public static void showFile(File archivo) throws IOException {
        BufferedReader objReader = null;
        objReader = new BufferedReader(new FileReader(archivo.getAbsolutePath()));
        String strCurrentLine;
        int i = 0;
        while ((strCurrentLine = objReader.readLine()) != null) {
            
            String datos[] = strCurrentLine.split(",");
            Student student = new Student(datos[0], datos[1], datos[2], datos[3], datos[4]);
            listStudents = newVector(student, i);
//           
            i++;
        }
         seeStudent();
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

    private static void seeStudent() {
        for (int i = 0; i < listStudents.length; i++) {
            System.out.println("Estudiante: " + listStudents[i].getName());
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

    public static void  createEmail() throws IOException {
        
        try {
            
            System.out.println("email a que quiere enviar");
            emailTo=br.readLine();
            System.out.println("Asunto del correo");
            subject= br.readLine();
            System.out.println("Mensaje");
            content = br.readLine();
            properties.put("mail.smtp.host","smtp.gmail.com");
            properties.put("mail.smtp.ssl.trust","smtp.gmail.com");
            properties.setProperty("mail.smtp.starttls.enable","true");
            properties.setProperty("mail.smtp.port","587");
            properties.setProperty("mail.smtp.user",emailFrom);
            properties.setProperty("mail.smtp.ssl.protocols","TLSv1.2");
            properties.setProperty("mail.smtp.auth","true");
            
            session=Session.getDefaultInstance(properties);
            BodyPart texto = new MimeBodyPart();
            try {
                texto.setText("mensaje");
            } catch (MessagingException ex) {
                Logger.getLogger(UcrSsur.class.getName()).log(Level.SEVERE, null, ex);
            }
            BodyPart adjunto=new MimeBodyPart ();
            
            adjunto.setDataHandler(new DataHandler(new FileDataSource("C:\\Users\\Hp EliteBook\\OneDrive\\Documentos\\Manual de usuario.txt")));
            adjunto.setFileName("Manual de usuario.txt");
            MimeMultipart m=new MimeMultipart();
            m.addBodyPart(texto);
            m.addBodyPart(adjunto);
            
            
            
            try {
                mimeMessage=new MimeMessage(session);
                mimeMessage.setFrom(new InternetAddress(emailFrom));
                mimeMessage.setRecipient(Message.RecipientType.TO, new InternetAddress(emailTo));
                mimeMessage.setSubject(subject);
                mimeMessage.setText(content, "ISO-8859-1", "html");
            } catch (AddressException ex) {
                Logger.getLogger(UcrSsur.class.getName()).log(Level.SEVERE, null, ex);
            } catch (MessagingException ex) {
                Logger.getLogger(UcrSsur.class.getName()).log(Level.SEVERE, null, ex);
            }
            sendEmail();
            
            
            
        } catch (MessagingException ex) {
            Logger.getLogger(UcrSsur.class.getName()).log(Level.SEVERE, null, ex);
        }
        
     
     
    }
    public static void sendEmail(){
        try {
            Transport transport=session.getTransport("smtp");
            transport.connect(emailFrom, passwordFrom);
            transport.sendMessage(mimeMessage, mimeMessage.getRecipients(Message.RecipientType.TO));
            transport.close();
            JOptionPane.showMessageDialog(null,"correo enviado");
        } catch (NoSuchProviderException ex) {
            Logger.getLogger(UcrSsur.class.getName()).log(Level.SEVERE, null, ex);
        } catch (MessagingException ex) {
            Logger.getLogger(UcrSsur.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
   
}
