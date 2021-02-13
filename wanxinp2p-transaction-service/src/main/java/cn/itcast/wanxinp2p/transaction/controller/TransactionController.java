package cn.itcast.wanxinp2p.transaction.controller;

import cn.itcast.wanxinp2p.common.domain.PageVO;
import cn.itcast.wanxinp2p.common.domain.RestResponse;
import cn.itcast.wanxinp2p.transaction.TransactionApi;
import cn.itcast.wanxinp2p.transaction.model.*;
import cn.itcast.wanxinp2p.transaction.service.ProjectService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author : xsh
 * @create : 2021-01-22 - 1:39
 * @describe:
 */
@Api(value = "交易中心服务", tags = "transaction")
@RestController
public class TransactionController implements TransactionApi {

    @Autowired
    private ProjectService projectService;

    @Override
    @ApiOperation("借款人发标")
    @ApiImplicitParam(name = "project", value = "标的信息", required = true, dataType = "Project", paramType = "body")
    @PostMapping("/my/projects")
    public RestResponse<ProjectDTO> createProject(@RequestBody ProjectDTO projectDTO) {
        ProjectDTO dto = projectService.createProject(projectDTO);
        return RestResponse.success(dto);
    }

    /**
     * 后台系统 检索标的信息
     *
     * @param projectQueryDTO 封装查询条件
     * @param order
     * @param pageNo
     * @param pageSize
     * @param sortBy
     * @return
     */
    @Override
    @ApiOperation("检索标的信息")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "projectQueryDTO", value = "标的信息查询对象", required = true, dataType = "ProjectQueryDTO", paramType = "body"),
            @ApiImplicitParam(name = "order", value = "顺序", required = false, dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "pageNo", value = "页码", required = true, dataType = "int", paramType = "query"),
            @ApiImplicitParam(name = "pageSize", value = "每页记录数", required = true, dataType = "int", paramType = "query"),
            @ApiImplicitParam(name = "sortBy", value = "排序字段", required = true, dataType = "string", paramType = "query")})
    @PostMapping("/projects/q")
    public RestResponse<PageVO<ProjectDTO>> queryProjects(
            @RequestBody ProjectQueryDTO projectQueryDTO,
            String order, Integer pageNo, Integer pageSize, String sortBy) {
        PageVO<ProjectDTO> projects = projectService.queryProjectsByQueryDTO(projectQueryDTO, order, pageNo, pageSize, sortBy);
        return RestResponse.success(projects);
    }

    /**
     * 管理员审核标的信息
     *
     * @param id
     * @param approveStatus
     * @return
     */
    @Override
    @ApiOperation("管理员审核标的信息")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "标的id", required = true, dataType = "long", paramType = "path"),
            @ApiImplicitParam(name = "approveStatus", value = "审批状态", required = true, dataType = "ref", paramType = "path")})
    @PutMapping("/m/projects/{id}/projectStatus/{approveStatus}")
    public RestResponse<String> projectsApprovalStatus(@PathVariable("id") Long id,
                                                       @PathVariable("approveStatus") String approveStatus) {
        String result = projectService.projectsApprovalStatus(id, approveStatus);
        return RestResponse.success(result);
    }

    /**
     * 标的信息快速检索
     *
     * @param projectQueryDTO
     * @param pageNo
     * @param pageSize
     * @param sortBy
     * @param order
     * @return
     */
    @Override
    @ApiOperation("从ES检索标的信息")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "projectQueryDTO", value = "标的信息条件对象", required = true, dataType = "ProjectQueryDTO", paramType = "body"),
            @ApiImplicitParam(name = "order", value = "顺序", required = false, dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "pageNo", value = "页码", required = true, dataType = "int", paramType = "query"),
            @ApiImplicitParam(name = "pageSize", value = "每页记录数", required = true, dataType = "int", paramType = "query"),
            @ApiImplicitParam(name = "sortBy", value = "排序字段", required = false, dataType = "string", paramType = "query")})
    @PostMapping("/projects/indexes/q")
    public RestResponse<PageVO<ProjectDTO>> queryProjects(@RequestBody ProjectQueryDTO projectQueryDTO,
                                                          Integer pageNo, Integer pageSize, String sortBy, String order) {
        PageVO<ProjectDTO> projects = projectService.queryProjects(projectQueryDTO, order, pageNo, pageSize, sortBy);
        return RestResponse.success(projects);
    }

    /**
     * 通过ids获取多个标的
     * 交易中心查询标的信息接口
     *
     * @param ids
     * @return
     */
    @Override
    @ApiOperation("通过ids获取多个标的")
    @GetMapping("/projects/{ids}")
    public RestResponse<List<ProjectDTO>> queryProjectsIds(@PathVariable String ids) {

        List<ProjectDTO> projectDTOS = projectService.queryProjectsIds(ids);
        return RestResponse.success(projectDTOS);
    }

    /**
     * 根据标的id查询投标记录
     * 交易中心查询投标记录接口
     *
     * @param id
     * @return
     */
    @Override
    @ApiOperation("根据标的id查询投标记录")
    @GetMapping("/tenders/projects/{id}")
    public RestResponse<List<TenderOverviewDTO>> queryTendersByProjectId(@PathVariable Long id) {

        return RestResponse.success(projectService.queryTendersByProjectId(id));
    }

    /**
     * 用户投标
     *
     * @param projectInvestDTO
     * @return
     */
    @Override
    @ApiOperation("用户投标")
    @ApiImplicitParam(name = "projectInvestDTO", value = "投标信息", required = true, dataType = "ProjectInvestDTO", paramType = "body")
    @PostMapping("/my/tenders")
    public RestResponse<TenderDTO> createTender(@RequestBody ProjectInvestDTO projectInvestDTO) {
        TenderDTO tenderDTO = projectService.createTender(projectInvestDTO);
        return RestResponse.success(tenderDTO);
    }
}
