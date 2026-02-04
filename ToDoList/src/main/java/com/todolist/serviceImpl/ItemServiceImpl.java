package com.todolist.serviceImpl;

import com.todolist.entity.Item;
import com.todolist.repository.ItemRepository;
import com.todolist.service.ItemService;
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
            return repositoryObj.save(existingItem);
        }

        @Override
        public void deleteItem(Long id) {
            if (repositoryObj.existsById(id)) {
                repositoryObj.deleteById(id);
            } else {
                throw new RuntimeException("Item not found with id: " + id);
            }
        }
    }


