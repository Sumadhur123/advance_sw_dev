package com.example.fooddelivery.controller;

import com.example.fooddelivery.models.User;
import com.example.fooddelivery.repository.UserRepository;
import com.example.fooddelivery.repository.actionInterface.UserActions;
import com.example.fooddelivery.service.UserAuthService;

import java.util.List;
import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.view.RedirectView;

@Controller
@RequestMapping(value = "/auth")
public class UserAuthController {
  private static String suggestedUsernamesMessage = "Username is taken!! Below are some suggestions: ";
  private UserAuthService userAuthService;

  @Autowired
  public UserAuthController(UserRepository userRepository) {
    UserActions userActions = userRepository;
    this.userAuthService = new UserAuthService(userActions);
  }

  @GetMapping("/user_signup")
  public String userSignup(Model model) {
    model.addAttribute("user", new User());
    return "user/signup";
  }

  @PostMapping("/user_signup")
  public RedirectView acceptUserSignup(@ModelAttribute User user, Model model, RedirectAttributes redirectAttributes) {
    user.setUser_type("user");
    boolean usernameAlreadyexists = false;
    usernameAlreadyexists = userAuthService.userAlreadyExists(user.getUser_name());
    if (usernameAlreadyexists) {
      List<String> suggestedUsernames = userAuthService.getSuggestedUsername(user);
      redirectAttributes.addFlashAttribute("message", suggestedUsernamesMessage);
      redirectAttributes.addFlashAttribute("suggestedUsernames", suggestedUsernames);
      return new RedirectView("user_signup", true);
    } else {
      this.userAuthService.registerUser(user);
      return new RedirectView("signin", true);
    }
  }

  @GetMapping("/admin_signup")
  public String adminSignup(Model model) {
    model.addAttribute("user", new User());
    return "admin/signup";
  }

  @PostMapping("/admin_signup")
  public RedirectView acceptAdminSignup(@ModelAttribute User user, Model model, RedirectAttributes redirectAttributes) {
    user.setUser_type("admin");
    boolean usernameAlreadyexists = false;
    usernameAlreadyexists = userAuthService.userAlreadyExists(user.getUser_name());
    if (usernameAlreadyexists) {
      List<String> suggestedUsername = userAuthService.getSuggestedUsername(user);
      redirectAttributes.addFlashAttribute("message", suggestedUsernamesMessage);
      redirectAttributes.addFlashAttribute("suggestedUsernames", suggestedUsername);
      return new RedirectView("admin_signup", true);
    } else {
      this.userAuthService.registerUser(user);
      return new RedirectView("signin", true);
    }

  }

  @GetMapping("/signin")
  public String userSignIn() {
    return "signin";
  }

  @RequestMapping(value = "/logout")
  public String logout(HttpServletRequest request) {
    request.getSession().invalidate();
    return "redirect:/auth/signin";
  }

  @GetMapping("/userhome")
  public String userHome() {
    return "user/userhome";
  }

  @PostMapping(value = "/userhome")
  public String userHome(@RequestParam("name") String name, @RequestParam String password, HttpServletRequest request, Model model) {
    boolean loginCredsValid = false;
    loginCredsValid = userAuthService.isCredentialsValid(name, password);

    if (loginCredsValid) {
      User user = this.userAuthService.getUserDetailsFromUsername(name);
      if (user != null) {
        request.getSession().setAttribute("SESSION_USER_ID", user.getId());
        request.getSession().setAttribute("SESSION_USER_TYPE", user.getUser_type());
        request.getSession().setAttribute("SESSION_HOTEL_ID", user.getHotel_id());
      }

      String messages = (String) request.getSession().getAttribute("SESSION_USER");
      if (messages == null) {
        messages = new String();
        request.getSession().setAttribute("SESSION_USER", messages);
      }
      messages = name;
      request.getSession().setAttribute("SESSION_USER", messages);
      if (user.getUser_type().equals("admin")) {
        return "redirect:/admin-analysis";
      } else {
        return "user/userhome";
      }
    } else {
      return "user/loginfailed";
    }
  }
}
