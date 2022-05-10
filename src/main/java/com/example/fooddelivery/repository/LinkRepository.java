package com.example.fooddelivery.repository;

import com.example.fooddelivery.models.Link;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;
import com.example.fooddelivery.repository.actionInterface.LinkActions;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Component
public class LinkRepository  implements LinkActions {
    private final String getLink="SELECT * FROM Links";
    private final String getRestaurantPincode="SELECT Restaurant_pincode FROM Restaurant WHERE Restaurant_ID=?";
    private final String getUserPincode="SELECT pincode FROM user WHERE user_name=?";

    @Autowired
    private JdbcTemplate jdbcTemplate;

    public List<Link> getLinks(){
      return jdbcTemplate.query(getLink, new RowMapper<Link>() {
          @Override
          public Link mapRow(ResultSet rs, int rowNum) throws SQLException {
              Link l = new Link();
              l.setSourcePincode(rs.getString("source_pincode"));
              l.setDestinationPincode(rs.getString("destination_pincode"));
              l.setLink(rs.getInt("link"));

              return l;
          }
      });

    }

    public String getSourcePincode(int restaurantID){
        String sourcePincode =  jdbcTemplate.queryForObject(getRestaurantPincode, String.class, new Object[] { restaurantID });
        return sourcePincode;
    }


    public String getDestinationPincode(String userName){
        String destinationPincode =  jdbcTemplate.queryForObject(getUserPincode, String.class, new Object[] { userName});
        return destinationPincode;
    }
}

