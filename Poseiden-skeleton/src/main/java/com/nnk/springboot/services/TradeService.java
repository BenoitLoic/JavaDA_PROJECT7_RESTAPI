package com.nnk.springboot.services;

import com.nnk.springboot.dto.CreateTradeDto;
import com.nnk.springboot.dto.GetTradeDto;
import com.nnk.springboot.dto.UpdateTradeDto;
import java.util.Collection;

/**
 * Interface for TradeService.
 * Contains method used by CRUD controller.
 */
public interface TradeService {

  /**
   * Get all trade saved in DB and return their Dto.
   *
   * @return the collection of trade dto
   */
  Collection<GetTradeDto> findAllTrades();

  /**
   * Create a new trade in DB.
   *
   * @param createTradeDto the trade to save in DB.
   */
  void createTrade(CreateTradeDto createTradeDto);

  /**
   * Get Trade identified by the given id.
   *
   * @param id the id of the trade to read
   * @return the dto of the rule
   */
  UpdateTradeDto getTradeWithId(int id);

  /**
   * Update an existing Trade in DB.
   *
   * @param id             the id of the trade to update
   * @param updateTradeDto the trade to update in DB.
   */
  void updateTrade(int id, UpdateTradeDto updateTradeDto);

  /**
   * Delete an existing trade in DB with its id.
   *
   * @param id the id of the trade to delete
   */
  void deleteTrade(int id);
}
