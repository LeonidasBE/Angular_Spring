package com.leonidas.helpdesk.ticket.api.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping
public class TicketController {

    @GetMapping
    public String hello() {
        return "Hello from TicketController";
    }

}
