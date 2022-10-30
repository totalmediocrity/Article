package com.example.demo.models;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.sql.Date;
import java.util.Collection;
import java.util.List;
import java.util.Set;

@Entity
public class User implements UserDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(unique = true)
    private String username;
    private String password;
    private boolean active;

    //-----------------------------------------------------------------------------------

    @NotEmpty(message = "Поле не может быть пустым")
    @NotBlank(message = "Поле не должно состоять только из пробелов")
    @Size(min = 2, max = 40, message = "Размер данного поля должен быть в диапазоне от 2 до 40")
    private String name, yourself;

    private char pol;

    @Min(value = 18, message = "Пользователь должен быть старше 18")
    @NotNull(message = "Поле не может быть пустым")
    @Positive(message = "Поле должно быть больше 0")
    private int age;

    @PastOrPresent(message = "Дата должна быть либо в настоящем, либо в прошлом")
    private Date dOfs;

    //-----------------------------------------------------------------------------------

    @ElementCollection(targetClass = Role.class, fetch = FetchType.EAGER)
    @CollectionTable(name = "user_role",joinColumns = @JoinColumn(name="user_id"))
    @Enumerated(EnumType.STRING)
    private Set<Role> roles;

    @OneToOne(optional = true, cascade = CascadeType.ALL)
    @JoinColumn(name = "contacts_id")
    private Contacts contacts;

    @ManyToMany()
    @JoinTable(name = "user_comment",
            joinColumns = @JoinColumn (name = "user_id"),
            inverseJoinColumns = @JoinColumn (name = "comment_id")
    )
    public List<Comment> comment;

    public List<Comment> getComment() {
        return comment;
    }

    public void setComment(List<Comment> comment) {
        this.comment = comment;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
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
        return isActive();
    }

    public void setUsername(String username) {
        this.username = username;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return getRoles();
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public Set<Role> getRoles() {
        return roles;
    }

    public void setRoles(Set<Role> roles) {
        this.roles = roles;
    }

    //-----------------------------------------------------------------------------------


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getYourself() {
        return yourself;
    }

    public void setYourself(String yourself) {
        this.yourself = yourself;
    }

    public char getPol() {
        return pol;
    }

    public void setPol(char pol) {
        this.pol = pol;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public Date getdOfs() {
        return dOfs;
    }

    public void setdOfs(Date dOfs) {
        this.dOfs = dOfs;
    }

    public Contacts getContacts() {
        return contacts;
    }

    public void setContacts(Contacts contacts) {
        this.contacts = contacts;
    }
}

