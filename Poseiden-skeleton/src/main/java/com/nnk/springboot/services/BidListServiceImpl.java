package com.nnk.springboot.services;

import com.nnk.springboot.domain.BidList;
import com.nnk.springboot.dto.CreateBidListDto;
import com.nnk.springboot.dto.GetBidListDto;
import com.nnk.springboot.dto.UpdateBidListDto;
import com.nnk.springboot.exceptions.DataNotFoundException;
import com.nnk.springboot.repositories.BidListRepository;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Optional;

/**
 * Implementation for BidListService.
 * Contains method used by BidList CRUD controller to interact with DB.
 */
@Service
public class BidListServiceImpl implements BidListService {

  @Autowired
  BidListRepository bidListRepository;

  private final Logger log = LogManager.getLogger(getClass().getName());

  /**
   * Get all bids saved in DB and return their Dto.
   *
   * @return the collection of bid dto
   */
  @Override
  public Collection<GetBidListDto> findAllBids() {

    log.trace("Getting all BidList.");

    Collection<GetBidListDto> bids = new ArrayList<>();

    for (BidList bidlist : bidListRepository.findAll()) {
      GetBidListDto temp = new GetBidListDto(
          bidlist.getBidListId(),
          bidlist.getAccount(),
          bidlist.getType(),
          bidlist.getBidQuantity());
      bids.add(temp);
    }

    log.info("Returning " + bids.size() + " bids.");
    return bids;
  }

  /**
   * Create a new bid in DB.
   *
   * @param createBidListDto the bid to save in DB.
   */
  @Transactional
  @Override
  public void createBid(CreateBidListDto createBidListDto) {

    BidList bidEntity =new BidList();
            bidEntity.setAccount(createBidListDto.getAccount());
            bidEntity.setType(createBidListDto.getType());
            bidEntity.setBidQuantity(createBidListDto.getBidQuantity());

    log.info("Saving new Bid : " + createBidListDto);

    bidListRepository.save(bidEntity);

    log.info("Success - bid saved with id: " + bidEntity.getBidListId());
  }

  /**
   * Get Bid identified by the given id.
   *
   * @param id the id of the bid to read
   * @return the dto of the bid
   */
  @Override
  public UpdateBidListDto getBidWithId(int id) {

    Optional<BidList> tempBid = bidListRepository.findById(id);

    if (tempBid.isEmpty()) {
      log.warn("KO - Error bid not find, id: " + id);
      throw new DataNotFoundException("Error Bid with id: " + id + " doesn't exist.");
    }

    UpdateBidListDto bidDto = new UpdateBidListDto();
    BeanUtils.copyProperties(tempBid.get(), bidDto);

    log.info("Success - found bid : " + bidDto);

    return bidDto;
  }

  /**
   * Update an existing Bid in DB.
   *
   * @param updateBidListDto the bid to update in DB.
   */
  @Transactional
  @Override
  public void updateBid(int id, UpdateBidListDto updateBidListDto) {

    log.trace("Updating bid : " + updateBidListDto);
    Optional<BidList> temp = bidListRepository.findById(id);

    if (temp.isEmpty()) {
      log.warn("Error can't find bid with id: " + id);
      throw new DataNotFoundException("Error can't find bid");
    }

    BidList bidEntity = temp.get();
    int count = 0;
    if (updateBidListDto.getAccount() != null
        && (!updateBidListDto.getAccount().isBlank())) {
      log.trace("Updating account.");
      bidEntity.setAccount(updateBidListDto.getAccount());
      count++;
    }
    if (updateBidListDto.getType() != null
        && (!updateBidListDto.getType().isBlank())) {
      log.trace("Updating type.");
      bidEntity.setType(updateBidListDto.getType());
      count++;
    }
    if (!(updateBidListDto.getBidQuantity() == 0d)) {
      log.trace("Updating bidQuantity.");
      bidEntity.setBidQuantity(updateBidListDto.getBidQuantity());
      count++;
    }

    bidListRepository.save(bidEntity);

    log.info("Success - update OK for bid id: "
        + bidEntity.getBidListId()
        + ". "
        + count
        + " field changed.");
  }

  /**
   * Delete an existing bid in DB with its id.
   *
   * @param id the id of the bid to delete
   */
  @Transactional
  @Override
  public void deleteBid(int id) {

    Optional<BidList> bidToDelete = bidListRepository.findById(id);

    if (bidToDelete.isEmpty()) {
      log.warn("KO - Error can't find bid with id : " + id);
      throw new DataNotFoundException("Error can't find bid.");
    }

    log.trace("Deleting bid : " + bidToDelete.get());

    bidListRepository.deleteById(id);

    log.info("Success - bid with id : "
        + id
        + " deleted.");

  }
}
