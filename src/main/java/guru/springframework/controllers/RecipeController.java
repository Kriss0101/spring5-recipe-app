package guru.springframework.controllers;

import guru.springframework.domain.Recipe;
import guru.springframework.services.RecipeService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class RecipeController {

    RecipeService service;

    public RecipeController(RecipeService service) {
        this.service = service;
    }

    @RequestMapping("recipe/{id}/show")
    public String getRecipePage(Model model, @PathVariable("id") Long id) {
        Recipe recipe = service.getById(id);
        model.addAttribute("recipe", recipe);

        return "recipe/show";
    }
}
