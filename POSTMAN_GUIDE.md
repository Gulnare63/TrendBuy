# TrendyBuy — Postman Test Bələdçisi

> **Base URL:** `http://localhost:8080`
> **Qeyd:** Hər sorğunun Header-ında `Authorization: Bearer {token}` olmalıdır (login-dən sonra).

---

## STEP 1 — İstifadəçi Qeydiyyatı

### 1.1 Yeni İstifadəçi Yarat (CUSTOMER)
```
POST http://localhost:8080/api/users
Content-Type: application/json

{
  "userName": "testuser",
  "email": "test@gmail.com",
  "password": "123456",
  "fullName": "Test İstifadəçi",
  "phoneNumber": "+994501234567"
}
```

### 1.2 Admin İstifadəçi Yarat
```
POST http://localhost:8080/api/users
Content-Type: application/json

{
  "userName": "admin",
  "email": "admin@trendybuy.com",
  "password": "admin123",
  "fullName": "Admin",
  "role": "ADMIN"
}
```

### 1.3 Satıcı İstifadəçi Yarat
```
POST http://localhost:8080/api/users
Content-Type: application/json

{
  "userName": "seller1",
  "email": "seller@trendybuy.com",
  "password": "seller123",
  "fullName": "Satıcı",
  "role": "SELLER"
}
```

---

## STEP 2 — Login (JWT Token Al)

### 2.1 Customer Login
```
POST http://localhost:8080/api/auth/login
Content-Type: application/json

{
  "userName": "testuser",
  "password": "123456"
}
```
> Response-dan gələn `token` dəyərini kopyala → Postman-da Authorization → Bearer Token → yapışdır

### 2.2 Seller Login
```
POST http://localhost:8080/api/auth/login
Content-Type: application/json

{
  "userName": "seller1",
  "password": "seller123"
}
```

### 2.3 Admin Login
```
POST http://localhost:8080/api/auth/login
Content-Type: application/json

{
  "userName": "admin",
  "password": "admin123"
}
```

---

## STEP 3 — Kateqoriyalar

### 3.1 Bütün kateqoriyaları gör
```
GET http://localhost:8080/api/categories
```

### 3.2 Aktiv kateqoriyaları gör
```
GET http://localhost:8080/api/categories/active
```

### 3.3 Kateqoriya yarat
```
POST http://localhost:8080/api/categories
Authorization: Bearer {token}
Content-Type: application/json

{
  "categoryName": "Elektronika",
  "parentId": null
}
```

### 3.4 Alt kateqoriya yarat
```
POST http://localhost:8080/api/categories
Authorization: Bearer {token}
Content-Type: application/json

{
  "categoryName": "Telefonlar",
  "parentId": 1
}
```

### 3.5 Kateqoriyaya bax
```
GET http://localhost:8080/api/categories/1
```

### 3.6 Alt kateqoriyaları gör
```
GET http://localhost:8080/api/categories/1/children
```

### 3.7 Kateqoriyanı yenilə
```
PUT http://localhost:8080/api/categories/1
Authorization: Bearer {token}
Content-Type: application/json

{
  "categoryName": "Elektronika (Yenilənmiş)",
  "parentId": null
}
```

### 3.8 Kateqoriyanı deaktiv et
```
PATCH http://localhost:8080/api/categories/1/deactivate
Authorization: Bearer {token}
```

### 3.9 Kateqoriyanı aktiv et
```
PATCH http://localhost:8080/api/categories/1/activate
Authorization: Bearer {token}
```

### 3.10 Kateqoriyanı sil
```
DELETE http://localhost:8080/api/categories/2
Authorization: Bearer {token}
```

---

## STEP 4 — Məhsullar (SELLER token lazım yaratmaq üçün)

### 4.1 Bütün məhsulları gör
```
GET http://localhost:8080/api/products
```

### 4.2 Məhsula bax
```
GET http://localhost:8080/api/products/1
```

### 4.3 Məhsul axtar
```
GET http://localhost:8080/api/products/search?name=telefon
GET http://localhost:8080/api/products/search?categoryId=2
GET http://localhost:8080/api/products/search?minPrice=100&maxPrice=500
```

### 4.4 Məhsul yarat (SELLER token ilə!)
```
POST http://localhost:8080/api/products
Authorization: Bearer {sellerToken}
Content-Type: application/json

{
  "categoryId": 2,
  "name": "iPhone 15",
  "sku": "IPH-15-BLK",
  "description": "Apple iPhone 15 128GB",
  "price": 1299.99,
  "stockQuantity": 50
}
```

