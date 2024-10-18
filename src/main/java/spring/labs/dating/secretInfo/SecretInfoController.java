package spring.labs.dating.secretInfo;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import spring.labs.dating.secretInfo.dto.SecretAccessStatusDto;
import spring.labs.dating.secretInfo.dto.SecretDto;
import spring.labs.dating.secretInfo.interfaces.SecretInfoService;
import spring.labs.dating.secretInfo.models.SecretInfo;
import spring.labs.dating.secretInfo.models.SecretInfoAccess;
import spring.labs.dating.user.models.User;

import java.util.List;

@RestController
@RequestMapping("/api/v1/secret")
@AllArgsConstructor
public class SecretInfoController {
    private final SecretInfoService secretInfoService;

    @Operation(
            summary = "Create secret info",
            description = "You can create secret info for user"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Created"),
    })
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    SecretInfo createSecretInfo(@Valid @RequestBody SecretDto secretDto, HttpServletRequest request) {
        User user = (User)request.getAttribute("user");
        return secretInfoService.createSecretInfo(secretDto, user);
    }

    @Operation(
            summary = "Get user secret info",
            description = "You can get user secret info"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successful"),
            @ApiResponse(responseCode = "404", description = "Not found secret info", content = @Content()),
            @ApiResponse(responseCode = "403", description = "You haven't requested this resource", content = @Content()),
            @ApiResponse(responseCode = "403", description = "You don't have access to this resource", content = @Content()),
    })
    @GetMapping("/{id}")
    SecretInfo getSecretInfo(@PathVariable Long id, HttpServletRequest request) {
        User user = (User)request.getAttribute("user");
        return secretInfoService.getSecretInfo(id, user.getId());
    }

    @Operation(
            summary = "Update secret info",
            description = "You can update your secret info"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successful"),
            @ApiResponse(responseCode = "404", description = "Not found secret info", content = @Content()),
    })
    @PatchMapping
    SecretInfo updateSecretInfo(@Valid @RequestBody SecretDto secretDto, HttpServletRequest request) {
        User user = (User)request.getAttribute("user");
        return secretInfoService.updateSecretInfo(secretDto, user.getId());
    }

    @Operation(
            summary = "Delete secret info",
            description = "You can delete your secret info"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Successful"),
            @ApiResponse(responseCode = "404", description = "Not found secret info", content = @Content()),
    })
    @DeleteMapping
    @ResponseStatus(HttpStatus.NO_CONTENT)
    void deleteSecretInfo(HttpServletRequest request) {
        User user = (User)request.getAttribute("user");
        secretInfoService.deleteSecretInfo(user.getId());
    }

    @Operation(
            summary = "Delete secret info",
            description = "You can delete your secret info"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Successful"),
            @ApiResponse(responseCode = "403", description = "You can't create resource for you secret", content = @Content()),
            @ApiResponse(responseCode = "404", description = "No such secret info", content = @Content()),
            @ApiResponse(responseCode = "409", description = "You have already created this request", content = @Content()),
    })
    @PostMapping("/access/{userOwnerId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    void accessSecretInfo(@PathVariable Long userOwnerId, HttpServletRequest request) {
        User user = (User)request.getAttribute("user");
        secretInfoService.createRequestForSecretInfo(user, userOwnerId);
    }

    @Operation(
            summary = "Get requests for secret",
            description = "You can get requests for your secret info"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successful"),
            @ApiResponse(responseCode = "404", description = "You don't have a secret info", content = @Content()),
    })
    @GetMapping("/access")
    List<SecretInfoAccess> getSecretInfoAccesses(HttpServletRequest request) {
        User user = (User)request.getAttribute("user");
        return secretInfoService.getRequestForSecretInfo(user.getId());
    }

    @Operation(
            summary = "Update secret info request status",
            description = "You can update secret info request status"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successful"),
            @ApiResponse(responseCode = "403", description = "You can't change status of this secret", content = @Content()),
            @ApiResponse(responseCode = "404", description = "No such secret info", content = @Content()),
    })
    @PatchMapping("/access/{secretAccessId}")
    void updateSecretInfoAccessStatus(
            @PathVariable Long secretAccessId,
            @Valid @RequestBody SecretAccessStatusDto secretAccessStatusDto,
            HttpServletRequest request) {
        User user = (User)request.getAttribute("user");
        secretInfoService.updateRequestStatus(secretAccessId, secretAccessStatusDto.status(), user.getId());
    }
}
