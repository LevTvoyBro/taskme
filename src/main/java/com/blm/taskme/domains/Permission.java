package com.blm.taskme.domains;

import com.blm.taskme.domains.enums.PermissionName;
import lombok.Data;
import org.springframework.security.core.GrantedAuthority;

import javax.persistence.*;

@Data
@Entity
@Table(name = "permissions")
public class Permission implements GrantedAuthority {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Enumerated(value = EnumType.STRING)
    private PermissionName name;

    @Override
    public String getAuthority() {
        return name.name();
    }
}
