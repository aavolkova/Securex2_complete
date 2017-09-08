package byAJ.Securex.repositories;

import byAJ.Securex.models.Book;
import org.springframework.data.repository.CrudRepository;


public interface BookRepository extends CrudRepository<Book, Integer> {
}
