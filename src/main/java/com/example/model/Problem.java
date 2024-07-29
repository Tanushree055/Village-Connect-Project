package com.example.model;

public class Problem {
    private int id;
    private int userId;
    private String firstname;
    private String lastname;
    private String email;
    private String subject;
    private String description;
    private int upvotes;

    // Constructor for creating Problem from database results
    public Problem(int id, int userId, String firstname, String lastname, String email, String subject, String description, int upvotes) {
        this.id = id;
        this.userId = userId;
        this.firstname = firstname;
        this.lastname = lastname;
        this.email = email;
        this.subject = subject;
        this.description = description;
        this.upvotes = upvotes;
    }

    // Constructor for creating new Problem to be inserted into database
    public Problem(int userId, String firstname, String lastname, String email, String subject, String description, int upvotes) {
        this.userId = userId;
        this.firstname = firstname;
        this.lastname = lastname;
        this.email = email;
        this.subject = subject;
        this.description = description;
        this.upvotes = upvotes;
    }

    // Getters and setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public int getUserId() { return userId; }
    public void setUserId(int userId) { this.userId = userId; }

    public String getFirstname() { return firstname; }
    public void setFirstname(String firstname) { this.firstname = firstname; }

    public String getLastname() { return lastname; }
    public void setLastname(String lastname) { this.lastname = lastname; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getSubject() { return subject; }
    public void setSubject(String subject) { this.subject = subject; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public int getUpvotes() { return upvotes; }
    public void setUpvotes(int upvotes) { this.upvotes = upvotes; }
}
