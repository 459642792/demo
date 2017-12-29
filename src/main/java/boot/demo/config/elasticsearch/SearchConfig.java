package boot.demo.config.elasticsearch;


import org.elasticsearch.action.search.SearchRequestBuilder;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.transport.InetSocketTransportAddress;
import org.elasticsearch.transport.client.PreBuiltTransportClient;

import java.net.InetAddress;
import java.net.UnknownHostException;

/**
 * 配置
 *
 * @author xiaojiang
 * @create 2017-12-29  10:06
 */
public class SearchConfig{
    private  static TransportClient client;
    static {
        // 集群名
       Settings settings = Settings.builder().put("cluster.name", "my-application").build();
       try {
           client = TransportClient.builder().settings(settings).build()
                   .addTransportAddress(new InetSocketTransportAddress(InetAddress.getByName("192.168.113.128"), 9300))
                   .addTransportAddress(new InetSocketTransportAddress(InetAddress.getByName("192.168.113.130"), 9300));
       }catch (UnknownHostException e){
            new Exception();
       }
    }
    public static SearchRequestBuilder getSearchRequestBuilder(SearchEnum searchEnum){
        return  client.prepareSearch(searchEnum.getIndex()).setTypes(searchEnum.getType());
    }
}
