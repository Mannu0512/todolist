package com.todolist.controller;

import com.todolist.entity.Item;
import com.todolist.service.ItemService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.lang.String;
import java.util.*;

import java.util.List;

@Tag(name = "Todo List APIs", description = "CRUD operations for Todo List")
@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/todolist")
public class ItemController {

    private final ItemService serviceobj;

    public ItemController(ItemService serviceobj) {
        this.serviceobj = serviceobj;
    }

    @Operation(summary = "Create new Todo Item")
    @PostMapping("/createItem")
    public ResponseEntity<Item> createItem(@RequestBody Item item){
        Item createItem = serviceobj.createItem(item);
        return new ResponseEntity<>(createItem, HttpStatus.CREATED);
    }

    @Operation(summary = "Get all Todo Items")
    @GetMapping("/getAllitems")
    public ResponseEntity<List<Item>> getAllItem(){
        List<Item> getItems = serviceobj.getAllItems();
        return new ResponseEntity<>(getItems, HttpStatus.OK);
    }

    @Operation(summary = "Get Single Todo Items")
    @GetMapping("/getItemById/{id}")
    public ResponseEntity<Item> getItemByItem(@PathVariable Long id){
        Item itemById = serviceobj.getItemById(id);
        return new ResponseEntity<>(itemById, HttpStatus.OK);
    }

    @Operation(summary = "Update Todo Item by ID")
    @PutMapping("updateItembyId/{id}")
    public ResponseEntity<Item> updateItem(@PathVariable Long id , @RequestBody Item item){
        Item updateItem  = serviceobj.updateItem(id, item);
        return new ResponseEntity<>(updateItem, HttpStatus.OK);
    }


    @Operation(summary = "Delete Todo Item by ID")
    @DeleteMapping("/deleteItembyId/{id}")
    public ResponseEntity<String> deteleItemById(@PathVariable Long id){
     serviceobj.deleteItem(id);
     return ResponseEntity.ok("Item deleted successfully with id:"+id);
    }

    @PutMapping("/updateCompleted/{id}")
    public ResponseEntity<Item> updateCompleted(
            @PathVariable Long id,
            @RequestBody Map<String, Boolean> body) {

        boolean completed = body.get("completed");

        return ResponseEntity.ok(
                serviceobj.updateCompleted(id, completed)
        );
    }

    //for pagination
    @GetMapping("/items")
    public Page<Item> getItems(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ) {
        return serviceobj.getItems(page, size);
    }

//for search
@GetMapping("/items/search")
public Page<Item> searchItems(
        @RequestParam(defaultValue = "itemNumber") String filter,
        @RequestParam(defaultValue = "") String keyword,
        @RequestParam(defaultValue = "0") int page,
        @RequestParam(defaultValue = "10") int size
) {
    return serviceobj.searchItems(filter, keyword, page, size);
}

}
