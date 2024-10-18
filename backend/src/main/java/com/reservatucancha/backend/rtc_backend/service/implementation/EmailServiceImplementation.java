package com.reservatucancha.backend.rtc_backend.service.implementation;
import com.amazonaws.util.IOUtils;
import com.reservatucancha.backend.rtc_backend.dto.response.BookingConfirmation;
import com.reservatucancha.backend.rtc_backend.service.IEmailService;
import jakarta.mail.*;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;

import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import static org.antlr.v4.runtime.misc.Utils.readFile;

@Service
public class EmailServiceImplementation implements IEmailService {

    @Autowired
    private JavaMailSender mailSender;

    @Value("${spring.mail.username}")
    private String sender;

    @Override
    public void sendMailConnfirmationBook(BookingConfirmation createdReservation) throws MessagingException {
        MimeMessage message = mailSender.createMimeMessage();
        try {
            message.setFrom(new InternetAddress(sender));
            message.setSubject(" Tu Cancha | Confirmacion de reserva");
            message.setRecipients(MimeMessage.RecipientType.TO, createdReservation.getUserEmail());
            // Set the email's content to be the HTML template
            message.setContent(htmlToSend(createdReservation), "text/html; charset=utf-8");

            mailSender.send(message);
        }catch (MessagingException e){
            throw new MessagingException(e.getMessage());
        }
    }

    private String htmlToSend(BookingConfirmation reservationDetail) {
        try {
            // Leer el archivo HTML en una cadena
            String htmlTemplate = readFile("confirmationEmail.html");

            // Reemplazar los placeholders en el HTML
            htmlTemplate = htmlTemplate.replace("${clientName}", reservationDetail.getClientName());
            htmlTemplate = htmlTemplate.replace("${reservationDate}", reservationDetail.getDate().toString());
            htmlTemplate = htmlTemplate.replace("${startTime}", reservationDetail.getStartTime().toString());
            htmlTemplate = htmlTemplate.replace("${endTime}", reservationDetail.getEndTime().toString());
            htmlTemplate = htmlTemplate.replace("${pitchName}", reservationDetail.getPitchName());
            htmlTemplate = htmlTemplate.replace("${pitchAddress}", reservationDetail.getPitchAddress());

            return htmlTemplate;
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException("Failed to read or process the HTML template", e);
        }
    }
    private String readFile(String fileName) throws IOException {
        ClassLoader classLoader = getClass().getClassLoader();
        try (InputStream inputStream = classLoader.getResourceAsStream("templates/" + fileName)) {
            if (inputStream == null) {
                throw new IOException("Resource not found: " + fileName);
            }
            return IOUtils.toString(inputStream);
        }
    }
}