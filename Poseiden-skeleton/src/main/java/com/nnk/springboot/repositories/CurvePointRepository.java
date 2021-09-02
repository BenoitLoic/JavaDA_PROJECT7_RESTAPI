package com.nnk.springboot.repositories;

import com.nnk.springboot.domain.CurvePoint;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Jpa repository for CurvePoint table.
 */
@Repository
public interface CurvePointRepository extends JpaRepository<CurvePoint, Integer> {

}
