package ac.kopo.kr.realglowadmin;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class RealglowAdminApplication {

    public static void main(String[] args) {
        SpringApplication.run(RealglowAdminApplication.class, args);
    }

}
