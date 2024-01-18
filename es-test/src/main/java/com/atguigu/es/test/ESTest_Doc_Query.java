package com.atguigu.es.test;

import org.apache.http.HttpHost;
import org.elasticsearch.action.get.GetRequest;
import org.elasticsearch.action.get.GetResponse;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.common.unit.Fuzziness;
import org.elasticsearch.index.query.*;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.SearchHits;
import org.elasticsearch.search.aggregations.AggregationBuilder;
import org.elasticsearch.search.aggregations.AggregationBuilders;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.elasticsearch.search.fetch.subphase.highlight.HighlightBuilder;
import org.elasticsearch.search.sort.SortOrder;

import java.io.IOException;

public class ESTest_Doc_Query {
    public static void main(String[] args) throws IOException {
        // 创建ES客户端
        RestHighLevelClient esClient = new RestHighLevelClient(
                RestClient.builder(new HttpHost("localhost",9200,"http"))
        );

        SearchRequest request = new SearchRequest();
        request.indices("user");

        // 1.查询索引中全部的数据
//        request.source(new SearchSourceBuilder().query(QueryBuilders.matchAllQuery()));

        // 2.条件查询
//        request.source(new SearchSourceBuilder().query(QueryBuilders.termQuery("age","30")));


        // 3.分页查询
//        SearchSourceBuilder builder = new SearchSourceBuilder().query(QueryBuilders.matchAllQuery());
//        // (当前页码-1)*每页显示数据条数
//        builder.from(0);    // 页码
//        builder.size(2);    // 每页显示数量
//        request.source(builder);

        // 4.查询结果排序
//        SearchSourceBuilder builder = new SearchSourceBuilder().query(QueryBuilders.matchAllQuery());
//        builder.sort("age", SortOrder.DESC);
//        request.source(builder);

        // 5.过滤字段
//        SearchSourceBuilder builder = new SearchSourceBuilder().query(QueryBuilders.matchAllQuery());
//        String[] excludes ={"age"};
//        String[] includes ={};
//        builder.fetchSource(includes,excludes);
//        request.source(builder);

        // 6.组合查询((must=and/mustNot=not and)/(should=or))
//        SearchSourceBuilder builder = new SearchSourceBuilder();
//        BoolQueryBuilder boolQueryBuilder = QueryBuilders.boolQuery();
////        boolQueryBuilder.must(QueryBuilders.matchQuery("age",30));
////        boolQueryBuilder.must(QueryBuilders.matchQuery("sex","男"));
////        boolQueryBuilder.mustNot(QueryBuilders.matchQuery("sex","男"));
//        boolQueryBuilder.should(QueryBuilders.matchQuery("age",30));
//        boolQueryBuilder.should(QueryBuilders.matchQuery("age",40));
//        builder.query(boolQueryBuilder);

        // 7.范围查询
//        SearchSourceBuilder builder = new SearchSourceBuilder();
//        RangeQueryBuilder rangeQuery = QueryBuilders.rangeQuery("age");
//        rangeQuery.gte(30);
//        rangeQuery.lte(40);
//        builder.query(rangeQuery);

        // 8.模糊查询
//        SearchSourceBuilder builder = new SearchSourceBuilder();
//        // 模糊查询姓名为wangwu的人，名字相差一个字母或者不相差
//        // 通过设置Fuzziness的属性设置偏差值
//        builder.query(QueryBuilders.fuzzyQuery("name","wangwu").fuzziness(Fuzziness.ONE));

        // 9.高亮查询
//        SearchSourceBuilder builder = new SearchSourceBuilder();
//        TermQueryBuilder termQueryBuilder = QueryBuilders.termQuery("name", "zhangsan");
//        // 使用高亮
//        HighlightBuilder highlightBuilder = new HighlightBuilder();
//        // 设置高亮
//        highlightBuilder.preTags("<font color='red'>");   //前缀标签
//        highlightBuilder.postTags("</font");            // 后缀标签
//        highlightBuilder.field("name");           //指定高亮显示的名称
//        builder.highlighter(highlightBuilder);
//        builder.query(termQueryBuilder);

        // 10.聚合查询
//        SearchSourceBuilder builder = new SearchSourceBuilder();
//        AggregationBuilder aggregationBuilder = AggregationBuilders.max("maxAge").field("age");
//        builder.aggregation(aggregationBuilder);

        // 11.分组查询(根据年龄分组)
        SearchSourceBuilder builder = new SearchSourceBuilder();
        AggregationBuilder aggregationBuilder = AggregationBuilders.terms("ageGroup").field("age");
        builder.aggregation(aggregationBuilder);



        request.source(builder);
        SearchResponse response = esClient.search(request, RequestOptions.DEFAULT);
        SearchHits hits = response.getHits();
        // 响应状态
        System.out.println(hits.getTotalHits());
        System.out.println(response.getTook());

        for (SearchHit hit : hits) {
            System.out.println(hit.getSourceAsString());
        }

        // 关闭ES客户端
        esClient.close();
    }
}