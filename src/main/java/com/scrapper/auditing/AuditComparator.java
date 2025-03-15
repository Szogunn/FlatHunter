package com.scrapper.auditing;

import com.scrapper.entities.Audit;

import java.util.List;

public interface AuditComparator <T> {

    List<Audit> audit(T oldAuditableEntity, T newAuditableEntity);
}
