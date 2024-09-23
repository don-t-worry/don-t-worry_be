package kwangwoon.chambit.dontworry.domain.user.repository;

import kwangwoon.chambit.dontworry.domain.user.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User,Long> {
    @Query("select u from User u where u.username = :username")
    Optional<User> findByUsername(@Param("username") String username);

    @Modifying
    @Query("delete from User u where u.username = :username")
    void deleteByUsername(@Param("username") String username);
}
