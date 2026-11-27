package com.akillidevam.app;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.SystemClock;
import android.provider.Settings;
import android.text.InputType;
import android.view.View;
import android.widget.*;
import android.graphics.Color;
public class MainActivity extends Activity {
 private final Handler handler=new Handler(Looper.getMainLooper());
 private TextView status;
 private EditText limit,delay;
 private CheckBox fallback;
 private final Runnable refresh=new Runnable(){public void run(){if(status!=null)status.setText(Session.status+(Session.decision==null?"":"\nGönderilen devam işlemi: "+Session.decision.count()));handler.postDelayed(this,700);}};
 @Override public void onCreate(Bundle state){
 super.onCreate(state);
 ScrollView scroll=new ScrollView(this);
 LinearLayout col=new LinearLayout(this);col.setOrientation(LinearLayout.VERTICAL);col.setPadding(dp(24),dp(32),dp(24),dp(24));scroll.addView(col);setContentView(scroll);
 TextView title=text(col,"Akıllı Devam",30);title.setTextColor(Color.rgb(0,105,79));
 text(col,"Android • Deneysel sürüm 0.1",15);
 text(col,"Yalnızca resmi ChatGPT uygulamasında çalışır. Başka uygulamaya geçmek otomasyonu durdurur. Bir sohbet açıkken kullanın; otomasyon sırasında başka sohbete geçmeyin.",16);
 status=text(col,"Hazır",18);
 text(col,"Bekleme süresi (10–120 saniye)",16);delay=number(col,"20");
 text(col,"En fazla otomatik devam sayısı (1–50)",16);limit=number(col,"10");
 fallback=new CheckBox(this);fallback.setText("Devam düğmesi yoksa, yanıt üretimi gözlendikten sonra 'Devam et' gönder (deneysel)");col.addView(fallback);
 button(col,"1. Erişilebilirlik ayarlarını aç",v->new AlertDialog.Builder(this).setTitle("Erişilebilirlik izni").setMessage("Uygulama yalnızca ChatGPT düğmelerini ve boş mesaj kutusunu algılamak için erişilebilirlik izni ister. Sohbet yanıtlarını kaydetmez; ağ erişimi yoktur. İstediğiniz an izni kapatabilirsiniz.").setNegativeButton("Vazgeç",null).setPositiveButton("Ayarları aç",(d,w)->startActivity(new Intent(Settings.ACTION_ACCESSIBILITY_SETTINGS))).show());
 button(col,"2. Başlat ve ChatGPT'yi aç",v->startSession());button(col,"Durdur",v->Session.stop("Kullanıcı durdurdu"));
 text(col,"Not: Uygulama projenin içeriğini okuyarak bittiğini anlayamaz. İşlem sınırını ayarlayın ve sonuçları kontrol edin. Otomatik mesaj seçeneği kapalı başlar.",15);
 }
 @Override protected void onResume(){super.onResume();if(Session.active)Session.stop("Uygulamaya dönüldü; durduruldu");handler.removeCallbacks(refresh);handler.post(refresh);}
 @Override protected void onPause(){super.onPause();handler.removeCallbacks(refresh);}
 private void startSession(){
 if(!ContinueService.connected){Session.status="Önce erişilebilirlik iznini açın.";return;}
 int m,s;
 try{m=Integer.parseInt(limit.getText().toString());s=Integer.parseInt(delay.getText().toString());}catch(NumberFormatException e){Session.status="Geçerli sayı girin.";return;}
 if(m<1||m>50||s<10||s>120){Session.status="Değerler belirtilen aralıkta olmalı.";return;}
 Intent launch=getPackageManager().getLaunchIntentForPackage(Session.TARGET);
 if(launch==null){Session.status="Resmi ChatGPT uygulaması bulunamadı.";return;}
 new AlertDialog.Builder(this).setTitle("Otomasyonu başlat?").setMessage("En fazla "+m+" işlem. Kullanım sınırları ve uyarılar aşılmaz. Otomatik mesaj: "+(fallback.isChecked()?"Açık":"Kapalı")+". ChatGPT'de çalışacağınız sohbeti önceden açın.").setNegativeButton("Vazgeç",null).setPositiveButton("Başlat",(d,w)->{Session.decision=new Decision(m,s*1000L,fallback.isChecked());Session.started=SystemClock.elapsedRealtime();Session.status="ChatGPT bekleniyor";Session.active=true;try{startActivity(launch);}catch(Exception e){Session.stop("ChatGPT açılamadı");}}).show();
 }
 private int dp(int n){return (int)(n*getResources().getDisplayMetrics().density+.5f);}
 private TextView text(LinearLayout c,String t,int size){TextView v=new TextView(this);v.setText(t);v.setTextSize(size);v.setPadding(0,dp(7),0,dp(7));c.addView(v);return v;}
 private EditText number(LinearLayout c,String t){EditText e=new EditText(this);e.setInputType(InputType.TYPE_CLASS_NUMBER);e.setText(t);c.addView(e);return e;}
 private void button(LinearLayout c,String t,View.OnClickListener l){Button b=new Button(this);b.setText(t);b.setAllCaps(false);b.setOnClickListener(l);c.addView(b);}
}
