package com.nnk.springboot.repository;

import com.nnk.springboot.domain.BidList;
import com.nnk.springboot.repositories.BidListRepository;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import static org.junit.jupiter.api.Assertions.*;


@SpringBootTest
@org.springframework.test.context.ActiveProfiles("test")
public class BidTests {

  @Autowired
  private BidListRepository bidListRepository;

  @Test
  public void bidListTest() {
    BidList bid = new BidList();
    bid.setAccount("Account Test");
    bid.setType("Type Test");
    bid.setBidQuantity(10d);

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
    System.out.println(bidListRepository.findAll().size());

  }
}
