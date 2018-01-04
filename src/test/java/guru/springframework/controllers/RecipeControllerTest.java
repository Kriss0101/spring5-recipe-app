package guru.springframework.controllers;

import guru.springframework.domain.Recipe;
import guru.springframework.repositories.RecipeRepository;
import guru.springframework.services.RecipeService;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.ui.Model;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

public class RecipeControllerTest {

    @Mock
    RecipeService service;

    @Mock
    Model model;
    //RecipeRepository repo;

    RecipeController controller;
    private MockMvc mvc;


    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        controller = new RecipeController(service);
        mvc = MockMvcBuilders.standaloneSetup(controller).build();
    }

    @Test
    public void testMockMVC() throws Exception {

        mvc.perform(get("show/1"))
                .andExpect(status().isOk())
                .andExpect(view().name("show"));

    }

    @Test
    public void getRecipe() {
        Long id = 1L;
        Recipe recipe = new Recipe();
        recipe.setId(id);

        when(service.getById(id)).thenReturn(recipe);
        String view=controller.getRecipePage(model, id);

        verify(service, times(1)).getById(id);
        verify(model, times(1)).addAttribute("recipe", recipe);

        assertThat(view).isEqualTo("recipe/show");

        //verify(model, times(1)).addAttribute(eq(""))
    }

    @Test
    public void getRecipeMockMvc() throws Exception {
        Recipe recipe = new Recipe();
        recipe.setId(1L);
        when(service.getById(1L)).thenReturn(recipe);

        mvc.perform(get("/recipe/1/show"))
                .andExpect(status().isOk())
                .andExpect(view().name("recipe/show"))
                .andExpect(model().attributeExists("recipe"));

    }
}
