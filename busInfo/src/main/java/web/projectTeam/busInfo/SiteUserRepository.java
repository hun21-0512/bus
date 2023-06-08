package web.projectTeam.busInfo;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SiteUserRepository extends CrudRepository<SiteUser, Long> {
	SiteUser findByEmail(String email);
	
	//@Modifying
	//@Query("DELETE FROM site_user WHERE email = ?1")
	void deleteClientByName(String email);
}
