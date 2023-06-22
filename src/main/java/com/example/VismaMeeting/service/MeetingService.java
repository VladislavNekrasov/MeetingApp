package com.example.VismaMeeting.service;

import com.example.VismaMeeting.model.Category;
import com.example.VismaMeeting.model.Meeting;
import com.example.VismaMeeting.model.Type;
import com.example.VismaMeeting.repository.MeetingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class MeetingService {
    @Autowired
    private MeetingRepository meetingRepository;

    public Meeting findMeetingById(Long id) {
        return meetingRepository.getMeetings().stream()
                .filter(meeting -> meeting.getId().equals(id))
                .findFirst()
                .orElse(null);
    }

    public boolean isPersonBusy(String person, LocalDate startDate, LocalDate endDate) {
        return meetingRepository.getMeetings().stream()
                .anyMatch(meeting -> !meeting.getResponsiblePerson().equals(person) &&
                        ((meeting.getStartDate().isBefore(startDate) && meeting.getEndDate().isAfter(startDate)) ||
                                (meeting.getStartDate().isAfter(startDate) && meeting.getStartDate().isBefore(endDate))));
    }

    public void create(Meeting meeting) {
        meetingRepository.getMeetings().add(meeting);
        meetingRepository.saveMeetingsToFile();
    }

    public void delete(Long meetingId, String responsiblePerson) {
        Meeting meeting = findMeetingById(meetingId);
        if (meeting != null && meeting.getResponsiblePerson().equals(responsiblePerson)) {
            meetingRepository.getMeetings().remove(meeting);
            meetingRepository.saveMeetingsToFile();
        } else {
            throw new IllegalArgumentException("Meeting not found or responsible person does not match");
        }
    }

    public void addPerson(Long meetingId, String person,LocalDate time) {
        Meeting meeting = findMeetingById(meetingId);
        if (meeting == null) {
            throw new IllegalArgumentException("Meeting not found");
        }

        if (meeting.getAttendees().contains(person)) {
            throw new IllegalArgumentException("Person is already added to the meeting");
        }

        if (isPersonBusy(person, meeting.getStartDate(), meeting.getEndDate())) {
            throw new IllegalArgumentException("Person is already busy during the meeting time");
        }
            meeting.getAttendees().add(person);
        meetingRepository.saveMeetingsToFile();
    }

    public void removePerson(Long meetingId, String person) {
        Meeting meeting = findMeetingById(meetingId);
        if (meeting != null && !meeting.getResponsiblePerson().equals(person)) {
            meeting.getAttendees().remove(person);
            meetingRepository.saveMeetingsToFile();
        } else {
            throw new IllegalArgumentException("Invalid meeting or responsible person cannot be removed.");
        }
    }
    public List<Meeting> getAllMeetings(String name,
             String description,
            String responsiblePerson,
             Category category,
             Type type,
             LocalDate startDate,
             LocalDate endDate,
             Integer minAttendees) {

        return meetingRepository.getMeetings().stream()
                .filter(meeting -> name == null|| meeting.getName().contains(name))
                .filter(meeting -> description == null || meeting.getDescription().contains(description))
                .filter(meeting -> responsiblePerson == null || meeting.getResponsiblePerson().equals(responsiblePerson))
                .filter(meeting -> category == null || meeting.getCategory() == category)
                .filter(meeting -> type == null || meeting.getType() == type)
                .filter(meeting -> startDate == null || meeting.getStartDate().isAfter(startDate.minusDays(1)))
                .filter(meeting -> endDate == null || meeting.getEndDate().isBefore(endDate.plusDays(1)))
                .filter(meeting -> minAttendees == null || meeting.getAttendees().size() >= minAttendees)
                .collect(Collectors.toList());
    }

}
