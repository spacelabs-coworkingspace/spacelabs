package com.rawlabs.spacelabs.controller;

import com.rawlabs.spacelabs.domain.dao.CoworkingSpace;
import com.rawlabs.spacelabs.service.CoworkingSpaceService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping(value = "/coworking-space")
@SecurityRequirement(name = "bearer")
public class CoworkingSpaceController {
    private final CoworkingSpaceService coworkingSpaceService;

    @Autowired
    public CoworkingSpaceController(CoworkingSpaceService coworkingSpaceService) {
        this.coworkingSpaceService = coworkingSpaceService;
    }

    @GetMapping(value = "", produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "Get all coworking spaces by address")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "Success")
            }
    )
    public List<CoworkingSpace> getCoworkingSpaces(@RequestParam(value = "address", required = false) String address,
                                                   @RequestParam(value = "name", required = false) String name){
        return coworkingSpaceService.getCoworkingSpaces(address, name);
    }


    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "Get coworking space by ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Success")
    })
    public CoworkingSpace getByID(@PathVariable(value = "id") Long id){
        return coworkingSpaceService.getById(id);
    }

    @DeleteMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "Delete coworking space by ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Success")
    })
    public void deleteCoworkingSpace(@PathVariable(value = "id") Long id){
        coworkingSpaceService.deleteById(id);
    }


}
