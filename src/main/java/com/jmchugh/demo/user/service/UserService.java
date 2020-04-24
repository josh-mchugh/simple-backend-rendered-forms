package com.jmchugh.demo.user.service;

import com.jmchugh.demo.user.entity.QUserEntity;
import com.jmchugh.demo.user.entity.UserEntity;
import com.jmchugh.demo.user.entity.UserStatus;
import com.jmchugh.demo.user.model.EditForm;
import com.jmchugh.demo.user.model.FilterForm;
import com.jmchugh.demo.user.model.NewForm;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.jpa.JPQLQuery;
import com.querydsl.jpa.JPQLQueryFactory;
import lombok.RequiredArgsConstructor;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;

@Component
@Transactional
@RequiredArgsConstructor
public class UserService {

    private final JPQLQueryFactory queryFactory;
    private final EntityManager entityManager;

    public Page<UserEntity> getPageable(Pageable pageable, FilterForm filter) {

        QUserEntity qUser = QUserEntity.userEntity;

        BooleanBuilder predicate = new BooleanBuilder();

        if (StringUtils.isNotBlank(filter.getSearch())) {

            BooleanBuilder searchPredicate = new BooleanBuilder();
            searchPredicate.or(qUser.username.containsIgnoreCase(filter.getSearch()));
            searchPredicate.or(qUser.email.containsIgnoreCase(filter.getSearch()));
            searchPredicate.or(qUser.firstName.containsIgnoreCase(filter.getSearch()));
            searchPredicate.or(qUser.lastName.containsIgnoreCase(filter.getSearch()));

            predicate.and(searchPredicate);
        }

        if (CollectionUtils.isNotEmpty(filter.getSelectedStatuses())) {

            predicate.and(qUser.status.in(filter.getSelectedStatuses()));
        }

        JPQLQuery<UserEntity> query = queryFactory.selectFrom(qUser)
                .where(predicate)
                .fetchAll()
                .limit(pageable.getPageSize())
                .offset(pageable.getOffset());

        if(pageable.getSort().isSorted()) {

            query.orderBy(getSortOrder(pageable.getSort()));
        }

        return new PageImpl<>(query.fetch(), pageable, query.fetchCount());
    }

    public UserEntity save(NewForm form) {

        UserEntity userEntity = new UserEntity();
        userEntity.setUsername(form.getUsername());
        userEntity.setEmail(form.getEmail());
        userEntity.setFirstName(form.getFirstName());
        userEntity.setLastName(form.getLastName());
        userEntity.setStatus(UserStatus.ACTIVE);

        entityManager.persist(userEntity);

        return userEntity;
    }

    public EditForm createEditForm(Long id) {

        UserEntity userEntity = findById(id);

        EditForm editForm = new EditForm();
        editForm.setUsername(userEntity.getUsername());
        editForm.setEmail(userEntity.getEmail());
        editForm.setFirstName(userEntity.getFirstName());
        editForm.setLastName(userEntity.getLastName());
        editForm.setStatus(userEntity.getStatus());
        editForm.setStreetAddress(userEntity.getAddress().getStreetAddress());
        editForm.setSecondaryAddress(userEntity.getAddress().getSecondaryAddress());
        editForm.setBuildingNumber(userEntity.getAddress().getBuildingNumber());
        editForm.setCity(userEntity.getAddress().getCity());
        editForm.setState(userEntity.getAddress().getState());
        editForm.setPostalCode(userEntity.getAddress().getPostalCode());
        editForm.setCountry(userEntity.getAddress().getCountry());

        return editForm;
    }

    public UserEntity update(Long id, EditForm form) {

        UserEntity userEntity = findById(id);
        userEntity.setUsername(form.getUsername());
        userEntity.setEmail(form.getEmail());
        userEntity.setFirstName(form.getFirstName());
        userEntity.setLastName(form.getLastName());
        userEntity.setStatus(form.getStatus());
        userEntity.getAddress().setStreetAddress(form.getStreetAddress());
        userEntity.getAddress().setSecondaryAddress(form.getSecondaryAddress());
        userEntity.getAddress().setBuildingNumber(form.getBuildingNumber());
        userEntity.getAddress().setCity(form.getCity());
        userEntity.getAddress().setState(form.getState());
        userEntity.getAddress().setPostalCode(form.getPostalCode());
        userEntity.getAddress().setCountry(form.getCountry());

        entityManager.persist(userEntity);

        return userEntity;
    }

    public UserEntity findById(Long id) {

        QUserEntity qUser = QUserEntity.userEntity;

        return queryFactory.selectFrom(qUser)
                .where(qUser.id.eq(id))
                .fetchOne();
    }

    private OrderSpecifier<?> getSortOrder(Sort sort) {

        QUserEntity qUser = QUserEntity.userEntity;
        Sort.Order order = sort.iterator().next();

        switch (order.getProperty()) {
            case "username":
                return order.isAscending() ? qUser.username.asc() : qUser.username.desc();
            case "email":
                return order.isAscending() ? qUser.email.asc() : qUser.email.desc();
            case "firstName":
                return order.isAscending() ? qUser.firstName.asc() : qUser.firstName.desc();
            case "lastName":
                return order.isAscending() ? qUser.lastName.asc() : qUser.lastName.desc();
            case "status":
                return order.isAscending() ? qUser.status.asc() : qUser.status.desc();
        }

        return null;
    }
}
