package util;

import exception.InvalidClientException;

public class AuthenticateClient {

    private AuthenticateClient(){

    }

    public static void validate(String name, String ssn, String email, String phone, int age){
        verifyName(name);
        verifySsn(ssn);
        verifyEmail(email);
        verifyPhone(phone);
        verifyAge(age);
    }

    private static void verifyName(String name){
        if(name == null || name.trim().isEmpty()){
            throw new InvalidClientException("Client's name cannot be empty.");
        }

        if(name.trim().length() < 3){
            throw new InvalidClientException("Client's name must have at least 3 characters.");
        }
    }

    private static void verifySsn(String ssn){
        if(ssn == null || ssn.trim().isEmpty()){
            throw new InvalidClientException("Client's ssn cannot be empty.");
        }

        if(ssn.length() != 11){
            throw new InvalidClientException("Client's ssn must have 11 numbers.");
        }

        for(int i = 0; i < ssn.length(); i++){
            if(!Character.isDigit(ssn.charAt(i))){
                throw new InvalidClientException("Client's ssn must contains only numbers.");
            }
        }
    }

    private static void verifyEmail(String email){
        if(email == null || email.trim().isEmpty()){
            throw new InvalidClientException("Client's email cannot be empty.");
        }

        if(!email.contains("@") || !email.contains(".")){
            throw new InvalidClientException("The email address is invalid. Check it and try again.");
        }
    }

    private static void verifyPhone(String phone){
        if(phone == null || phone.trim().isEmpty()){
            throw new InvalidClientException("Client's phone cannot be empty.");
        }

        if(!phone.contains("(") || !phone.contains(")") || !phone.contains("-")){
            throw new InvalidClientException("The email phone number is invalid. " +
                    "Check it and try again.");
        }

        if(phone.length() != 15){
            throw new InvalidClientException("Client's phone must have 15 characters.");
        }
    }

    private static void verifyAge(int age){
        if(age < 18){
            throw new InvalidClientException("People with less 18 years cannot open an account.");
        }
    }
}
