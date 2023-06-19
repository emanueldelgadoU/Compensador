package com.app.api.repositories;

import com.app.api.models.CompensacionModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CompensacionRepository extends JpaRepository<CompensacionModel, Long> {



}
