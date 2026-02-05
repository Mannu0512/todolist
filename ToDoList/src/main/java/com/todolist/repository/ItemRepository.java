package com.todolist.repository;

import com.todolist.entity.Item;
import com.todolist.status.ItemStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ItemRepository extends JpaRepository<Item, Long> {
    Item findTopByOrderByItemIdDesc();

    Page<Item> findByItemNumberContainingIgnoreCase(String keyword, Pageable pageable);

    Page<Item> findByTitleContainingIgnoreCase(String keyword, Pageable pageable);

    Page<Item> findByStatus(ItemStatus itemStatus, Pageable pageable);
}
