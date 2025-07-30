package com.leonidas.HelpDesk.api.dto;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
public class Summary implements Serializable {

    private static final long serialVersionUID = 1L;

    private Integer amountNew;
    private Integer amountResolved;
    private Integer amountApproved;
    private Integer amountDisapproved;
    private Integer amountAssigned;
    private Integer amountClosed;


}
