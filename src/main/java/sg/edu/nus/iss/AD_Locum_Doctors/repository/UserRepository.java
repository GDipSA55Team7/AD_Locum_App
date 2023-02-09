package sg.edu.nus.iss.AD_Locum_Doctors.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import sg.edu.nus.iss.AD_Locum_Doctors.model.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

	@Query(value = "SELECT * FROM User u WHERE u.username = BINARY ?1 AND u.password = BINARY ?2", nativeQuery = true)
	User findUserByUsernameAndPassword(String username, String password);

    @Query ("SELECT u FROM User u WHERE u.organization.id=?1")
    List<User> findByOrganizationId(Long id);
    
}