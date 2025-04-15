package com.example.poker_calculator.Controller;


import com.example.poker_calculator.Model.Hand;
import com.example.poker_calculator.Model.Table;
import com.example.poker_calculator.Model.WorkingCards;
import com.example.poker_calculator.Service.AllService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;


import java.util.ArrayList;
import java.util.List;

@Controller
public class DefaultController {
    AllService allService;

    public DefaultController(AllService allService) {
        this.allService = allService;
    }
    @GetMapping("/")
    public String redirectToHome() {
        return "redirect:/home";
    }

    @GetMapping("/home")
    public String getCards(Model model) {
        model.addAttribute("allService", allService);
        return "index";
    }

    @PostMapping("/home")
    public String processCard(AllService formAllService, Model model) {
        allService.setHandsFromSite(formAllService.getHandsFromSite());
        allService.setTableFromSite(formAllService.getTableFromSite());
        allService.setPlayers(formAllService.getPlayers());


        allService.calculate();


        model.addAttribute("allService", allService);
        return "index";
    }

    @PostMapping("/clear")
    public String clear(Model model) {
        allService.clear();
        model.addAttribute("allService", allService);
        return "redirect:/home";
    }



}
