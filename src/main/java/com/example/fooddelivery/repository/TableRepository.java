package com.example.fooddelivery.repository;

import com.example.fooddelivery.models.Table;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import com.example.fooddelivery.repository.actionInterface.TableActions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

@Repository
public class TableRepository implements TableActions {
    @Autowired
    JdbcTemplate jdbcTemplate;

    public TableRepository() {
    }

    public List<Table> listAll() {
        return this.jdbcTemplate.query("select * from reserve", new TableRepository.TableRowMapper());
    }

    public void save(Table table) {
        this.jdbcTemplate.update("insert into reserve (table_id, owner_name, no_of_people, restaurant, time ) values(?, ?, ?, ?, ?)", new Object[]{table.getTable_id(), table.getOwner_name(), table.getNo_of_people(), table.getRestaurant(), table.getTime()});
    }

    public void update(Table table) {
        String updateQuery = "update reserve set owner_name = ? , no_of_people = ? ,restaurant = ?, time= ? where table_id = ?";
        this.jdbcTemplate.update(updateQuery, new Object[]{table.getOwner_name(),table.getNo_of_people(),table.getRestaurant(),table.getTime(), table.getTable_id()});
    }

    public void delete(Integer id) {
        this.jdbcTemplate.update("delete from reserve where table_id=?", new Object[]{id});
    }

    public Table get(Integer id) {
        String query = "select * from reserve where table_id=?";
        Table table = (Table)this.jdbcTemplate.queryForObject(query, new Object[]{id}, new BeanPropertyRowMapper(Table.class));
        return table;
    }

    class TableRowMapper implements RowMapper<Table> {
        TableRowMapper() {
        }

        public Table mapRow(ResultSet rs, int rowNum) throws SQLException {

            Table table = new Table();
            table.setTable_id(rs.getInt("table_id"));
            table.setOwner_name(rs.getString("owner_name"));
            table.setNo_of_people(rs.getString("no_of_people"));
            table.setRestaurant(rs.getString("restaurant"));
            table.setTime(rs.getString("time"));
            return table;

        }
    }
}
