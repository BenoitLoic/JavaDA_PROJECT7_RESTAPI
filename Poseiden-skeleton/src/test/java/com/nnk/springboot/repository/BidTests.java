package com.nnk.springboot.repository;

import com.nnk.springboot.domain.BidList;
import com.nnk.springboot.repositories.BidListRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;


@SpringBootTest
@ActiveProfiles("test")
public class BidTests {

  @Autowired
  private BidListRepository bidListRepository;

  @Transactional
  @Test
  public void bidListTest() {
    BidList bid = new BidList();
    bid.setAccount("Account Test");
    bid.setType("Type Test");
    bid.setBidQuantity(10d);
    for (BidList bidy : bidListRepository.findAll()) {
      System.out.println(bidy.getBidListId());
    }
    // Save
    bid = bidListRepository.save(bid);
    assertTrue(bid.getBidListId() > 0);
    assertEquals(bid.getBidQuantity(), 10d, 10d);

    // Update
    bid.setBidQuantity(20d);
    bid = bidListRepository.save(bid);
    assertEquals(bid.getBidQuantity(), 20d, 20d);

    // Find
    List<BidList> listResult = bidListRepository.findAll();
    assertTrue(listResult.size() > 0);

    // Delete
    int id = bid.getBidListId();
    bidListRepository.delete(bid);
    Optional<BidList> bidList = bidListRepository.findById(id);
    assertFalse(bidList.isPresent());

  }
}
