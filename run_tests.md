# Test Çalıştırma Rehberi

Bu dosya, projedeki belirli testleri Maven komut satırı üzerinden nasıl çalıştıracağınızı açıklar.

## Hedef Testler
Aşağıdaki test sınıfları çalıştırılacaktır:
1. **TestUS023** (C:\Users\user\IdeaProjects\playwright96\src\test\java\webTest\LoyalFriendCare\lylTest\ikinci20\TestUS023.java)
2. **TestUS024** (C:\Users\user\IdeaProjects\playwright96\src\test\java\webTest\LoyalFriendCare\lylTest\ikinci20\TestUS024.java)

---

## Nasıl Çalıştırılır?

Terminali açın ve proje kök dizininde (C:\Users\user\IdeaProjects\playwright96) olduğunuzdan emin olun.

### 1. İki Testi Birlikte Çalıştırma
Her iki testi sırasıyla çalıştırmak için:


```bash
mvn test -Dtest=TestRunner
```

```sh
mvn test -Dtest=runners.TestRunner
```






  
