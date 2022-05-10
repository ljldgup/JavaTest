package httpClient;

import com.alibaba.fastjson.JSONObject;
import org.apache.http.HttpEntity;
import org.apache.http.HttpStatus;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.entity.mime.HttpMultipartMode;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.concurrent.TimeUnit;

/**
 * @author zhye
 * @since 2018/6/28
 */

public class PooledClient {

    private static CloseableHttpClient pooledClient;

    static {
        //httpClient池化，避免下载机器人图片视频时消耗过多资源
        PoolingHttpClientConnectionManager connectionManager = new PoolingHttpClientConnectionManager(3, TimeUnit.MINUTES);
        connectionManager.setMaxTotal(50);//max-total：连接池里的最大连接数
        connectionManager.setDefaultMaxPerRoute(5);//default-max-per-route：一个服务每次能并行接收的请求数量，这里route指的是域名

        RequestConfig requestConfig = RequestConfig.custom()
                .setConnectionRequestTimeout(60)//connection-request-timeout  请求超时
                .setConnectTimeout(60)//connect-timeout  获取连接超时
                .setSocketTimeout(120000)//socket-timeout  读超时,注意上传文件这里太短会导致连接直接报time out
                .build();

        pooledClient = HttpClients.custom()
                .setConnectionManager(connectionManager)
                .setDefaultRequestConfig(requestConfig)
                .build();
    }

    /**
     * post请求传输map数据
     *
     * @param url
     * @param map
     * @param encoding
     * @return
     * @throws ClientProtocolException
     * @throws IOException
     */
    public static String sendPostDataByMap(String url, Map<String, String> map, String encoding) throws ClientProtocolException, IOException {
        String result = "";

        // 创建post方式请求对象
        HttpPost httpPost = new HttpPost(url);

        // 装填参数
        List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
        if (map != null) {
            for (Entry<String, String> entry : map.entrySet()) {
                nameValuePairs.add(new BasicNameValuePair(entry.getKey(), entry.getValue()));
            }
        }

        // 设置参数到请求对象中
        httpPost.setEntity(new UrlEncodedFormEntity(nameValuePairs, encoding));

        // 设置header信息
        // 指定报文头【Content-type】、【User-Agent】
        httpPost.setHeader("Content-type", "application/x-www-form-urlencoded");
        httpPost.setHeader("User-Agent", "Mozilla/4.0 (compatible; MSIE 5.0; Windows NT; DigExt)");

        // 执行请求操作，并拿到结果（同步阻塞）
        try (CloseableHttpResponse response = pooledClient.execute(httpPost)) {
            // 获取结果实体
            // 判断网络连接状态码是否正常(0--200都数正常)
            if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
                result = EntityUtils.toString(response.getEntity(), "utf-8");
            }
        }

