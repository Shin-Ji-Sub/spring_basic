/*
 * Generated by the Jasper component of Apache Tomcat
 * Version: Apache Tomcat/10.1.24
 * Generated at: 2024-07-04 07:00:36 UTC
 * Note: The last modified time of this file was set to
 *       the last modified time of the source file after
 *       generation to assist with modification tracking.
 */
package org.apache.jsp.WEB_002dINF.views.account;

import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.jsp.*;

public final class login_jsp extends org.apache.jasper.runtime.HttpJspBase
    implements org.apache.jasper.runtime.JspSourceDependent,
                 org.apache.jasper.runtime.JspSourceImports,
                 org.apache.jasper.runtime.JspSourceDirectives {

  private static final jakarta.servlet.jsp.JspFactory _jspxFactory =
          jakarta.servlet.jsp.JspFactory.getDefaultFactory();

  private static java.util.Map<java.lang.String,java.lang.Long> _jspx_dependants;

  static {
    _jspx_dependants = new java.util.HashMap<java.lang.String,java.lang.Long>(2);
    _jspx_dependants.put("/WEB-INF/lib/jakarta.servlet.jsp.jstl-3.0.1.jar", Long.valueOf(1719277156599L));
    _jspx_dependants.put("jar:file:/D:/SJS/workspace/spring-basic/.metadata/.plugins/org.eclipse.wst.server.core/tmp0/wtpwebapps/spring-mvc-demoweb-a3/WEB-INF/lib/jakarta.servlet.jsp.jstl-3.0.1.jar!/META-INF/c.tld", Long.valueOf(1664421078000L));
  }

  private static final java.util.Set<java.lang.String> _jspx_imports_packages;

  private static final java.util.Set<java.lang.String> _jspx_imports_classes;

  static {
    _jspx_imports_packages = new java.util.LinkedHashSet<>(3);
    _jspx_imports_packages.add("jakarta.servlet");
    _jspx_imports_packages.add("jakarta.servlet.http");
    _jspx_imports_packages.add("jakarta.servlet.jsp");
    _jspx_imports_classes = null;
  }

  private volatile jakarta.el.ExpressionFactory _el_expressionfactory;
  private volatile org.apache.tomcat.InstanceManager _jsp_instancemanager;

  public java.util.Map<java.lang.String,java.lang.Long> getDependants() {
    return _jspx_dependants;
  }

  public java.util.Set<java.lang.String> getPackageImports() {
    return _jspx_imports_packages;
  }

  public java.util.Set<java.lang.String> getClassImports() {
    return _jspx_imports_classes;
  }

  public boolean getErrorOnELNotFound() {
    return false;
  }

  public jakarta.el.ExpressionFactory _jsp_getExpressionFactory() {
    if (_el_expressionfactory == null) {
      synchronized (this) {
        if (_el_expressionfactory == null) {
          _el_expressionfactory = _jspxFactory.getJspApplicationContext(getServletConfig().getServletContext()).getExpressionFactory();
        }
      }
    }
    return _el_expressionfactory;
  }

  public org.apache.tomcat.InstanceManager _jsp_getInstanceManager() {
    if (_jsp_instancemanager == null) {
      synchronized (this) {
        if (_jsp_instancemanager == null) {
          _jsp_instancemanager = org.apache.jasper.runtime.InstanceManagerFactory.getInstanceManager(getServletConfig());
        }
      }
    }
    return _jsp_instancemanager;
  }

  public void _jspInit() {
  }

  public void _jspDestroy() {
  }

  public void _jspService(final jakarta.servlet.http.HttpServletRequest request, final jakarta.servlet.http.HttpServletResponse response)
      throws java.io.IOException, jakarta.servlet.ServletException {

    if (!jakarta.servlet.DispatcherType.ERROR.equals(request.getDispatcherType())) {
      final java.lang.String _jspx_method = request.getMethod();
      if ("OPTIONS".equals(_jspx_method)) {
        response.setHeader("Allow","GET, HEAD, POST, OPTIONS");
        return;
      }
      if (!"GET".equals(_jspx_method) && !"POST".equals(_jspx_method) && !"HEAD".equals(_jspx_method)) {
        response.setHeader("Allow","GET, HEAD, POST, OPTIONS");
        response.sendError(HttpServletResponse.SC_METHOD_NOT_ALLOWED, "JSP들은 오직 GET, POST 또는 HEAD 메소드만을 허용합니다. Jasper는 OPTIONS 메소드 또한 허용합니다.");
        return;
      }
    }

    final jakarta.servlet.jsp.PageContext pageContext;
    jakarta.servlet.http.HttpSession session = null;
    final jakarta.servlet.ServletContext application;
    final jakarta.servlet.ServletConfig config;
    jakarta.servlet.jsp.JspWriter out = null;
    final java.lang.Object page = this;
    jakarta.servlet.jsp.JspWriter _jspx_out = null;
    jakarta.servlet.jsp.PageContext _jspx_page_context = null;


    try {
      response.setContentType("text/html; charset=utf-8");
      pageContext = _jspxFactory.getPageContext(this, request, response,
      			null, true, 8192, true);
      _jspx_page_context = pageContext;
      application = pageContext.getServletContext();
      config = pageContext.getServletConfig();
      session = pageContext.getSession();
      out = pageContext.getOut();
      _jspx_out = out;

      out.write("\n");
      out.write("		 \n");
      out.write("\n");
      out.write("\n");
      out.write("<!DOCTYPE html>\n");
      out.write("\n");
      out.write("<html>\n");
      out.write("<head>\n");
      out.write("	<meta charset='utf-8' />\n");
      out.write("	<title>Login</title>\n");
      out.write("	<link rel='Stylesheet' href='/spring-demoweb/resources/styles/default.css' />\n");
      out.write("	<link rel='Stylesheet' href='/spring-demoweb/resources/styles/input.css' />\n");
      out.write("</head>\n");
      out.write("<body>\n");
      out.write("\n");
      out.write("	<div id='pageContainer'>\n");
      out.write("		\n");
      out.write("		");
      org.apache.jasper.runtime.JspRuntimeLibrary.include(request, response, "/WEB-INF/views/modules/header.jsp", out, false);
      out.write("\n");
      out.write("		\n");
      out.write("		<div id=\"inputcontent\">\n");
      out.write("			<br /><br />\n");
      out.write("		    <div id=\"inputmain\">\n");
      out.write("		        <div class=\"inputsubtitle\">로그인정보</div>\n");
      out.write("		        \n");
      out.write("		        <form action=\"login\" method=\"post\">\n");
      out.write("		        <input type=\"hidden\" name=\"returnUri\" value=\"");
      out.write((java.lang.String) org.apache.jasper.runtime.PageContextImpl.proprietaryEvaluate("${ returnUri }", java.lang.String.class, (jakarta.servlet.jsp.PageContext)_jspx_page_context, null));
      out.write("\">\n");
      out.write("		        <table>\n");
      out.write("		            <tr>\n");
      out.write("		                <th>아이디(ID)</th>\n");
      out.write("		                <td>\n");
      out.write("		                    <input type=\"text\" name=\"memberId\" style=\"width:280px\" />\n");
      out.write("		                </td>\n");
      out.write("		            </tr>\n");
      out.write("		            <tr>\n");
      out.write("		                <th>비밀번호</th>\n");
      out.write("		                <td>\n");
      out.write("		                	<input type=\"password\" name=\"passwd\" style=\"width:280px\" />\n");
      out.write("		                </td>\n");
      out.write("		            </tr>\n");
      out.write("		        </table>\n");
      out.write("		        \n");
      out.write("		        <div class=\"buttons\">\n");
      out.write("		        	<input type=\"submit\" value=\"로그인\" style=\"height:25px\" />\n");
      out.write("		        	<input type=\"button\" id=\"cancel-btn\" value=\"취소\" style=\"height:25px\" />\n");
      out.write("		        	<input type=\"button\" id=\"reset-pwd-btn\" value=\"비밀번호초기화\" style=\"height:25px\" />\n");
      out.write("		        </div>\n");
      out.write("		        </form>\n");
      out.write("		        \n");
      out.write("		    </div>\n");
      out.write("		</div>  	\n");
      out.write("	</div>\n");
      out.write("	\n");
      out.write("	<script src=\"http://code.jquery.com/jquery-3.7.1.js\"></script>\n");
      out.write("	<script type=\"text/javascript\">\n");
      out.write("	$(function() {\n");
      out.write("		\n");
      out.write("		$('#cancel-btn').on(\"click\", function(event) {\n");
      out.write("			location.href = \"/spring-demoweb/home\";\n");
      out.write("		});\n");
      out.write("	\n");
      out.write("		$('#reset-pwd-btn').on(\"click\", function(event) {\n");
      out.write("			location.href = \"/spring-demoweb/account/reset-passwd\";\n");
      out.write("		});\n");
      out.write("		\n");
      out.write("		");
      out.write('\n');
      out.write('	');
      out.write('	');
 if (request.getAttribute("loginfail") != null) { 
      out.write("\n");
      out.write("		alert(\"로그인 실패 (forward)\");\n");
      out.write("		");
 } 
      out.write("\n");
      out.write("		\n");
      out.write("		");
      out.write('\n');
      out.write('	');
      out.write('	');
 if (request.getParameter("loginfail") != null) { 
      out.write("\n");
      out.write("		alert(\"로그인 실패 (redirect)\");\n");
      out.write("		location.href = \"login\";\n");
      out.write("		");
 } 
      out.write("\n");
      out.write("		\n");
      out.write("	});\n");
      out.write("	\n");
      out.write("	</script>\n");
      out.write("\n");
      out.write("</body>\n");
      out.write("</html>\n");
      out.write("\n");
    } catch (java.lang.Throwable t) {
      if (!(t instanceof jakarta.servlet.jsp.SkipPageException)){
        out = _jspx_out;
        if (out != null && out.getBufferSize() != 0)
          try {
            if (response.isCommitted()) {
              out.flush();
            } else {
              out.clearBuffer();
            }
          } catch (java.io.IOException e) {}
        if (_jspx_page_context != null) _jspx_page_context.handlePageException(t);
        else throw new ServletException(t);
      }
    } finally {
      _jspxFactory.releasePageContext(_jspx_page_context);
    }
  }
}
