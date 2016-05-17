package com.immo.data.repository.jpa;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.scheduling.annotation.Async;

import com.immo.bean.jpa.ModuleEntity;
import com.immo.bean.jpa.ModuleProfilEntity;
import com.immo.bean.jpa.ProfilEntity;

/**
 * Repository : ModuleProfil.
 */
public interface ModuleProfilJpaRepository extends PagingAndSortingRepository<ModuleProfilEntity, Long> {

	@Async
    @Query("SELECT t.module FROM ModuleProfilEntity t where t.profil = :profil") 
    List<ModuleEntity> findModuleByProfil(@Param("profil") ProfilEntity profil);
}
