package uis.vet.club.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import uis.vet.club.model.JwtRequest;
import uis.vet.club.model.JwtResponse;
import uis.vet.club.model.Usuario;
import uis.vet.club.security.util.JwtUtil;
import uis.vet.club.service.util.impl.UserDao;
import uis.vet.club.service.util.impl.UserDetailsServiceImpl;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
@RequestMapping("/auth")
@RequiredArgsConstructor
@Slf4j
public class AuthenticationController {

    @Autowired
    private final AuthenticationManager authenticationManager;

    @Autowired
    private final UserDao userDao;

    @Autowired
    private UserDetailsServiceImpl userDetailsService;

    @Autowired
    private JwtUtil jwtUtil;

    @PostMapping("/token")
    public ResponseEntity<?> authenticate(@RequestBody Usuario usuario) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(usuario.getUsername(), usuario.getPassword()));
        final UserDetails userDetails = this.userDetailsService.loadUserByUsername(usuario.getUsername());
        if (userDetails != null) {
            String token = this.jwtUtil.generateToken(userDetails);
            return ResponseEntity.ok(new JwtResponse(token));
            //return ResponseEntity.ok(jwtUtil.generateToken(userDetails));
        }
        return ResponseEntity.status(400).body("Algun error ha ocurrido");
    }

/*
    @PostMapping("/token")
    public ResponseEntity<?> generarToken(@RequestBody Usuario usuario) throws Exception{
        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(usuario.getUsername(), usuario.getPassword()));
        }catch (Exception exception) {
            exception.printStackTrace();
            throw new Exception("Usuario no encontrado");
        }
        final UserDetails userDetails = this.userDetailsService.loadUserByUsername(usuario.getUsername());
        if (userDetails != null) {
            String token = this.jwtUtil.generateToken(userDetails);
            return ResponseEntity.ok(new JwtResponse(token));
            //return ResponseEntity.ok(jwtUtil.generateToken(userDetails));
        }
        return ResponseEntity.status(400).body("Algun error ha ocurrido");
    }


    @PostMapping("/token")
    public ResponseEntity<?> generarToken(@RequestBody JwtRequest jwtRequest) throws Exception{
        try {
            autenticar(jwtRequest.getUsername(), jwtRequest.getPassword());
        }catch (Exception exception) {
            exception.printStackTrace();
            throw new Exception("Usuario no encontrado");
        }
        //antes
       // UserDetails userDetails = this.userDetailsService.loadUserByUsername(jwtRequest.getUsername());
        //String token = this.jwtUtil.generateToken(userDetails);
        //return ResponseEntity.ok(new JwtResponse(token));
        //despues
        final UserDetails userDetails = this.userDao.findUserByUsername(jwtRequest.getUsername());
        if(userDetails != null){
            return ResponseEntity.ok(jwtUtil.generateToken(userDetails));
        }
        return ResponseEntity.status(400).body("Algun error ha ocurrido");
    }

    private void autenticar(String username, String password) throws Exception {
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
        } catch (DisabledException disabledException) {
            new Exception("Usuario Deshabilitado" + disabledException.getMessage());
        } catch (BadCredentialsException badCredentialsException) {
            new Exception("Credenciales Invalidas" + badCredentialsException.getMessage());
        }
    }

    @PostMapping("/token")
    public ResponseEntity<?> generarToken(@RequestBody Usuario usuario) throws Exception {
        try {
            autenticar(usuario.getUsername(), usuario.getPassword());
        } catch (Exception exception) {
            exception.printStackTrace();
            throw new Exception("Usuario no encontrado");
        }
        UserDetails userDetails = this.userDetailsService.loadUserByUsername(usuario.getUsername());
        String token = this.jwtUtil.generateToken(userDetails);
        return ResponseEntity.ok(new JwtResponse(token));
    }*/
}
