package com.example.car_parking_backend_api.controller;

import com.example.car_parking_backend_api.dto.request.DateTimeInfo;
import com.example.car_parking_backend_api.dto.request.LoginRequest;
import com.example.car_parking_backend_api.dto.response.*;
import com.example.car_parking_backend_api.model.Spot;
import com.example.car_parking_backend_api.service.AuthService;
import com.example.car_parking_backend_api.service.ParkingLotService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
public class HomeController {

    private final ParkingLotService parkingLotService;
    private final AuthService authService;
    @Operation(summary = "Get all empty parking spots")
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Get all empty parking spots successfully",
                    content = @Content(
                            schema = @Schema(implementation = ParkingLotWithEmptySpotsResponse.class),
                            examples = @ExampleObject("""
                                    {
                                           "emptySpots": {
                                               "ZoneA": [],
                                               "ZoneS": [
                                                   {
                                                       "code": "T1",
                                                       "available": true
                                                   },
                                                   {
                                                       "code": "T2",
                                                       "available": true
                                                   }
                                               ]
                                           }
                                       }
                                    """)
                    )
            ),

    })
    @GetMapping("/emptySpots")
    public ResponseEntity<?> emptySpots() {
        return parkingLotService.getEmptySpots();
    }


    @Operation(summary = "Get all empty spots by zone type")
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Get all empty spots by zone type successfully",
                    content = @Content(
                           schema =  @Schema(implementation = ZoneWithEmptySpotsResponse.class)
                    )
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Zone not found",
                    content = @Content(
                            schema = @Schema(implementation = ErrorResponse.class)
                    )
            )
    })
    @GetMapping("/emptySpots/{zoneType}")
    public ResponseEntity<?> emptySpotsByType(@PathVariable String zoneType) {
        return parkingLotService.getEmptySpotsByType(zoneType);
    }


    @Operation(summary = "Get pricing information]")
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Get pricing information successfully",
                    content = @Content(
                            schema = @Schema(implementation = PricingDetailsInfoResponse.class)
                    )
            )
    })
    @GetMapping("/pricingDetails")
    public ResponseEntity<?> getPricingDetails() {
        return parkingLotService.getPricingDetails();
    }


    @Operation(summary = "Get parking price to particular time")
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Get parking price to particular time successfully",
                    content = @Content(
                            schema = @Schema(implementation = ParkingPriceResponse.class)
                    )
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Invalid time format",
                    content = @Content(
                            schema = @Schema(implementation = ErrorResponse.class)
                    )
            )
    })
    @GetMapping("/pricingTo/{dateTimeInfo}")
    public ResponseEntity<?> pricingTo(@RequestBody DateTimeInfo dateTimeInfo) {
        return parkingLotService.getPricingTo(dateTimeInfo);
    }


    @Operation(summary = "Login")
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Login successfully",
                    content = @Content(
                            schema = @Schema(implementation = LoginResponse.class)
                    )
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Invalid username or password",
                    content = @Content(
                            schema = @Schema(implementation = ErrorResponse.class)
                    )
            )
    })
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest loginRequest) {
        return authService.login(loginRequest);
    }
}
