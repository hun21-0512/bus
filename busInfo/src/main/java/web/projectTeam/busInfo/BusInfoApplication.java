package web.projectTeam.busInfo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
public class BusInfoApplication {
	public static void main(String[] args) {
		SpringApplication.run(BusInfoApplication.class, args);
	}

}
