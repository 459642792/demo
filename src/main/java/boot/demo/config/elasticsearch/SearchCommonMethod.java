package boot.demo.config.elasticsearch;

import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.action.search.SearchType;
import org.elasticsearch.index.query.QueryBuilders;

import java.util.Map;

/**
 * @author xiaojiang
 * @create 2017-12-29  10:08
 */
public class SearchCommonMethod {
    public static SearchResponse setQuery(String type,Object content, SearchEnum searchEnum){
        try {
            return  SearchConfig.getSearchRequestBuilder(searchEnum)
                    .setQuery(QueryBuilders.matchPhraseQuery(type, content)).setFrom(0)
                    .setSize(10).setExplain(true).execute().actionGet();
        }catch (Exception e){
            System.out.println("null");
            return null;
        }

    }
}
