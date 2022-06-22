package google;


import com.google.common.base.Joiner;
import com.google.common.base.Splitter;
import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import com.google.common.collect.*;
import com.google.common.util.concurrent.RateLimiter;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

public class GuavaTest {
    public static void main(String[] args) {
        //统计用
        Multiset<Integer> set = HashMultiset.create();
        set.add(3);
        set.add(3);
        set.add(4);
        set.add(5);
        set.add(4);
        set.add(1, 10);
        System.out.println(set);

        //双键map，
        Table<String, String, List<Object>> tables = HashBasedTable.create();
        tables.put("财务部", "总监", Lists.newArrayList());
        tables.put("财务部", "职员", Lists.newArrayList());
        tables.put("法务部", "助理", Lists.newArrayList());
        System.out.println(tables);

        List<Integer> list = Lists.newArrayList();
        list.add(34);
        list.add(64);
        list.add(267);
        list.add(865);
        String result = Joiner.on("-").skipNulls().join(list);
        System.out.println(result);

        //map join
        Map<String, Integer> map = Maps.newHashMap();
        map.put("key1", 45);
        map.put("key2", 234);
        result = Joiner.on(",").withKeyValueSeparator("=").join(map);
        System.out.println(result);

        String str = "1-2-3-4-5-6";
        List<String> list2 = Splitter.on("-").splitToList(str);
        System.out.println(list2);

        //单机缓存
        // LoadingCache是Cache的缓存实现
        LoadingCache<String, Object> cache = CacheBuilder.newBuilder()
                //设置缓存大小
                .maximumSize(1000)
                //设置到期时间
                .expireAfterWrite(10, TimeUnit.MINUTES)
                //设置缓存里的值两分钟刷新一次
                .refreshAfterWrite(2, TimeUnit.MINUTES)
                //开启缓存的统计功能
                .recordStats()
                //构建缓存
                .build(new CacheLoader<String, Object>() {
                    //此处实现如果根据key找不到value需要去如何获取
                    @Override
                    public Object load(String s) throws Exception {
                        return new Object();
                    }

                    //如果批量加载有比反复调用load更优的方法则重写这个方法
                    @Override
                    public Map<String, Object> loadAll(Iterable<? extends String> keys) throws Exception {
                        return super.loadAll(keys);
                    }
                });

        cache.invalidateAll();//清除所有缓存项
//清理的时机：在写操作时顺带做少量的维护工作，或者偶尔在读操作时做——如果写操作实在太少的话
//如果想自己维护则可以调用Cache.cleanUp();
        cache.cleanUp();
//另外有时候需要缓存中的数据做出变化重载一次,这个过程可以异步执行
        cache.refresh("key");


        //单机限流
        //平滑突发限流
        RateLimiter r = RateLimiter.create(5);
        while (true) {
            System.out.println("get 1 tokens: " + r.acquire() + "s");
        }
        //平均分发令牌速率为 2，预热期为 3 分钟
        //平滑预热限流并不会像平滑突发限流一样先将所有的令牌创建好，它启动后会有一段预热期，逐步将分发频率提升到配置的速率
//        RateLimiter r2 = RateLimiter.create(2, 3, TimeUnit.SECONDS);
//        while (true) {
//            System.out.println("get 1 tokens: " + r2.acquire(1) + "s");
//            System.out.println("get 1 tokens: " + r2.acquire(1) + "s");
//            System.out.println("end");
//        }
    }
}
