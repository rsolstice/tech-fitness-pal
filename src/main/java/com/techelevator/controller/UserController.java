

package com.techelevator.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import com.techelevator.model.dao.ExerciseDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.techelevator.model.dto.User;
import com.techelevator.model.dao.UserDAO;

@Controller
public class UserController {

	private UserDAO userDAO;

	@Autowired
	public UserController(UserDAO userDAO) {
		this.userDAO = userDAO;
	}

	//-------------------------------------------------------------------REGISTERING
	@RequestMapping(path="/users/new", method=RequestMethod.GET)
	public String displayNewUserForm(ModelMap modelHolder) {
		if( ! modelHolder.containsAttribute("user")) {
			modelHolder.addAttribute("user", new User());
		}
		return "newUser";
	}
	
	@RequestMapping(path="/users/new", method=RequestMethod.POST)
	public String createUser(@Valid @ModelAttribute User user, BindingResult result, RedirectAttributes flash) {

		boolean isUserNameAvailable = userDAO.isUserNameAvailable(user.getUserName());
		if(!isUserNameAvailable) {
			FieldError error = new FieldError("user", "userName","The UserName is not available.");
			result.addError(error);
		}

		if(result.hasErrors()) {
			flash.addFlashAttribute("user", user);
			flash.addFlashAttribute(BindingResult.MODEL_KEY_PREFIX + "user", result);
			return "redirect:/users/new";
		}

		userDAO.saveUser(user.getUserName(), user.getPassword());
		return "redirect:/login";
	}

	@RequestMapping(path="/users/create", method=RequestMethod.POST)
	public String createProfile(@Valid @ModelAttribute User user, BindingResult result, RedirectAttributes flash) {

		//boolean isUserName

		if (result.hasErrors()) {
			flash.addFlashAttribute("user", user);
			flash.addFlashAttribute(BindingResult.MODEL_KEY_PREFIX + "user", result);
			return "redirect:/users/create";
		}


		return "redirect:/login";
	}


	//----------------------------------------------------------------- Home Page
	@RequestMapping(path="/", method=RequestMethod.GET)
	public String displayHomePage(ModelMap modelHolder) {

		return "homepage";
	}

	//----------------------------------------------------------------- Profile Page
	@RequestMapping(path="/users/profile", method=RequestMethod.GET)
	public String displayUserProfile(ModelMap modelHolder) {
		if( ! modelHolder.containsAttribute("user")) {
			modelHolder.addAttribute("user", new User());
		}
		return "profilePage";
	}

	//----------------------------------------------------------------- Display Edit Profile Page
	@RequestMapping(path="/users/edit", method=RequestMethod.GET)
	public String displayEditProfileForm(ModelMap modelHolder, @ModelAttribute User user) {
		if( ! modelHolder.containsAttribute("user")) {
			modelHolder.addAttribute("user", new User());

		}

		return "editProfile";
	}

	//----------------------------------------------------------------- Edit Profile
	@RequestMapping(path="/users/edit", method=RequestMethod.POST)
	public String editProfile(@Valid @ModelAttribute User user, BindingResult result, RedirectAttributes flash, HttpSession session) {
		if (result.hasErrors()) {
			flash.addFlashAttribute("user", user);
			flash.addFlashAttribute(BindingResult.MODEL_KEY_PREFIX + "user", result);
			return "redirect:/users/edit";
		}

		User currentUser = (User) session.getAttribute("currentUser");

		userDAO.updateProfile(currentUser.getUserName(), user);

		session.setAttribute("currentUser", userDAO.getUserByUserName(currentUser.getUserName()));

		return "redirect:/users/profile";
	}

}
