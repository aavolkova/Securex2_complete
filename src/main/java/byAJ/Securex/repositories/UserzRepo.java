package byAJ.Securex.repositories;


import byAJ.Securex.models.Userz;
import org.springframework.data.repository.CrudRepository;

public interface UserzRepo extends CrudRepository<Userz, Long> {
    Userz findByUsername(String username);

    Userz findByEmail(String email);

    Long countByEmail(String email);

    Long countByUsername(String username);

}
