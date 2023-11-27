package com.sms.controller;


import com.sms.exception.TwilioException;
import com.sms.payload.SmsRequest;
import com.sms.service.TwilioSmsService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/sms")
public class SmsController {

    private final TwilioSmsService twilioSmsService;

    public SmsController(TwilioSmsService twilioSmsService) {
        this.twilioSmsService = twilioSmsService;
    }


    @PostMapping("/send")
    public ResponseEntity<String> sendSms(@RequestBody SmsRequest smsRequest) {
        try {
            twilioSmsService.sendSms(smsRequest.getTo(), smsRequest.getMessage());
            return ResponseEntity.ok("SMS sent successfully");
        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Your Ph No. is Not Registered in Twillio");
        }
    }


    @ExceptionHandler(TwilioException.class)
    public ResponseEntity<String> handleDTOValidationException(TwilioException ex) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getMessage());
    }
}
