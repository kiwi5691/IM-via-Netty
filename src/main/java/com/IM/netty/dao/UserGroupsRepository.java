package com.IM.netty.dao;

import com.IM.netty.entity.User;
import com.IM.netty.entity.UserGroups;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

/**
 * @author kiwi
 * @date 2019/11/24 11:27
 */
@Repository("userGroupsDao")
public interface UserGroupsRepository<T extends UserGroups> extends PagingAndSortingRepository<T, Integer>, JpaSpecificationExecutor<T> {
}
