package cat.udl.eps.entsoftarch.thesismarket;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.SecurityDataConfiguration;
import org.springframework.context.annotation.Import;

@SpringBootApplication
@Import({SecurityDataConfiguration.class})
public class ThesismarketApiApplication {

	public static void main(String[] args) {
		SpringApplication.run(ThesismarketApiApplication.class, args);
	}
}
