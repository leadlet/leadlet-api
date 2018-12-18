package com.leadlet.repository;

import com.leadlet.domain.User;
import com.leadlet.domain.enumeration.SyncStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

import java.time.Instant;
import java.util.List;
import java.util.Optional;

/**
 * Spring Data JPA repository for the User entity.
 */

@Repository
public interface UserRepository extends JpaRepository<User, Long>, JpaSpecificationExecutor<User>  {

    User findOneByIdAndAppAccount_Id(Long login, Long appAccountId);

    Optional<User> findOneByActivationKey(String activationKey);

    List<User> findAllByActivatedIsFalseAndCreatedDateBefore(Instant dateTime);

    Optional<User> findOneByResetKey(String resetKey);

    Optional<User> findOneByLoginAndAppAccount_Id(String login,Long appAccountId);

    Optional<User> findOneByLogin(String login);

    @EntityGraph(attributePaths = "authorities")
    User findOneWithAuthoritiesById(Long id);

    @EntityGraph(attributePaths = "authorities")
    Optional<User> findOneWithAuthoritiesByLoginAndAppAccount_Id(String login, Long appAccountId);

    @EntityGraph(attributePaths = "authorities")
    Optional<User> findOneWithAuthoritiesByLogin(String login);

    Page<User> findAllByLoginNotAndAppAccount_Id(Pageable pageable, String login, Long appAccountId);
    List<User> findAllByIdIn(List<Long> ids);

    Page<User> findAllBySyncStatusAndCreatedDateLessThan(SyncStatus syncStatus, Instant maxDate, Pageable page);

    @Modifying
    @Query("update #{#entityName} user set user.syncStatus = ?1 where user.id in ?2")
    void updateUsersStatus(SyncStatus syncStatus, List<Long> ids);

}
