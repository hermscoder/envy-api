package br.com.egp.envy.security;

import br.com.egp.envy.dto.CredentialsDTO;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;

public class JwtAuthenticationFilter extends UsernamePasswordAuthenticationFilter {

    private JwtTokenProvider jwtTokenProvider;
    private AuthenticationManager authenticationManager;

    public JwtAuthenticationFilter(JwtTokenProvider jwtTokenProvider, AuthenticationManager authenticationManager) {
        this.jwtTokenProvider = jwtTokenProvider;
        this.authenticationManager = authenticationManager;
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
        try{
            CredentialsDTO credentialsDTO = new ObjectMapper().readValue(request.getInputStream(), CredentialsDTO.class);
            UsernamePasswordAuthenticationToken authenticationToken =
                    new UsernamePasswordAuthenticationToken(credentialsDTO.getUsernameOrEmail(),
                                                            credentialsDTO.getSenha(), new ArrayList<>());
            Authentication auth = authenticationManager.authenticate(authenticationToken);
            return auth;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request,
                                            HttpServletResponse response,
                                            FilterChain chain,
                                            Authentication authResult) throws IOException, ServletException {

        String username = ((UserPrincipal) authResult.getPrincipal()).getUsername();
        String token = jwtTokenProvider.generateToken(username);
        response.addHeader("Authorization", "Bearer " + token);
    }
}
