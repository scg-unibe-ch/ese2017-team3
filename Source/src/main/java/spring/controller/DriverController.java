package spring.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * Created by olulrich on 24.10.17.
 */

@Controller
public class DriverController {

    @GetMapping(path = "/drivers")
    public String driverOverview(Model model) {
        return "drivers";
    }
}
