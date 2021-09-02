package com.nnk.springboot.repository;

import com.nnk.springboot.domain.CurvePoint;
import com.nnk.springboot.repositories.CurvePointRepository;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import static org.junit.jupiter.api.Assertions.*;


@SpringBootTest
@ActiveProfiles("test")
public class CurvePointTests {

  @Autowired
  private CurvePointRepository curvePointRepository;

  @Test
  public void curvePointTest() {
    CurvePoint curvePoint = new CurvePoint();

    curvePoint.setCurveId(10);
    curvePoint.setTerm(10d);
    curvePoint.setValue(30d);
    // Save
    curvePoint = curvePointRepository.save(curvePoint);
    assertNotNull(curvePoint.getId());
    assertEquals(curvePoint.getCurveId(), 10);

    // Update
    curvePoint.setCurveId(20);
    curvePoint = curvePointRepository.save(curvePoint);
    assertEquals(curvePoint.getCurveId(), 20);

    // Find
    List<CurvePoint> listResult = curvePointRepository.findAll();
    assertTrue(listResult.size() > 0);

    // Delete
    int id = curvePoint.getId();
    curvePointRepository.delete(curvePoint);
    Optional<CurvePoint> curvePointList = curvePointRepository.findById(id);
    assertFalse(curvePointList.isPresent());
  }

}
