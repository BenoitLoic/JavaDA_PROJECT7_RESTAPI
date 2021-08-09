package com.nnk.springboot.services;

import com.nnk.springboot.dto.CreateCurvePointDto;
import com.nnk.springboot.dto.GetCurvePointDto;
import com.nnk.springboot.dto.UpdateCurvePointDto;

import java.util.Collection;

/**
 * Interface for CurveService.
 * contains method used by Curve CRUD controller.
 */
public interface CurveService {

  /**
   * Get all curve point saved in DB.
   *
   * @return collection of curve point dto
   */
  Collection<GetCurvePointDto> getAllCurvePoint();

  /**
   * Get the curve point with the given id in db.
   *
   * @param id the id of the point to read
   * @return the dto
   */
  UpdateCurvePointDto getPointWithId(int id);

  /**
   * Create a new Curve point and save it in DB.
   *
   * @param createCurvePointDto the curve point to create.
   */
  void createCurvePoint(CreateCurvePointDto createCurvePointDto);

  /**
   * Update an existing Curve point in DB.
   *
   * @param id                  the curve point id
   * @param updateCurvePointDto the data to update
   */
  void updateCurvePoint(int id, UpdateCurvePointDto updateCurvePointDto);

  /**
   * Delete the curve point identified by its id.
   *
   * @param id the curve point id
   */
  void deleteCurvePoint(int id);
}
