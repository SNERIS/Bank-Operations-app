package com.my1project.my1projecg.dto;


import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public class UserRegistrationDTO {

    @NotBlank(message = "Username nuk mund të jetë bosh")
    private String username;

    @NotBlank(message = "Password nuk mund të jetë bosh")
    @Size(min = 8, message = "Password duhet të ketë të paktën 8 karaktere")
    private String password;

    @NotBlank(message = "Roli duhet të specifikohet")
    private String roleName;

    // Konstruktori bosh (duhet për Spring)
    public UserRegistrationDTO() {}

    // Getters dhe Setters
    public String getUsername() { return username; }
    public void setUsername(String username) { this.username = username; }

    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }

    public String getRoleName() { return roleName; }
    public void setRoleName(String roleName) { this.roleName = roleName; }
}
