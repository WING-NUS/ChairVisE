package sg.edu.nus.comp.chairvise4;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import sg.edu.nus.comp.chairvise4.entity.Person;
import sg.edu.nus.comp.chairvise4.repository.PersonRepository;

@SpringBootApplication
public class Chairvise4Application {

    public static void main(String[] args) {
        SpringApplication.run(Chairvise4Application.class, args);
    }

//    @Bean
//    public CommandLineRunner dataLoader(PersonRepository repo) {
//        return new CommandLineRunner() {
//            @Override
//            public void run(String... args) throws Exception {
////                repo.findAll();
//            }
//        };
//    }
}
