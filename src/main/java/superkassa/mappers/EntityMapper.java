package superkassa.mappers;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.SneakyThrows;
import org.springframework.jdbc.core.RowMapper;
import superkassa.model.entity.JsonDto;
import superkassa.model.entity.Entity;
import java.sql.ResultSet;
import java.sql.SQLException;



public class EntityMapper implements RowMapper<Entity> {


    @SneakyThrows
    @Override
    public Entity mapRow(ResultSet rs, int rowNum) throws SQLException {

        ObjectMapper objectMapper = new ObjectMapper();
        Entity entity = new Entity();
        entity.setId(rs.getInt("id"));
        entity.setObj(objectMapper.readValue(rs.getString("obj"), JsonDto.class));
        return entity;
    }
}
