package com.test.CoffeeBackend.repository;

import com.test.CoffeeBackend.entity.Queue;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface QueueRepository extends JpaRepository<Queue, Integer>{
    List<Queue> findAllByShopId(Integer id);
}
