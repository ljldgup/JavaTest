package com.test;

import javax.servlet.*;
import java.util.*;

//ʵ�� Filter ��
public class LogFilter implements Filter  {
    public void  init(FilterConfig config) throws ServletException {
        // ��ȡ��ʼ������
        String site = config.getInitParameter("Site"); 

        // �����ʼ������
        System.out.println("test: " + site); 
    }
    public void  doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws java.io.IOException, ServletException {

        // ���վ������
        System.out.println("alfucklaaaaaa");

        // �����󴫻ع�����
        chain.doFilter(request,response);
    }
    public void destroy( ){
        /* �� Filter ʵ���� Web �����ӷ����Ƴ�֮ǰ���� */
    }
}