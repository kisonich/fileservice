package com.fileservice.fileservice.src.model;

import jakarta.persistence.*;

import java.util.Objects;

@Entity
@Table(name = "documents", schema = "fileservice", catalog = "")
public class Document{
    private int id;
    private String name;
    private Long size;
    private String fullName;

    @Id
    @Column(name = "id", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public int getId() {
        return id;
    }

    public Document setId(int id) {
        this.id = id;
        return this;
    }

    @Basic
    @Column(name = "name", nullable = true, length = 256)
    public String getName() {
        return name;
    }

    public Document setName(String name) {
        this.name = name;
        return this;
    }

    @Basic
    @Column(name = "size", nullable = true)
    public Long getSize() {
        return size;
    }

    public Document setSize(Long size) {
        this.size = size;
        return this;
    }

    @Basic
    @Column(name = "full_name", nullable = true, length = 256)
    public String getFullName() {
        return fullName;
    }

    public Document setFullName(String fullName) {
        this.fullName = fullName;
        return this;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Document document = (Document) o;
        return id == document.id && Objects.equals(name, document.name) && Objects.equals(size, document.size) && Objects.equals(fullName, document.fullName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, size, fullName);
    }
}
