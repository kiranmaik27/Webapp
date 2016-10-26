package com.kiran.sample.domain;

import lombok.Data;

import javax.persistence.*;
import java.util.Date;

/**
 * Created by Kiranmai on 10/19/16.
 */
@Data
@Entity
@Table(name = "Person")
public class Person {

    @Id
    @SequenceGenerator(name = "person_seq_generator",
            sequenceName = "person_seq",
            allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE,
            generator = "person_seq_generator")
    @Column(name = "personid", updatable = false)
    private Long id;

    @Column(name = "first")
    private String name;

    //@Column(name="dob")
   // Date date;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    /*public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }*/
}
