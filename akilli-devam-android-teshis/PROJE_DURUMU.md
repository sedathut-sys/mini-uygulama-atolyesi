# Akıllı Devam 0.3 – Kurulum teşhisi
- V0.1 ve V0.2 dosyaları ve iş akışı değiştirilmedi. V0.3 ayrı paket ve ayrı klasördür.
- Paket: com.akillidevam.teshis; min API 26, hedef API 36. Erişilebilirlik servisi, ağ, özel izinler veya otomasyon yok.
- Kullanıcı V0.1 ve V0.2 için telefonda "yüklenemedi" hatası bildirdi. Kesin Android kurulum hata kodu bilinmiyor.
- Teşhis paketinin kurulabilmesi erişilebilirlik hizmetinin veya önceki paket kimliğinin soruna yol açtığını tek başına kanıtlamaz; iki değişken birlikte değiştirildi.
- Tek derlemelik özel test yayın anahtarı; Google/Xiaomi onayı değildir. Güvenlik engelleri aşılmaz.
- CI: APK imzası, paket bilgisi, izin/servis manifest kontrolü, Android 16 emülatör kurulumu ve açılışı. Gerçek telefon ve HyperOS testi kullanıcı tarafından yapılacak.
