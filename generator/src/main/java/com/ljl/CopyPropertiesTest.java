package com.ljl;

import cn.hutool.core.bean.BeanUtil;
import org.springframework.beans.BeanUtils;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.*;
import java.util.stream.Collectors;

public class CopyPropertiesTest {
    public static class Source {

        public Long id = 1L;

        /**
         * 钱包ID
         */
        public String walletId = "1";

        /**
         * 钱包名称
         */
        public String walletName = "1";

        /**
         * 钱包等级（1-一类；2-二类）
         */
        public Integer walletLevel = 1231231;

        /**
         * 钱包状态（1-正常、2-已注销、3-已挂失、4-已冻结、5-已止付、6-待启用、7-作废）
         */
        public Integer walletStatus = 1231231;

        /**
         * 钱包类型（9-对公）
         */
        public Integer walletType = 1231231;

        /**
         * 客户编号
         */
        public String userId = "1";

        /**
         * 邮箱
         */
        public String mailId = "2";

        /**
         * 企业名称
         */
        public String companyName = "13123";

        /**
         * 注册日期
         */
        public Date regDate = new Date();
        ;

        /**
         * 注册时间
         */
        public Date regTime = new Date();
        ;

        /**
         * 支付密码设置标志（1-已设置；2-未设置）
         */
        public Integer payPasswordSetFlag;

        /**
         * 失败原因
         */
        public String failRsnDesc = "13123";

        /**
         * 缺少单位证件类型
         */
        public Integer creditType;

        /**
         * 单位证件号码
         */
        public String creditId = "13123";

        /**
         * 证件注册地址（实际经营地址）
         */
        public String companyAddress = "13123";

        /**
         * 单位证件有效期(开始日期）
         */
        public Date companyEnableDateS = new Date();

        /**
         * 单位证件有效期(结束日期）
         */
        public Date companyEnableDateE = new Date();
        ;

        /**
         * 法人手机号
         */
        public String lpPhoneNo = "13123";

        /**
         * 法人姓名
         */
        public String lpName = "13123";

        /**
         * 法人证件类型
         */
        public Integer lpCreditType;

        /**
         * 法人证件号码
         */
        public String lpIdNumber = "13123";

        /**
         * 法人证件有效期(开始日期）
         */
        public Date lpIdNumberEnableDateS = new Date();

        /**
         * 法人证件有效期(结束日期）
         */
        public Date lpIdNumberEnableDateE = new Date();

        /**
         * 申请人手机号
         */
        public String applyPhoneNo = "13123";

        /**
         * 申请人姓名
         */
        public String applyName = "13123";

        /**
         * 申请人证件类型
         */
        public Integer applyCreditType;

        /**
         * 申请人证件号码
         */
        public String applyIdNumber = "13123";

        /**
         * 申请人证件有效期(开始日期）
         */
        public Date applyIdNumberEnableDateS = new Date();
        ;

        /**
         * 申请人证件有效期(结束日期）
         */
        public Date applyIdNumberEnableDateE = new Date();

        /**
         * 合作方银行行号
         */
        public String cooperatorBankNo = "13123";

        /**
         * 金融机构标识码
         */
        public String bankBranchId = "13123";

        /**
         * 开立方式
         * 1-线上；2-线下送过来的是2，则为面签（现场）；
         * 送过来的是1，且认证标识和账户性质是1，则面签（现场）；
         * 其他情况，则网签（远程）
         */
        public Integer loginType;

        /**
         * 认证标识（U盾:硬件证书）
         * 1-硬件证书；2-其他
         */
        public String certifyType = "13123";

        /**
         * 合作方银行KYC结果
         * 1-是；2-否
         */
        public Integer cooperatorConfig;


        /**
         * 钱包单笔交易限额
         */
        public Double walletSingleLimit;

        /**
         * 钱包日累计限额
         */
        public Double walletDayLimit;

        /**
         * 回调地址
         */
        public String callBackUri = "1312321312323";

        /**
         * 附件编号，调用上传API上送的附件名称
         */
        public String materialsId = "1312321312323";

        /**
         * 机构名称
         */
        public String orgName = "13123";

        /**
         * 机构id
         */
        public Long orgId = 123124142124l;

        /**
         * 运营机构id
         */
        public Long operationOrgId = 1l;
        ;

        /**
         * 申请编号
         */
        public String applyNo = "1312321312323";

        /**
         * 申请状态
         */
        public Long applyStatus = 123124142124l;

    }

    public static class Destination {

        public Long id;

        /**
         * 钱包ID
         */
        public String walletId;

        /**
         * 钱包名称
         */
        public String walletName;

        /**
         * 钱包等级（1-一类；2-二类）
         */
        public Integer walletLevel;

        /**
         * 钱包状态（1-正常、2-已注销、3-已挂失、4-已冻结、5-已止付、6-待启用、7-作废）
         */
        public Integer walletStatus;

        /**
         * 钱包类型（9-对公）
         */
        public Integer walletType;

        /**
         * 客户编号
         */
        public String userId;

        /**
         * 邮箱
         */
        public String mailId;

        /**
         * 企业名称
         */
        public String companyName;

        /**
         * 注册日期
         */
        public Date regDate;

        /**
         * 注册时间
         */
        public Date regTime;

        /**
         * 支付密码设置标志（1-已设置；2-未设置）
         */
        public Integer payPasswordSetFlag;

        /**
         * 失败原因
         */
        public String failRsnDesc;

