package com.todolist.service;

import com.todolist.entity.Item;

import java.util.List;

public interface ItemService {

    List<Item> getAllItems();

    Item getItemById(Long id);

    Item createItem(Item item);

    Item updateItem(Long id, Item item);

    void deleteItem(Long id);
}
