package cn.itcast.wanxindepository.service;

import cn.itcast.wanxindepository.entity.Project;
import cn.itcast.wanxindepository.model.CreateProjectResponse;
import cn.itcast.wanxindepository.model.ModifyProjectResponse;
import com.baomidou.mybatisplus.extension.service.IService;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * <p>
 * 标的信息表 服务类
 * </p>
 *
 * @author zhupeiyuan
 * @since 2019-05-13
 */
public interface ProjectService extends IService<Project> {

	/**
	 * 创建标的
	 * @param reqData 业务数据
	 * @return
	 */
	CreateProjectResponse createProject(String reqData);

	/**
	 * 更新标的状态
	 * @param reqData 业务数据
	 * @return
	 */
	ModifyProjectResponse modifyProject(String reqData);

	/**
	 * 根据标的编号获取标的信息
	 * @param projectNo
	 * @return
	 */
	Project getByProjectNo(String projectNo);

}
