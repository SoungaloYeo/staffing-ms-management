package com.staffing.service;

import com.staffing.service.dto.AddressDTO;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link com.staffing.domain.Address}.
 */
public interface AddressService {
    /**
     * Save a address.
     *
     * @param addressDTO the entity to save.
     * @return the persisted entity.
     */
    AddressDTO save(AddressDTO addressDTO);

    /**
     * Updates a address.
     *
     * @param addressDTO the entity to update.
     * @return the persisted entity.
     */
    AddressDTO update(AddressDTO addressDTO);

    /**
     * Partially updates a address.
     *
     * @param addressDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<AddressDTO> partialUpdate(AddressDTO addressDTO);

    /**
     * Get all the addresses.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<AddressDTO> findAll(Pageable pageable);

    /**
     * Get all the addresses with eager load of many-to-many relationships.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<AddressDTO> findAllWithEagerRelationships(Pageable pageable);

    /**
     * Get the "id" address.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<AddressDTO> findOne(Long id);

    /**
     * Delete the "id" address.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
