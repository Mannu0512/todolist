package com.todolist.serviceImpl;

import com.todolist.entity.Item;
import com.todolist.repository.ItemRepository;
import com.todolist.service.ItemService;
import com.todolist.status.ItemStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class ItemServiceImpl implements ItemService {

        private final ItemRepository repositoryObj;

        public ItemServiceImpl(ItemRepository repositoryObj) {
            this.repositoryObj = repositoryObj;
        }

        @Override
        public List<Item> getAllItems() {
            return repositoryObj.findAll();
        }

        @Override
        public Item getItemById(Long id) {
            return repositoryObj.findById(id).orElseThrow(()-> new RuntimeException("Item not found with id: "+id));
        }

        @Override
        public Item createItem(Item item) {

            Item lastItem = repositoryObj.findTopByOrderByItemIdDesc();

            int nextNumber = 1;
            if (lastItem != null && lastItem.getItemNumber() != null) {
                String last = lastItem.getItemNumber();   // item_7
                if (last.contains("_")) {
                    try {
                        nextNumber = Integer.parseInt(last.split("_")[1]) + 1;
                    } catch (Exception e) {
                        nextNumber = 1;   // safety
                    }
                }
            }

            item.setItemNumber("item_" + nextNumber);
            item.setCreatedAt(LocalDateTime.now());
            item.setUpdatedOn(LocalDateTime.now());

            item.setStatus(ItemStatus.NEW_ITEM);

            return repositoryObj.save(item);
        }

        @Override
        public Item updateItem(Long id, Item item) {
        Item existingItem = repositoryObj.findById(id).orElseThrow(()-> new RuntimeException("Item not found with id:"+id));

            if (item.getTitle() != null) {
                existingItem.setTitle(item.getTitle());
            }
            if (item.getDescription() != null) {
                existingItem.setDescription(item.getDescription());
            }

            existingItem.setCompleted(item.isCompleted());
            existingItem.setUpdatedOn(LocalDateTime.now());
            existingItem.setStatus(ItemStatus.MODIFIED);
            return repositoryObj.save(existingItem);
        }

    @Override
    public void deleteItem(Long id) {

        Item item = repositoryObj.findById(id)
                .orElseThrow(() ->
                        new RuntimeException("Item not found with id: " + id)
                );

        // 🔥 Soft delete
        item.setStatus(ItemStatus.DELETED);
        item.setUpdatedOn(LocalDateTime.now());

        repositoryObj.save(item);
    }

    @Override
    public Item updateCompleted(Long id, boolean completed) {
        Item item = repositoryObj.findById(id)
                .orElseThrow(() -> new RuntimeException("Item not found"));
        item.setCompleted(completed);
        item.setUpdatedOn(LocalDateTime.now());
        if (completed) {
            item.setStatus(ItemStatus.MARKED_DONE);
        } else {
            item.setStatus(ItemStatus.PENDING);
        }
        return repositoryObj.save(item);
    }

    @Override
    public Page<Item> getItems(int page, int size) {
        return repositoryObj.findAll(
                PageRequest.of(page, size)
        );
    }

    @Override
    public Page<Item> searchItems(String filter, String keyword, int page, int size) {

        Pageable pageable = PageRequest.of(page, size);

        if (keyword == null || keyword.isBlank()) {
            return (Page<Item>) repositoryObj.findAll(pageable);
        }

        switch (filter) {
            case "itemNumber":
                return repositoryObj.findByItemNumberContainingIgnoreCase(keyword, pageable);

            case "title":
                return repositoryObj.findByTitleContainingIgnoreCase(keyword, pageable);

            case "status":
                return repositoryObj.findByStatus(ItemStatus.valueOf(keyword.toUpperCase()), pageable);

            default:
                return repositoryObj.findAll(pageable);
        }
    }



}


