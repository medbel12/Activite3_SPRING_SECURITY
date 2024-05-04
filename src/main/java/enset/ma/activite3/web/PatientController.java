package enset.ma.activite3.web;

import enset.ma.activite3.entities.Patient;
import enset.ma.activite3.repository.PatientRepository;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.text.SimpleDateFormat;
import java.util.Date;

@Controller
@AllArgsConstructor
public class PatientController {
    @Autowired
    private PatientRepository patientRepository;
    @GetMapping("/user/index")

    public String index(Model model, @RequestParam(name ="page",defaultValue = "0") int page,
                        @RequestParam(name ="size",defaultValue = "5") int size,
                        @RequestParam(name ="keyword",defaultValue = "") String kw){
        Page<Patient> pagePatients = patientRepository.findByNomContains(kw, PageRequest.of(page,size));
        model.addAttribute("listPatients",pagePatients.getContent());
        model.addAttribute("pages",new int[pagePatients.getTotalPages()]);
        model.addAttribute("currentPage",page);
        model.addAttribute("keyword",kw);
        return "patients";

    }
    @GetMapping("/admin/delete")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public String delete(Long id,String keyword,int page){
        patientRepository.deleteById(id);

        return "redirect:/user/index?page="+page+"&keyword="+keyword;
    }
    @PostMapping("/addPatient")
    public String addPatient(@RequestParam String nom,
                             @RequestParam String dateNaissance,
                             @RequestParam(defaultValue = "false") boolean malade,
                             @RequestParam int score) {

        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date parsedDate;
        try {
            parsedDate = dateFormat.parse(dateNaissance);
        } catch (java.text.ParseException e) {
            e.printStackTrace();
            return "redirect:/index";
        }

        Patient newPatient = new Patient();
        newPatient.setNom(nom);
        newPatient.setDateNaissance(parsedDate);
        newPatient.setMalade(malade);
        newPatient.setScore(score);

        patientRepository.save(newPatient);

        return "redirect:/index";
    }
    @GetMapping("/")
    public String home(){
        return "redirect:/user/index";
    }

    @GetMapping("/admin/formPatients")
    @PreAuthorize("hasRole('ROLE_ADMIN')")

    public String formPatient(Model model){
        model.addAttribute("patient", new Patient() );
        return "formPatients";
    }
    @PostMapping("/admin/savePatient")
    @PreAuthorize("hasRole('ROLE_ADMIN')")

    public String savePatient(@Valid Patient patient, BindingResult bindingResult){
        if(bindingResult.hasErrors()){
            return "formPatients";
        }
        patientRepository.save(patient);
        return "redirect:/user/index?keyword="+patient.getNom();
    }
    @GetMapping("/admin/editPatient")
    @PreAuthorize("hasRole('ROLE_ADMIN')")

    public String editPatient(Model model,@RequestParam(name = "id")  Long id){
        Patient patient = patientRepository.findById(id).get();
        model.addAttribute("patient",patient);
        return "editPatient";
    }



}