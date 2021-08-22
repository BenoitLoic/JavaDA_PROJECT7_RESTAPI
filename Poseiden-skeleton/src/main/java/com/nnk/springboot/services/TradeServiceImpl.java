package com.nnk.springboot.services;

import com.nnk.springboot.domain.Trade;
import com.nnk.springboot.dto.CreateTradeDto;
import com.nnk.springboot.dto.GetTradeDto;
import com.nnk.springboot.dto.UpdateTradeDto;
import com.nnk.springboot.exceptions.DataNotFoundException;
import com.nnk.springboot.repositories.TradeRepository;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Optional;

@Service
public class TradeServiceImpl implements TradeService {


  private final TradeRepository tradeRepository;

  private final Logger log = LogManager.getLogger(getClass().getName());

  @Autowired
  public TradeServiceImpl(TradeRepository tradeRepository) {
    this.tradeRepository = tradeRepository;
  }

  /**
   * Get all trade saved in DB and return their Dto.
   *
   * @return the collection of trade dto
   */
  @Override
  public Collection<GetTradeDto> findAllTrades() {
    log.trace("Getting all trades.");

    //int tradeId, String account, String type, Double buyQuantity
    Collection<GetTradeDto> trades = new ArrayList<>();
    for (Trade trade : tradeRepository.findAll()) {
      GetTradeDto temp = new GetTradeDto();
      BeanUtils.copyProperties(trade,temp);
      trades.add(temp);
    }

    log.info("Returning " + trades.size() + " trade.");

    return trades;

  }

  /**
   * Create a new trade in DB.
   *
   * @param createTradeDto the trade to save in DB.
   */
  @Transactional
  @Override
  public void createTrade(CreateTradeDto createTradeDto) {

    Trade entity = new Trade();
    BeanUtils.copyProperties(createTradeDto, entity);

    log.info("Saving trade: " + entity);

    tradeRepository.save(entity);

    log.info("Success - Trade saved with id: " + entity.getTradeId());

  }

  /**
   * Get Trade identified by the given id.
   *
   * @param id the id of the trade to read
   * @return the dto of the rule
   */
  @Override
  public UpdateTradeDto getTradeWithId(int id) {

    log.trace("Getting trade, id: " + id);

    Optional<Trade> optional = tradeRepository.findById(id);

    if (optional.isEmpty()) {
      log.warn("Error can't find trade with id: " + id);
      throw new DataNotFoundException("Error can't find trade.");
    }

    UpdateTradeDto updateDto = new UpdateTradeDto();
    BeanUtils.copyProperties(optional.get(), updateDto);

    return updateDto;
  }

