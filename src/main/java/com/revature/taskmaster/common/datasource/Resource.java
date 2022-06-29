package com.revature.taskmaster.common.datasource;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;

@MappedSuperclass
public abstract class Resource {

    @Id
    @Column(name = "resource_id")
    @GenericGenerator(name = "uuid", strategy = "uuid4")
    protected String id;

    @Embedded
    protected ResourceMetadata metadata;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public ResourceMetadata getMetadata() {
        return metadata;
    }

    public void setMetadata(ResourceMetadata metadata) {
        this.metadata = metadata;
    }
}
