package com.javamsdt.filestore.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name = "image")
@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
@ToString
public class Image {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Lob
    private byte[] content;

    @Column
    private String name;

    @Column
    private String url;

    @Column
    private String alt;

    @Column
    private String extension;

    @Column(name = "content_type")
    private String contentType;

}