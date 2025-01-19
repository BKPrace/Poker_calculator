package com.example.poker_calculator;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;


/* celá aplikace je v angličtině, kromě komentářů,
 protože jsem línej se s tím psát */

@SpringBootApplication
public class PokerCalculatorApplication {

    public static void main(String[] args) {
        SpringApplication.run(PokerCalculatorApplication.class, args);

        System.out.println("běží to: http://localhost:8081 ");
    }

}
