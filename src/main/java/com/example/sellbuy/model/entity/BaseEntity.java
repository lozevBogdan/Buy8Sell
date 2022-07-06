package com.example.sellbuy.model.entity;

import javax.persistence.*;
import java.time.Instant;
import java.time.LocalDateTime;

@MappedSuperclass
public abstract class BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private LocalDateTime created;

    private LocalDateTime modified;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDateTime getCreated() {
        return created;
    }

    public void setCreated(LocalDateTime created) {
        this.created = created;
    }

    public LocalDateTime getModified() {
        return modified;
    }

    public void setModified(LocalDateTime modified) {
        this.modified = modified;
    }

    // this method will be called when entity is created and before put (persist) in DB
    @PrePersist
    public void beforeCreate(){
        System.out.println("BEFORE CREATE A ENTITY - PREPERSIST");
        setCreated(LocalDateTime.now());
    }

    // this method will be called when already saved entity in DB is called, modified and go again i DB
    @PostPersist
    void onUpdate(){
        System.out.println("POSTPERSIST");
        setModified(LocalDateTime.now());
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        BaseEntity that = (BaseEntity) o;

        return getId() != null ? getId().equals(that.getId()) : that.getId() == null;
    }

    @Override
    public int hashCode() {
        return getId() != null ? getId().hashCode() : 0;
    }
}
