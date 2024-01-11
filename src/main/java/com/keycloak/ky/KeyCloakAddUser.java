package com.keycloak.ky;

import org.keycloak.admin.client.Keycloak;
import org.keycloak.admin.client.resource.RoleResource;
import org.keycloak.admin.client.resource.RolesResource;
import org.keycloak.admin.client.resource.UsersResource;
import org.keycloak.representations.idm.CredentialRepresentation;
import org.keycloak.representations.idm.RoleRepresentation;
import org.keycloak.representations.idm.UserRepresentation;
import org.springframework.stereotype.Service;

import javax.ws.rs.core.Response;
import java.util.*;

@Service
public class KeyCloakAddUser {

    public void setUser(UserDTO user) {
        Response response;

        Keycloak keycloak = KeycloakConfig.getInstance();
        UsersResource usersResource = keycloak.realm("healthmanagementsystem").users();
        UserRepresentation kcUser = new UserRepresentation();


        String id = UUID.randomUUID().toString();
        Map<String, List<String>> customAttributes = new HashMap<>();
        customAttributes.put("code", Collections.singletonList("code"));


        CredentialRepresentation credentialRepresentation = Credentials.createPasswordCredentials(user.getPassword());
        kcUser.setUsername(user.getUserName());
        kcUser.setCredentials(Collections.singletonList(credentialRepresentation));
        kcUser.setFirstName(user.getUserName());
        kcUser.setLastName(user.getLastName());

        kcUser.setEnabled(true);
        kcUser.setEmailVerified(true);
        kcUser.setAttributes(customAttributes);

        response = usersResource.create(kcUser);


        List<UserRepresentation> keycloakUserList = usersResource.search(user.getUserName());
        /*if (response.getStatus() == 201) {
           String userId=keycloakUserList.get(0).getId();
            System.out.println(keycloakUserList.get(0).getId());
            // Assign roles to the user
            RolesResource rolesResource = keycloak.realm("healthmanagementsystem").roles();
            RoleRepresentation roleRepresentation = rolesResource.get("app_user").toRepresentation();
            if (roleRepresentation != null) {
                kcUser.setRealmRoles(Collections.singletonList(roleRepresentation.getName()));
                usersResource.get(userId).update(kcUser);
            }
        }*/

        if (response.getStatus() == 201) {
            String userId = response.getLocation().getPath().replaceAll(".*/([^/]+)$", "$1");
            System.out.println(userId);
            // Assign roles to the user
            RolesResource rolesResource = keycloak.realm("healthmanagementsystem").roles();
            RoleRepresentation roleRepresentation = rolesResource.get("app_user").toRepresentation();

            if (roleRepresentation != null) {
                List<RoleRepresentation> rolesToAdd = new ArrayList<>();
                rolesToAdd.add(roleRepresentation);

                usersResource.get(userId).roles().realmLevel().add(rolesToAdd);
            }
        } else {
            System.out.println(":here");
        }

    }
}
