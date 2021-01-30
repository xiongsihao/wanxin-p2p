package cn.itcast.wanxinp2p.transaction.mapper;

import cn.itcast.wanxinp2p.transaction.entity.Tender;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Select;

import java.math.BigDecimal;
import java.util.List;

/**
 * @author : xsh
 * @create : 2021-01-27 - 23:00
 * @describe: 投标信息表 Mapper 接口
 */
public interface TenderMapper extends BaseMapper<Tender> {

    /**
     * 根据标的id, 获取标的已投金额, 如果未投返回0.0
     * @param id
     * @return
     */
    @Select("SELECT IFNULL(SUM(AMOUNT), 0.0) FROM tender WHERE PROJECT_ID = #{id} AND STATUS = 1")
    List<BigDecimal> selectAmountInvestedByProjectId(Long id);
}
