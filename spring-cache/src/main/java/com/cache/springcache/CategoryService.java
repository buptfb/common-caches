package com.cache.springcache;

import com.common.cache.constants.CategoryCacheConstants;
import com.common.cache.dto.Category;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * @author zhuzhenke
 * @date 2018/10/29
 */
@Service
public class CategoryService {


    @Caching(evict = {@CacheEvict(value = CategoryCacheConstants.CATEGORY_DOMAIN,
            key = "#category.getCategoryCacheKey()",
            beforeInvocation = true)})
    public int add(Category category) {
        System.out.println("模拟进行数据库交互操作......");
        System.out.println("Cache became invalid,value:" + CategoryCacheConstants.CATEGORY_DOMAIN
                + ",key:" + category.getCategoryCacheKey());
        return 1;
    }


    @Caching(evict = {@CacheEvict(value = CategoryCacheConstants.CATEGORY_DOMAIN,
            key = "#category.getCategoryCacheKey()",
            beforeInvocation = true)})
    public int delete(Category category) {
        System.out.println("模拟进行数据库交互操作......");
        System.out.println("Cache became invalid,value:" + CategoryCacheConstants.CATEGORY_DOMAIN
                + ",key:" + category.getCategoryCacheKey());
        return 0;
    }


    @Caching(evict = {@CacheEvict(value = CategoryCacheConstants.CATEGORY_DOMAIN,
            key = "#category.getCategoryCacheKey()")})
    public int update(Category category) {
        System.out.println("模拟进行数据库交互操作......");
        System.out.println("Cache updated,value:" + CategoryCacheConstants.CATEGORY_DOMAIN
                + ",key:" + category.getCategoryCacheKey()
                + ",category:" + category);
        return 1;
    }


    @Cacheable(value = CategoryCacheConstants.CATEGORY_DOMAIN,
            key = "#category.getCategoryCacheKey()")
    public Category get(Category category) {
        System.out.println("模拟进行数据库交互操作......");
        Category result = new Category();
        result.setCateId(category.getCateId());
        result.setCateName(category.getCateId() + "CateName");
        result.setParentId(category.getCateId() - 10);
        return result;
    }

    /**
     * use guava cache manager
     */
    @Cacheable(value = CategoryCacheConstants.CATEGORY_LIST,
            key = "#pageNo+'_'+#pageSize",
            cacheManager = CategoryCacheConstants.CACHE_MANAGER_GUAVA)
    public List<Category> queryList(Integer pageNo, Integer pageSize) {
        List<Category> resultList = new ArrayList<Category>();
        for (int i = 0; i < pageSize; i++) {
            resultList.add(new Category(Long.valueOf((pageNo - 1) * pageSize + i), "CateName" + i, Long.valueOf(pageNo)));
        }
        return resultList;
    }
}
