package com.example.demo.controller;
import java.util.List;
import java.util.Properties;

import com.example.demo.model.ConfirmationToken;
import com.example.demo.repository.ConfirmationTokenRepository;
import com.example.demo.service.EmailSenderService;
import org.apache.catalina.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import com.example.demo.repository.userRepositery;

import com.example.demo.model.registered;
import com.example.demo.service.UserService;

@RestController
@RequestMapping("/api")
public class registerController {
    @Autowired
    UserService regService;
    @Autowired
    private userRepositery usrep;

    @Autowired
    private ConfirmationTokenRepository conrep;
    @Autowired
    private EmailSenderService emailSenderService;
    @RequestMapping(value="/regii", method=RequestMethod.POST)
    public registered createUser(@RequestBody registered user) {
        return regService.createUser(user);
    }

    @RequestMapping(value="/regi", method = RequestMethod.POST)
    public registered registerUser(@RequestBody registered user)
    {

        registered existingUser = usrep.findByuseremailIgnoreCase(user.getUseremail());
        if(existingUser != null)
        {
            System.out.println("already exists");
        }
        else
        {
            Properties props = new Properties();
            props.setProperty("mail.smtp.starttls.enable", "true");
            props.setProperty("mail.smtp.ssl.protocols", "TLSv1.2");
            props.put("mail.smtp.ssl.trust", "smtp.gmail.com");
            props.put("mail.smtp.ssl.trust", "host-url");
            usrep.save(user);

            ConfirmationToken confirmationToken = new ConfirmationToken(user);

            conrep.save(confirmationToken);

            SimpleMailMessage mailMessage = new SimpleMailMessage();
            mailMessage.setTo(user.getUseremail());
            mailMessage.setSubject("Complete Registration!");
            mailMessage.setFrom("semoghazal@gmail.com");
            mailMessage.setText("To confirm your account, please click here : "
                    +"http://localhost:8081/confirm?token="+confirmationToken.getConfirmationToken());

            emailSenderService.sendEmail(mailMessage);

            regService.createUser(user);
        }

        return  regService.createUser(user);

    }

    @RequestMapping(value="/regi", method=RequestMethod.GET)
    public List<registered> readUsers() {
        return regService.getUsers();
    }

    @RequestMapping(value="/regi/{ID}", method=RequestMethod.DELETE)
    public void deleteUser(@PathVariable(value = "ID") Long id) {

        regService.deleteUser(id);
    }
    @RequestMapping(value="/confirm", method= {RequestMethod.GET, RequestMethod.POST})
    public void confirmUserAccount(@RequestParam("token")String confirmationToken)
    {
        ConfirmationToken token = conrep.findByConfirmationToken(confirmationToken);

        if(token != null)
        {
            registered user = usrep.findByuseremailIgnoreCase(token.getUser().getUseremail());
            user.setEnabled(true);
            usrep.save(user);
        }
        else
        {
            System.out.println("link invalid");
        }

return ;    }
}