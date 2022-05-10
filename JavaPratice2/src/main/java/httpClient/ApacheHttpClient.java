package httpClient;


import com.alibaba.fastjson.JSONObject;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ApacheHttpClient {
    public static String deleteFile(String url, List<String> filePaths, Map<String, String> headerParams) throws IOException {
        String result = null;
        CloseableHttpClient httpClient = HttpClients.createDefault();
        HttpPost httpPost = new HttpPost(url);

        //添加header
        if (headerParams != null && headerParams.size() > 0) {
            for (Map.Entry<String, String> e : headerParams.entrySet()) {
                httpPost.addHeader(e.getKey(), e.getValue());
            }
        }
        StringEntity stringEntity = new StringEntity(JSONObject.toJSONString(filePaths));
        stringEntity.setContentType("application/json;charset=UTF-8");
        httpPost.setEntity(stringEntity);
        HttpResponse response = httpClient.execute(httpPost);// 执行提交
        HttpEntity responseEntity = response.getEntity();
        if (responseEntity != null) {
            // 将响应内容转换为字符串
            result = EntityUtils.toString(responseEntity);
        }
        httpClient.close();
        return result;
    }

    public static void main(String[] args) throws IOException {
        String url = "http://192.168.15.205:18016/file/delete";
        List<String> paths = new ArrayList<>();
        paths.add("2021-12-2320211223T172245602_c30b57ba24124a91a7b310585a860fde-41fb3e49-80c5-4a93-8432-cba567571912.mp4");
        paths.add("2021-12-2320211223T172245602_a7e850f7fe47474e9b18d718186c3835-56290c35-0d9e-4b72-a0e2-a95bf77b13ce.mp4");
        Map<String, String> headerParams = new HashMap<>();
        headerParams.put("appKey", "57f5c5b7-b21e-4fe1-bb19-d0dc558435ea");
        headerParams.put("appToken", "e0acdd34-6c25-4935-b159-57484f49a212");
        deleteFile(url, paths,headerParams);
    }
}
