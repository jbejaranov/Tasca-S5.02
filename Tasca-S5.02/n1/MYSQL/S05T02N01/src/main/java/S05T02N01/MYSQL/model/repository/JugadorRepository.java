package S05T02N01.MYSQL.model.repository;


import S05T02N01.MYSQL.model.domain.Jugador;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JugadorRepository extends JpaRepository<Jugador, Long> {

}