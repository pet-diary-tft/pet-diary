package com.petdiary.domain.rdscore.listener;

import jakarta.persistence.PrePersist;
import jakarta.persistence.PreRemove;
import jakarta.persistence.PreUpdate;

public class PreventAnyUpdate {
    @PrePersist
    void onPrePersist(Object o) {
        throw new IllegalStateException("[PreventAnyUpdate]: JPA is trying to persist an entity of type " + (o == null ? "null" : o.getClass()));
    }

    @PreUpdate
    void onPreUpdate(Object o) {
        throw new IllegalStateException("[PreventAnyUpdate]: JPA is trying to update an entity of type " + (o == null ? "null" : o.getClass()));
    }

    @PreRemove
    void onPreRemove(Object o) {
        throw new IllegalStateException("[PreventAnyUpdate]: JPA is trying to remove an entity of type " + (o == null ? "null" : o.getClass()));
    }
}
