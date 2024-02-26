package com.contract.authentication.controller;


import java.util.Arrays;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.contract.authentication.config.JwtTokenUtil;
import com.contract.authentication.model.JwtRequest;
import com.contract.authentication.model.JwtResponse;
import com.contract.authentication.service.JwtUserDetailsService;
import com.contract.common.provider.LDAP;
import com.contract.nguoidung.nguoidung.model.ProfileModel;
import com.contract.nguoidung.nguoidung.service.NguoiDungService;

@CrossOrigin
@RestController
@RequestMapping("/api/authentication")
public class AuthenticationController {

    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private JwtTokenUtil jwtTokenUtil;
    @Autowired
    private JwtUserDetailsService jwtUserDetailsService;
    @Autowired
    private NguoiDungService nguoiDungService;
    @Autowired
    private Environment environment;

    protected final Log logger = LogFactory.getLog(getClass());

    @PostMapping()
    public ResponseEntity<?> createAuthenticationToken(
            @RequestBody JwtRequest authenticationRequest) {
        try {
            String username = authenticationRequest.getUsername().toLowerCase();
            String password = authenticationRequest.getPassword();
            if (username != null && password != null) {
                String pwd = password;

                Boolean isUser = true;
                Boolean isProd = !Arrays.stream(environment.getActiveProfiles())
                        .noneMatch(str -> str.equals("prod"));
                logger.warn("isProd" + isProd);
                if (isProd && !username.equals("superadmin@mobifone.vn")) {
                    isUser = LDAP.authentication(username, password);
                    pwd = "hdnt@2023$";
                }
                if (!isUser) {
                    final HttpHeaders httpHeaders = new HttpHeaders();
                    httpHeaders.setContentType(MediaType.APPLICATION_JSON);
                    return new ResponseEntity<String>(
                            "{\"mess\": \"Vui lòng kiểm tra thông tin tài khoản!\"}", httpHeaders,
                            HttpStatus.BAD_REQUEST);
                }
                authenticate(username, pwd);

                final UserDetails userDetails = jwtUserDetailsService.loadUserByUsername(username);
                final String token = jwtTokenUtil.generateToken(userDetails);

                ProfileModel profile = nguoiDungService.getProfile(userDetails.getUsername());

                return ResponseEntity.ok(new JwtResponse(token, profile));
            } else {
                final HttpHeaders httpHeaders = new HttpHeaders();
                httpHeaders.setContentType(MediaType.APPLICATION_JSON);
                return new ResponseEntity<String>(
                        "{\"mess\": \"Vui lòng kiểm tra thông tin tài khoản!\"}", httpHeaders,
                        HttpStatus.BAD_REQUEST);
            }
        } catch (Exception ex) {
            logger.warn("ex: " + ex.getMessage());
            final HttpHeaders httpHeaders = new HttpHeaders();
            httpHeaders.setContentType(MediaType.APPLICATION_JSON);
            return new ResponseEntity<String>(
                    "{\"mess\": \"Vui lòng kiểm tra thông tin tài khoản!\"}", httpHeaders,
                    HttpStatus.BAD_REQUEST);
        }
    }

    private void authenticate(String username, String password) throws Exception {
        try {
            authenticationManager
                    .authenticate(new UsernamePasswordAuthenticationToken(username, password));
        } catch (DisabledException e) {
            throw new Exception("USER_DISABLED", e);
        } catch (BadCredentialsException e) {
            throw new Exception("INVALID_CREDENTIALS", e);
        }
    }
}
