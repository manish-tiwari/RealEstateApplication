package com.org.RealEstateApplication.utils;

public class ResponseError {

private String message;

public ResponseError(String message, String... args) {

this.message = String.format(message, args);

}

public ResponseError(Exception e) {

this.message = e.getMessage();

}

public String getMessage() {

return "<html><body><font color=\"red\">"+this.message+"</font></body></html>";

}

}
