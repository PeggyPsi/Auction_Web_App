package com.ted.auction_app.Errors;

import java.util.List;

public class SuccessResponse {
    //General success message
    private String message;

    public SuccessResponse(String message) {
        super();
        this.message = message;
    }

    //Getter and setters

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

}
