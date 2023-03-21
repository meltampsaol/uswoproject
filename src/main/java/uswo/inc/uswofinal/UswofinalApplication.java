package uswo.inc.uswofinal;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jackson.JacksonAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication(exclude = { JacksonAutoConfiguration.class })
@ComponentScan(basePackages = "uswo.inc.uswofinal")
public class UswofinalApplication {
	public static void main(String[] args) {
		SpringApplication.run(UswofinalApplication.class, args);
	}
}
