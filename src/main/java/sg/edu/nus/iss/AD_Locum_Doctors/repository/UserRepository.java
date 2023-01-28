package sg.edu.nus.iss.AD_Locum_Doctors.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import sg.edu.nus.iss.AD_Locum_Doctors.model.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

	@Query("select u From User u Where UPPER(u.username) = UPPER(:username) and UPPER(u.password) = UPPER(:password)")
	User findUserByUsernameAndPassword(@Param("username") String username,@Param("password") String password);

}