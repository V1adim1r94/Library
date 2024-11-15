package com.dragunov.library.service.inerface;

public interface EmailService {

    void sendSimpleEmail(String toAddress, String subject, String message);

}
