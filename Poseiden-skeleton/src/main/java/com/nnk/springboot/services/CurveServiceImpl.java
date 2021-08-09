package com.nnk.springboot.services;

import com.nnk.springboot.domain.CurvePoint;
import com.nnk.springboot.dto.CreateCurvePointDto;
import com.nnk.springboot.dto.GetCurvePointDto;
import com.nnk.springboot.dto.UpdateCurvePointDto;
import com.nnk.springboot.repositories.CurvePointRepository;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Collection;
import java.util.NoSuchElementException;
import java.util.Optional;

/**
 * Implementation for CurveService.
 * contains method used by Curve CRUD controller.
 */
@Service
public class CurveServiceImpl implements CurveService {

  private final Logger log = LogManager.getLogger(getClass().getName());

  @Autowired
  CurvePointRepository curvePointRepository;

  /**
   * Get all curve point saved in DB.
   *
   * @return collection of curve point dto
   */
  @Override
  public Collection<GetCurvePointDto> getAllCurvePoint() {

    log.trace("Getting all curve points.");

    Collection<GetCurvePointDto> curvePoints = new ArrayList<>();
    for (CurvePoint curvePoint : curvePointRepository.findAll()) {
      GetCurvePointDto temp = new GetCurvePointDto(
          curvePoint.getId(),
          curvePoint.getCurveId(),
          curvePoint.getTerm(),
          curvePoint.getValue());
      curvePoints.add(temp);
    }

    log.info("Returning " + curvePoints.size() + " curve points.");

    return curvePoints;
  }

  /**
   * Get the curve point with the given id in db.
   *
   * @param id the id of the point to read
   * @return the dto
   */
  @Override
  public UpdateCurvePointDto getPointWithId(int id) {

    log.trace("Getting curvePoint, id: " + id);

    Optional<CurvePoint> optional = curvePointRepository.findById(id);

    if (optional.isEmpty()) {
      log.warn("Error can't find curve point with id: " + id);
      throw new NoSuchElementException("Error can't find curve point");
    }

    UpdateCurvePointDto upPoint = new UpdateCurvePointDto();
    BeanUtils.copyProperties(optional.get(),upPoint);

    return upPoint;
  }

  /**
   * Create a new Curve point and save it in DB.
   *
   * @param createCurvePointDto the curve point to create.
   */
  @Transactional
  @Override
  public void createCurvePoint(CreateCurvePointDto createCurvePointDto) {

    CurvePoint curvePointEntity = new CurvePoint();
    BeanUtils.copyProperties(createCurvePointDto, curvePointEntity);

    log.info("Saving curve point : " + curvePointEntity);

    curvePointRepository.save(curvePointEntity);

    log.info("Success - Curve point saved with id: " + curvePointEntity.getId());

  }

  /**
   * Update an existing Curve point in DB.
   *
   * @param id                  the curve point id
   * @param updateCurvePointDto the data to update
   */
  @Transactional
  @Override
  public void updateCurvePoint(int id, UpdateCurvePointDto updateCurvePointDto) {

    log.trace("Updating Curve point with id: "
        + id
        + " and data: "
        + updateCurvePointDto);

    Optional<CurvePoint> optional = curvePointRepository.findById(id);

    if (optional.isEmpty()) {
      log.warn("KO - Error can't find curvePoint with id: "
          + id);
      throw new NoSuchElementException("Error can't find curvePoint");
    }
    CurvePoint curvePointEntity = optional.get();

    if (updateCurvePointDto.getCurveId() != curvePointEntity.getCurveId()) {
      log.trace("Updating curveID.");
      curvePointEntity.setCurveId(updateCurvePointDto.getCurveId());
    }
    if (updateCurvePointDto.getTerm() != curvePointEntity.getTerm()) {
      log.trace("Updating Term.");
      curvePointEntity.setTerm(updateCurvePointDto.getTerm());
    }
    if (updateCurvePointDto.getValue() != curvePointEntity.getValue()) {
      log.trace("Updating Value.");
      curvePointEntity.setValue(updateCurvePointDto.getValue());
    }

    log.info("Updating CurvePoint: " + curvePointEntity);

    curvePointRepository.save(curvePointEntity);

    log.info("Update - OK");
  }

  /**
   * Delete the curve point identified by its id.
   *
   * @param id the curve point id
   */
  @Transactional
  @Override
  public void deleteCurvePoint(int id) {

    log.info("Deleting Curve point with id: " + id);

    if (curvePointRepository.findById(id).isEmpty()) {
      log.warn("KO - Can't find CurvePoint with id: " + id);
      throw new NoSuchElementException("Error - Can't find Curve Point.");
    }

    curvePointRepository.deleteById(id);

    log.info("Delete - OK");

  }
}
