package spring.labs.dating.user;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import spring.labs.dating.user.dto.UpdateUserDTO;
import spring.labs.dating.user.interfaces.UserService;
import spring.labs.dating.user.models.User;
import spring.labs.dating.user.projections.UserProjection;

import java.util.List;

@RestController
@RequestMapping("/api/v1/users")
@AllArgsConstructor
public class UserController {
    private final UserService userService;

    @Operation(
            summary = "Get all users",
            description = "You can get all users"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successful"),
    })
    @GetMapping()
    List<UserProjection> getAll() {
        return userService.getALlUsers();
    }

    @Operation(
            summary = "Get user by id",
            description = "You can get user by id"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successful"),
    })
    @GetMapping("/{id}")
    UserProjection getById(@PathVariable Long id) {
        return userService.getUserById(id);
    }

    @Operation(
            summary = "Update user",
            description = "You can update yourself"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successful"),
    })
    @PatchMapping()
    UserProjection update(@Valid @RequestBody UpdateUserDTO updateUserDTO, HttpServletRequest request) {
        User user = (User)request.getAttribute("user");
        return userService.updateUser(user, updateUserDTO);
    }

    @Operation(
            summary = "Delete user",
            description = "You can delete yourself"
    )
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping()
    void delete(HttpServletRequest request) {
        User user = (User)request.getAttribute("user");
        userService.deleteUser(user.getId());
    }
}
