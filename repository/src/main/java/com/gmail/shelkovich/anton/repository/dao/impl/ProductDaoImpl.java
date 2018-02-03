package com.gmail.shelkovich.anton.repository.dao.impl;

import com.gmail.shelkovich.anton.repository.dao.ProductDao;
import com.gmail.shelkovich.anton.repository.dao.SortOrder;
import com.gmail.shelkovich.anton.repository.model.Product;
import org.apache.log4j.Logger;
import org.hibernate.query.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository("productDAO")
public class ProductDaoImpl extends GenericDaoImpl<Product, Long> implements ProductDao {

    private static final Logger logger = Logger.getLogger(ProductDaoImpl.class);

    public ProductDaoImpl() {
        super(Product.class);
    }

    @Override
    public List<Product> getPage(int count, int page, SortOrder sortOrder) {
        Query query = getCurrentSession().createQuery("FROM " + entityClass.getName() + " WHERE isActive = true ORDER BY " + OrderToInstruction(sortOrder));
        query.setMaxResults(count);
        query.setFirstResult((page - 1) * count);
        return query.list();
    }

    @Override
    public Product getById(Long id) {
        return (Product) getCurrentSession().createQuery("FROM  " + entityClass.getName() + " WHERE isActive = true AND id = '" + id + "'").uniqueResult();
    }

    @Override
    public List<Product> getAll() {
        return getCurrentSession().createQuery("FROM " + entityClass.getName() +" WHERE isActive = true").list();
    }

    @Override
    public Integer getRowCount(){
        String countStr = getCurrentSession().createQuery("SELECT COUNT(*) as count FROM " + entityClass.getName()+" WHERE isActive = true").uniqueResult().toString();
        Integer count = null;
        try {
            count = Integer.parseInt(countStr);
        }catch (Exception e){
            logger.error(e.getMessage(), e);
        }
        return count;
    }

    @Override
    public boolean delete(Product bean) {
        if (bean == null) {
            return false;
        }
        if (getCurrentSession().contains(bean)) {
            bean.setActive(false);
        } else {
            Product associatedBean = getById(bean.getId());
            if (associatedBean == null) {
                return false;
            }
            associatedBean.setActive(false);
        }
        return true;
    }

    public boolean hardDelete(Product bean){
        return super.delete(bean);
    }
}
