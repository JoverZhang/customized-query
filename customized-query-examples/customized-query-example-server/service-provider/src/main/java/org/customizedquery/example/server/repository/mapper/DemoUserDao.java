package org.customizedquery.example.server.repository.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.customizedquery.core.repository.BaseRepository;
import org.customizedquery.example.server.domain.DemoUser;

/**
 * @author Jover Zhang
 */
@Mapper
public interface DemoUserDao extends BaseRepository<DemoUser> {

}
