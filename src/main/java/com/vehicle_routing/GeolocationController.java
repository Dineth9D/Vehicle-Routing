package com.vehicle_routing;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class GeolocationController {

    private final GeolocationService geolocationService;

    @Autowired
    public GeolocationController(GeolocationService geolocationService) {
        this.geolocationService = geolocationService;
    }

    @RequestMapping("/")
    public String index() {
        return "index.html";
    }

    @PostMapping("/geolocation")
    public String getGeolocation(@RequestParam("latitude") double latitude, @RequestParam("longitude") double longitude, Model model) {
        GeolocationResult result = geolocationService.getGeolocation(latitude, longitude);
        model.addAttribute("result", result);
        return "result";
    }
}