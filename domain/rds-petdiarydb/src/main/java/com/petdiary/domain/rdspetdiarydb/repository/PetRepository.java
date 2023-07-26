package com.petdiary.domain.rdspetdiarydb.repository;

import com.petdiary.domain.rdscore.repository.ExtendedRepository;
import com.petdiary.domain.rdspetdiarydb.domain.Pet;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface PetRepository extends ExtendedRepository<Pet, Long>, JpaSpecificationExecutor<Pet> {
}
