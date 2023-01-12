package com.divary.audit.listener;

import com.divary.audit.AuditEntity;

import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import java.util.Date;

public class AuditEntityListener {
    public AuditEntityListener() {
    }

    @PrePersist
    public void prePersist(AuditEntity a) {
        a.setCreatedDate(new Date());
    }

    @PreUpdate
    public void preUpdate(AuditEntity a) {
        a.setLastModifiedDate(new Date());
    }
}
