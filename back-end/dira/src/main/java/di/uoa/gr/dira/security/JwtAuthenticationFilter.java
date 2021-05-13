package di.uoa.gr.dira.security;

import di.uoa.gr.dira.repositories.CustomerRepository;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Optional;

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {
    private final CustomerRepository customerRepository;
    private final JwtHelper jwtHelper;

    public JwtAuthenticationFilter(CustomerRepository customerRepository, JwtHelper jwtHelper) {
        this.customerRepository = customerRepository;
        this.jwtHelper = jwtHelper;
    }

    @Override
    protected void doFilterInternal(
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain filterChain
    ) throws ServletException, IOException {
        String header = request.getHeader(HttpHeaders.AUTHORIZATION);

        if (header == null || header.isEmpty() || !header.startsWith(JwtConstants.TOKEN_PREFIX)) {
            filterChain.doFilter(request, response);
            return;
        }

        String jwtToken = header.replace(JwtConstants.TOKEN_PREFIX, "");
        if (!jwtHelper.validateJwtToken(jwtToken)) {
            filterChain.doFilter(request, response);
            return;
        }

        UserDetails userDetails = customerRepository.findByUsername(jwtHelper.getUsername(jwtToken)).orElse(null);

        UsernamePasswordAuthenticationToken auth = new UsernamePasswordAuthenticationToken(
                userDetails,
                null,
                Optional.ofNullable(userDetails)
                        .map(UserDetails::getAuthorities)
                        .orElse(new ArrayList<>())
        );

        auth.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

        SecurityContextHolder.getContext().setAuthentication(auth);
        filterChain.doFilter(request, response);
    }
}