package spring.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.codec.Base64;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import spring.entity.Image;
import spring.entity.Truck;
import spring.repositories.ImageRepository;
import spring.repositories.TruckRepository;
import spring.service.TruckService;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by olulrich on 05.11.17.
 */

@Controller
public class TruckController {

    @Autowired
    TruckService truckService;

    @Autowired
    TruckRepository truckRepository;

    @Autowired
    ImageRepository imageRepository;

    @GetMapping(path = "/trucks")
    public String truckOverview(Model model) throws Exception {

        List<Truck> truckList = truckService.getSortedTrucks();
        List<Long> truckImageIds = new ArrayList<>();
        HashMap<Long, String> truckIdsAndImages = new HashMap<>();
        for(Truck t : truckList) {
            if (!truckImageIds.contains(t.getImageId())) {
                long truckImageId = t.getImageId();
                truckImageIds.add(truckImageId);
                byte[] temp = Base64.encode(imageRepository.findOne(truckImageId).getImage());
                String base64Encoded = new String(temp, "UTF-8");
                truckIdsAndImages.put(t.getImageId(), base64Encoded);
            }
        }


        model.addAttribute("trucks", truckList);
        model.addAttribute("images", truckIdsAndImages);

        return "backend/trucks";
    }


    @GetMapping("/addTruck")
    public String listUploadedFiles(Model model) throws IOException {
        return "backend/addTruck";
    }

    @PostMapping("/addTruck")
    public String handleFileUpload(@RequestParam("file") MultipartFile file,
                                   @RequestParam("amount") Integer amount,
                                   @RequestParam("truckType") String truckType,
                                   RedirectAttributes redirectAttributes) {

        if (amount <= 0) {
            redirectAttributes.addFlashAttribute("errorMessage",
                    "Error creating new truck: amount of trucks must be at least one.");
            return "redirect:/addTruck";
        }

        try {
            Image img = imageRepository.save(new Image(file.getBytes()));

            for (int i=0; i<amount; i++) {
                truckRepository.save(new Truck(truckType, img.getId(), true));
            }

            redirectAttributes.addFlashAttribute("creationMessage",
                    "You successfully created " + amount + " new trucks!");
            return "redirect:/trucks";
        } catch (IOException e) {
            redirectAttributes.addFlashAttribute("errorMessage",
                    "There was an error while adding the new truck, please try again.");
            return "redirect:/addTruck";
        }
    }

}
