package klasik.group.user.core.configuration;/**
 * @author pc00275
 * @since 23.01.2021
 */

import com.nimbusds.jose.shaded.json.JSONArray;
import com.nimbusds.jose.shaded.json.JSONObject;
import org.springframework.core.convert.converter.Converter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationConverter;
import org.springframework.security.oauth2.server.resource.authentication.JwtGrantedAuthoritiesConverter;

import java.util.Collection;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * KeycloakJwtAuthenticationConverter
 *
 * @author pc00275
 * @since 23.01.2021
 */
class KeycloakJwtAuthenticationConverter extends JwtAuthenticationConverter {
    private static final String ROLES = "roles";
    private static final String RESOURCE_ACCESS = "resource_access";
    private final Converter<Jwt, Collection<GrantedAuthority>> jwtGrantedAuthoritiesConverter = new JwtGrantedAuthoritiesConverter();

    protected Collection<GrantedAuthority> extractAuthorities(Jwt theJwt) {
        if (theJwt != null) {
            JSONObject keycloakClientAuthorities = ((JSONObject) theJwt.getClaimAsMap(RESOURCE_ACCESS).get("production-warehouse"));
            if (keycloakClientAuthorities != null) {
                JSONArray clientRoles = (JSONArray) keycloakClientAuthorities.get(ROLES);

                if (clientRoles != null) {
                    Collection<GrantedAuthority> clientAuthorities = clientRoles.stream()
                            .map(aRole -> "ROLE_" + aRole)
                            .map(SimpleGrantedAuthority::new)
                            .collect(Collectors.toList());

                    clientAuthorities.addAll(Objects.requireNonNull(jwtGrantedAuthoritiesConverter.convert(theJwt)));

                    return clientAuthorities;
                }
            }

        }
        return jwtGrantedAuthoritiesConverter.convert(Objects.requireNonNull(theJwt));
    }

}
