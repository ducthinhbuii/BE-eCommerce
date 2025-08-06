package com.example.ecommerce.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.servlet.view.RedirectView;

@RestController
public class MainController {
    @Operation(
        summary = "Default endpoint - redirects to Swagger UI",
        responses = {
            @ApiResponse(
                responseCode = "302",
                description = "Redirects to Swagger UI"
            )
        }
    )
    @GetMapping("/")
    public RedirectView redirectToSwagger(){
        return new RedirectView("/swagger-ui/index.html");
    }

    @Operation(
        summary = "Test endpoint - returns service account path",
        responses = {
            @ApiResponse(
                responseCode = "200",
                description = "Returns service account path"
            )
        }
    )
    @GetMapping("/test")
    public String test(){
        String serviceAccountPath = System.getProperty("user.dir");
        return serviceAccountPath;
    }
}
