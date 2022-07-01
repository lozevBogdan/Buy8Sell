package com.example.sellbuy.model.entity;

import javax.persistence.*;
import java.time.Instant;

@MappedSuperclass
public abstract class BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Instant created;

    private Instant modified;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Instant getCreated() {
        return created;
    }

    public void setCreated(Instant created) {
        this.created = created;
    }

    public Instant getModified() {
        return modified;
    }

    public void setModified(Instant modified) {
        this.modified = modified;
    }

    // this method will be called when entity is created and before put (persist) in DB
    @PrePersist
    public void beforeCreate(){
        System.out.println("BEFORE CREATE A ENTITY - PREPERSIST");
        setCreated(Instant.now());
    }

    // this method will be called when already saved entity in DB is called, modified and go again i DB
    @PostPersist
    void onUpdate(){
        System.out.println("POSTPERSIST");
        setModified(Instant.now());
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
