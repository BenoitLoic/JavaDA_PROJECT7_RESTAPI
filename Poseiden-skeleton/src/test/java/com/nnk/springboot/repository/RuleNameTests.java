package com.nnk.springboot.repository;

import com.nnk.springboot.domain.RuleName;
import com.nnk.springboot.repositories.RuleNameRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;


@SpringBootTest
@ActiveProfiles("test")
public class RuleNameTests {

	@Autowired
	private RuleNameRepository ruleNameRepository;

	@Test
	public void ruleTest() {
		RuleName ruleName = new RuleName("RuleName Name", "Description", "Json", "Template", "SQL", "SQL Part");

		// Save
		ruleName = ruleNameRepository.save(ruleName);
		assertNotNull(ruleName.getId());
		assertTrue(ruleName.getName().equals("RuleName Name"));

		// Update
		ruleName.setName("RuleName Name Update");
		ruleName = ruleNameRepository.save(ruleName);
		assertTrue(ruleName.getName().equals("RuleName Name Update"));

		// Find
		List<RuleName> listResult = ruleNameRepository.findAll();
		assertTrue(listResult.size() > 0);

		// Delete
		Integer id = ruleName.getId();
		ruleNameRepository.delete(ruleName);
		Optional<RuleName> ruleList = ruleNameRepository.findById(id);
		assertFalse(ruleList.isPresent());
	}
}
