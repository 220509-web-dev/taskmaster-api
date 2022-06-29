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

    @Column(name = "creator_by")
    private String creatorBy;

    @Column(name = "last_modified_by")
    private String lastModifiedBy;

    @Column(name = "is_active")
    private boolean active;

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

    public String getCreatorBy() {
        return creatorBy;
    }

    public void setCreatorBy(String creatorBy) {
        this.creatorBy = creatorBy;
    }

    public String getLastModifiedBy() {
        return lastModifiedBy;
    }

    public void setLastModifiedBy(String lastModifiedBy) {
        this.lastModifiedBy = lastModifiedBy;
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
                ", creatorBy='" + creatorBy + '\'' +
                ", lastModifiedBy='" + lastModifiedBy + '\'' +
                ", active=" + active +
                '}';
    }

}
