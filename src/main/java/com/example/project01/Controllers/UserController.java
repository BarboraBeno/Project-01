package com.example.project01.Controllers;

import com.example.project01.Models.User;
import com.example.project01.Repositories.UserRepository;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
public class UserController {

    private UserRepository userRepository;

    public UserController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }


    @GetMapping("/login")
    public String loginPage(){
        return "login";
    }

    @GetMapping("/logout")
    public String logout(){
        return "redirect:login";
    }

    @PostMapping("/adduser")
    public String addUser(@ModelAttribute User user, RedirectAttributes attributes){
        List<User> userList = userRepository.findAllByLoginName(user.getLoginName());
        if(!userList.isEmpty()){
            attributes.addFlashAttribute("error","The user already exists.");
        }else {
            userRepository.save(user);
        }
        return "redirect:/login";
    }

    @PostMapping("/login")
    public String login(@RequestParam(name="loginName") String loginName, @RequestParam(name="password") String password,
                        RedirectAttributes attributes){
        User user = userRepository.findByLoginNameAndPassword(loginName,password);
        if(user == null){
            attributes.addFlashAttribute("error2","The user doesnt exist. Please register new user.");
            return "redirect:/login";
        }
        return "redirect:/" + loginName;
    }
}
