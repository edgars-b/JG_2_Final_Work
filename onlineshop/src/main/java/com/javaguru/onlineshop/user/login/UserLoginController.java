package com.javaguru.onlineshop.user.login;

import com.javaguru.onlineshop.user.UserRoles;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;
import java.util.Collections;

@RestController
@RequestMapping("/api/v1/registration")
public class UserLoginController {

    @Autowired
    private UserLoginService service;

    @ModelAttribute("user")
    public UserLoginDTO userLoginDto() {
        return new UserLoginDTO();
    }

    @GetMapping
    public ModelAndView registration() {
        ModelAndView view = new ModelAndView();
        view.setViewName("registration");
        return view;
    }

    @GetMapping("/{id}")
    public UserLoginDTO findUser(@PathVariable Long id) {
        return service.findByID(id);
    }

    @PostMapping
    public ModelAndView addUser(@ModelAttribute("user") @Valid UserLoginDTO dto, BindingResult result) {
        UserLoginDTO found = service.findByLogin(dto.getUsername());
        ModelAndView view = new ModelAndView();
        if (found != null) {
            result.rejectValue("username", null, "User already exists");
        }
        if (result.hasErrors()) {
            view.setViewName("registration");
            return view;
        }
        dto.setActive(true);
        dto.setRoles(Collections.singleton(UserRoles.USER));
        service.save(dto);
        view.setViewName("redirect:/login");
        return view;
    }

//    @PostMapping("/save")
//    public ResponseEntity<Void> save(@Valid @RequestBody UserLoginDTO dto){
//        UserLoginDTO created = service.save(dto);
//        URI location = ServletUriComponentsBuilder
//                .fromCurrentRequest()
//                .path("/{id}")
//                .buildAndExpand()
//                .toUri();
//        return ResponseEntity.created(location).build();
//    }
}
