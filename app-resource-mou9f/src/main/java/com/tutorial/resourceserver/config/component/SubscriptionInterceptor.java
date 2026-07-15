package com.tutorial.resourceserver.config.component;

import com.tutorial.resourceserver.feign.AuthorizationFeign;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import java.util.Optional;

//@Component
public class SubscriptionInterceptor {
//        implements HandlerInterceptor {
//
//    @Autowired
//    AuthorizationFeign authorizationFeign;
//    @Override
//    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
//        // Ignorer les endpoints publics spéciaux (login, register, etc.)
//        if (isPublicEndpoint(request)) {
//            return true;
//        }
//        // Récupérer l'utilisateur authentifié
//        String username = request.getParameter("id");
//        System.out.println("username pour tester l'abonnement "+username);
//        if(username != null) {
//            return authorizationFeign.userIsEnabled(username);
//        }
//        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
//        if (authentication != null) {
//            String usernameAuth = authentication.getName();
//            // Vérifier l'abonnement
//            if (!authorizationFeign.userIsEnabled(usernameAuth)) {
//                response.setStatus(HttpServletResponse.SC_PAYMENT_REQUIRED); // 402 Payment Required
//                response.setContentType("application/json");
//                response.getWriter().write("{\"error\": \"subscription_expired\", \"message\": " +
//                        "\"Your subscription has expired. Please renew to access this resource.\"}");
//                return false;
//            }
//        }
//        // Vérifier les permissions spécifiques selon l'URL
////        if (!hasAccessToEndpoint(user, request.getRequestURI())) {
////            response.setStatus(HttpServletResponse.SC_FORBIDDEN);
////            response.getWriter().write("{\"error\": \"insufficient_subscription\"," +
////                    " \"message\": \"Your subscription plan does not allow access to this resource.\"}");
////            return false;
////        }
//        return true;
//    }
//    private boolean isPublicEndpoint(HttpServletRequest request) {
//        String  uri = request.getRequestURI();
//        return  uri.startsWith("/open/addClient/") ||
//                uri.startsWith("/swagger-ui/") ||
//                uri.startsWith("/v3/api-docs/")||
//                uri.startsWith("/auth/")||
//                uri.startsWith("/error/");
//    }
//    private boolean hasAccessToEndpoint(User user, String uri) {
//        // Logique pour déterminer si l'utilisateur a accès à l'endpoint selon son abonnement
//        if (uri.startsWith("/api/basic/")) {
//            return user.hasAccessToFeature("basic");
//        } else if (uri.startsWith("/api/premium/")) {
//            return user.hasAccessToFeature("premium");
//        } else if (uri.startsWith("/api/enterprise/")) {
//            return user.hasAccessToFeature("enterprise");
//        }
//        return true; // Accès par défaut
//    }
}
