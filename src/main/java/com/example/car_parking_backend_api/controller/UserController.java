package com.example.car_parking_backend_api.controller;

import com.example.car_parking_backend_api.dto.request.ParkingRequest;
import com.example.car_parking_backend_api.dto.response.ErrorResponse;
import com.example.car_parking_backend_api.dto.response.ParkingBill;
import com.example.car_parking_backend_api.dto.response.SuccessResponse;
import com.example.car_parking_backend_api.model.User;
import com.example.car_parking_backend_api.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@RestController
public class UserController {

    @Autowired
    private UserService userService;



    //TODO: user cannot park if he has already parked
    @Operation(summary = "User park a car")
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "User park a car",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = SuccessResponse.class),
                            examples = @ExampleObject("""
                                    {
                                        "message": "User park a car"
                                    }
                                    """)

                    )
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Bad request",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = ErrorResponse.class),
                            examples = @ExampleObject("""
                                    {
                                        "message": "Spot is not available"
                                    }
                                    """)
                    )
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Spot not found",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = ErrorResponse.class),
                            examples = @ExampleObject("""
                                    {
                                        "message": "Spot not found"
                                    }
                                    """)
                    )
            ),
            @ApiResponse(
                    responseCode = "410",
                    description = "You can't park here",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = ErrorResponse.class),
                            examples = @ExampleObject("""
                                    {
                                        "message": "Spot is not available"
                                    }
                                    """)
                    )
            )
    })
    @PostMapping("/park")
    public ResponseEntity<?> park(@RequestBody ParkingRequest parkingRequest) {
        User user = getCurrentLoginUser();
        return userService.park(user, parkingRequest);
    }


    @Operation(summary = "Get user's parking bill")
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Get user's parking bill",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = ParkingBill.class),
                            examples = @ExampleObject("""
                                    {
                                        "message": "Get user's parking bill"
                                    }
                                    """)

                    )
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Bad request",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = ErrorResponse.class),
                            examples = @ExampleObject("""
                                    {
                                        "message": "You have not parked a car"
                                    }
                                    """)
                    )
            )
    })
    @GetMapping("/bill")
    public ResponseEntity<?> bill() {
        User user = getCurrentLoginUser();
        return userService.getParkingBill(user);
    }


    @Operation(summary = "User back out the parking lot")
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "User back out the parking lot successfully",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = ParkingBill.class),
                            examples = @ExampleObject("""
                                    {
                                        "message": "User back out the parking lot"
                                    }
                                    """)

                    )
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Bad request",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = ErrorResponse.class),
                            examples = @ExampleObject("""
                                    {
                                        "message": "You have not parked a car"
                                    }
                                    """)
                    )
            )
    })
    @PostMapping("//backout")
    public ResponseEntity<?> backOut() {
        User user = getCurrentLoginUser();
        return userService.backOut(user);
    }

    private User getCurrentLoginUser() {
        String userEmail = SecurityContextHolder.getContext().getAuthentication().getName();
        return userService.getUserByEmail(userEmail);
    }

}
