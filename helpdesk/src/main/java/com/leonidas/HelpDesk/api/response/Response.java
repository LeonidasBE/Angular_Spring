package com.leonidas.HelpDesk.api.response;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class Response<T> {

    private T data;
    private List<String> errors;

    public List<String> getErrors() {
        if(this.errors == null)
            this.errors = new ArrayList<>();
        return this.errors;
    }
}
