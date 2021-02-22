package urjc.ugc.ultragamecenter;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
@Controller
public class AplicationController{
    @GetMapping("/Ejemplo")
    public String ejemploAnuncio(Model model){
        model.addAttribute("nombre","Mundo!");
        return "EjemploTemplate";
    }
}