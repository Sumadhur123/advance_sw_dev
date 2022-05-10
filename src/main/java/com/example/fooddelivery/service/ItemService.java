package com.example.fooddelivery.service;
import com.example.fooddelivery.models.Item;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import com.example.fooddelivery.repository.actionInterface.ItemActions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Service;

@Service
public class ItemService {
	
    @Autowired
    JdbcTemplate jdbcTemplate;
    private ItemActions repo;

    public ItemService(ItemActions itemActions) {
        this.repo = itemActions;
    }
    public List<Item> listAll() {
        return this.repo.listAll();
    }

    public void save(Item item) {
        this.repo.save(item);
    }

    public void update(Item item) {
        this.repo.update(item);
    }

    public Item get(Integer id) {
        return this.repo.get(id);
    }

    public void delete(Integer id) {
        this.repo.delete(id);
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
