package com.example.trendybuy.enums;

public enum SellerStatus {
    PENDING,    // qeydiyyatdan keçib, amma admin/gateway təsdiq etməyib
    ACTIVE,     // aktiv satıcı, məhsul sata bilər
    REJECTED,   // sənədləri və ya ödənişi rədd olunub
    SUSPENDED   // qaydanı pozduğu üçün bloklanıb
}
