# Akıllı Devam 0.2
- 0.1 commit: 8337ce519d38239bb5c3a09e62989751d510b984; CI 46029332494, 16 karar testi başarılı, debug APK üretildi.
- Kullanıcı Redmi Note 17'de kurulum hatası bildirdi. Telefonun kesin PackageInstaller hata kodu bilinmiyor. Eski APK ZIP içindeki APK ile SHA-256 eşleşiyor.
- 0.2: API 36, AGP 8.10.1, Gradle 8.11.1, release derlemesi, zipalign ve apksigner doğrulaması; Android 16 emülatöründe normal adb install ve Activity açılış testi.
- Kalıcı imza için AKILLI_DEVAM_KEYSTORE_B64 ve AKILLI_DEVAM_KEYSTORE_PASSWORD GitHub Actions secrets gerekir (alias akillidevam). Yoksa tek derlemelik test yayın anahtarı üretilir; sonraki imza farklıysa yerinde güncelleme yapılamaz. Anahtar veya parola public depoya veya artifact'e konulmaz.
- HyperOS gerçek cihaz testi ve ChatGPT arayüzünde çalışma doğrulaması bekliyor. Güvenlik engelleri aşılmaz. Diğer projeler kapsam dışı.