        return result;
    }

    /**
     * post请求传输json数据
     *
     * @param url
     * @param json
     * @param encoding
     * @return
     * @throws ClientProtocolException
     * @throws IOException
     */
    public static String sendPostDataByJson(String url, String json, String encoding) throws ClientProtocolException, IOException {
        String result = "";

        // 创建post方式请求对象
        HttpPost httpPost = new HttpPost(url);
        StringEntity stringEntity = new StringEntity(json, ContentType.APPLICATION_JSON);
        stringEntity.setContentEncoding(encoding);
        httpPost.setEntity(stringEntity);

        try (CloseableHttpResponse response = pooledClient.execute(httpPost)) {
            if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
                result = EntityUtils.toString(response.getEntity(), "utf-8");
            }
        }

        return result;
    }

    /**
     * post请求传输json数据
     *
     * @param url
     * @param json
     * @param encoding
     * @return
     * @throws ClientProtocolException
     * @throws IOException
     */
    public static byte[] sendPostDataByJsonInputStream(String url, String json, String encoding) throws ClientProtocolException, IOException {
        byte[] result = null;

        HttpPost httpPost = new HttpPost(url);
        StringEntity stringEntity = new StringEntity(json, ContentType.APPLICATION_JSON);
        stringEntity.setContentEncoding(encoding);
        httpPost.setEntity(stringEntity);

        try (CloseableHttpResponse response = pooledClient.execute(httpPost)) {
            if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
                result = toByteArray(response.getEntity().getContent());
            }
        }

        return result;
    }

    public static byte[] toByteArray(InputStream input) throws IOException {
        ByteArrayOutputStream output = new ByteArrayOutputStream();
        byte[] buffer = new byte[1024 * 4];
        int n = 0;
        while (-1 != (n = input.read(buffer))) {
            output.write(buffer, 0, n);
        }
        return output.toByteArray();
    }


    /**
     * get请求传输数据(去除请求头数据格式设置---用于odoo请求)
     *
     * @param url
     * @param encoding
     * @return
     * @throws ClientProtocolException
     * @throws IOException
     */
    public static String sendGetData(String url, String encoding) throws ClientProtocolException, IOException {
        String result = "";

        HttpGet httpGet = new HttpGet(url);
//        httpGet.addHeader("Content-type", "application/json");

        try (CloseableHttpResponse response = pooledClient.execute(httpGet)) {
            if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
                result = EntityUtils.toString(response.getEntity(), encoding);
            }
        }

        return result;
    }


    /**
     * get请求传输数据(去除请求头数据格式设置---用于odoo请求)
     *
     * @param url
     * @param encoding
     * @return
     * @throws ClientProtocolException
     * @throws IOException
     */
    public static String sendGetDataForOrientate(String url, String encoding, String token) throws ClientProtocolException, IOException {
        String result = "";

        // 创建get方式请求对象
        HttpGet httpGet = new HttpGet(url);
        httpGet.addHeader("Content-type", "application/x-www-form-urlencoded");
        httpGet.addHeader("Content-type", "application/json");
        httpGet.addHeader("Authorization", token);
        try (CloseableHttpResponse response = pooledClient.execute(httpGet)) {
            if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
                result = EntityUtils.toString(response.getEntity(), encoding);
            }
        }

        return result;
    }


    public static String uploadFile(String url, InputStream inputStream, String fileParamName, Map<String, String> headerParams, Map<String, String> otherParams, String fileName) throws IOException {
        String result = null;

        HttpPost httpPost = new HttpPost(url);
        if (headerParams != null && headerParams.size() > 0) {
            for (Map.Entry<String, String> e : headerParams.entrySet()) {
                httpPost.addHeader(e.getKey(), e.getValue());
            }
        }

        MultipartEntityBuilder builder = MultipartEntityBuilder.create();
        builder.setCharset(StandardCharsets.UTF_8);
        builder.setMode(HttpMultipartMode.BROWSER_COMPATIBLE);//加上此行代码解决返回中文乱码问题
        builder.addBinaryBody(fileParamName, inputStream, ContentType.MULTIPART_FORM_DATA, fileName);// 文件流
        if (otherParams != null && otherParams.size() > 0) {
            for (Map.Entry<String, String> e : otherParams.entrySet()) {
                builder.addTextBody(e.getKey(), e.getValue());// 类似浏览器表单提交，对应input的name和value
            }
        }

        HttpEntity entity = builder.build();
        httpPost.setEntity(entity);

        // 执行提交
        try (CloseableHttpResponse response = pooledClient.execute(httpPost)) {
            HttpEntity responseEntity = response.getEntity();
            if (responseEntity != null) result = EntityUtils.toString(responseEntity, StandardCharsets.UTF_8);
        } finally {
            inputStream.close();
        }

        return result;
    }

    //机器人大视频采用流下载，直接写入文件服务器
    public static String uploadFile(String fromUrl, String toUrl, String fileParamName, Map<String, String> headerParams, Map<String, String> otherParams, String fileName) throws Exception {
        String result = null;
        HttpGet httpGet = new HttpGet(fromUrl);

        try (CloseableHttpResponse response = pooledClient.execute(httpGet);) {
            HttpEntity getEntity = response.getEntity();
            InputStream videoInputStream = getEntity.getContent();
//            InputStream fileInputStream = convertToFileInputStream(videoInputStream, "robot.temp");
            result = uploadFile(toUrl, videoInputStream, fileParamName, headerParams, otherParams, fileName);
            videoInputStream.close();
        }
        return result;
    }

    public static InputStream convertToFileInputStream(InputStream is, String fileName) {
        try (FileOutputStream fos = new FileOutputStream(fileName)) {
            byte[] b = new byte[1024];
            int length;
            while ((length = is.read(b)) > 0) fos.write(b, 0, length);
            is.close();
            return new FileInputStream(fileName);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static String deleteFile(String url, List<String> filePaths, Map<String, String> headerParams) throws IOException {
        String result = null;

        HttpPost httpPost = new HttpPost(url);
        if (headerParams != null && headerParams.size() > 0) {
            for (Map.Entry<String, String> e : headerParams.entrySet()) {
                httpPost.addHeader(e.getKey(), e.getValue());
            }
        }

        StringEntity stringEntity = new StringEntity(JSONObject.toJSONString(filePaths));
        stringEntity.setContentType("application/json;charset=UTF-8");
        httpPost.setEntity(stringEntity);

        try (CloseableHttpResponse response = pooledClient.execute(httpPost)) {
            HttpEntity responseEntity = response.getEntity();
            if (responseEntity != null) result = EntityUtils.toString(responseEntity, StandardCharsets.UTF_8);
        }
        return result;

    }
}
