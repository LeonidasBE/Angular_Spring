package com.leonidas.HelpDesk.api.enums;

public enum StatusEnum {

    NEW,
    ASSIGNED,
    RESOLVED,
    APPROVED,
    DISAPPROVED,
    CLOSED;

    public static StatusEnum getStatus(String status){
        switch (status){
            case "New":
                return NEW;
            case "Assigned":
                return ASSIGNED;
            case "Resolved":
                return RESOLVED;
            case "Approved":
                return APPROVED;
            case "Disapproved":
                return DISAPPROVED;
            case "Closed":
                return CLOSED;
            default:
                return null;
        }
    }

}
