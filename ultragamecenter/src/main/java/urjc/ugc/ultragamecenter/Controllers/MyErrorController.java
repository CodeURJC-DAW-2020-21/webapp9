package urjc.ugc.ultragamecenter.Controllers;

import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class MyErrorController implements ErrorController {

    @RequestMapping("/error")
    public String error(){
        return "404";
    }

    @Override
    public String getErrorPath() {
        return null;
    }
    
}