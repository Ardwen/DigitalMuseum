package com.example.digitalmuseum.api;


import com.example.digitalmuseum.Security.Jwt.JwtTokenUtil;
import com.example.digitalmuseum.Security.Jwt.JwtUserDetailsService;
import com.example.digitalmuseum.Util.AuthUtil;
import com.example.digitalmuseum.Util.WebUtils;
import com.example.digitalmuseum.dao.UserDAO;
import com.example.digitalmuseum.model.Security.AppRole;
import com.example.digitalmuseum.model.Security.User;
import com.example.digitalmuseum.payload.AppUserForm;
import com.example.digitalmuseum.payload.JwtRequest;
import com.example.digitalmuseum.payload.JwtResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.social.connect.Connection;
import org.springframework.social.connect.ConnectionFactoryLocator;

import org.springframework.social.connect.UsersConnectionRepository;
import org.springframework.social.connect.web.ProviderSignInUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.WebRequest;
import org.springframework.security.core.Authentication;

import javax.transaction.Transactional;
import java.security.Principal;
import java.util.ArrayList;
import java.util.List;

@CrossOrigin
@Controller
@Transactional
public class UserController {

    @Autowired
    private UserDAO appUserDAO;

    private ConnectionFactoryLocator connectionFactoryLocator;

    private UsersConnectionRepository connectionRepository;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    @Autowired
    private JwtUserDetailsService userDetailsService;

    //authenticate user
    @RequestMapping(value = "/authenticate", method = RequestMethod.POST)
    public ResponseEntity<?> createAuthenticationToken(@RequestBody JwtRequest authenticationRequest) throws Exception {

        authenticate(authenticationRequest.getUsername(), authenticationRequest.getPassword());

        final UserDetails userDetails = userDetailsService
                .loadUserByUsername(authenticationRequest.getUsername());

        final String token = jwtTokenUtil.generateToken(userDetails);

        return ResponseEntity.ok(new JwtResponse(token));

    }

    /**
     *  get password from clinet and generate json web token
     * */
    private void authenticate(String username, String password) throws Exception {
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
        } catch (DisabledException e) {
            throw new Exception("USER_DISABLED", e);
        } catch (BadCredentialsException e) {
            throw new Exception("INVALID_CREDENTIALS", e);
        }
    }


    @CrossOrigin
    @RequestMapping(value = "/403", method = RequestMethod.GET)
    public String accessDenied(Model model, Principal principal) {

        if (principal != null) {
            UserDetails loginedUser = (UserDetails) ((Authentication) principal).getPrincipal();

            String userInfo = WebUtils.toString(loginedUser);

            model.addAttribute("userInfo", userInfo);

            String message = "Hi " + principal.getName() //
                    + "<br> You do not have permission to access this page!";
            model.addAttribute("message", message);

        }

        return "403Page";
    }

    /*@CrossOrigin
    @RequestMapping(value = { "/login" }, method = RequestMethod.GET)
    public String login(Model model) {
        return "loginPage";
    }

    @RequestMapping(value = { "/signin" }, method = RequestMethod.GET)
    public String signInPage(Model model) {
        return "redirect:/login";
    }*/

    @RequestMapping(value = { "/register" }, method = RequestMethod.GET)
    public String signupPage(WebRequest request, Model model) {
        ProviderSignInUtils providerSignInUtils = new ProviderSignInUtils(connectionFactoryLocator,
                connectionRepository);
        Connection<?> connection = providerSignInUtils.getConnectionFromSession(request);
        AppUserForm myForm = null;
        if (connection != null) {
            myForm = new AppUserForm(connection);
        } else {
            myForm = new AppUserForm();
        }
        model.addAttribute("myForm", myForm);
        return "signupPage";
    }

    @CrossOrigin
    @RequestMapping(value = { "/register" }, method = RequestMethod.POST)
    public String signupSave(WebRequest request, //
                             Model model, //
                             @RequestBody AppUserForm appUserForm, //
                             BindingResult result) {

        List<String> roleNames = new ArrayList<String>();
        roleNames.add(AppRole.ROLE_USER);

        User registered = null;

        try {
            registered = appUserDAO.registerNewUserAccount(appUserForm, roleNames);
        } catch (Exception ex) {
            ex.printStackTrace();
            model.addAttribute("errorMessage", "Error " + ex.getMessage());
            return "signupPage";
        }

        if (appUserForm.getSignInProvider() != null) {
            ProviderSignInUtils providerSignInUtils //
                    = new ProviderSignInUtils(connectionFactoryLocator, connectionRepository);
            providerSignInUtils.doPostSignUp(registered.getUsername(), request);
        }

        AuthUtil.logInUser(registered, roleNames);
        return "redirect:/userInfo";
    }

}
