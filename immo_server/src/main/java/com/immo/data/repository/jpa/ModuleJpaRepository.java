package com.immo.data.repository.jpa;

import org.springframework.data.repository.PagingAndSortingRepository;
import com.immo.bean.jpa.ModuleEntity;

/**
 * Repository : Module.
 */
public interface ModuleJpaRepository extends PagingAndSortingRepository<ModuleEntity, Long> {

}
