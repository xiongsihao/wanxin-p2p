package cn.itcast.wanxinp2p.search.service;

import cn.itcast.wanxinp2p.common.domain.PageVO;
import cn.itcast.wanxinp2p.search.model.ProjectQueryParamsDTO;
import cn.itcast.wanxinp2p.transaction.model.ProjectDTO;
import org.apache.commons.lang.StringUtils;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.SearchHits;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.elasticsearch.search.sort.SortOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author : xsh
 * @create : 2021-01-26 - 3:17
 * @describe:
 */
@Service
public class ProjectIndexServiceImpl  implements ProjectIndexService{

    @Autowired
    private RestHighLevelClient restHighLevelClient;
    @Value("${wanxinp2p.es.index}")
    private String projectIndex;


    @Override
    public PageVO<ProjectDTO> queryProjectIndex(ProjectQueryParamsDTO projectQueryParamsDTO, Integer pageNo, Integer pageSize, String sortBy, String order) {

        //1.创建搜索请求对象
        SearchRequest searchRequest = new SearchRequest(projectIndex);

        //2.搜索条件
        //2.1 创建条件封装对象
        BoolQueryBuilder queryBuilder = QueryBuilders.boolQuery();
        //2.2 非空判断并封装条件
        if (StringUtils.isNotBlank(projectQueryParamsDTO.getName())) {
            queryBuilder.must(QueryBuilders.termQuery("name", projectQueryParamsDTO.getName()));
        }
        //gte lte 范围查询
        if (projectQueryParamsDTO.getStartPeriod() != null) {
            queryBuilder.must(QueryBuilders.rangeQuery("period").gte(projectQueryParamsDTO.getStartPeriod()));
        }
        if (projectQueryParamsDTO.getEndPeriod() != null) {
            queryBuilder.must(QueryBuilders.rangeQuery("period").lte(projectQueryParamsDTO.getEndPeriod()));
        }

        //3.创建SearchSourceBuilder对象---总的封装对象
        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
        //3.1 封装条件
        searchSourceBuilder.query(queryBuilder);
        //3.2 设置排序参数
        if (StringUtils.isNotBlank(sortBy) && StringUtils.isNotBlank(order)) {
            if (order.toLowerCase().equals("asc")) {
                searchSourceBuilder.sort(sortBy, SortOrder.ASC);
            }
            if (order.toLowerCase().equals("desc")) {
                searchSourceBuilder.sort(sortBy, SortOrder.DESC);
            }
        } else {
            searchSourceBuilder.sort("createdate", SortOrder.DESC);
        }

        //3.2 设置分页参数
        searchSourceBuilder.from((pageNo - 1) * pageSize);
        searchSourceBuilder.size(pageSize);

        //4.完成封装
        searchRequest.source(searchSourceBuilder);

        //5.执行搜索
        List<ProjectDTO> list = new ArrayList<>();
        PageVO<ProjectDTO> pageVO = new PageVO<>();
        try {
            SearchResponse searchResponse = restHighLevelClient.search(searchRequest, RequestOptions.DEFAULT);
            //6.获取响应结果
            SearchHits hits = searchResponse.getHits();
            long totalHits = hits.getTotalHits().value;//匹配的总记录数
            pageVO.setTotal(totalHits);
            SearchHit[] searchHits = hits.getHits();//获取匹配数据
            //7.循环封装DTO
            for (SearchHit hit : searchHits) {
                ProjectDTO projectDTO = new ProjectDTO();
                Map<String, Object> sourceAsMap = hit.getSourceAsMap();
                Double amount = (Double) sourceAsMap.get("amount");
                String projectstatus = (String) sourceAsMap.get("projectstatus");
                Integer period = Integer.parseInt(sourceAsMap.get("period").toString());
                String name = (String) sourceAsMap.get("name");
                String description = (String) sourceAsMap.get("description");
                BigDecimal annualRate = new BigDecimal(sourceAsMap.get("annualrate").toString());
                projectDTO.setAmount(new BigDecimal(amount));
                projectDTO.setProjectStatus(projectstatus);
                projectDTO.setPeriod(period);
                projectDTO.setName(name);
                projectDTO.setDescription(description);
                projectDTO.setAnnualRate(annualRate);
                projectDTO.setId(Long.parseLong(sourceAsMap.get("id").toString()));
                list.add(projectDTO);
            }
        } catch (Exception e) {
            return null;
        }
        //8.封装为PageVO对象并返回
        pageVO.setContent(list);
        pageVO.setPageSize(pageSize);
        pageVO.setPageNo(pageNo);
        return pageVO;
    }

}
