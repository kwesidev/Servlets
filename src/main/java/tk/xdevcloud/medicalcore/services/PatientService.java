package tk.xdevcloud.medicalcore.services;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import tk.xdevcloud.medicalcore.models.Patient;
import java.util.List;
import java.util.UUID;
import tk.xdevcloud.medicalcore.exceptions.*;
import javax.persistence.NoResultException;

public class PatientService extends DBService {
     
	public PatientService(EntityManager entityManager) {

		super(entityManager);
	}

	/**
	 * Adds a Patient 
	 * @param Patient entity
	 * @return
	 */
	public boolean add(Patient patient) {

		EntityTransaction tx = null;
		try {
			tx = entityManager.getTransaction();
			tx.begin();
			entityManager.persist(patient);
			tx.commit();
		} catch (Exception e) {

			if (tx != null && tx.isActive()) {
				tx.rollback();
			}
			return false;
		}

		finally {

			entityManager.close();
		}

		return true;
	}

	/**
	 * Updates Patient record
	 * @param Patient p
	 * @param UUID uuid
	 * @return bool
	 * @throws Exception
	 */

	public boolean update(Patient p, UUID uuid) throws NotFoundException, Exception {

		Patient patient = findByUUID(uuid);
		EntityTransaction tx = null;
		try {
			tx = entityManager.getTransaction();
			tx.begin();
			patient.setFirstName(p.getFirstName());
			patient.setLastName(p.getLastName());
			patient.setIdNumber(p.getIdNumber());
			tx.commit();

		} catch (Exception e) {

			if (tx != null && tx.isActive()) {
				tx.rollback();
			}
			return false;

		} finally {

			entityManager.close();
		}
		return true;
	}

	/**
	 * Gets a specific patient record
	 * @param UUID uuid of the patient to view
	 * @return Patient entity
	 * @throws NotFoundException
	 */
	public Patient getPatient(UUID uuid) throws NotFoundException, Exception {

		Patient patient = findByUUID(uuid);
		if (patient == null) {

			throw new NotFoundException("No Record found by that id");
		}

		return patient;
	}

	@SuppressWarnings("unchecked")
	/**
	 * returns a list of patients
	 * 
	 * @return List
	 */
	public List<Patient> getPatients() {

		return (List<Patient>) entityManager.createQuery("SELECT p FROM Patient p").setMaxResults(50).getResultList();

	}

	/**
	 * delete a specific patient
	 * @param uuid id of the specific patient
	 * @return bool
	 * @throws Exception
	 */
	public boolean delete(UUID uuid) throws NotFoundException, Exception {

		EntityTransaction tx = entityManager.getTransaction();
		Patient patient = findByUUID(uuid);
		try {
			tx.begin();
			entityManager.remove(patient);
			tx.commit();

		} catch (Exception e) {
			if (tx != null && tx.isActive()) {
				tx.rollback();
			}
		}

		return true;
	}

	/**
	 * find entity by uuid
	 * @param uuid
	 * @return
	 * @throws NotFoundException
	 */
	private Patient findByUUID(UUID uuid) throws NotFoundException {
		Patient patient;
		try {
			patient = (Patient) entityManager.createQuery("SELECT p FROM Patient p WHERE p.uuid = :uuid")
					.setParameter("uuid", uuid).getSingleResult();

		} catch (NoResultException exception) {

			throw new NotFoundException("No Record found by that id");

		}
		return patient;
	}

}
