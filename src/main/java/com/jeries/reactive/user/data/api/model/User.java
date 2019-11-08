package com.jeries.reactive.user.data.api.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Setter
public class User {

    private int id;
    private String name;
    private String username;
    private String email;
    private Address address;
    private String phone;
    private String website;
    private Company company;
    private List<Comment> comments;

    public User(User user, List<Comment> comments) {
        this.id = user.id;
        this.name = user.name;
        this.username = user.username;
        this.email = user.email;
        this.address = user.address;
        this.phone = user.phone;
        this.website = user.website;
        this.company = user.company;
        this.comments = comments;
    }
}
