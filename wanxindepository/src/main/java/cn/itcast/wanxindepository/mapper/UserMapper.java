package cn.itcast.wanxindepository.mapper;

import cn.itcast.wanxindepository.entity.User;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.springframework.stereotype.Repository;

/**
 * <p>
 * 存管用户信息表 Mapper 接口
 * </p>
 *
 * @author zhupeiyuan
 * @since 2019-05-13
 */
@Repository
public interface UserMapper extends BaseMapper<User> {

}
