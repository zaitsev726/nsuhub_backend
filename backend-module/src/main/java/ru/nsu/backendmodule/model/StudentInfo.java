package ru.nsu.backendmodule.model;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "student_info")
public class StudentInfo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    protected Long id;

    @Column(name = "faculty")
    private String faculty;

    @Column(name = "direction")
    private String direction;

    @Column(name = "course")
    private Integer course;

    @Column(name = "group")
    private String group;

    public StudentInfo(){}

    public StudentInfo(String faculty, Integer course) {
        this.faculty = faculty;
        this.course = course;
    }

    public StudentInfo(String faculty, String direction, Integer course, String group) {
        this.faculty = faculty;
        this.direction = direction;
        this.course = course;
        this.group = group;
    }

    public String getFaculty() {
        return faculty;
    }

    public void setFaculty(String faculty) {
        this.faculty = faculty;
    }

    public String getDirection() {
        return direction;
    }

    public void setDirection(String direction) {
        this.direction = direction;
    }

    public Integer getCourse() {
        return course;
    }

    public void setCourse(Integer course) {
        this.course = course;
    }

    public String getGroup() {
        return group;
    }

    public void setGroup(String group) {
        this.group = group;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        StudentInfo that = (StudentInfo) o;
        return Objects.equals(faculty, that.faculty) && Objects.equals(direction, that.direction) && Objects.equals(course, that.course) && Objects.equals(group, that.group);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), faculty, direction, course, group);
    }
}
