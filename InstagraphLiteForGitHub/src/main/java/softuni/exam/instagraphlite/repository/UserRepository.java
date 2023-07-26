package softuni.exam.instagraphlite.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import softuni.exam.instagraphlite.models.entities.User;

import java.util.List;

//ToDo
@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    User findByUsername(String username);

    @Query("SELECT u FROM User u ORDER BY u.posts.size DESC, u.id")
    List<User> findAllByPostsOrderByPostsPostsDescThenByUserId();
}
