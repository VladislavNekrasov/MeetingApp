package com.example.VismaMeeting.service;

import com.example.VismaMeeting.model.Meeting;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import com.example.VismaMeeting.model.Category;
import com.example.VismaMeeting.model.Type;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

import java.time.LocalDate;
import java.time.Month;

public class MeetingServiceTest {
    private MeetingService meetingService;
    private Meeting getMeeting(){
        Meeting meeting = new Meeting();

        meeting.setId(1L);
        meeting.setName("Meeting 1");
        meeting.setResponsiblePerson("John");
        meeting.setDescription("wowowow");
        meeting.setCategory(Category.TeamBuilding);
        meeting.setType(Type.Live);
        meeting.setStartDate(LocalDate.now());
        meeting.setEndDate(LocalDate.now().plusDays(1));

        return meeting;
    }

    @BeforeEach
    public void Setup() {
        meetingService = new MeetingService();
    }

    @Test
    public void testCreateMeeting(){
        meetingService.create(getMeeting());
        Meeting addedMeeting = meetingService.findMeetingById(1L);
        Assertions.assertNotNull(addedMeeting);
        Assertions.assertEquals("John", addedMeeting.getResponsiblePerson());
        meetingService.delete(1L, "John");
    }

    @Test
    public void testDeleteMeeting(){
        meetingService.create(getMeeting());
        meetingService.delete(1L, "John");
        Meeting deletedMeeting = meetingService.findMeetingById(1L);
        Assertions.assertNull(deletedMeeting);
    }
    @Test
    public void testAddPerson(){
        meetingService.create(getMeeting());
        Meeting createdMeeting = meetingService.findMeetingById(1L);
        meetingService.addPerson(createdMeeting.getId(),"Vlad", LocalDate.of(2023, Month.MARCH, 21));
        Assertions.assertNotNull(createdMeeting.getAttendees());
        Assertions.assertEquals("Vlad", createdMeeting.getAttendees().get(0));
        meetingService.delete(1L,"John");

    }
    @Test
    public void testRemovePerson(){
        meetingService.create(getMeeting());
        Meeting createdMeeting = meetingService.findMeetingById(1L);
        meetingService.addPerson(createdMeeting.getId(),"Vlad", LocalDate.of(2023, Month.MARCH, 21));
        Assertions.assertEquals("Vlad", createdMeeting.getAttendees().get(0));
        meetingService.removePerson(1L,"Vlad");
        assertThat(createdMeeting.getAttendees().size(), equalTo(0));
        meetingService.delete(1L,"John");
    }
}
