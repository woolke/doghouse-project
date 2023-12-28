//package pl.wolke.doghouse.core.security.services;
//
//import jakarta.servlet.FilterChain;
//import jakarta.servlet.ServletException;
//import jakarta.servlet.http.HttpServletRequest;
//import jakarta.servlet.http.HttpServletResponse;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.stereotype.Service;
//import org.springframework.web.filter.OncePerRequestFilter;
//import org.springframework.web.util.ContentCachingRequestWrapper;
//import org.springframework.web.util.ContentCachingResponseWrapper;
//
//import java.io.IOException;
//
//@Service
//public class PayloadCapturingFilter extends OncePerRequestFilter {
//
//    Logger logger = LoggerFactory.getLogger(PayloadCapturingFilter.class);
//
//
//    @Override
//    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
//        ContentCachingRequestWrapper requestWrapper = new ContentCachingRequestWrapper(request);
//        ContentCachingResponseWrapper responseWrapper = new ContentCachingResponseWrapper(response);
//
//        filterChain.doFilter(requestWrapper, responseWrapper);
//
//        String requestBody = new String(requestWrapper.getContentAsByteArray());
//        String responseBody = new String(responseWrapper.getContentAsByteArray());
//
//    }
//}
