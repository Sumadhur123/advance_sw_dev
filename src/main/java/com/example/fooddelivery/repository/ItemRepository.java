package com.example.fooddelivery.repository;

import com.example.fooddelivery.models.Item;
import com.example.fooddelivery.repository.actionInterface.ItemActions;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

@Repository
public class ItemRepository implements ItemActions {
    @Autowired
    JdbcTemplate jdbcTemplate;

    public ItemRepository() {
    }

    public List<Item> listAll() {
        return this.jdbcTemplate.query("select * from item", new ItemRepository.ItemRowMapper());
    }

    public void save(Item item) {
        this.jdbcTemplate.update("insert into item (item_id, item_name, item_catagory, item_price) values(?, ?, ?, ?)", new Object[]{item.getItem_id(), item.getItem_name(), item.getItem_catagory(), item.getItem_price()});
    }

    public void update(Item item) {
        String updateQuery = "update item set item_name = ? , item_catagory=? , item_price=? where item_id = ?";
        this.jdbcTemplate.update(updateQuery, new Object[]{item.getItem_name(), item.getItem_catagory(), item.getItem_price(), item.getItem_id()});
    }

    public void delete(Integer id) {
        this.jdbcTemplate.update("delete from item where item_id=?", new Object[]{id});
    }

    public Item get(Integer id) {
        String query = "select * from item where item_id=?";
        Item item = (Item)this.jdbcTemplate.queryForObject(query, new Object[]{id}, new BeanPropertyRowMapper(Item.class));
        return item;
    }

    class ItemRowMapper implements RowMapper<Item> {
        ItemRowMapper() {
        }

        public Item mapRow(ResultSet rs, int rowNum) throws SQLException {
            Item item = new Item();
            item.setItem_id(rs.getInt("item_id"));
            item.setItem_name(rs.getString("item_name"));
            item.setItem_catagory(rs.getString("item_catagory"));
            item.setItem_price(rs.getInt("item_price"));
            return item;
        }
    }
}
