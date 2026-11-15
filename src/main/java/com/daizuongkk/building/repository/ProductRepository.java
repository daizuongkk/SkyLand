package com.daizuongkk.building.repository;

import java.io.IOException;
import java.util.Date;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.daizuongkk.building.entity.Building;
import com.daizuongkk.building.form.ProductForm;
import com.daizuongkk.building.model.ProductInfo;
import com.daizuongkk.building.pagination.PaginationResult;

import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import lombok.RequiredArgsConstructor;

@Transactional
@RequiredArgsConstructor
@Repository
public class ProductRepository {

  @PersistenceContext
  private EntityManager entityManager;

  public Building findProduct(Long id) {
    if (id == null) {
      return null;
    }
    try {
      String jpql = "SELECT e FROM Building e WHERE e.id = :id";
      TypedQuery<Building> query = entityManager.createQuery(jpql, Building.class);
      query.setParameter("id", id);
      return query.getSingleResult();
    } catch (NoResultException e) {
      return null;
    }
  }

  public ProductInfo findProductInfo(Long id) {
    Building building = this.findProduct(id);
    if (building == null) {
      return null;
    }
    return new ProductInfo(
        building.getId(),
        building.getName(),
        building.getPrice(),
        building.getStreet(),
        building.getWard(),
        building.getDistrict());
  }

  @Transactional(rollbackFor = Exception.class)
  public void save(ProductForm productForm) throws IOException {
    Long id = productForm.getId();
    Building building = (id != null) ? this.findProduct(id) : null;

    boolean isNew = false;
    if (building == null) {
      isNew = true;
      building = new Building();
      building.setCreatedDate(new Date());
    }

    building.setName(productForm.getName());
    building.setPrice(productForm.getPrice());

    if (productForm.getFileData() != null && !productForm.getFileData().isEmpty()) {
      byte[] image = productForm.getFileData().getBytes();
      if (image != null && image.length > 0) {
        building.setImage(image);
      }
    }

    if (isNew) {
      entityManager.persist(building);
    }
    entityManager.flush();
  }

  @Transactional(readOnly = true)
  public PaginationResult<ProductInfo> queryProducts(int page, int maxResult, int maxNavigationPage, String likeName) {
    StringBuilder jpql = new StringBuilder(
        "SELECT new com.daizuongkk.building.model.ProductInfo(p.id, p.name, p.price, p.street, p.ward, p.district) FROM Building p");
    StringBuilder countJpql = new StringBuilder("SELECT COUNT(p) FROM Building p");

    boolean hasLike = likeName != null && !likeName.trim().isEmpty();

    if (hasLike) {
      String whereClause = " WHERE LOWER(p.name) LIKE :likeName ESCAPE '\\'";
      jpql.append(whereClause);
      countJpql.append(whereClause);
    }

    // Ensure 'createdDate' matches the exact field name in your Building entity
    jpql.append(" ORDER BY p.createdDate DESC");

    TypedQuery<ProductInfo> query = entityManager.createQuery(jpql.toString(), ProductInfo.class);
    TypedQuery<Long> countQuery = entityManager.createQuery(countJpql.toString(), Long.class);

    if (hasLike) {
      String escapedName = escapeLikeWildcards(likeName.trim().toLowerCase());
      String pattern = "%" + escapedName + "%";
      query.setParameter("likeName", pattern);
      countQuery.setParameter("likeName", pattern);
    }

    return new PaginationResult<>(query, countQuery, page, maxResult, maxNavigationPage);
  }

  @Transactional(readOnly = true)
  public PaginationResult<ProductInfo> queryProducts(int page, int maxResult, int maxNavigationPage) {
    return queryProducts(page, maxResult, maxNavigationPage, null);
  }

  private String escapeLikeWildcards(String input) {
    return input
        .replace("\\", "\\\\")
        .replace("%", "\\%")
        .replace("_", "\\_");
  }
}