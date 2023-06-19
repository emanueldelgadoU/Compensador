package com.app.api.repositories;

import com.app.api.models.FacturaModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface FacturaRepository extends JpaRepository<FacturaModel, Long> {

    List<FacturaModel> findByUserId(Long userId);



}
