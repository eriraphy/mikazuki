package rph.mikazuki.microservices.account.controllers;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import rph.mikazuki.microservices.account.model.Account;

import java.util.ArrayList;
import java.util.List;

@RestController
public class AccountController {
    private List<Account> accounts;

    public AccountController() {
        accounts = new ArrayList<>();
        accounts.add(new Account("111"));
        accounts.add(new Account("222"));
        accounts.add(new Account("333"));
        accounts.add(new Account("444"));
        accounts.add(new Account("555"));
        accounts.add(new Account("666"));
    }

    @RequestMapping("/")
    public String greetings(){
        return "Hi there! Account-center at ur service:D";
    }

    @RequestMapping("/get")
    public Account findAccount(@RequestParam(value = "id", defaultValue = "") String id,
                               @RequestParam(value = "token", defaultValue = "") String token) {
        System.out.println(">>>Find by id: " + id);
        if (!id.equals("")) {
            return findById(id);
        } else if (!token.equals("")) {
            return findByToken(token);
        }
        return null;
    }

    public Account findById(String id) {
        return accounts.stream().filter(it -> it.getId().equals(id)).findFirst().orElse(null);
    }

    public Account findByToken(String token) {
        return accounts.stream().filter(it -> it.getToken().equals(token)).findFirst().orElse(null);
    }
}
