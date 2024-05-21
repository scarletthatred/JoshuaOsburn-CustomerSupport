package com.example.customersupport.site;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.view.RedirectView;
import org.springframework.web.servlet.View;

@Controller
public class IndexController {
    @RequestMapping("/")
    public View index() {
        return new RedirectView("/ticket/list",true,false);

    }
}
