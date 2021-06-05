package com.ncit.finder.controllers;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;


@ControllerAdvice
public class NullPointerHandler {

    @ExceptionHandler(NullPointerException.class)
    public ModelAndView handleNullPointer(
        NullPointerException ex, 
        HttpServletRequest request,
        HttpServletResponse response) {
            ex.printStackTrace();
          ModelAndView modelAndView = new ModelAndView("errorpage");
          
          return modelAndView;
      }
    
}
