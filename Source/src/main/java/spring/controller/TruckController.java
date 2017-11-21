package spring.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.codec.Base64;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import spring.entity.Image;
import spring.entity.Truck;
import spring.repositories.ImageRepository;
import spring.repositories.TruckRepository;
import spring.service.TruckService;

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

}