### 4.5 İkinci məhsul yarat (SELLER token)
```
POST http://localhost:8080/api/products
Authorization: Bearer {sellerToken}
Content-Type: application/json

{
  "categoryId": 2,
  "name": "Samsung Galaxy S24",
  "sku": "SAM-S24-WHT",
  "description": "Samsung Galaxy S24 256GB",
  "price": 999.99,
  "stockQuantity": 30
}
```

### 4.6 Məhsulu yenilə (SELLER token)
```
PUT http://localhost:8080/api/products/1
Authorization: Bearer {sellerToken}
Content-Type: application/json

{
  "name": "iPhone 15 Pro",
  "price": 1499.99,
  "stockQuantity": 40,
  "description": "Yenilənmiş açıqlama"
}
```

### 4.7 Öz məhsullarımı gör (SELLER token)
```
GET http://localhost:8080/api/products/my-products
Authorization: Bearer {sellerToken}
```

### 4.8 Kateqoriyaya görə məhsullar
```
GET http://localhost:8080/api/products/by-category/2
```

### 4.9 Endirimdəki məhsullar
```
GET http://localhost:8080/api/products/on-discount
```

### 4.10 Ən yüksək reytinqli məhsullar
```
GET http://localhost:8080/api/products/top-rated
```

---

## STEP 5 — Endirimlər (SELLER token lazım)

### 5.1 Məhsula endirim yarat
```
POST http://localhost:8080/api/discounts
Authorization: Bearer {sellerToken}
Content-Type: application/json

{
  "productId": 1,
  "discountType": "PERCENTAGE",
  "discountValue": 15.00,
  "minOrderAmount": 100.00,
  "maxDiscountAmount": 200.00,
  "startDate": "2026-04-24T00:00:00",
  "endDate": "2026-05-01T23:59:59"
}
```

### 5.2 Məhsulun endirimlərini gör
```
GET http://localhost:8080/api/discounts/product/1
```

### 5.3 Endirimli qiyməti hesabla
```
GET http://localhost:8080/api/discounts/product/1/calculate
```

### 5.4 Aktiv endirimlər
```
GET http://localhost:8080/api/discounts/active
```

### 5.5 Endirimi yenilə (SELLER token)
```
PUT http://localhost:8080/api/discounts/1
Authorization: Bearer {sellerToken}
Content-Type: application/json

{
  "productId": 1,
  "discountType": "FIXED_AMOUNT",
  "discountValue": 50.00,
  "endDate": "2026-06-01T00:00:00"
}
```

### 5.6 Endirimi vaxtından əvvəl bitir (SELLER token)
```
POST http://localhost:8080/api/discounts/1/expire
Authorization: Bearer {sellerToken}
```

---

## STEP 6 — Alış-veriş Səbəti (CUSTOMER token lazım)

### 6.1 Səbətimə bax
```
GET http://localhost:8080/api/cart
Authorization: Bearer {token}
```

### 6.2 Səbətə məhsul əlavə et
```
POST http://localhost:8080/api/cart/add
Authorization: Bearer {token}
Content-Type: application/json

{
  "productId": 1,
  "quantity": 2
}
```

### 6.3 Səbətə ikinci məhsul əlavə et
```
POST http://localhost:8080/api/cart/add
Authorization: Bearer {token}
Content-Type: application/json

{
  "productId": 2,
  "quantity": 1
}
```

### 6.4 Miqdarı yenilə (cartItemId → əvvəlki cavabdan al)
```
PUT http://localhost:8080/api/cart/item/1?quantity=3
Authorization: Bearer {token}
```

### 6.5 Məhsulu səbətdən çıxar
```
DELETE http://localhost:8080/api/cart/item/1
Authorization: Bearer {token}
```

### 6.6 Səbəti tamamilə boşalt
```
DELETE http://localhost:8080/api/cart/clear
Authorization: Bearer {token}
```

---

## STEP 7 — İstək Siyahısı (Wishlist) (CUSTOMER token)

### 7.1 İstək siyahım
```
GET http://localhost:8080/api/wishlist
Authorization: Bearer {token}
```

### 7.2 Məhsulu əlavə et / çıxar (Toggle)
```
POST http://localhost:8080/api/wishlist/toggle/1
Authorization: Bearer {token}
```

### 7.3 Məhsulu istək siyahısından birbaşa səbətə köçür
```
POST http://localhost:8080/api/wishlist/1/move-to-cart
Authorization: Bearer {token}
```

### 7.4 İstək siyahısını təmizlə
```
DELETE http://localhost:8080/api/wishlist/clear
Authorization: Bearer {token}
```

