package com.example.fooddelivery.service;

import com.example.fooddelivery.models.Table;
import com.example.fooddelivery.repository.actionInterface.TableActions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Service;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Service
public class TableService {

        @Autowired
        JdbcTemplate jdbcTemplate;

        private TableActions repo;

        public TableService(TableActions tableActions) {
            this.repo = tableActions;
        }

        public List<Table> listAll() {
            return this.repo.listAll();
        }

        public void save(Table table) {
            this.repo.save(table);
        }

        public void update(Table table) {
            this.repo.update(table);
        }

        public Table get(Integer id) {
            return this.repo.get(id);
        }

        public void delete(Integer id) {
            this.repo.delete(id);
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
