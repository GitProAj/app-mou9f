package com.mou9f.service;

import com.mou9f.entity.User;
import com.mou9f.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class SubscriptionService {

//    @Autowired
//    private UserRepository userRepository;
//
////    @Autowired
////    private PaymentService paymentService;
//
//    public boolean activateSubscription(int userId, String plan, int durationMonths) {
//        User user = userRepository.findById(userId).orElseThrow();
//
//        // Vérifier le paiement
////        if (!paymentService.verifyPayment(userId)) {
////            throw new PaymentRequiredException("Payment not completed");
////        }
//
//        user.setSubscriptionActive(true);
////        user.setSubscriptionPlan(plan);
//        user.setSubscriptionStartDate(LocalDateTime.now());
//        user.setSubscriptionEndDate(LocalDateTime.now().plusMonths(durationMonths));
//        user.setTrialPeriod(false);
//        user.setTrialEndDate(null);
//
//        userRepository.save(user);
//        return true;
//    }
//
//    public void checkAndUpdateSubscriptions() {
//        List<User> users = userRepository.findBySubscriptionActiveTrue();
//        LocalDateTime now = LocalDateTime.now();
//
//        for (User user : users) {
//            if (user.getSubscriptionEndDate() != null && user.getSubscriptionEndDate().isBefore(now)) {
//                user.setSubscriptionActive(false);
//                userRepository.save(user);
//                // Notifier l'utilisateur
//                sendSubscriptionExpiredNotification(user);
//            }
//        }
//    }
//
//    @Scheduled(cron = "0 0 0 * * *") // Chaque jour à minuit
//    public void scheduledSubscriptionCheck() {
//        checkAndUpdateSubscriptions();
//    }
}
