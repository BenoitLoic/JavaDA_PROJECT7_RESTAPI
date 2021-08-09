package com.nnk.springboot.services;

import com.nnk.springboot.dto.CreateBidListDto;
import com.nnk.springboot.dto.GetBidListDto;
import com.nnk.springboot.dto.UpdateBidListDto;

import java.util.Collection;

/**
 * Interface for BidListService.
 * Contains method used by CRUD controller.
 */
public interface BidListService {

  /**
   * Get all bids saved in DB and return their Dto.
   * @return the collection of bid dto
   */
  Collection<GetBidListDto> findAllBids();

  /**
   * Create a new bid in DB.
   * @param createBidListDto the bid to save in DB.
   */
  void createBid(CreateBidListDto createBidListDto);

  /**
   * Get Bid identified by the given id.
   * @param id the id of the bid to read
   * @return the dto of the bid
   */
  UpdateBidListDto getBidWithId(int id);

  /**
   * Update an existing Bid in DB.
   * @param id the id of the bid to update
   * @param updateBidListDto the bid to update in DB.
   */
  void updateBid(int id,UpdateBidListDto updateBidListDto);

  /**
   * Delete an existing bid in DB with its id.
   * @param id the id of the bid to delete
   */
  void deleteBid(int id);
}
