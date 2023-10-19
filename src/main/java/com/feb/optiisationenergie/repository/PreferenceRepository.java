package com.feb.optiisationenergie.repository;

import com.feb.optiisationenergie.domain.Preference;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the Preference entity.
 */
@Repository
public interface PreferenceRepository extends JpaRepository<Preference, Long> {
    @Query("select preference from Preference preference where preference.user.login = ?#{principal.username}")
    List<Preference> findByUserIsCurrentUser();

    default Optional<Preference> findOneWithEagerRelationships(Long id) {
        return this.findOneWithToOneRelationships(id);
    }

    default List<Preference> findAllWithEagerRelationships() {
        return this.findAllWithToOneRelationships();
    }

    default Page<Preference> findAllWithEagerRelationships(Pageable pageable) {
        return this.findAllWithToOneRelationships(pageable);
    }

    @Query(
        value = "select distinct preference from Preference preference left join fetch preference.user",
        countQuery = "select count(distinct preference) from Preference preference"
    )
    Page<Preference> findAllWithToOneRelationships(Pageable pageable);

    @Query("select distinct preference from Preference preference left join fetch preference.user")
    List<Preference> findAllWithToOneRelationships();

    @Query("select preference from Preference preference left join fetch preference.user where preference.id =:id")
    Optional<Preference> findOneWithToOneRelationships(@Param("id") Long id);
}
