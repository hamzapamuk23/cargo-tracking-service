package com.agridin.cargotrackingservice.model;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import org.hibernate.annotations.GenericGenerator;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.rest.core.annotation.RestResource;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "agridin_order")
public class Order {

    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
    @Column(name = "id", updatable = false, nullable = false)
    private UUID id;

    @Column(nullable = false, name = "created_at")
    private Date createdAt;

    private String trackingNumber;

    private String state;

    private String customer;

    private String customerPhoneNumber;

    private String city;

    private String district;

    private String paymentType;

    private String number;

    private String amount;

    private String cargo;

    private LocalDateTime date;

    @JsonManagedReference
	@OneToMany(mappedBy = "order")
	private List<CargoTracking> cargoTrackings;

    public Order(UUID id, Date createdAt, String trackingNumber, String state, String customer,
            String customerPhoneNumber, String city, String district, String paymentType, String number, String amount,
            String cargo, LocalDateTime date) {
        this.id = id;
        this.createdAt = createdAt;
        this.trackingNumber = trackingNumber;
        this.state = state;
        this.customer = customer;
        this.customerPhoneNumber = customerPhoneNumber;
        this.city = city;
        this.district = district;
        this.paymentType = paymentType;
        this.number = number;
        this.amount = amount;
        this.cargo = cargo;
        this.date = date;
    }

}
