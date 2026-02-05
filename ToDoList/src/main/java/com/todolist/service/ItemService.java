package com.todolist.service;

import com.todolist.entity.Item;
import org.springframework.data.domain.Page;

import java.util.List;

public interface ItemService {

    List<Item> getAllItems();

    Item getItemById(Long id);

    Item createItem(Item item);

    Item updateItem(Long id, Item item);

    void deleteItem(Long id);

    Item updateCompleted(Long id, boolean completed);

    Page<Item> getItems(int page, int size);

    Page<Item> searchItems(String filter, String keyword, int page, int size);
}
