package com.nip.transfer.models.entity;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDate;

import static com.fasterxml.jackson.annotation.JsonInclude.Include.NON_NULL;


@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(NON_NULL)
@SuperBuilder
public class User {
    @Id
    @Column(unique = true, name = "user_id")
    private String userId;

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "middle_name")
    private String middleName;

    @Column(name = "last_name")
    private String lastName;

    @Column(name = "date_of_birth")

    private LocalDate dateOfBirth;

    @Column(name = "date_created")
    private LocalDate dateCreated;

    @Column(name = "date_updated")
    private LocalDate dateUpdated;

    @Column(unique = true)
    private String bvn;

    @Column(unique = true)
    private String email;

    private BigDecimal balance;
}
