package lu.rbc.robotsstoreapi.configurations.security;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

/**
 * Created by Hedi Ayed on 01/03/2018.
 *
 *
 * --------------------
 *
 * @author Hedi Ayed
 */
class TokenAuthenticationService {
    private static final long   EXPIRATIONTIME = 864_000_000; // 10 days
    private static final String SECRET = "ThisIsASecret";
    private static final String TOKEN_PREFIX = "Bearer";
    private static final String HEADER_STRING = "Authorization";

    static String[] users = new String[]{"admin", "user"};
    private static final String[] adminRoles = new String[]{"USER", "ADMIN"};
    private static final String[] userRoles = new String[]{"USER"};

    static String[] getUserRoles(String user) {
        String[] roles = null;

        if (user != null) {
            switch (user){
                case "admin":
                    roles = adminRoles;
                    break;
                case "user":
                    roles = userRoles;
                    break;
            }
        }

        return roles;
    }

    static void addAuthentication(HttpServletResponse res, String username) {
        String JWT = Jwts.builder()
                .setSubject(username)
                .setExpiration(new Date(System.currentTimeMillis() + EXPIRATIONTIME))
                .signWith(SignatureAlgorithm.HS512, SECRET)
                .compact();
        res.addHeader(HEADER_STRING, TOKEN_PREFIX + " " + JWT);
    }

    static Authentication getAuthentication(HttpServletRequest request) {

        Authentication authentication = null;

        String token = request.getHeader(HEADER_STRING);
        if (token != null) {
            // parse the token.
            String user = Jwts.parser()
                    .setSigningKey(SECRET)
                    .parseClaimsJws(token.replace(TOKEN_PREFIX, ""))
                    .getBody()
                    .getSubject();

            if (user != null) {
                List<GrantedAuthority> authorities = getUserAuthorities(user);
                authentication = new UsernamePasswordAuthenticationToken(user, null, authorities);
            }
        }

        return authentication;
    }

    private static List<GrantedAuthority> getUserAuthorities(String user) {
        String[] roles = getUserRoles(user);
        List<GrantedAuthority> authorities;

        if (roles == null || roles.length == 0) {
            authorities = Collections.emptyList();
        }else {
            authorities = new ArrayList<>();
            for(String role:roles){
                authorities.add(new SimpleGrantedAuthority("ROLE_"+role));
            }
        }

        return authorities;
    }
}
