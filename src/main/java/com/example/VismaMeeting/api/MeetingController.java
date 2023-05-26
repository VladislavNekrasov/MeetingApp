package com.example.VismaMeeting.api;
import com.example.VismaMeeting.model.Category;
import com.example.VismaMeeting.model.Meeting;
import com.example.VismaMeeting.model.Type;
import com.example.VismaMeeting.service.MeetingService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;


    @RestController
    @RequestMapping("/api/meetings")
    public class MeetingController {
        private final MeetingService meetingService;

        public MeetingController(MeetingService meetingService) {
            this.meetingService = meetingService;
        }

        @PostMapping
        public ResponseEntity<String> createMeeting(@RequestBody Meeting meeting){
            meetingService.create(meeting);
            return ResponseEntity.status(HttpStatus.CREATED).build();
        }

        @DeleteMapping("/{meetingId}")
        public ResponseEntity<String> deleteMeeting(@PathVariable Long meetingId, @RequestParam String responsiblePerson){
            meetingService.delete(meetingId,responsiblePerson);
            return ResponseEntity.ok().build();
        }

        @PostMapping("/{meetingId}/attendees")
        public ResponseEntity<String> addPerson(@PathVariable Long meetingId, @RequestParam String person , @RequestParam LocalDate time){
            meetingService.addPerson(meetingId,person,time);
            return ResponseEntity.ok().build();
        }

        @DeleteMapping("/{meetingId}/attendees/{person}")
    public ResponseEntity<String> removeAttendee(@PathVariable Long meetingId, @PathVariable String person) {
        meetingService.removePerson(meetingId, person);
        return ResponseEntity.ok().build();
    }

    @GetMapping
    public ResponseEntity<List<Meeting>> getAllMeetings(
            @RequestParam(required = false) String name,
            @RequestParam(required = false) String description,
            @RequestParam(required = false) String responsiblePerson,
            @RequestParam(required = false) Category category,
            @RequestParam(required = false) Type type,
            @RequestParam(required = false) LocalDate startDate,
            @RequestParam(required = false) LocalDate endDate,
            @RequestParam(required = false) Integer minAttendees) {

        List<Meeting> filteredMeetings = meetingService.getAllMeetings(name,description, responsiblePerson, category, type, startDate, endDate, minAttendees);
        return ResponseEntity.ok(filteredMeetings);
    }
    }
