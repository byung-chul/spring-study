package hash.hellospring.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HelloController {

    @GetMapping("hello")
    public String hello(Model model) {
        model.addAttribute("data", "Spring!!!");
        // 문자로 return 시 viewResolver 가 hello 를 기준으로 resources/templates 에서 찾아서 hello.html 을 rendering
        // viewResolver 는 resources:templates/ + {ViewName} + .html 을 기본으로 mapping
        return "hello";
    }
}
