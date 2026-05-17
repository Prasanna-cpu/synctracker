package com.spring.synctracker.user_service.controller;

import com.spring.synctracker.user_service.dto.UserDTO;
import com.spring.synctracker.user_service.entity.User;
import com.spring.synctracker.user_service.enums.Role;
import com.spring.synctracker.user_service.exception.ConflictingResourcesException;
import com.spring.synctracker.user_service.exception.ObjectNotFoundException;
import com.spring.synctracker.user_service.jwt.JwtProvider;
import com.spring.synctracker.user_service.mapper.UserMapper;
import com.spring.synctracker.user_service.repository.UserRepository;
import com.spring.synctracker.user_service.request.Login;
import com.spring.synctracker.user_service.request.Register;
import com.spring.synctracker.user_service.response.ApiResponse;
import com.spring.synctracker.user_service.response.AuthResponse;
import com.spring.synctracker.user_service.service.implementation.CustomUserDetailServiceImplementation;
import io.jsonwebtoken.Claims;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {
    private final UserRepository userRepository;
    private final CustomUserDetailServiceImplementation customUserDetailService;
    private final PasswordEncoder passwordEncoder;
    private final JwtProvider jwtProvider;

    @PostMapping("/register")
    public ResponseEntity<AuthResponse> registerUserHandler(
            @RequestBody Register register
    ){
        String email = register.getEmail();
        String password = register.getPassword();
        String confirmPassword = register.getConfirmPassword();
        String fullName = register.getFullName();
        Role role = register.getRole();

        Boolean existingEmail = userRepository.existsByEmail(email);
//        log.info("Existing email: {}", existingEmail);

        //Logic to check if the password and confirm password are same
        if(!password.equals(confirmPassword)){
            throw new ConflictingResourcesException("Password and confirm password do not match");
        }

        if(existingEmail){
            throw new ConflictingResourcesException("Email already exists");
        }

        User createdUser = new User();
        createdUser.setEmail(email);
        createdUser.setPassword(passwordEncoder.encode(password));
        createdUser.setFullName(fullName);
        createdUser.setRole(role);

        User savedUser = userRepository.save(createdUser);

        UserDTO userDTO = UserMapper.mapUserToUserDTO(savedUser);

        Authentication authentication = new UsernamePasswordAuthenticationToken(
                email,
                null,
                List.of(new SimpleGrantedAuthority("ROLE_" + role.name()))
        );

        SecurityContextHolder.getContext().setAuthentication(authentication);

        String accessToken = jwtProvider.generateToken(authentication);
        String refreshToken = jwtProvider.generateRefreshToken(authentication);

        return ResponseEntity.status(HttpStatus.CREATED).body(
                new AuthResponse(
                        accessToken,
                        refreshToken,
                        userDTO,
                        "User registered successfully",
                        HttpStatus.CREATED.value(),
                        HttpStatus.CREATED
                )
        );
    }

    @PostMapping("/login")
    public ResponseEntity<AuthResponse> loginUserHandler(
            @RequestBody Login login
    ) {
        String email = login.getEmail();
        String password = login.getPassword();

        Boolean existingEmail = userRepository.existsByEmail(email);

        if(!existingEmail){
            throw new ObjectNotFoundException("Email does not exist");
        }

        Authentication authentication = authenticateUser(email, password);

        SecurityContextHolder.getContext().setAuthentication(authentication);

        String accessToken = jwtProvider.generateToken(authentication);
        String refreshToken = jwtProvider.generateRefreshToken(authentication);

        return ResponseEntity.status(HttpStatus.OK).body(
                new AuthResponse(
                        accessToken,
                        refreshToken,
                        null,
                        "User logged in successfully",
                        HttpStatus.OK.value(),
                        HttpStatus.OK
                )
        );
    }

    private Authentication authenticateUser(String email, String password){
        UserDetails details=customUserDetailService.loadUserByUsername(email);

        if(details == null){
            throw  new BadCredentialsException("Invalid credentials");
        }
        if(!passwordEncoder.matches(password,details.getPassword())){
            throw new BadCredentialsException("Invalid password");
        }
        return new UsernamePasswordAuthenticationToken(details, null, details.getAuthorities());
    }

    @PostMapping("/refresh")
    public ResponseEntity<AuthResponse> refreshToken(HttpServletRequest request){
        String header = request.getHeader("Authorization");

        if(header == null || !header.startsWith("Bearer ")){
            throw new BadCredentialsException("Invalid token");
        }

        String token = header.substring(7);

        Claims claims = jwtProvider.extractClaims(token);

        String type = (String) claims.get("type");

        if(!type.equals("refresh")){
            throw new BadCredentialsException("Invalid token");
        }

        String email = claims.getSubject();
        UserDetails userDetails = customUserDetailService.loadUserByUsername(email);

        Authentication authentication = new UsernamePasswordAuthenticationToken(
                userDetails,
                null,
                userDetails.getAuthorities()
        );

        String newAccessToken = jwtProvider.generateToken(authentication);

        return ResponseEntity.status(HttpStatus.OK).body(
                new AuthResponse(
                        newAccessToken,
                        token,
                        null,
                        "Token Refreshed",
                        HttpStatus.OK.value(),
                        HttpStatus.OK
                )
        );

    }

}
