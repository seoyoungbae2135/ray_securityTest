package com.example.securityTest.controller;

import com.example.securityTest.dto.UserDto;
import com.example.securityTest.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

//20240222-3
@Controller
@RequiredArgsConstructor
public class MainController {

    private final UserService userService;
    private final PasswordEncoder passwordEncoder;

    /*@Autowired
    private UserService userService;*/

    @GetMapping("/")
    public String main(){

        return "index";
    }

    @GetMapping("/login")
    public String login(){
        return "login";
    }
    @PostMapping("/login")
    public String login(@RequestParam(value = "error",required = false) String error, //20240222-7
                        @RequestParam(value = "error_message" ,required = false) String msg,
                        Model model){ //시큐리티에 설정한 로그인 url매핑
        model.addAttribute("error" , error);
        model.addAttribute("error_message",msg);
        return "login";

    }
    @GetMapping("/public/a")
    public String an(){
        return "index";
    }

    @GetMapping("/guest")
    public String guest(){
        return "guest/main1.html";
    }

    @GetMapping("/member")
    public String member(){
        return "member/main2";
    }

    @GetMapping("/admin")
    public String admin(){
        return "admin/main3";
    }

    @GetMapping("/loginError")
    public String loginFail(){
        return "loginError";
    }

    @GetMapping("/join")
    public String join(Model model){
        model.addAttribute("userDto", new UserDto());
        return "join";
    }

    @PostMapping("/join")
    public String join(UserDto userDto, Model model){

        userService.saveUser(userDto, passwordEncoder);

        return "redirect:/";
    }

}
