package com.siemens.demo.repository;

import com.siemens.demo.model.Item;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TutorialRepository extends JpaRepository<Item, Long> {
    List<Item> findByName(String name);

}
