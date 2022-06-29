package com.revature.taskmaster.common.datasource;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.time.LocalDateTime;

@Embeddable
public class ResourceMetadata {

    @Column(name = "creation_datetime")
    private LocalDateTime creationDatetime;

    @Column(name = "updated_datetime")
    private LocalDateTime updatedDatetime;

    @Column(name = "is_active")
    private boolean active;

    public ResourceMetadata() {
        this.creationDatetime = LocalDateTime.now();
        this.updatedDatetime = LocalDateTime.now();
        this.active = false;
    }

    public LocalDateTime getCreationDatetime() {
        return creationDatetime;
    }

    public void setCreationDatetime(LocalDateTime creationDatetime) {
        this.creationDatetime = creationDatetime;
    }

    public LocalDateTime getUpdatedDatetime() {
        return updatedDatetime;
    }

    public void setUpdatedDatetime(LocalDateTime updatedDatetime) {
        this.updatedDatetime = updatedDatetime;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    @Override
    public String toString() {
        return "ResourceMetadata{" +
                "creationDatetime=" + creationDatetime +
                ", updatedDatetime=" + updatedDatetime +
                ", active=" + active +
                '}';
    }

}
