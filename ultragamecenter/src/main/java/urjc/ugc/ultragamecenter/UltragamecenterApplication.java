package urjc.ugc.ultragamecenter;

import springfox.documentation.builders.PathSelectors;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;



@SpringBootApplication
public class UltragamecenterApplication {


	public static void main(String[] args) {
		SpringApplication.run(UltragamecenterApplication.class, args);
	}
}
