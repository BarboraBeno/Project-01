package com.example.project01.Controllers;

import com.example.project01.Models.Plant;
import com.example.project01.Models.Problem;
import com.example.project01.Models.User;
import com.example.project01.Repositories.PlantRepository;
import com.example.project01.Repositories.ProblemRepository;
import com.example.project01.Repositories.UserRepository;
import com.example.project01.Services.PlantService;
import com.example.project01.Services.ProblemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
@RequestMapping("/{loginName}")
public class MainController {

    private final PlantService plantService;
    private final PlantRepository plantRepository;
    private final ProblemRepository problemRepository;
    private final ProblemService problemService;
    private final UserRepository userRepository;


    @Autowired
    public MainController(PlantService plantService, PlantRepository plantRepository,
                          ProblemRepository problemRepository, ProblemService problemService,
                          UserRepository userRepository) {
        this.plantService = plantService;
        this.plantRepository = plantRepository;
        this.problemRepository = problemRepository;
        this.problemService = problemService;
        this.userRepository = userRepository;
    }

    @GetMapping("")
    public String contentPage(Model model, @PathVariable String loginName){
        List<Plant> listOfPlants = plantRepository.findAllByUser_UserIdIsNull();
        List<Problem> listOfProblems = problemRepository.findAll();
        model.addAttribute("loginName",loginName);
        model.addAttribute("listOfProblems",listOfProblems);
        model.addAttribute("listOfPlants",listOfPlants);
        return "index";
    }

    @PostMapping("/add")
    public String addPlant(@ModelAttribute Plant plant,@RequestParam(name="problemadd", required = false) String problemadd, @PathVariable String loginName){
        Problem problem = new Problem(problemadd);
        problemRepository.save(problem);
        plantRepository.save(plant);
        return "redirect:/" + loginName;
    }

    @GetMapping("/remove/{id}")
    public String removePlant(@PathVariable Long id, @PathVariable String loginName){
        plantRepository.deleteById(id);
        return "redirect:/" + loginName;
    }

    @GetMapping("/edit/{id}")
    public String editPlant(@PathVariable Long id, Model model, @PathVariable String loginName){
        List<Problem> listOfProblems = problemRepository.findAll();
        model.addAttribute("listOfProblems",listOfProblems);
        model.addAttribute("plant",plantRepository.getById(id));
        return "edit";
    }

    @PostMapping("/edit")
    public String editPlants(@ModelAttribute Plant plant, Model model, @PathVariable String loginName){
        List<Problem> listOfProblems = problemRepository.findAll();
        model.addAttribute("listOfProblems",listOfProblems);
        plantRepository.save(plant);
        return "redirect:/" + loginName;
    }

    @GetMapping("/used/{id}")
    public String changeUsed(@PathVariable Long id, @PathVariable String loginName){
        Plant plant = plantRepository.getById(id);
        if(plant.isUsed()){
            plant.setUsed(false);
        }else{
            plant.setUsed(true);
        }
        plantRepository.save(plant);
        return "redirect:/" + loginName;
    }

    @PostMapping("/change")
    public String changeProblem(@RequestParam(value="plant") Long id,
                                @RequestParam(value="problem") Long problemId,
                                @PathVariable String loginName){
        Plant plant = plantRepository.getById(id);
        Problem problem = problemRepository.getById(problemId);

        plant.setProblem(problem);
        plantRepository.save(plant);
        return "redirect:/" + loginName;
    }

    @GetMapping("/used")
    public String filterDone(Model model, @PathVariable String loginName){
        model.addAttribute("listOfPlants", plantRepository.findAllByUsedIsTrue());
        return "index";
    }

    @GetMapping("/unused")
    public String filterUndone(Model model, @PathVariable String loginName){
        model.addAttribute("listOfPlants", plantRepository.findAllByUsedIsFalse());
        return "index";
    }

    @GetMapping("/myList")
    public String myList(@PathVariable String loginName, Model model){
        User user = userRepository.findByLoginName(loginName);
        Long userId = user.getUserId();
        List<Plant> myList = plantRepository.findAllByUser_UserId(userId);
        model.addAttribute("myList", myList);
        return "MyList";
    }

    @GetMapping("/myListDelete/{id}")
    public String deleteFromMyList(@PathVariable String loginName, @PathVariable Long id){
        Plant plant = plantRepository.getById(id);
        plant.setUser(null);
        plantRepository.save(plant);
        return "redirect:/myList" + loginName;
    }

    @GetMapping("/myList/{id}")
    public String addToMyList(@PathVariable Long id, RedirectAttributes attributes, @PathVariable String loginName){
        User user = userRepository.findByLoginName(loginName);
        Plant plant = plantRepository.getById(id);
        if(plant.isUsed()){
            attributes.addFlashAttribute("error3","You cant move used plant. Change it to unused.");
        }else{
            plant.setUser(user);
            plantRepository.save(plant);
        }

        return "redirect:/myList" + loginName;
    }

    @GetMapping("/strength")
    public String orderByStrength(Model model, @PathVariable String loginName){
        model.addAttribute("listOfPlants",plantRepository.findAllOrderByStrengthDesc());
        return "index";
    }

    @GetMapping("/kidsafe")
    public String filterByKids(Model model, @PathVariable String loginName){
        model.addAttribute("listOfPlants",plantRepository.findAllByForKidsIsTrue());
        return "index";
    }
}
