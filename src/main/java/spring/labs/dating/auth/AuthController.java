package spring.labs.dating.auth;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import spring.labs.dating.auth.interfaces.AuthService;
import spring.labs.dating.auth.dto.LoginDTO;
import spring.labs.dating.auth.dto.RegisterDTO;
import spring.labs.dating.user.models.User;


@RestController
@RequestMapping("/api/v1/auth")
@AllArgsConstructor
public class AuthController {
    private final AuthService authService;

    @Operation(
            summary = "Register new user",
            description = "You can register a new user"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "User is registered"),
            @ApiResponse(responseCode = "400", description = "User with such email exists", content = @Content()),
    })
    @PostMapping("/register")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<User> register(@Valid @RequestBody RegisterDTO userDTO) {
        return new ResponseEntity<>(authService.registerUser(userDTO), HttpStatus.CREATED);
    }

    @Operation(
            summary = "Login user",
            description = "You can login and get token for auth"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Logined"),
            @ApiResponse(responseCode = "400", description = "User with such email doesn't exist", content = @Content()),
            @ApiResponse(responseCode = "401", description = "Unauthorized", content = @Content()),
    })
    @PostMapping("/login")
    public ResponseEntity<String> login(@Valid @RequestBody LoginDTO loginDTO) {
        return new ResponseEntity<>(authService.login(loginDTO.email(), loginDTO.password()), HttpStatus.OK);
    }
}
