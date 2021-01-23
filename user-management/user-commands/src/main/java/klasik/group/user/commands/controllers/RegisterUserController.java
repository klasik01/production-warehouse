package klasik.group.user.commands.controllers;

import com.nimbusds.jose.shaded.json.JSONArray;
import com.nimbusds.jose.shaded.json.JSONObject;
import klasik.group.user.core.models.UserClaims;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collection;
import java.util.stream.Collectors;

@RestController
@RequestMapping(path = "/api/v1/user")
public class RegisterUserController {

    @RequestMapping(value = "/anonymous", method = RequestMethod.GET)
    public ResponseEntity<String> getAnonymous(@AuthenticationPrincipal Jwt jwt) {
        UserClaims claims = UserClaims.builder()
                .uid(jwt.getClaim("preferred_username"))
                .name(jwt.getClaim("name"))
                .firstname(jwt.getClaim("given_name"))
                .lastname(jwt.getClaim("family_name"))
                .email(jwt.getClaim("email"))
                .roles(jwt.getClaim("resource_access.production-warehouse.roles"))
                .build();

        JSONObject keycloakClientAuthorities = ((JSONObject) jwt.getClaimAsMap("resource_access").get("production-warehouse"));
        if (keycloakClientAuthorities != null) {
            JSONArray clientRoles = (JSONArray) keycloakClientAuthorities.get("roles");
            if (clientRoles != null) {
                Collection<GrantedAuthority> clientAuthorities = clientRoles.stream()
                        .map(aRole -> "ROLE_" + (String) aRole)
                        .map(SimpleGrantedAuthority::new)
                        .collect(Collectors.toList());

                claims.setRoles(clientAuthorities);
            }
        }
        return ResponseEntity.ok(String.format("Hello, %s", claims.toString()));
    }

    @PreAuthorize("hasRole('user')")
    @RequestMapping(value = "/user", method = RequestMethod.GET)
    public ResponseEntity<String> getUser(@AuthenticationPrincipal Jwt jwt) {
        System.out.println("Principal" + jwt.getClaim("preferred_username").toString());
        return ResponseEntity.ok(String.format("Hello, %s", jwt.getClaim("preferred_username").toString()));
    }

    @PreAuthorize("hasRole('admin')")
    @RequestMapping(value = "/admin", method = RequestMethod.GET)
    public ResponseEntity<String> getAdmin(@AuthenticationPrincipal Jwt jwt) {
        return ResponseEntity.ok("Hello Admin");
    }
}
