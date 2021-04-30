package urjc.ugc.ultragamecenter.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class SPAController {
    @GetMapping({ "/new/**/{path:[^\\.]*}", "/{path:new[^\\.]*}" })
    public String redirect() {
        System.out.println("Ha reconocido la url como la de new");
        return "forward:/new/index.html";
    }
}
