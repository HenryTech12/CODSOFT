package com.convert.demo.controller;
import com.convert.demo.dto.APIData;
import com.convert.demo.dto.Request;
import com.convert.demo.dto.Response;
import com.convert.demo.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.stereotype.Controller; 

@Controller
public class HomeController {

    @Autowired
      private ConverterService converterService;
    
  
    @RequestMapping("/") 
     public String home(Model model) {
         try {
          APIData data = converterService.getJSON();
          model.addAttribute("countries", data.getCountries());
         } catch(Exception e) { e.printStackTrace(); }
         model.addAttribute("obj", new Request());
         return "index";
     }

     @PostMapping("/convert")
    public String convert(@ModelAttribute Request obj, Model model)  throws Exception{
        APIData apiData = converterService.getJSON();
        String result = converterService.convert(obj,apiData);
        model.addAttribute("countries", apiData.getCountries());
        model.addAttribute("obj", new Request());
        model.addAttribute("result",result);
        return "index";
     }

}