package com.relx.exam.exception;

public class CompanyException extends RuntimeException{
    private static final long serialVersionUID = -766188197421923311L;

    private CompanyError error;

    public CompanyException(CompanyError error){
        this.error = error;
    }

    public CompanyError getCompanyError(){
        return error;
    }
}
