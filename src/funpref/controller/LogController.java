/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package funpref.controller;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

//import org.apache.commons.mail.DefaultAuthenticator;
//import org.apache.commons.mail.EmailAttachment;
//import org.apache.commons.mail.EmailException;
//import org.apache.commons.mail.HtmlEmail;

/**
 * Classe responsavel pelo Log's.
 *
 * @author jeanderson
 */
public class LogController {

    /**
     * Gera um arquivo log dentro da Pasta Logger que fica na Pasta principal do
     * Usuario.
     *
     * @param className
     * @param ex
     */
    public static void reportException(String className, Exception ex) {
        try {
            /*
             * Informamos qual o nome do Logger, que no caso vai ser o nome da
             * Classe que acontecer a exceo
             */
            Logger log = Logger.getLogger(className);
            /*
             * Variavel que vai conter qual a pasta do sistema que liga ao
             * usuario, por padro ser do sistema operacional Windows
             */
            String systemPath = "/Users/";
            /* Se for outro sistema operacional */
            if (System.getProperty("os.name").startsWith("Linux")) {
                systemPath = "/home/";
            }
            /* Pasta onde vamos colocar os Logs */
            File directoryPathLog = new File(systemPath + System.getProperty("user.name") + "/Logger");
            if (!directoryPathLog.exists()) {
                directoryPathLog.mkdir();
            }
            String fileDirectory = directoryPathLog.getAbsolutePath() + "/LOG_"
                    + LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd-MM_HH-mm-ss")) + ".log";
            /* Classe responsavel por escrever o arquivo */
            FileHandler writer = new FileHandler(fileDirectory, true);
            /*
             * Precisamos informar como ser escrito(formato) as excees, Vamos
             * Utilizar uma Classe j pronta para isso a Classe SimpleFormatter
             */
            writer.setFormatter(new SimpleFormatter());
            /*
             * Adicionamos ao nosso log a Classe que vai escrever a exceo que
             * for gerada
             */
            log.addHandler(writer);

            /*
             * Geramos o Log, passamos que ser de Nivel Severe(Alto), e
             * passamos a exceo para ele
             */
            log.log(Level.SEVERE, null, ex);

            /* Finalizamos a escrita */
            writer.flush();
            writer.close();
            ///*Envia por email a exceo*/
            //LogController.reportExceptionEmail(className, ex.getMessage(), fileDirectory);

        } catch (IOException | SecurityException e) {
            Logger.getLogger(LogController.class.getName()).log(Level.SEVERE, null, e);
        }
    }

//    public static void reportExceptionEmail(String className, String exception, String logPath) {
//        /*
//         * Para compreender melhor acesse esse site:
//         * http://www.botecodigital.info/java/enviando-e-mail-em-java-com-api-
//         * commons-email-da-apache/
//         */
//        HtmlEmail email = new HtmlEmail();
//        email.setSSLOnConnect(true);
//        email.setHostName("smtp.gmail.com");
//        email.setSslSmtpPort("465");
//        email.setAuthenticator(new DefaultAuthenticator("jjsoftwares10@gmail.com", "jean1420"));
//        try {
//            email.setFrom("jjsoftwares10@gmail.com", "Software da clinica");
//            email.setSubject("Exceo ocorrida no app da clinica");
//            StringBuilder msg = new StringBuilder();
//            msg.append("<h1 style=\"text-align: center;\">Excecao Ocorrida</h1>");
//            msg.append("<p><strong>Na Classe: " + className + " </strong></p>");
//            msg.append("<p><strong>Data e Horario do ocorrido: "
//                    + LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:s"))
//                    + "</strong></p>");
//            msg.append("<h2 style=\"text-align: center;\"><strong>Excecao</strong></h2>");
//            msg.append("<p><span style=\"color: #ff0000;\">" + exception + "</span></p>");
//            msg.append("<p><strong>Segue anexo com detalhes</strong></p>");
//            /*Enviando o anexo com detalhes da exceo*/
//            File arqLog = new File(logPath);
//            if (arqLog.exists()) {
//                EmailAttachment anexo = new EmailAttachment();
//                anexo.setPath(logPath);
//                anexo.setDisposition(EmailAttachment.ATTACHMENT);
//                anexo.setName(arqLog.getName());
//                email.attach(anexo);
//            }
//            /*enviando*/
//            email.setHtmlMsg(msg.toString());
//            email.addTo("jeandersonfju@gmail.com");
//            email.addTo("jeff-assis@hotmail.com");
//            email.send();
//        } catch (EmailException e) {
//            e.printStackTrace();
//
//        }
//    }

}
