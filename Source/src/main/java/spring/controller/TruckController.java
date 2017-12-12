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
        List<String> truckTypes = new ArrayList<>();
        List<Truck> distinctTrucks = new ArrayList<>();
        HashMap<Long, String> truckIdsAndImages = new HashMap<>();
        for(Truck t : truckList) {
            if (!truckImageIds.contains(t.getImageId())) {
                long truckImageId = t.getImageId();
                truckImageIds.add(truckImageId);
                byte[] temp = Base64.encode(imageRepository.findOne(truckImageId).getImage());
                String base64Encoded = new String(temp, "UTF-8");
                truckIdsAndImages.put(t.getImageId(), base64Encoded);
            }
            if (!truckTypes.contains(t.getTruckType())) {
                truckTypes.add(t.getTruckType());
                distinctTrucks.add(t);
            }
        }


        model.addAttribute("trucks", truckList);
        model.addAttribute("distinctTrucks", distinctTrucks);
        model.addAttribute("images", truckIdsAndImages);

        return "backend/trucks";
    }


    @GetMapping("/addTruck")
    public String loadAddTruckTemplate(Model model)  {
        return "backend/addTruck";
    }

    @PostMapping("/addTruck")
    public String handleFileUpload(@RequestParam("file") MultipartFile file,
                                   @RequestParam("amount") Integer amount,
                                   @RequestParam("truckType") String truckType,
                                   @RequestParam("length") int length,
                                   @RequestParam("width") int width,
                                   @RequestParam("payload") int payload,
                                   @RequestParam("description") String description,
                                   RedirectAttributes redirectAttributes) {

        if (amount <= 0) {
            redirectAttributes.addFlashAttribute("errorMessage",
                    "Error creating new truck: amount of trucks must be at least one.");
            return "redirect:/addTruck";
        }

        String errorMessage = "";

        if (length <= 0) {
            errorMessage += "Truck length must be positive!\n";
        }
        if (width <= 0) {
            errorMessage += "Truck width must be positive!\n";
        }
        if (payload <= 0) {
            errorMessage += "Truck weight must be positive!\n";
        }

        if (!errorMessage.isEmpty()) {
            redirectAttributes.addFlashAttribute("errorMessage",
                    errorMessage);
            return "redirect:/addTruck";
        }

        try {
            Image img = imageRepository.save(new Image(file.getBytes()));

            for (int i=0; i<amount; i++) {
                Truck t = new Truck(truckType, img.getId(), true);
                t.setLength(length);
                t.setWidth(width);
                t.setPayload(payload);
                t.setDescription((description == null) ? "" : description);
                truckRepository.save(t);
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
