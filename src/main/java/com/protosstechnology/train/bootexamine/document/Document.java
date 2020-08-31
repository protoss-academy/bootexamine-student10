package com.protosstechnology.train.bootexamine.document;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Data
@Entity
public class Document {
    @Id @GeneratedValue
    private Long id;
    private String documentNumber;
    private String description;
}