---

## STEP 8 — Çatdırılma Ünvanı (CUSTOMER token)

### 8.1 Ünvan əlavə et
```
POST http://localhost:8080/api/addresses
Authorization: Bearer {token}
Content-Type: application/json

{
  "address": "Nizami küçəsi 12, mənzil 5",
  "city": "Bakı",
  "postalCode": "AZ1000",
  "defaultAddress": true
}
```

### 8.2 Ünvanlarımı gör
```
GET http://localhost:8080/api/addresses
Authorization: Bearer {token}
```

### 8.3 Əsas ünvanımı gör
```
GET http://localhost:8080/api/addresses/default
Authorization: Bearer {token}
```

### 8.4 Ünvanı əsas et
```
POST http://localhost:8080/api/addresses/1/default
Authorization: Bearer {token}
```

### 8.5 Ünvanı yenilə
```
PUT http://localhost:8080/api/addresses/1
Authorization: Bearer {token}
Content-Type: application/json

{
  "address": "İstiqlaliyyət küçəsi 5",
  "city": "Bakı",
  "postalCode": "AZ1001",
  "defaultAddress": false
}
```

### 8.6 Ünvanı sil
```
DELETE http://localhost:8080/api/addresses/1
Authorization: Bearer {token}
```

---

## STEP 9 — Sifarişlər (CUSTOMER token)

### 9.1 Əvvəlcə səbətə məhsul əlavə et (əgər boşdursa)
```
POST http://localhost:8080/api/cart/add
Authorization: Bearer {token}
Content-Type: application/json

{
  "productId": 1,
  "quantity": 2
}
```

### 9.2 Səbətdən sifariş yarat (ƏN YAXŞI ÜSUL)
```
POST http://localhost:8080/api/orders/from-cart
Authorization: Bearer {token}
```

### 9.3 Manual sifariş yarat (məhsulları özün seç)
```
POST http://localhost:8080/api/orders
Authorization: Bearer {token}
Content-Type: application/json

{
  "items": [
    {
      "productId": 1,
      "quantity": 2
    },
    {
      "productId": 2,
      "quantity": 1
    }
  ],
  "shippingAddressText": "Nizami küçəsi 12",
  "shippingCity": "Bakı",
  "shippingPostalCode": "AZ1000",
  "notes": "Zəng etmədən gətirin"
}
```

### 9.4 Sifarişimə bax
```
GET http://localhost:8080/api/orders/1
Authorization: Bearer {token}
```

### 9.5 Öz sifarişlərimi gör
```
GET http://localhost:8080/api/orders/my-orders
Authorization: Bearer {token}
```

### 9.6 Sifariş statusunu dəyiş (ADMIN token!)
```
PUT http://localhost:8080/api/orders/1/status
Authorization: Bearer {adminToken}
Content-Type: application/json

{
  "status": "SHIPPED"
}
```

---

## STEP 10 — Sifariş Maddələri

### 10.1 Sifarişdəki məhsulları gör
```
GET http://localhost:8080/api/order-items/order/1
Authorization: Bearer {token}
```

---

## STEP 11 — Ödəniş (CUSTOMER token)

### 11.1 Sifariş üçün ödəniş et
```
POST http://localhost:8080/api/payments/process
Authorization: Bearer {token}
Content-Type: application/json

{
  "orderId": 1,
  "paymentMethod": "CREDIT_CARD",
  "providerRef": "TEST-REF-001"
}
```
> paymentMethod: CREDIT_CARD, DEBIT_CARD, CASH, BANK_TRANSFER

### 11.2 Ödənişi gör
```
GET http://localhost:8080/api/payments/1
Authorization: Bearer {token}
```

### 11.3 Sifarişin ödənişlərini gör
```
GET http://localhost:8080/api/payments/order/1
Authorization: Bearer {token}
```

---

## STEP 12 — Rəylər (CUSTOMER token)

### 12.1 Məhsula rəy yaz
```
POST http://localhost:8080/api/reviews
Authorization: Bearer {token}
Content-Type: application/json

{
  "productId": 1,
  "rating": 5,
  "comment": "Çox gözəl məhsuldur, tövsiyə edirəm!"
}
```
> ⚠️ YALNIZ həmin məhsulu sifariş edən istifadəçi rəy yaza bilər!

### 12.2 Məhsulun rəylərini gör (token lazım deyil)
```
GET http://localhost:8080/api/reviews/product/1
```

---

## STEP 13 — Bildirişlər (CUSTOMER token)

