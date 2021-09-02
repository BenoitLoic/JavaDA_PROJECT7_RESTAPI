package com.nnk.springboot.repository;

import com.nnk.springboot.domain.Rating;
import com.nnk.springboot.repositories.RatingRepository;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@ActiveProfiles("test")
public class RatingTests {

  @Autowired
  private RatingRepository ratingRepository;


  @Test
  public void ratingTest() {
    Rating rating = new Rating();
    rating.setMoodysRating("Moodys Rating");
    rating.setSandPRating("Sand PRating");
    rating.setFitchRating("Fitch Rating");
    rating.setOrderNumber(10);
    // Save
    rating = ratingRepository.save(rating);
    assertNotNull(rating.getId());
    assertEquals(rating.getOrderNumber(), 10);

    // Update
    rating.setOrderNumber(20);
    rating = ratingRepository.save(rating);
    assertEquals(rating.getOrderNumber(), 20);

    // Find
    List<Rating> listResult = ratingRepository.findAll();
    assertTrue(listResult.size() > 0);

    // Delete
    Integer id = rating.getId();
    ratingRepository.delete(rating);
    Optional<Rating> ratingList = ratingRepository.findById(id);
    assertFalse(ratingList.isPresent());

  }
}
