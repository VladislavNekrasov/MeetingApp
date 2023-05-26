package com.example.VismaMeeting.model;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicLong;

public class Meeting {
    private static final AtomicLong ID_GENERATOR = new AtomicLong();
    private Long id;
    private String name;
    private String responsiblePerson;
    private String description;
    private Category category;
    private Type type;
    private LocalDate startDate;
    private LocalDate endDate;
    private List<String> attendees;

    public Meeting() {
        this.id = ID_GENERATOR.incrementAndGet();
        attendees = new ArrayList<>();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getResponsiblePerson() {
        return responsiblePerson;
    }

    public void setResponsiblePerson(String responsiblePerson) {
        this.responsiblePerson = responsiblePerson;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public Type getType() {
        return type;
    }

    public void setType(Type type) {
        this.type = type;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }

    public List<String> getAttendees() {
        return attendees;
    }

    public void setAttendees(List<String> attendees) {
        this.attendees = attendees;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Meeting meeting = (Meeting) o;
        return Objects.equals(id, meeting.id) && Objects.equals(name, meeting.name) && Objects.equals(responsiblePerson, meeting.responsiblePerson) && Objects.equals(description, meeting.description) && category == meeting.category && type == meeting.type && Objects.equals(startDate, meeting.startDate) && Objects.equals(endDate, meeting.endDate) && Objects.equals(attendees, meeting.attendees);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, responsiblePerson, description, category, type, startDate, endDate, attendees);
    }

    @Override
    public String toString() {
        return "Meeting{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", responsiblePerson='" + responsiblePerson + '\'' +
                ", description='" + description + '\'' +
                ", category=" + category +
                ", type=" + type +
                ", startDate=" + startDate +
                ", endDate=" + endDate +
                ", attendees=" + attendees +
                '}';
    }
}