//package com.example.myapp1.DatabaseHelper;
//
//import com.google.auth.oauth2.GoogleCredentials;
//import com.google.firebase.FirebaseApp;
//import com.google.firebase.FirebaseOptions;
//import com.google.firebase.auth.FirebaseAuth;
//import com.google.firebase.messaging.FirebaseMessaging;
//import com.google.firebase.messaging.Message;
//import com.google.firebase.messaging.Notification;
//
//import java.io.FileInputStream;
//import java.io.IOException;
//import java.util.concurrent.ExecutionException;
//
//public class SmSOTP {
//    public static void main(String[] args) {
//        try {
//            // Đường dẫn đến file service account key JSON
//            String serviceAccountPath = "path/to/service_account_key.json";
//
//            // Khởi tạo Firebase App
//            FirebaseOptions options = FirebaseOptions.builder()
//                    .setCredentials(GoogleCredentials.fromStream(new FileInputStream(serviceAccountPath)))
//                    .build();
//            FirebaseApp.initializeApp(options);
//
//            // Gửi mã OTP tới thiết bị người dùng
//            sendOtpSms("device_token", "123456");
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//    }
//
//    public static void sendOtpSms(String deviceToken, String otp) {
//        try {
//            Message message = Message.builder()
//                    .setNotification(new Notification("OTP Verification", "Your OTP: " + otp))
//                    .setToken(deviceToken)
//                    .build();
//
//            String response = FirebaseMessaging.getInstance().sendAsync(message).get();
//
//            System.out.println("OTP message sent: " + response);
//        } catch (InterruptedException | ExecutionException e) {
//            e.printStackTrace();
//        }
//    }
//}
