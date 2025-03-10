package jmaster.io.accountservice.controller;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import jmaster.io.accountservice.kafka.Producer;
import jmaster.io.accountservice.kafka.User;
import jmaster.io.accountservice.model.MessageDTO;
import jmaster.io.accountservice.model.StatisticDTO;
import jmaster.io.accountservice.service.NotificationService;
import jmaster.io.accountservice.service.StatisticService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import jmaster.io.accountservice.model.AccountDTO;
import jmaster.io.accountservice.service.AccountService;

@RestController
@RequiredArgsConstructor
public class AccountController {

    private final AccountService accountService;
    private final StatisticService statisticService;
    private final NotificationService notificationService;
    private final Producer producer;

    // add new
    @PostMapping("/account")
    public AccountDTO addAccount(@RequestBody AccountDTO accountDTO) {
        accountService.add(accountDTO);
        statisticService.add(new StatisticDTO("Account " + accountDTO.getUsername() + " is created ", new Date()));

        //send notification
        MessageDTO messageDTO = new MessageDTO();
        messageDTO.setFrom("anhnguyen02072000@gmail.com");
        messageDTO.setTo("anhnguyen0207200ddd0@gmail.com");
        messageDTO.setToName(accountDTO.getName());
        messageDTO.setSubject("Hello spring cloud");
        messageDTO.setContent("Account: " + accountDTO.getUsername() + " is created");

        notificationService.sendNotification(messageDTO);

        return accountDTO;
    }

    // get all
    @GetMapping("/accounts")
    public List<AccountDTO> getAll() {
        producer.sendMessage(
                new User("anhnguyen02072000@gmail.com", "anh nguyen", 20, new Date(), "Get all accounts")
                , "get_all_accounts");
        return accountService.getAll();
    }

    @GetMapping("/account/{id}")
    public ResponseEntity<AccountDTO> get(@PathVariable(name = "id") Long id) {
        return Optional.of(new ResponseEntity<AccountDTO>(accountService.getOne(id), HttpStatus.OK))
                .orElse(new ResponseEntity<AccountDTO>(HttpStatus.NOT_FOUND));
    }

    @DeleteMapping("/account/{id}")
    public void delete(@PathVariable(name = "id") Long id) {
        statisticService.add(new StatisticDTO("Delete account id" + id + " is deleted ", new Date()));
        accountService.delete(id);
    }

    @PutMapping("/account")
    public void update(@RequestBody AccountDTO accountDTO) {
        statisticService.add(new StatisticDTO("Update account: " + accountDTO.getUsername(), new Date()));
        accountService.update(accountDTO);
    }
}
