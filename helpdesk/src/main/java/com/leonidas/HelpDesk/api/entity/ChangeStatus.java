package com.leonidas.HelpDesk.api.entity;

import com.leonidas.HelpDesk.api.enums.StatusEnum;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;

@Document
@Getter
@Setter
public class ChangeStatus {

    @Id
    private String id;

    @DBRef(lazy = true)
    private Ticket ticket;

    @DBRef(lazy = true)
    private User userChange;

    private Date dateChangeStatus;

    private StatusEnum status;

}
