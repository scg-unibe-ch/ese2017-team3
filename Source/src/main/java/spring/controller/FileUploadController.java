package spring.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.commons.CommonsMultipartFile;
import org.springframework.web.servlet.mvc.method.annotation.MvcUriComponentsBuilder;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import spring.entity.Image;
import spring.entity.Truck;
import spring.repositories.ImageRepository;
import spring.repositories.TruckRepository;
import spring.storage.StorageFileNotFoundException;
import spring.storage.StorageService;

@Controller
public class FileUploadController {

    private final StorageService storageService;

    @Autowired
    private TruckRepository truckRepository;

    @Autowired
    private ImageRepository imageRepository;


    @Autowired
    public FileUploadController(StorageService storageService) {
        this.storageService = storageService;
    }

    @GetMapping("/addTruck")
    public String listUploadedFiles(Model model) throws IOException {

        model.addAttribute("files", storageService.loadAll().map(
                path -> MvcUriComponentsBuilder.fromMethodName(FileUploadController.class,
                        "serveFile", path.getFileName().toString()).build().toString())
                .collect(Collectors.toList()));

        return "backend/addTruck";
    }

    @GetMapping("/files/{filename:.+}")
    @ResponseBody
    public ResponseEntity<Resource> serveFile(@PathVariable String filename) {

        Resource file = storageService.loadAsResource(filename);
        return ResponseEntity.ok().header(HttpHeaders.CONTENT_DISPOSITION,
                "attachment; filename=\"" + file.getFilename() + "\"").body(file);
    }

    @PostMapping("/addTruck")
    public String handleFileUpload(@RequestParam("file") MultipartFile file,
                                   @RequestParam("amount") Integer amount,
                                   @RequestParam("truckType") String truckType,
                                   RedirectAttributes redirectAttributes) {

        try {
            Image img = imageRepository.save(new Image(file.getBytes()));

            for (int i=0; i<amount; i++) {
                truckRepository.save(new Truck(truckType, img.getId(), true));
            }

            redirectAttributes.addFlashAttribute("message",
                    "You successfully created " + amount + " new trucks!");
            return "redirect:/addTruck";
        } catch (IOException e) {
            redirectAttributes.addFlashAttribute("message",
                    "There was an error while adding the new truck, please try again.");
            return "redirect:/addTruck";
        }
    }

    @ExceptionHandler(StorageFileNotFoundException.class)
    public ResponseEntity<?> handleStorageFileNotFound(StorageFileNotFoundException exc) {
        return ResponseEntity.notFound().build();
    }

}
