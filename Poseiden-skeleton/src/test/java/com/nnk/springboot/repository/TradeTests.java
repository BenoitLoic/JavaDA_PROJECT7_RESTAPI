package com.nnk.springboot.repository;

import com.nnk.springboot.domain.Trade;
import com.nnk.springboot.repositories.TradeRepository;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@ActiveProfiles("test")
public class TradeTests {

  @Autowired
  private TradeRepository tradeRepository;

  @Test
  public void tradeTest() {
    Trade trade = new Trade();
    trade.setAccount("Trade Account");
    trade.setType("Type");
    // Save
    trade = tradeRepository.save(trade);
    assertNotNull(trade.getTradeId());
    assertEquals(true, trade.getAccount().equals("Trade Account"));

    // Update
    trade.setAccount("Trade Account Update");
    trade = tradeRepository.save(trade);
    assertEquals(true, trade.getAccount().equals("Trade Account Update"));

    // Find
    List<Trade> listResult = tradeRepository.findAll();
    assertTrue(listResult.size() > 0);

    // Delete
    int id = trade.getTradeId();
    tradeRepository.delete(trade);
    Optional<Trade> tradeList = tradeRepository.findById(id);
    assertFalse(tradeList.isPresent());
  }
}
