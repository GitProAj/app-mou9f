package com.tutorial.resourceserver.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class UserResponseDto {
    private int id;
    private String username;
    private boolean enabled ;
    private LocalDateTime subscriptionStartDate;
    private LocalDateTime subscriptionEndDate;
    private boolean trialPeriod;
    private LocalDateTime trialEndDate;

//                public boolean isEnabled() {
//                        return enabled && isSubscriptionValid();
//                }
//
//                public boolean isSubscriptionValid() {
//                        if (!subscriptionActive) return false;
//
//                        if (trialPeriod && trialEndDate != null) {
//                                return LocalDateTime.now().isBefore(trialEndDate);
//                        }
//
//                        if (subscriptionEndDate != null) {
//                                return LocalDateTime.now().isBefore(subscriptionEndDate);
//                        }
//
//                        return false;
//                }

//    public boolean hasAccessToFeature(String feature) {
//        if (!isSubscriptionValid()) return false;
//
//        return switch (subscriptionPlan) {
//            case "BASIC" -> Set.of("read", "basic").contains(feature);
//            case "PREMIUM" -> Set.of("read", "write", "premium").contains(feature);
//            case "ENTERPRISE" -> true;
//            default -> false;
//        };
//    }

        }