### 13.1 Öz bildirişlərimi gör
```
GET http://localhost:8080/api/notifications
Authorization: Bearer {token}
```

### 13.2 Bildirişi oxunmuş işarələ
```
POST http://localhost:8080/api/notifications/1/read
Authorization: Bearer {token}
```

### 13.3 Hamısını oxunmuş et
```
POST http://localhost:8080/api/notifications/read-all
Authorization: Bearer {token}
```

---

## STEP 14 — Tarixçə (CUSTOMER token)

### 14.1 Öz tarixçəmi gör
```
GET http://localhost:8080/api/history
Authorization: Bearer {token}
```

### 14.2 Sifarişin tarixçəsini gör
```
GET http://localhost:8080/api/history/order/1
Authorization: Bearer {token}
```

---

## STEP 15 — İstifadəçi Əməliyyatları

### 15.1 Bütün istifadəçilər
```
GET http://localhost:8080/api/users
Authorization: Bearer {adminToken}
```

### 15.2 İstifadəçiyə bax
```
GET http://localhost:8080/api/users/1
Authorization: Bearer {token}
```

### 15.3 Profilimi yenilə
```
PUT http://localhost:8080/api/users/1
Authorization: Bearer {token}
Content-Type: application/json

{
  "fullName": "Yenilənmiş Ad",
  "phoneNumber": "+994559876543"
}
```

### 15.4 İstifadəçinin sifarişlərini gör
```
GET http://localhost:8080/api/users/1/orders
Authorization: Bearer {token}
```

### 15.5 İstifadəçinin bildirişlərini gör
```
GET http://localhost:8080/api/users/1/notifications
Authorization: Bearer {token}
```

### 15.6 İstifadəçinin səbətini gör
```
GET http://localhost:8080/api/users/1/cart
Authorization: Bearer {token}
```

### 15.7 İstifadəçinin tarixçəsini gör
```
GET http://localhost:8080/api/users/1/history
Authorization: Bearer {token}
```

### 15.8 İstifadəçini aktiv/deaktiv et (ADMIN)
```
POST http://localhost:8080/api/users/1/activate
Authorization: Bearer {adminToken}

POST http://localhost:8080/api/users/1/deactivate
Authorization: Bearer {adminToken}
```

---

## TAM TEST SENARİSİ (Ardıcıllıqla yoxla)

```
1.  POST /api/users              → customer yarat
2.  POST /api/users              → seller yarat (role: SELLER)
3.  POST /api/auth/login         → seller login → sellerToken al
4.  POST /api/categories         → "Elektronika" kateqoriya yarat → id=1
5.  POST /api/categories         → "Telefonlar" alt kateqoriya yarat → id=2
6.  POST /api/products           → iPhone yarat (sellerToken, categoryId=2) → id=1
7.  POST /api/products           → Samsung yarat (sellerToken) → id=2
8.  POST /api/discounts          → iPhone-a 15% endirim yarat
9.  GET  /api/discounts/product/1/calculate → endirimli qiyməti yoxla
10. POST /api/auth/login         → customer login → token al
11. POST /api/addresses          → ünvan əlavə et
12. POST /api/cart/add           → iPhone səbətə əlavə et (qty=2)
13. POST /api/cart/add           → Samsung səbətə əlavə et (qty=1)
14. GET  /api/cart               → səbətə bax
15. POST /api/orders/from-cart   → sifarişə çevir → orderId=1
16. GET  /api/orders/1           → sifarişə bax (totalAmount endirimli!)
17. GET  /api/order-items/order/1 → sifarişdəki məhsulları gör
18. POST /api/payments/process   → ödəniş et (orderId=1)
19. GET  /api/notifications      → bildirişlər (2 ədəd olmalı)
20. GET  /api/history            → tarixçə (2 ədəd olmalı)
21. POST /api/reviews            → rəy yaz (productId=1)
22. GET  /api/reviews/product/1  → rəyləri gör
```

---

## XƏTALİ HALLAR (bunları da test et!)

| Test | Sorğu | Gözlənilən |
|------|-------|------------|
| Token olmadan | GET /api/cart (token yox) | 401 Unauthorized |
| Olmayan məhsul | GET /api/products/9999 | 404 Not Found |
| Stoksuz alış | POST /api/cart/add (quantity > stock) | 400 Bad Request |
| Almadan rəy | POST /api/reviews (sifariş yoxdur) | 400 Bad Request |
| Eyni email | POST /api/users (eyni email) | 409 Conflict |
| Boş səbətlə sifariş | POST /api/orders/from-cart (səbət boş) | 400 Bad Request |
