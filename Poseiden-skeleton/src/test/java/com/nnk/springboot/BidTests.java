package com.nnk.springboot;

import com.nnk.springboot.domain.Bid;
import com.nnk.springboot.repositories.BidRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;


@SpringBootTest
public class BidTests {

	@Autowired
	private BidRepository bidListRepository;

	@Test
	public void bidListTest() {
		Bid bid = new Bid("Account Test", "Type Test", 10d);

		// Save
		bid = bidListRepository.save(bid);
		assertNotNull(bid.getBidListId());
		assertEquals(bid.getBidQuantity(), 10d, 10d);

		// Update
		bid.setBidQuantity(20d);
		bid = bidListRepository.save(bid);
		assertEquals(bid.getBidQuantity(), 20d, 20d);

		// Find
		List<Bid> listResult = bidListRepository.findAll();
		assertTrue(listResult.size() > 0);

		// Delete
		Integer id = bid.getBidListId();
		bidListRepository.delete(bid);
		Optional<Bid> bidList = bidListRepository.findById(id);
		assertFalse(bidList.isPresent());
	}
}
