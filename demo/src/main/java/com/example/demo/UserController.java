package com.example.demo;

import java.awt.PageAttributes.MediaType;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("users")
public class UserController {
 
    @PutMapping("/{userId}")
    public String updateUser(@PathVariable String userId, @RequestBody UserDetailsRequestModel requestUserDetails)    {
        String returnValue = new String();

        //BeanUtils.copyProperties(requestUserDetails.toString(), returnValue);
        System.out.print("Ricevuto utente: ");
        System.out.println(requestUserDetails.toString());

        return returnValue;
    }
}
