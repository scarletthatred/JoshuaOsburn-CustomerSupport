package com.example.customersupport.site;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.View;
import org.springframework.web.servlet.view.RedirectView;

import java.util.Hashtable;
import java.util.Map;

@Controller
public class AuthenticationController {
    public static final Map<String, String> userDB = new Hashtable<>();
    static {
        userDB.put("Joshua","admin456");
        userDB.put("Rachel","wife123");
        userDB.put("Hiccup","CatMeow");
    }

    @GetMapping("/newUser")
    public String newUser(HttpServletRequest request) {
        return "newUser";
    }

    @RequestMapping("logout")
    public View logout(HttpSession session){
        session.invalidate();
        return new RedirectView("login",true,false);
    }
    @GetMapping("login")
    public ModelAndView loginForm(Model model,HttpSession session){
        if(session.getAttribute("username") != null){
            return new ModelAndView(new RedirectView("/ticket/list",true,false));
        }
        model.addAttribute("loginFailed",false);
        return new ModelAndView("login", "loginForm", new LoginForm());

    }
    @PostMapping("login")
    public ModelAndView loginCheck(@ModelAttribute("loginForm") LoginForm form,
                                   Model model,
                                   HttpSession session,
                                   HttpServletRequest request){
        if (session.getAttribute("username") != null) {
            return new ModelAndView(new RedirectView("/ticket/list", true, false));
        }

        String username = form.getUsername();
        String password = form.getPassword();

        // Check if the "New User" button was clicked
        if ("New User".equals(request.getParameter("submit"))) {
            if (username == null || password == null || username.isEmpty() || password.isEmpty()) {
                model.addAttribute("loginFailed", true);
                return new ModelAndView("login");
            }
            if (userDB.containsKey(username)) {
                model.addAttribute("loginFailed", true);
                return new ModelAndView("login");
            }
            userDB.put(username, password);
            session.setAttribute("username", username);
            return new ModelAndView(new RedirectView("/ticket/list", true, false));
        }

        // Continue with the existing login check logic
        if (username == null || password == null || !userDB.containsKey(username) || !password.equals(userDB.get(username))) {
            model.addAttribute("loginFailed", true);
            model.addAttribute("loginForm", form);
            return new ModelAndView("login");
        }

        // We are logged in
        session.setAttribute("username", username);
        request.changeSessionId();
        return new ModelAndView(new RedirectView("/ticket/list", true, false));
    }

    public static class LoginForm{
        private String username;
        private String password;

        public String getUsername() {
            return username;
        }

        public void setUsername(String username) {
            this.username = username;
        }

        public String getPassword() {
            return password;
        }

        public void setPassword(String password) {
            this.password = password;
        }
    }
}
