package org.vaadin.spring.tutorial;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import java.security.Principal;

/**
 * @author ELHAOUARI , Ilem MA
 * @since 15/08/2017
 */

@Controller
@PreAuthorize("hasRole('USER')")
public class UserController {

    private static final Logger log = LoggerFactory.getLogger(UserController.class);

    @RequestMapping("/")
    public Principal resource(Principal principal){
        log.debug(principal.getName());
        return principal;
    }

}
