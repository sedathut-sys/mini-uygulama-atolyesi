# Akıllı Devam 0.4 – Erişilebilirlik bağlantısı teşhisi
- V0.1 ve V0.2 telefon kurulum hatası; kesin hata kodu bilinmiyor.
- V0.3 (Actions 46038051411) Android 16 emülatör kurulumu ve açılışı başarılı. 27 Kasım 2026 kullanıcı beyanı: gerçek telefonda kurulum, açılış ve dokunma testi başarılı. Kullanıcının güvenlik ayarlarını değiştirdiğine ilişkin bir bilgi yok.
- V0.4 bağımsız klasör ve paket com.akillidevam.teshis.v04; V0.3 paketine dokunulmaz. Tek derlemelik test yayın anahtarı nedeniyle ayrı paket kullanılır. Bu yüzden kontrollü karşılaştırmada paket kimliği ve imza da farklıdır; sonuç tek başına nedenselliği kanıtlamaz.
- Hizmet yalnız kendi paketinde pencere durum olayına kayıtlıdır; olay içeriğini işlemez. Ekran içeriği ve jest yeteneği kapalı. Otomatik tıklama, mesaj, ağ ve özel kullanıcı izinleri yok. Sistem BIND_ACCESSIBILITY_SERVICE izni yalnız sistemin hizmete bağlanmasını sınırlar.
- Normal Android ayarlarıyla kullanıcı onaylı bağlantı testi; kısıtlamalar aşılmaz. CI imza, manifest, normal kurulum ve Activity başlatma testleri. Gerçek cihazda hizmet etkinleştirme henüz bekliyor.
