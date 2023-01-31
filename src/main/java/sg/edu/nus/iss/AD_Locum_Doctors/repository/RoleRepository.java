package sg.edu.nus.iss.AD_Locum_Doctors.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import sg.edu.nus.iss.AD_Locum_Doctors.model.Role;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {
	Role findByName(String name);

    @Query("SELECT r FROM Role r WHERE r.name=?1")
    Optional<Role> findByName(String name);
}