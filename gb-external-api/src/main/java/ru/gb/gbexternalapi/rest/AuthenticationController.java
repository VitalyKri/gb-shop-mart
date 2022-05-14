package ru.gb.gbexternalapi.rest;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.gb.gbapi.security.AuthenticationUserDto;
import ru.gb.gbapi.security.api.AuthGateway;
import ru.gb.gbexternalapi.entity.security.AccountUser;
import ru.gb.gbexternalapi.security.jwt.JwtTokenProvider;
import ru.gb.gbexternalapi.service.UserService;

import java.util.HashMap;
import java.util.Optional;

@RequiredArgsConstructor
@RestController
@RequestMapping(value = "/api/v1/auth")
public class AuthenticationController implements AuthGateway {

    private final UserService userService;
    private final AuthenticationManager authenticationManager;
    private final JwtTokenProvider jwtTokenProvider;

    @PostMapping("/login")
    public ResponseEntity<AuthenticationUserDto> login(@RequestBody AuthenticationUserDto authenticationUserDto) {
        final String username = authenticationUserDto.getUsername();
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        username,
                        authenticationUserDto.getPassword()
                ));
        Optional<AccountUser> accountUserOptional = userService.findByUsername(username);
        if (accountUserOptional.isPresent()) {
            String jwtToken = jwtTokenProvider.createToken(username, accountUserOptional.get().getRoles());
            authenticationUserDto.setToken(jwtToken);
            return ResponseEntity.ok(authenticationUserDto);
        } else {
            throw  new BadCredentialsException("Invalid username or password");
        }
    }
}
