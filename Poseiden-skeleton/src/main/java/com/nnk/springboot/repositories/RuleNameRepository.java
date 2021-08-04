package com.nnk.springboot.repositories;

import com.nnk.springboot.domain.Rule;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


public interface RuleNameRepository extends JpaRepository<Rule, Integer> {
}
