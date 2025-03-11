package com.example.springbootsecurityjwtauthenticationandauthorization.repository;

import com.example.springbootsecurityjwtauthenticationandauthorization.model.AuditLog;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface AuditLogRepository extends JpaRepository<AuditLog, Long> {

    List<AuditLog> findByPerformedBy(String performedBy); // ✅ Find logs for a user
}