  /**
   * Update an existing Trade in DB.
   *
   * @param id             the id of the trade to update
   * @param updateTradeDto the trade to update in DB.
   */
  @Transactional
  @Override
  public void updateTrade(int id, UpdateTradeDto updateTradeDto) {

    log.trace("Updating trade with id: "
        + id
        + " and data: "
        + updateTradeDto);

    Optional<Trade> optional = tradeRepository.findById(id);

    if (optional.isEmpty()) {
      log.warn("KO - Error can't find trade with id: "
          + id);
      throw new DataNotFoundException("Error can't find trade.");
    }


    Trade entity = optional.get();
    int count = 0;
    if ((updateTradeDto.getAccount() != null
        && !updateTradeDto.getAccount().isBlank())) {
      log.trace("Updating account.");
      entity.setAccount(updateTradeDto.getAccount());
      count++;
    }
    if (updateTradeDto.getType() != null
        && !updateTradeDto.getType().isBlank()) {
      log.trace("Updating type.");
      entity.setType(updateTradeDto.getType());
      count++;
    }
    if (!updateTradeDto.getBuyQuantity().equals(entity.getBuyQuantity())) {
      log.trace("Updating buyQuantity.");
      entity.setBuyQuantity(updateTradeDto.getBuyQuantity());
    }
    if (!updateTradeDto.getSellQuantity().equals(entity.getSellQuantity())) {
      log.trace("Updating sellQuantity.");
      entity.setSellQuantity(updateTradeDto.getSellQuantity());
      count++;
    }
    if (!updateTradeDto.getBuyPrice().equals(entity.getBuyPrice())) {
      log.trace("Updating buyPrice.");
      entity.setBuyPrice(updateTradeDto.getBuyPrice());
      count++;
    }
    if (!updateTradeDto.getSellPrice().equals(entity.getSellPrice())) {
      log.trace("Updating sellPrice.");
      entity.setSellPrice(updateTradeDto.getSellPrice());
      count++;
    }
    if (updateTradeDto.getTradeDate() != null
        && !updateTradeDto.getTradeDate().equals(entity.getTradeDate())) {
      log.trace("Updating tradeDate.");
      entity.setTradeDate(updateTradeDto.getTradeDate());
      count++;
    }
    if (updateTradeDto.getSecurity() != null
        && !updateTradeDto.getSecurity().isBlank()) {
      log.trace("Updating security.");
      entity.setSecurity(updateTradeDto.getSecurity());
      count++;
    }
    if (updateTradeDto.getStatus() != null
        && !updateTradeDto.getStatus().isBlank()) {
      log.trace("Updating status.");
      entity.setStatus(updateTradeDto.getStatus());
      count++;
    }
    if (updateTradeDto.getTrader() != null
        && !updateTradeDto.getTrader().isBlank()) {
      log.trace("Updating trader.");
      entity.setTrader(updateTradeDto.getTrader());
      count++;
    }
    if (updateTradeDto.getBenchmark() != null
        && !updateTradeDto.getBenchmark().isBlank()) {
      log.trace("Updating benchmark.");
      entity.setBenchmark(updateTradeDto.getBenchmark());
      count++;
    }
    if (updateTradeDto.getBook() != null
        && !updateTradeDto.getBook().isBlank()) {
      log.trace("Updating book.");
      entity.setBook(updateTradeDto.getBook());
      count++;
    }
    if (updateTradeDto.getCreationName() != null
        && !updateTradeDto.getCreationName().isBlank()) {
      log.trace("Updating crationName.");
      entity.setCreationName(updateTradeDto.getCreationName());
      count++;
    }
    if (updateTradeDto.getRevisionName() != null
        && !updateTradeDto.getRevisionName().isBlank()) {
      log.trace("Updating revisionName.");
      entity.setRevisionName(updateTradeDto.getRevisionName());
      count++;
    }
    if (updateTradeDto.getDealName() != null
        && !updateTradeDto.getDealName().isBlank()) {
      log.trace("Updating dealName.");
      entity.setDealName(updateTradeDto.getDealName());
      count++;
    }
    if (updateTradeDto.getDealType() != null
        && !updateTradeDto.getDealType().isBlank()) {
      log.trace("Updating dealType.");
      entity.setDealType(updateTradeDto.getDealType());
      count++;
    }
    if (updateTradeDto.getSourceListId() != null
        && !updateTradeDto.getSourceListId().isBlank()) {
      log.trace("Updating sourceListId.");
      entity.setSourceListId(updateTradeDto.getSourceListId());
      count++;
    }
    if (updateTradeDto.getSide() != null
        && !(updateTradeDto.getSide().isEmpty() && updateTradeDto.getSide().isBlank())) {
      log.trace("Updating side.");
      entity.setSide(updateTradeDto.getSide());
      count++;
    }

    log.info("Updating Trade: " + entity);

    tradeRepository.save(entity);

    log.info("Update - OK for trade id: "
        + entity.getTradeId()
        + ". "
        + count
        + " fields changed.");
  }

  /**
   * Delete an existing trade in DB with its id.
   *
   * @param id the id of the trade to delete
   */
  @Transactional
  @Override
  public void deleteTrade(int id) {

    log.info("Deleting Trade with id: " + id);

    if (tradeRepository.findById(id).isEmpty()) {
      log.warn("KO - Can't find Trade with id: " + id);
      throw new DataNotFoundException("Error - Can't find Trade.");
    }

    tradeRepository.deleteById(id);

    log.info("Delete - OK");

  }
}
