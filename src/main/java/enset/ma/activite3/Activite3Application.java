package enset.ma.activite3;

import enset.ma.activite3.entities.Patient;
import enset.ma.activite3.repository.PatientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Date;

@SpringBootApplication
public class Activite3Application implements CommandLineRunner {

    @Autowired
    PatientRepository patientRepository;


    public static void main(String[] args) {
        SpringApplication.run(Activite3Application.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
//        patientRepository.save(new Patient(null,"karim",new Date(),true,200));
//        patientRepository.save(new Patient(null,"ibrahim",new Date(),false,555));
//        patientRepository.save(new Patient(null,"anouar",new Date(),true,123));
//        patientRepository.save(new Patient(null,"bilal",new Date(),false,344));


    }
    @Bean
    PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }
}
