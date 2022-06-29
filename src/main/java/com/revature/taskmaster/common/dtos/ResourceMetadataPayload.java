package com.revature.taskmaster.common.dtos;

import com.revature.taskmaster.common.datasource.ResourceMetadata;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
public class ResourceMetadataPayload {

    private LocalDateTime creationDatetime;
    private LocalDateTime updatedDatetime;
    private boolean isActive;

    public ResourceMetadataPayload(ResourceMetadata metadata) {
        this.creationDatetime = metadata.getCreationDatetime();
        this.updatedDatetime = metadata.getUpdatedDatetime();
        this.isActive = metadata.isActive();
    }

}
