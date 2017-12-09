package spring.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import spring.entity.Animal;
import spring.repositories.AnimalRepository;

import java.io.IOException;
import java.util.List;

/**
 * Created by olulrich on 09.12.17.
 */

@Controller
public class AnimalController {

    @Autowired
    AnimalRepository animalRepository;

    @GetMapping(path = "/animals")
    public String animalOverview(Model model) {
        List<Animal> animals = animalRepository.findAll();
        model.addAttribute("animals", animals);

        return "backend/animals";
    }

    @GetMapping(path = "/animals/delete")
    public ModelAndView deleteAnimal(Model model, @RequestParam String species) {
        Animal toDelete = animalRepository.findAnimalBySpecies(species);

        animalRepository.delete(toDelete);

        return new ModelAndView("redirect:/animals");
    }

    @GetMapping(path = "/addAnimal")
    public String addAnimalForm(Model model) {
        return "backend/addAnimal";
    }

    @PostMapping(path = "/addAnimal")
    public String addAnimal(@RequestParam("species") String species,
                            @RequestParam("length") int length,
                            @RequestParam("width") int width,
                            @RequestParam("weight") int weight,
                            RedirectAttributes redirectAttributes) {

        String errorMessage = "";

        if (length <= 0) {
            errorMessage += "Animal length must be positive!\n";
        }
        if (width <= 0) {
            errorMessage += "Animal width must be positive!\n";
        }
        if (weight <= 0) {
            errorMessage += "Animal weight must be positive!\n";
        }

        if (!errorMessage.isEmpty()) {
            redirectAttributes.addFlashAttribute("errorMessage",
                    errorMessage);
            return "redirect:/addAnimal";
        } else {

            Animal animal = new Animal(species, width, length, weight);
            animal.setLength(length);
            animal.setWidth(width);
            animal.setWeight(weight);
            animalRepository.save(animal);

            redirectAttributes.addFlashAttribute("creationMessage",
                    "You successfully created a new Animal");
            return "redirect:/animals";
        }
    }
}
