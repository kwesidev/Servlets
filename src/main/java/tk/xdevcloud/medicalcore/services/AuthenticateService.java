package tk.xdevcloud.medicalcore.services;

import javax.persistence.EntityManager;
import tk.xdevcloud.medicalcore.models.User;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class AuthenticateService extends DBService {

	public AuthenticateService(EntityManager entityManager) {

		super(entityManager);
	}

	/**
	 * verify if username and password is valid
	 * 
	 * @param username
	 * @param rawPassword unencrypted password
	 * @return True if password valid
	 * @throws Throwable
	 */
	public boolean verify(String username, String rawPassword) {

		try {
			User user = entityManager.createQuery("SELECT u FROM User u WHERE username = :username", User.class)
					.setParameter("username", username).getSingleResult();
			BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
			return encoder.matches(rawPassword, user.getPassword());

		} catch (Throwable e) {

			return false;

		}
	}

}
