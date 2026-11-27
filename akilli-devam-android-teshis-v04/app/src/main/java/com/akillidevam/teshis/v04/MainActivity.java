package com.akillidevam.teshis.v04;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Build;
import android.os.Handler;
import android.os.Looper;
import android.provider.Settings;
import android.graphics.Color;
import android.widget.*;
public final class MainActivity extends Activity {
 private TextView serviceStatus;
 private final Handler handler=new Handler(Looper.getMainLooper());
 private final Runnable refresh=new Runnable(){public void run(){serviceStatus.setText(DiagnosticService.connected?"Erişilebilirlik hizmeti: Bağlı":"Erişilebilirlik hizmeti: Bağlı değil");handler.postDelayed(this,500);}};
 @Override public void onCreate(Bundle saved){
  super.onCreate(saved);
  ScrollView scroll=new ScrollView(this);LinearLayout col=new LinearLayout(this);
  col.setOrientation(LinearLayout.VERTICAL);col.setBackgroundColor(Color.rgb(249,250,251));scroll.addView(col);
  col.setPadding(dp(24),dp(32),dp(24),dp(24));
  col.setOnApplyWindowInsetsListener((v,insets)->{v.setPadding(dp(24),dp(24)+insets.getSystemWindowInsetTop(),dp(24),dp(24)+insets.getSystemWindowInsetBottom());return insets;});
  setContentView(scroll);
  add(col,"Akıllı Devam • Teşhis 0.4",26).setTextColor(Color.rgb(0,105,79));
  add(col,"Kurulum başarılı",23).setTextColor(Color.rgb(0,105,79));
  add(col,"Bu sürüm yalnızca erişilebilirlik bağlantısını test eder. Ekran içeriğini okumaz, otomatik tıklamaz ve mesaj göndermez.",17);
  add(col,"Android: "+Build.VERSION.RELEASE+" (API "+Build.VERSION.SDK_INT+")\nCihaz: "+Build.MANUFACTURER+" "+Build.MODEL+"\nPaket: "+getPackageName(),16);
  final TextView touch=add(col,"Dokunma testi bekleniyor.",16);
  Button test=new Button(this);test.setAllCaps(false);test.setText("Dokunma testini yap");col.addView(test);
  test.setOnClickListener(v->touch.setText("Dokunma testi başarılı."));
  serviceStatus=add(col,"Erişilebilirlik hizmeti: Bağlı değil",19);
  Button settings=new Button(this);settings.setAllCaps(false);settings.setText("Erişilebilirlik ayarlarını aç");col.addView(settings);
  settings.setOnClickListener(v->new AlertDialog.Builder(this).setTitle("Bağlantı testi").setMessage("Akıllı Devam Teşhis 0.4 hizmetini yalnızca Android normal olarak izin veriyorsa etkinleştirin. Güvenlik uyarısı veya kısıtlama çıkarsa durun ve geliştiriciye bildirin.").setNegativeButton("Vazgeç",null).setPositiveButton("Ayarları aç",(d,w)->startActivity(new Intent(Settings.ACTION_ACCESSIBILITY_SETTINGS))).show());
  add(col,"Bağlantı testinden sonra hizmeti aynı ayarlardan kapatabilirsiniz. Güvenlik kısıtlamalarını değiştirmeyin.",15);
 }
 @Override protected void onResume(){super.onResume();handler.removeCallbacks(refresh);handler.post(refresh);}
 @Override protected void onPause(){handler.removeCallbacks(refresh);super.onPause();}
 private int dp(int n){return Math.round(n*getResources().getDisplayMetrics().density);}
 private TextView add(LinearLayout col,String s,int size){TextView t=new TextView(this);t.setText(s);t.setTextColor(Color.rgb(30,41,59));t.setTextSize(size);t.setPadding(0,dp(9),0,dp(9));col.addView(t);return t;}
}
