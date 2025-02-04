package com.astra.api.hub_api.model;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
@Table(name = "user")
public class User implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(name = "profile_picture")
    private String profilePicture;

    @Column(name = "user_name")
    private String userName;

    @Column
    private String email;
    
    @Column
    private String password;

    @Column
    private boolean enabled = false;

    @Column
    private boolean banned = false;

    @Column(name = "account_non_expired")
    private boolean accountNonExpired = true;
    
    @Column(name = "account_non_locked")
    private boolean accountNonLocked = true;
    
    @Column(name = "credentials_non_expired")
    private boolean credentialsNonExpired = true;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "user_permission_link",
                joinColumns = {@JoinColumn(name = "id_user")},
                inverseJoinColumns = {@JoinColumn(name = "id_permission")})
    private List<Permission> permissions = new ArrayList<>();
    
    //@Column(name = "favorite_projects")
    //private List<Project> favoriteProjects;

    //@Column(name = "favorite_articles")
    //private List<Article> favoriteArticles;

    //@Column
    //private List<Article> articles;

    //@Column
    //private List<Project> projects;
    
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return this.permissions;
    }

    @Override
    public String getPassword() {
        return this.password;
    }

    @Override
    public String getUsername() {
        return this.userName;
    }

    public List<String> getRoles(){
        List<String> roles = new ArrayList<>();
        for(Permission permission : permissions){
            roles.add(permission.getDescription());
        }
        return roles;
    }

    public void addPermission(Permission newPermission){
        permissions.add(newPermission);
    }

}