package com.adloveyou.uaa.repository.querydsl;

import com.adloveyou.uaa.domain.*;
import com.querydsl.jpa.impl.JPAQuery;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.Optional;

@Repository
public class UserQueryDslRepository {

    private final EntityManager entityManager;

    public UserQueryDslRepository(EntityManager entityManager) {
        this.entityManager = entityManager;
    }


/*    public Optional<User> findOneWithAuthoritiesById(Long id){
        JPAQuery<User> query = new JPAQuery(entityManager);

        QUser user = QUser.user;
        QProfile profile = QProfile.profile;
        QRole role = QRole.role;
        QPermission permission = QPermission.permission;

        query
            .select(user)
            .from(user)
            .join(user.profiles ,profile)
            .join(profile.roles, role)
            .join(role.permissions,permission)
            .where(user.id.eq(id));

        User u = query.fetchOne();
        return (u != null)?Optional.of(u):Optional.empty();
    }

    @Cacheable(cacheNames = "users")
    public Optional<User> findOneWithAuthoritiesByLogin(String login){
        JPAQuery<User> query = new JPAQuery(entityManager);

        QUser user = QUser.user;
        QProfile profile = QProfile.profile;
        QRole role = QRole.role;
        QPermission permission = QPermission.permission;

        query
            .select(user)
            .from(user)
            .join(user.profiles,profile)
            .join(profile.roles, role)
            .join(role.permissions,permission)
            .where(user.login.eq(login));

        User u = query.fetchOne();
        return (u != null)?Optional.of(u):Optional.empty();
    }*/
}