        /**
         * 缺少单位证件类型
         */
        public Integer creditType;

        /**
         * 单位证件号码
         */
        public String creditId;

        /**
         * 证件注册地址（实际经营地址）
         */
        public String companyAddress;

        /**
         * 单位证件有效期(开始日期）
         */
        public Date companyEnableDateS;

        /**
         * 单位证件有效期(结束日期）
         */
        public Date companyEnableDateE;

        /**
         * 法人手机号
         */
        public String lpPhoneNo;

        /**
         * 法人姓名
         */
        public String lpName;

        /**
         * 法人证件类型
         */
        public Integer lpCreditType;

        /**
         * 法人证件号码
         */
        public String lpIdNumber;

        /**
         * 法人证件有效期(开始日期）
         */
        public Date lpIdNumberEnableDateS;

        /**
         * 法人证件有效期(结束日期）
         */
        public Date lpIdNumberEnableDateE;

        /**
         * 申请人手机号
         */
        public String applyPhoneNo;

        /**
         * 申请人姓名
         */
        public String applyName;

        /**
         * 申请人证件类型
         */
        public Integer applyCreditType;

        /**
         * 申请人证件号码
         */
        public String applyIdNumber;

        /**
         * 申请人证件有效期(开始日期）
         */
        public Date applyIdNumberEnableDateS;

        /**
         * 申请人证件有效期(结束日期）
         */
        public Date applyIdNumberEnableDateE;

        /**
         * 合作方银行行号
         */
        public String cooperatorBankNo;

        /**
         * 金融机构标识码
         */
        public String bankBranchId;

        /**
         * 开立方式
         * 1-线上；2-线下送过来的是2，则为面签（现场）；
         * 送过来的是1，且认证标识和账户性质是1，则面签（现场）；
         * 其他情况，则网签（远程）
         */
        public Integer loginType;

        /**
         * 认证标识（U盾:硬件证书）
         * 1-硬件证书；2-其他
         */
        public String certifyType;

        /**
         * 合作方银行KYC结果
         * 1-是；2-否
         */
        public Integer cooperatorConfig;


        /**
         * 钱包单笔交易限额
         */
        public Double walletSingleLimit;

        /**
         * 钱包日累计限额
         */
        public Double walletDayLimit;

        /**
         * 回调地址
         */
        public String callBackUri;

        /**
         * 附件编号，调用上传API上送的附件名称
         */
        public String materialsId;

        /**
         * 机构名称
         */
        public String orgName;

        /**
         * 机构id
         */
        public Long orgId;

        /**
         * 运营机构id
         */
        public Long operationOrgId;

        /**
         * 申请编号
         */
        public String applyNo;

        /**
         * 申请状态
         */
        public Long applyStatus;


    }

    public static void testHutooCopier(Source src, Destination dest) {
        long startTime = System.currentTimeMillis();    //获取开始时间
        for (int i = 0; i < 1000000; i++) {
            BeanUtil.copyProperties(src, dest);
        }
        long endTime = System.currentTimeMillis();    //获取结束时间
        System.out.println(endTime - startTime);
    }

    public static void testSpringCopier(Source src, Destination dest) {
        long startTime = System.currentTimeMillis();    //获取开始时间
        for (int i = 0; i < 1000000; i++) {
            BeanUtils.copyProperties(src, dest);
        }
        long endTime = System.currentTimeMillis();    //获取结束时间
        System.out.println(endTime - startTime);
    }

    public static class CommonField {
        Field srcField;
        Field destField;

        CommonField(Field a, Field b) {
            this.srcField = a;
            this.destField = b;
        }
    }

    public static HashMap<Class, Map<Class, List<CommonField>>> records = new HashMap<>();

    public static List<CommonField> getCommonFields(Class src, Class target) {
        Field[] srcFields = src.getDeclaredFields();
        Map<String, Field> targetFieldMap = Arrays.stream(target.getDeclaredFields()).collect(Collectors.toMap(Field::getName, f -> f));
        return Arrays.stream(srcFields).filter(f -> targetFieldMap.containsKey(f.getName()))
                .map(f -> new CommonField(f, targetFieldMap.get(f.getName())))
                .collect(Collectors.toList());

    }

    public static void testMyCopier(Object src, Object dest) throws InvocationTargetException, IllegalAccessException {
        long startTime = System.currentTimeMillis();    //获取开始时间
        List<CommonField> fields;
        for (int i = 0; i < 1000000; i++) {
            if (records.containsKey(src.getClass()) && records.get(src.getClass()).containsKey(dest.getClass())) {
                fields = records.get(src.getClass()).get(dest.getClass());
            } else {
                fields = getCommonFields(src.getClass(), dest.getClass());
                if (!records.containsKey(src.getClass())) records.put(src.getClass(), new HashMap<>());
                records.get(src.getClass()).put(dest.getClass(), fields);
            }

            for (CommonField commonField : fields) {
                commonField.destField.set(dest, commonField.srcField.get(src));
            }
        }
        long endTime = System.currentTimeMillis();    //获取结束时间
        System.out.println(endTime - startTime);
    }

    public static void main(String[] args) throws InvocationTargetException, IllegalAccessException {
        Source src = new Source();
        Destination dest = new Destination();

        testHutooCopier(src, dest);
//        testSpringCopier(src, dest);

        src = new Source();
        dest = new Destination();
        testMyCopier(src, dest);
    }
}
