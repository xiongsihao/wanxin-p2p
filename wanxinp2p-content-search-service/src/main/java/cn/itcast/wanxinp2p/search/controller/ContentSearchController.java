package cn.itcast.wanxinp2p.search.controller;

import cn.itcast.wanxinp2p.common.domain.PageVO;
import cn.itcast.wanxinp2p.common.domain.RestResponse;
import cn.itcast.wanxinp2p.search.model.ProjectQueryParamsDTO;
import cn.itcast.wanxinp2p.transaction.model.ProjectDTO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author : xsh
 * @create : 2021-01-26 - 2:57
 * @describe:
 */
@Slf4j
@RestController
@Api(value = "检索服务", tags = "ContentSearch", description = "检索服务API")
public class ContentSearchController {


    @ApiOperation("检索标的")
    @ApiImplicitParams({@ApiImplicitParam(name = "projectQueryParamsDTO", value = "标的检索参数", required = true, dataType = "ProjectQueryParamsDTO", paramType = "body"), @ApiImplicitParam(name = "pageNo", value = "页码", required = true, dataType = "int", paramType = "query"), @ApiImplicitParam(name = "pageSize", value = "每页记录数", required = true, dataType = "int", paramType = "query"), @ApiImplicitParam(name = "sortBy", value = "排序字段", dataType = "String", paramType = "query"), @ApiImplicitParam(name = "order", value = "顺序", dataType = "String", paramType = "query")})
    @PostMapping(value = "/l/projects/indexes/q")
    public RestResponse<PageVO<ProjectDTO>> queryProjectIndex(
            @RequestBody ProjectQueryParamsDTO projectQueryParamsDTO,
            @RequestParam Integer pageNo, @RequestParam Integer pageSize,
            @RequestParam(required = false) String sortBy,
            @RequestParam(required = false) String order) {


        return null;
    }
}
