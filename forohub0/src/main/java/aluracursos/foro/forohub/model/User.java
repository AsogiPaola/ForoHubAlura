package aluracursos.foro.forohub.model;

import aluracursos.foro.forohub.dto.user.UpdateUserData;
import aluracursos.foro.forohub.dto.user.UserRegister;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

@Table(name = "users")
@Entity(name = "User")
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
public class User implements UserDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private String email;

    private String password;

    @Enumerated(EnumType.STRING)
    private Type type = Type.ROLE_USER;

    // Constructor para registro de usuario
    public User(UserRegister data) {
        this.name = data.name();
        this.email = data.email();
        this.password = data.password();
        if (data.type() != this.type) {
            this.type = data.type();
        }
    }

    // Metodo para actualizar datos del usuario
    public void updateData(UpdateUserData updateUserData) {
        if (updateUserData.name() != null) {
            this.name = updateUserData.name();
        }
        if (updateUserData.email() != null) {
            this.email = updateUserData.email();
        }
        if (updateUserData.password() != null) {
            this.password = updateUserData.password();
        }
        if (updateUserData.type() != updateUserData.type()) {
            this.type = updateUserData.type();
        }
    }



    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority(type.toString()));
    }

    @Override
    public String getUsername() {
        return email;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    // Métodos Getters
    public Long getId() {
        return id;
    }

    public String getEmail() {
        return email;
    }

    public String getName() {
        return name;
    }

    public Type getType() {
        return type;
    }
}
