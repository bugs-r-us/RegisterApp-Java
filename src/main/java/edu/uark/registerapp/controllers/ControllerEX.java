package edu.uark.registerapp.controllers;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class ControllerEX {
   @RequestMapping(value = "/index")
   public String index() {
      return "index";
   }
}