package com.revature.taskmaster.common.datasource;

import com.revature.taskmaster.common.util.exceptions.InvalidRequestException;
import com.revature.taskmaster.common.util.exceptions.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.Entity;
import javax.persistence.EntityManager;
import javax.persistence.criteria.*;
import java.lang.reflect.Field;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

@Repository
@SuppressWarnings({"unchecked", "rawtypes"})
public class EntitySearcher {

    private final EntityManager entityManager;

    @Autowired
    public EntitySearcher(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    public <T> Set<T> searchForEntity(Map<String, String> searchCriteria, Class<T> entityClass) {

        if (entityClass.getAnnotation(Entity.class) == null) {
            throw new InvalidRequestException("Provided class is not an Entity!");
        }

        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<T> query = cb.createQuery(entityClass);
        Root<T> root = query.from(entityClass);
        Predicate predicate = cb.conjunction();

        for(Map.Entry<String, String> searchEntry : searchCriteria.entrySet()) {

            String searchKey = searchEntry.getKey();
            String searchVal = searchEntry.getValue();

            try {

                if (searchKey.contains(".")) {

                    String[] searchKeyFrags = searchKey.split("\\.");
                    String nestedTypeName = searchKeyFrags[0];
                    String nestedTypeFieldName = searchKeyFrags[1];

                    Field nestedTypeField;
                    if (nestedTypeName.equals("metadata")) {
                        nestedTypeField = entityClass.getSuperclass().getDeclaredField(nestedTypeName).getType().getDeclaredField(nestedTypeFieldName);
                        predicate = getPredicate(cb, predicate, searchVal, nestedTypeField, root.get(nestedTypeName).get(nestedTypeFieldName));
                    } else {
                        Join joinType = root.join(nestedTypeName);
                        nestedTypeField = entityClass.getDeclaredField(nestedTypeName).getType().getDeclaredField(nestedTypeFieldName);
                        predicate = getPredicate(cb, predicate, searchVal, nestedTypeField, joinType.get(nestedTypeFieldName));
                    }

                } else {

                    Field searchField = (searchKey.equals("id"))
                            ? entityClass.getSuperclass().getDeclaredField(searchKey)
                            : entityClass.getDeclaredField(searchKey);

                    predicate = getPredicate(cb, predicate, searchVal, searchField, root.get(searchKey));

                }

            } catch (NoSuchFieldException e) {
                throw new InvalidRequestException(String.format("No attribute with name: %s found on entity: %s", searchKey, entityClass.getSimpleName()));
            }

        }

        query.where(predicate);

        return new HashSet<>(entityManager.createQuery(query).getResultList());
    }

    public Predicate getPredicate(CriteriaBuilder cb, Predicate predicate, String searchVal, Field searchField, Path path) {
        if (searchField.getType().isEnum()) {
            try {
                Enum enumVal = Enum.valueOf((Class<Enum>) searchField.getType(), searchVal.toUpperCase());
                predicate = cb.and(predicate, cb.equal(path, enumVal));
            } catch (IllegalArgumentException e) {
                throw new ResourceNotFoundException();
            }
        } else if (searchField.getType().equals(Boolean.class) || searchField.getType() == boolean.class) {
            predicate = cb.and(predicate, cb.equal(path, Boolean.parseBoolean(searchVal)));
        } else {
            predicate = cb.and(predicate, cb.equal(path, searchVal));
        }
        return predicate;
    }
}
