package payment_gateway.demo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class ViewController {

    /**
     * Handle all routes that don't start with /api and serve index.html This
     * allows Angular to handle client-side routing
     */
    @RequestMapping(value = {
        "/",
        "/{path:[^\\.]*}",
        "//{path:[^\\.]*}"
    })
    public String forward() {
        return "forward:/index.html";
    }
}