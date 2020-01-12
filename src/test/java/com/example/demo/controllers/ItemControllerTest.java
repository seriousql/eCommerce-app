package com.example.demo.controllers;

import com.example.demo.TestUtils;
import com.example.demo.model.persistence.Item;
import com.example.demo.model.persistence.repositories.ItemRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

import static org.junit.Assert.assertEquals;

@RunWith(SpringRunner.class)
@DataJpaTest
public class ItemControllerTest {

    private ItemController itemController;

    @Autowired
    private ItemRepository itemRepository;

    @Before
    public void setUp() {
        itemController = new ItemController();
        TestUtils.injectObjects(itemController, "itemRepository", itemRepository);
    }

    @Test
    public void testGetItems() {
        ResponseEntity<List<Item>> res = itemController.getItems();
        assertEquals(200, res.getStatusCodeValue());
        assertEquals(2, res.getBody().size());
        assertEquals("Round Widget", res.getBody().get(0).getName());
    }

    @Test
    public void testGetItemById() {
        ResponseEntity<Item> res = itemController.getItemById(5L);
        assertEquals(404, res.getStatusCodeValue());
    }

    @Test
    public void testGetItemsByName() {
        ResponseEntity<List<Item>> res = itemController.getItemsByName("Square Widget");
        assertEquals(200, res.getStatusCodeValue());
        assertEquals("Square Widget", res.getBody().get(0).getName());
    }
}
