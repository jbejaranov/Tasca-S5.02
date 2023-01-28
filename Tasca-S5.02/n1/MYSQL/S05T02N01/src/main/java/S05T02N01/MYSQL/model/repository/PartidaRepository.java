package S05T02N01.MYSQL.model.repository;

import S05T02N01.MYSQL.model.domain.Partida;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PartidaRepository extends JpaRepository<Partida, Long> {

}
