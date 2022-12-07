package superkassa.repository;

import com.fasterxml.jackson.databind.json.JsonMapper;
import com.google.gson.Gson;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import superkassa.exeptions.EntityNotFoundException;
import superkassa.mappers.EntityMapper;
import superkassa.model.entity.Entity;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

@Repository
@RequiredArgsConstructor
public class EntityRepository {

    private final JdbcTemplate jdbcTemplate;
    private final EntityMapper rowMapper = new EntityMapper();
    private ReadWriteLock readWriteLock = new ReentrantReadWriteLock();
    private final Gson gson = new Gson();
    private JsonMapper jsonMapper = new JsonMapper();

    public Entity getEntityById(int id) {

        try {
            readWriteLock.readLock().lock();
            String sql ="select * from sk_example_table where id = ?";
            return jdbcTemplate.queryForObject(sql, rowMapper, id);
        } catch (EmptyResultDataAccessException e) {
            throw new EntityNotFoundException("id = " + id);
        }
        finally {
            readWriteLock.readLock().unlock();
        }
    }


    public boolean updateEntityCurrentValue(Entity entity) {

                try {
                    readWriteLock.writeLock().lock();
                    return (jdbcTemplate.update("UPDATE sk_example_table SET obj = '"
                            + gson.toJson(entity.getObj()) +
                            "' WHERE id = ?", entity.getId()) == 1);
                }
                finally {
                    readWriteLock.writeLock().unlock();
                }

    }

    public boolean createEntity(Entity entity) {
        try {
            readWriteLock.writeLock().lock();
            return (jdbcTemplate.update("INSERT INTO sk_example_table(obj) VALUES ("
                            + "'" + gson.toJson(entity.getObj()) + "')") == 1);

        }
        finally {
            readWriteLock.writeLock().unlock();
        }
    }
}
