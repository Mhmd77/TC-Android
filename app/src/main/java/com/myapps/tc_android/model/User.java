package com.myapps.tc_android.model;

import com.google.gson.annotations.SerializedName;

public class User {
    @SerializedName("id")
    private int id;
    @SerializedName("name")
    private String name;
    @SerializedName("username")
    private String username;
    @SerializedName("lastName")
    private String lastName;
    @SerializedName("age")
    private int age;
    @SerializedName("identificationId")
    private int identificationId;
    @SerializedName("email")
    private String email;
    @SerializedName("password")
    private String password;
    @SerializedName("role")
    private String role;

    private User(int id, String name, String username, String lastName, int age, int identificationId, String email, String password, String role) {
        this.id = id;
        this.name = name;
        this.username = username;
        this.lastName = lastName;
        this.age = age;
        this.identificationId = identificationId;
        this.email = email;
        this.password = password;
        this.role = role;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getUsername() {
        return username;
    }

    public String getLastName() {
        return lastName;
    }

    public int getAge() {
        return age;
    }

    public int getIdentificationId() {
        return identificationId;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public String getRole() {
        return role;
    }

    public class UserBuilder {
        private int id;
        private String name;
        private String username;
        private String lastName;
        private int age;
        private int identificationId;
        private String email;
        private String password;
        private String role;

        public UserBuilder setId(int id) {
            this.id = id;
            return this;
        }

        public UserBuilder setName(String name) {
            this.name = name;
            return this;
        }

        public UserBuilder setUsername(String username) {
            this.username = username;
            return this;
        }

        public UserBuilder setLastName(String lastName) {
            this.lastName = lastName;
            return this;
        }

        public UserBuilder setAge(int age) {
            this.age = age;
            return this;
        }

        public UserBuilder setIdentificationId(int identificationId) {
            this.identificationId = identificationId;
            return this;
        }

        public UserBuilder setEmail(String email) {
            this.email = email;
            return this;
        }

        public UserBuilder setPassword(String password) {
            this.password = password;
            return this;
        }

        public UserBuilder setRole(String role) {
            this.role = role;
            return this;
        }

        public User createUser() {
            return new User(id, name, username, lastName, age, identificationId, email, password, role);
        }
    }
}
