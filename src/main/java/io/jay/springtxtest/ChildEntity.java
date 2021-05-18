package io.jay.springtxtest;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class ChildEntity {

    @Id
    @GeneratedValue
    private Long id;
    private String value;
}
