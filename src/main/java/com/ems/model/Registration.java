package com.ems.model;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import org.hibernate.validator.constraints.Email;
import javax.validation.constraints.Size;

/**
 * Registartion Entity
 * @author reshmivn
 * @since 0.0.1
 */
@Entity
@Table(name="registration")
public class Registration {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private long id;

    @NotNull(message="{email.empty}")
    @Email(message="{email.format}")
    @Column(name="email")
    private String email;

    @NotNull(message="{reg.name.empty}")
    @Size(min=2, max=50, message="{reg.name.length}")
    @Column(name="name")
    private String name;

    @Column(name="apikey")
    private String apiKey;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getApiKey() {
        return apiKey;
    }

    public void setApiKey(String apiKey) {
        this.apiKey = apiKey;
    }

    @Override
    public String toString() {
        return "Registration{" +
                "id=" + id +
                ", email='" + email + '\'' +
                ", name='" + name + '\'' +
                ", apiKey='" + apiKey + '\'' +
                '}';
    }
}
