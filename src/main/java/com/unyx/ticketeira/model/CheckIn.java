//package com.unyx.ticketeira.model;
//
//import jakarta.persistence.*;
//import lombok.AllArgsConstructor;
//import lombok.Data;
//import lombok.EqualsAndHashCode;
//import lombok.NoArgsConstructor;
//
//import java.time.LocalDateTime;
//
//@Entity
//@Data
//@NoArgsConstructor
//@AllArgsConstructor
//@Table(name = "check_ins")
//@EqualsAndHashCode(of = "id")
//public class CheckIn {
//    @Id
//    @GeneratedValue(strategy = GenerationType.UUID)
//    private String id;
//
//    @ManyToOne
//    @JoinColumn(name = "ticket_id", nullable = false)
//    private Ticket ticket;
//
//    @ManyToOne
//    @JoinColumn(name = "event_id", nullable = false)
//    private Event event;
//
//    @ManyToOne
//    @JoinColumn(name = "checked_by", nullable = false)
//    private User checkedBy;
//
//    @Column(name = "check_in_time", nullable = false)
//    private LocalDateTime checkInTime;
//
//    private String location;
//
//    @Column(name = "device_info", columnDefinition = "json")
//    private String deviceInfo;
//
//    private String notes;
//}