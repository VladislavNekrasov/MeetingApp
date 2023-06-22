package com.example.VismaMeeting.repository;

import com.example.VismaMeeting.model.Meeting;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.springframework.stereotype.Repository;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
@Repository
public class MeetingRepository {
    private static final String DATA_FILE_PATH = "meetings.json";
    private List<Meeting> meetings;
    private ObjectMapper objectMapper;

    public MeetingRepository() {
        objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
        loadMeetingsFromFile();
    }

    public void loadMeetingsFromFile() {
        try {
            meetings = objectMapper.readValue(new File(DATA_FILE_PATH), new TypeReference<List<Meeting>>() {});
        } catch (IOException e) {
            meetings = new ArrayList<>();
        }
    }

    public void saveMeetingsToFile() {
        try {
            objectMapper.writeValue(new File(DATA_FILE_PATH), meetings);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public List<Meeting> getMeetings() {
        return meetings;
    }

    public void setMeetings(List<Meeting> meetings) {
        this.meetings = meetings;
    }
}
