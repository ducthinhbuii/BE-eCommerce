package com.example.ecommerce.controller;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.TimeZone;

import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.ecommerce.response.PaymentResponse;
import com.example.ecommerce.vnpay.Config;

import jakarta.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/api/payment")
public class PaymentController {
    @GetMapping("/create-payment")
    public ResponseEntity<?> createPayment(HttpServletRequest httpServletRequest) throws UnsupportedEncodingException{
        String vnp_Version = "2.1.0";
        String vnp_Command = "pay";
        String vnp_TmnCode = Config.vnp_TmnCode;
        String orderType = "order";
        long amount = 1000000;
        String bankCode = "NCB";
        Map<String, String> vnpParamsMap = new HashMap<>();
        vnpParamsMap.put("vnp_Version", vnp_Version);
        vnpParamsMap.put("vnp_Command", vnp_Command);
        vnpParamsMap.put("vnp_TmnCode", vnp_TmnCode);
        vnpParamsMap.put("vnp_CurrCode", "VND");
        vnpParamsMap.put("vnp_TxnRef",  Config.getRandomNumber(8));
        vnpParamsMap.put("vnp_OrderInfo", "Thanh toan don hang:" +  Config.getRandomNumber(8));
        vnpParamsMap.put("vnp_OrderType", orderType);
        vnpParamsMap.put("vnp_Locale", "vn");
        vnpParamsMap.put("vnp_ReturnUrl", Config.vnp_ReturnUrl);
        Calendar calendar = Calendar.getInstance(TimeZone.getTimeZone("Asia/Ho_Chi_Minh"));
        SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMddHHmmss");
        String vnpCreateDate = formatter.format(calendar.getTime());
        vnpParamsMap.put("vnp_CreateDate", vnpCreateDate);
        calendar.add(Calendar.MINUTE, 15);
        String vnp_ExpireDate = formatter.format(calendar.getTime());
        vnpParamsMap.put("vnp_ExpireDate", vnp_ExpireDate);
        vnpParamsMap.put("vnp_Amount", String.valueOf(amount));
        if (bankCode != null && !bankCode.isEmpty()) {
            vnpParamsMap.put("vnp_BankCode", bankCode);
        }
        vnpParamsMap.put("vnp_IpAddr", Config.getIpAddress(httpServletRequest));
        String queryUrl = Config.getPaymentURL(vnpParamsMap, true);
        String hashData = Config.getPaymentURL(vnpParamsMap, false);
        String vnpSecureHash = Config.hmacSHA512(Config.secretKey, hashData);
        queryUrl += "&vnp_SecureHash=" + vnpSecureHash;
        String paymentUrl = Config.vnp_PayUrl + "?" + queryUrl;

        PaymentResponse paymentResponse = new PaymentResponse();
        paymentResponse.setStatus("ok");
        paymentResponse.setMessage("successfully");
        paymentResponse.setUrl(paymentUrl);
        return ResponseEntity.status(HttpStatus.OK).body(paymentResponse);
    }


    // public ResponseEntity<?> createPayment(HttpServletRequest httpServletRequest) throws UnsupportedEncodingException{
    //     String vnp_Version = "2.1.0";
    //     String vnp_Command = "pay";
    //     String orderType = "120000";
    //     // long amount = Integer.parseInt(req.getParameter("amount"))*100;
    //     long amount = 10000*100;
    //     // String bankCode = req.getParameter("bankCode");
    //     String bankCode = "NCB";
        
    //     String vnp_TxnRef = Config.getRandomNumber(8);
    //     String vnp_IpAddr = Config.getIpAddress(httpServletRequest);

    //     String vnp_TmnCode = Config.vnp_TmnCode;
        
    //     Map<String, String> vnp_Params = new HashMap<>();
    //     vnp_Params.put("vnp_Version", vnp_Version);
    //     vnp_Params.put("vnp_Command", vnp_Command);
    //     vnp_Params.put("vnp_TmnCode", vnp_TmnCode);
    //     vnp_Params.put("vnp_Amount", String.valueOf(amount));
    //     vnp_Params.put("vnp_CurrCode", "VND");
        
    //     if (bankCode != null && !bankCode.isEmpty()) {
    //         vnp_Params.put("vnp_BankCode", bankCode);
    //     }
    //     vnp_Params.put("vnp_TxnRef", vnp_TxnRef);
    //     vnp_Params.put("vnp_OrderInfo", "Thanh toan don hang:" + vnp_TxnRef);
    //     vnp_Params.put("vnp_OrderType", orderType);

    //     // String locate = req.getParameter("language");
    //     // if (locate != null && !locate.isEmpty()) {
    //     //     vnp_Params.put("vnp_Locale", locate);
    //     // } else {
    //     vnp_Params.put("vnp_Locale", "vn");
    //     // }
    //     vnp_Params.put("vnp_ReturnUrl", Config.vnp_ReturnUrl);
    //     vnp_Params.put("vnp_IpAddr", vnp_IpAddr);

    //     Calendar cld = Calendar.getInstance(TimeZone.getTimeZone("Etc/GMT+7"));
    //     SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMddHHmmss");
    //     String vnp_CreateDate = formatter.format(cld.getTime());
    //     vnp_Params.put("vnp_CreateDate", vnp_CreateDate);
        
    //     cld.add(Calendar.MINUTE, 30);
    //     String vnp_ExpireDate = formatter.format(cld.getTime());
    //     vnp_Params.put("vnp_ExpireDate", vnp);
        
    //     List fieldNames = new ArrayList(vnp_Params.keySet());
    //     System.out.println(fieldNames);
    //     Collections.sort(fieldNames);
    //     System.out.println(fieldNames);
    //     StringBuilder hashData = new StringBuilder();
    //     StringBuilder query = new StringBuilder();
    //     Iterator itr = fieldNames.iterator();
    //     while (itr.hasNext()) {
    //         String fieldName = (String) itr.next();
    //         String fieldValue = (String) vnp_Params.get(fieldName);
    //         if ((fieldValue != null) && (fieldValue.length() > 0)) {
    //             //Build hash data
    //             hashData.append(fieldName);
    //             hashData.append('=');
    //             hashData.append(URLEncoder.encode(fieldValue, StandardCharsets.US_ASCII.toString()));
    //             //Build query
    //             query.append(URLEncoder.encode(fieldName, StandardCharsets.US_ASCII.toString()));
    //             query.append('=');
    //             query.append(URLEncoder.encode(fieldValue, StandardCharsets.US_ASCII.toString()));
    //             if (itr.hasNext()) {
    //                 query.append('&');
    //                 hashData.append('&');
    //             }
    //         }
    //     }
    //     String queryUrl = query.toString();
    //     String vnp_SecureHash = Config.hmacSHA512(Config.secretKey, hashData.toString());
    //     queryUrl += "&vnp_SecureHash=" + vnp_SecureHash;
    //     String paymentUrl = Config.vnp_PayUrl + "?" + queryUrl;
    //     // com.google.gson.JsonObject job = new JsonObject();
    //     // job.addProperty("code", "00");
    //     // job.addProperty("message", "success");
    //     // job.addProperty("data", paymentUrl);
    //     // Gson gson = new Gson();
    //     // resp.getWriter().write(gson.toJson(job));

    //     PaymentResponse paymentResponse = new PaymentResponse();
    //     paymentResponse.setStatus("ok");
    //     paymentResponse.setMessage("successfully");
    //     paymentResponse.setUrl(paymentUrl);
    //     return ResponseEntity.status(HttpStatus.OK).body(paymentResponse);
    // }
    
}
