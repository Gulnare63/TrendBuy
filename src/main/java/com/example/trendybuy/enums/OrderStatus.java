package com.example.trendybuy.enums;
public enum OrderStatus {
    PLACED,        // sifariş yaradılıb
    CONFIRMED,     // satıcı/təsdiq sistemi tərəfindən təsdiqlənib
    PACKED,        // qablaşdırılıb
    SHIPPED,       // kargoya verilib
    DELIVERED,     // təhvil verilib
    CANCELLED,     // ləğv edilib
    RETURN_REQUESTED, // geri qaytarma istənib
    RETURNED       // geri qaytarılıb
}