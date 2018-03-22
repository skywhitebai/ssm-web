package com.sky.ssm.interceptor;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.Arrays;
import java.util.Date;
import java.util.Map;
import java.util.Map.Entry;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StringUtils;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.sky.ssm.utils.DateUtil;

/**
 * 记录信息:</br> 访问时间</br>Controller路径</br>对应方法名</br>请求参数信息</br>请求相对路径</br>请求处理时长
 * @author Administrator
 *
 */
public class LogInterceptor  implements HandlerInterceptor {
	private static final Logger logger =LoggerFactory.getLogger(LoginInterceptor.class);
	
    /**
     * 获取Ip地址
     * @param request
     * @return
     */
    public  String getIpAddr(HttpServletRequest request)  {
        String ip  =  request.getHeader( " x-forwarded-for " );
        if (ip  ==   null   ||  ip.length()  ==   0   ||   " unknown " .equalsIgnoreCase(ip))  {
            ip  =  request.getHeader( " Proxy-Client-IP " );
        }
        if (ip  ==   null   ||  ip.length()  ==   0   ||   " unknown " .equalsIgnoreCase(ip))  {
            ip  =  request.getHeader( " WL-Proxy-Client-IP " );
        }
        if (ip  ==   null   ||  ip.length()  ==   0   ||   " unknown " .equalsIgnoreCase(ip))  {
            ip  =  request.getRemoteAddr();
        }
        return  ip;
    }
    /**
	 * 获取IP信息
	 * 
	 * @param request
	 * @return
	 */
	private static String getIp(HttpServletRequest request) {
		String ip = request.getHeader("X-Forwarded-For");
		if (!StringUtils.isEmpty(ip) && !"unKnown".equalsIgnoreCase(ip)) {
			// 多次反向代理后会有多个ip值，第一个ip才是真实ip
			int index = ip.indexOf(",");
			if (index != -1) {
				return ip.substring(0, index);
			} else {
				return ip;
			}
		}
		ip = request.getHeader("X-Real-IP");
		if (!StringUtils.isEmpty(ip) && !"unKnown".equalsIgnoreCase(ip)) {
			return ip;
		}
		return request.getRemoteAddr();
	}
	public boolean preHandle(HttpServletRequest request,
			HttpServletResponse response, Object handler) throws Exception {
		 long startTime = System.currentTimeMillis();
	        request.setAttribute("executeStartTime", startTime);
	        if (handler instanceof HandlerMethod) {
	            StringBuilder sb = new StringBuilder(1000);

	            sb.append("-----------------------").append(DateUtil.getFormatSSS(new Date()))
	                    .append("-------------------------------------\n");
	            HandlerMethod h = (HandlerMethod) handler;
	            sb.append("Controller: ").append(h.getBean().getClass().getName()).append("\n");
	            sb.append("Method    : ").append(h.getMethod().getName()).append("\n");
	            sb.append("Params    : ").append(JSONObject.fromObject(request.getParameterMap())).append("\n");
	            sb.append("URI       : ").append(request.getRequestURI()).append("\n");
	            sb.append("IP        : ").append(getIp(request)).append("\n");
	            logger.info(sb.toString());
	        }
	        return true;
	}

	public void postHandle(HttpServletRequest request,
			HttpServletResponse response, Object handler,
			ModelAndView modelAndView) throws Exception {
		 long startTime = (Long) request.getAttribute("executeStartTime");
	        long endTime = System.currentTimeMillis();
	        long executeTime = endTime - startTime;
	        if(handler instanceof HandlerMethod){
	            StringBuilder sb = new StringBuilder(1000);
	            sb.append("CostTime  : ").append(executeTime).append("ms").append("\n");
	            sb.append("Response  : ").append(response.getOutputStream().toString()).append("\n");
	            sb.append("-------------------------------------------------------------------------------");
	            logger.info(sb.toString());
	        }
	}
	 private String getParamString(Map<String, String[]> map) {
	        StringBuilder sb = new StringBuilder();
	        for(Entry<String,String[]> e:map.entrySet()){
	            sb.append(e.getKey()).append("=");
	            String[] value = e.getValue();
	            if(value != null && value.length == 1){
	                sb.append(value[0]).append("\t");
	            }else{
	                sb.append(Arrays.toString(value)).append("\t");
	            }
	        }
	        return sb.toString();
	    }

	public void afterCompletion(HttpServletRequest request,
			HttpServletResponse response, Object handler, Exception ex)
			throws Exception {
		// TODO Auto-generated method stub
		
	}
   

}
