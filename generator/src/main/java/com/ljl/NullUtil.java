package com.ljl;

import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.ReflectUtil;
import com.srcb.branch.bhboot.core.startup.BHApplicationBuilder;
import com.srcb.branch.bhboot.mock.Application;
import com.srcb.branch.bhboot.mock.controller.entity.response.WalletQueryInfoResponseBizContent;
import com.srcb.branch.bhboot.mock.controller.entity.response.WalletTransferResponseBizContent;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.List;
import java.util.Map;

public class NullUtil {
    static public Object replaceNull(Class cls, Object object) {
        if (ObjectUtil.isNull(object)) return object;

        for (Field field : cls.getDeclaredFields()) {
            try {
                String upperFirstChar = field.getName().substring(0, 1).toUpperCase() + field.getName().substring(1);

                Method getMethod = cls.getMethod("get" + upperFirstChar);
                Method setMethod = cls.getMethod("set" + upperFirstChar, field.getType());
                Object fieldValue = getMethod.invoke(object);
                if (ObjectUtil.isNull(fieldValue) && field.getType().equals(String.class)) {
                    setMethod.invoke(object, "");
                } else if (ObjectUtil.isNotNull(fieldValue)) {
                    //列表递归
                    if (fieldValue instanceof List) {
                        List list = (List) fieldValue;
                        for (Object o : list) replaceNull(o.getClass(), o);
                    }
                    //非核心类递归
                    else if(ObjectUtil.isNotNull(fieldValue.getClass().getClassLoader())){
                        //field.getType()拿到的类是泛型擦除的类，需要fieldValue.getClass()
                        replaceNull(fieldValue.getClass(), fieldValue);
                    }
                }

            } catch (Exception e) {
            }
        }

        //父类递归
        if (ObjectUtil.isNotNull(cls.getSuperclass()) && ObjectUtil.isNotNull(cls.getSuperclass().getClassLoader())) {
            replaceNull(cls.getSuperclass(), object);
        }
        return object;
    }


//    public static void main(String[] args) {
//        WalletTransferResponseBizContent testBizContent = new WalletTransferResponseBizContent();
//        replaceNull(WalletTransferResponseBizContent.class, testBizContent);
//        List<WalletQueryInfoResponseBizContent> testList = new ArrayList<>();
//        IntStream.range(0, 10).forEach(x -> testList.add(new WalletQueryInfoResponseBizContent()));
//        replaceNull(testList);
//        System.out.println(1);
//    }
}
