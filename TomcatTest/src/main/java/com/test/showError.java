package com.test;

import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;
import java.util.*;

// ��չ HttpServlet ��
public class showError extends HttpServlet {
 
  // ���� GET ��������ķ���
  public void doGet(HttpServletRequest request,
                    HttpServletResponse response)
            throws ServletException, IOException
  {
      // ���ô�������ԭ��
      response.sendError(407, "Need althehanfucktiordnla!!!" );
  }
  // ���� POST ��������ķ���
  public void doPost(HttpServletRequest request,
                     HttpServletResponse response)
      throws ServletException, IOException {
     doGet(request, response);
  }
}