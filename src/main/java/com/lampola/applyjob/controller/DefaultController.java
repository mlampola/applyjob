/*
 * (C) Markus Lampola 2016
 */
package com.lampola.applyjob.controller;

import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.lampola.applyjob.domain.Appl;
import com.lampola.applyjob.repository.ApplicationRepository;

/**
 *
 * @author Markus Lampola
 */
@Controller
public class DefaultController {

    @Autowired
    private ApplicationRepository applicationRepository;
    
    @RequestMapping(value="*", method = RequestMethod.GET)
    public String any(Model model) {
        return "redirect:/";
    }

    @RequestMapping(value="/", method = RequestMethod.GET)
    public String home(@ModelAttribute Appl appl) {
        return "index";
    }

    @RequestMapping(value = "/", method = RequestMethod.POST)
    public String create(@Valid @ModelAttribute Appl appl, BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            return "index";
        }

        appl = applicationRepository.save(appl);
        return "redirect:/confirm/" + appl.getId();
    }
    
    @RequestMapping(value = "/confirm/{applicationId}", method = RequestMethod.GET)
    public String list(Model model, @PathVariable Long applicationId) {
        model.addAttribute("appl", applicationRepository.findOne(applicationId));
        return "confirm";
    }
}
