package com.leonidas.HelpDesk.api.entity;

import com.leonidas.HelpDesk.api.enums.PriorityEnum;
import com.leonidas.HelpDesk.api.enums.StatusEnum;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;
import java.util.List;

@Document
@Getter
@Setter
public class Ticket {

    @Id
    private String id;

    @DBRef(lazy = true)
    private User user;

    private Date date;

    private String title;

    private Integer number;

    private StatusEnum status;

    private PriorityEnum priority;

    @DBRef(lazy = true)
    private User assignee;

    private String description;

    private String image;

    @Transient
    private List<ChangeStatus> changes;
}
