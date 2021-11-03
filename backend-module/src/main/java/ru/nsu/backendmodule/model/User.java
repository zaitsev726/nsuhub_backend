package ru.nsu.backendmodule.model;

import javax.persistence.*;
import java.time.Instant;

@Entity
@Table(name = "users")
public class User extends BaseModel<String> {

    @Column(name = "name")
    private String name;

    @Column(name = "email")
    private String email;

    @Column(name = "login")
    private String login;

    @Column(name = "password")
    private String password;

    @Column(name = "phone_number")
    private String phoneNumber;

    @Column(name = "is_student")
    private Boolean isStudent;

    @Column(name = "bio")
    private String bio;

    @Column(name = "created_at")
    private Instant createdAt;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "student_info_id", referencedColumnName = "id")
    private StudentInfo studentInfo;

    public User() {
    }

    public User(String id, String name, String email, String login, String password,
                String phoneNumber, Boolean isStudent, String bio, StudentInfo studentInfo) {
        this.id = id;
        this.name = name;
        if (email != null) {
            this.email = email;
        }
        this.login = login;
        this.password = password;
        if (email != null) {
            this.phoneNumber = phoneNumber;
        }

        this.isStudent = isStudent;
        if (isStudent) {
            this.bio = bio;
            this.studentInfo = studentInfo;
        }

        this.createdAt = Instant.now();
    }

    public User(String id, String name, String email, String login, String password, String phone) {
       this(id, name, email, login, password, phone, false, null, null);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public Boolean getStudent() {
        return isStudent;
    }

    public void setStudent(Boolean student) {
        isStudent = student;
    }

    public String getBio() {
        return bio;
    }

    public void setBio(String bio) {
        this.bio = bio;
    }

    public Instant getCreatedAt() {
        return createdAt;
    }

    public StudentInfo getStudentInfo() {
        return studentInfo;
    }

    public void setStudentInfo(StudentInfo studentInfo) {
        this.studentInfo = studentInfo;
    }
}
