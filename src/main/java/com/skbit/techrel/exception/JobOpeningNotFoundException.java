package com.skbit.techrel.exception;

public class JobOpeningNotFoundException extends RuntimeException {

 public   JobOpeningNotFoundException(){
     super("Job Opening Not found ");
    }
    public   JobOpeningNotFoundException(String message){
        super(message);
       }
   
}
