package superkassa.services;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import superkassa.model.dto.request.ModifyRq;
import superkassa.model.dto.response.ModifyRs;
import superkassa.model.entity.Entity;
import superkassa.repository.EntityRepository;



@Service
@RequiredArgsConstructor
public class EntityService {

    private final EntityRepository entityRepository;

    public synchronized ResponseEntity<ModifyRs> postModify(ModifyRq request) {

        try {
            //Поиск сущности
            Entity entity = entityRepository.getEntityById(request.getId());

            //сумма
            int sum = entity.getObj().getCurrent() + request.getAdd();

            //добавляем число
            entity.getObj().setCurrent(sum);

            //обновляем данные
            entityRepository.updateEntityCurrentValue(entity);

            //ответ на успешное выполнение запроса
            return ResponseEntity.ok(ModifyRs
                    .builder()
                    .current(sum)
                    .build());

        } catch (Exception e) {
            e.printStackTrace();
        }

        //пользователь чайник =)
        return new ResponseEntity(HttpStatus.I_AM_A_TEAPOT);
    }
}
