package com.akillidevam.teshis;
import android.app.Activity;
import android.os.Bundle;
import android.os.Build;
import android.graphics.Color;
import android.widget.*;
import android.view.View;
import android.view.WindowInsets;
public class MainActivity extends Activity {
 @Override public void onCreate(Bundle saved){
  super.onCreate(saved);
  ScrollView scroll=new ScrollView(this);
  LinearLayout col=new LinearLayout(this);
  col.setOrientation(LinearLayout.VERTICAL);
  col.setBackgroundColor(Color.rgb(249,250,251));
  scroll.addView(col);
  col.setPadding(dp(24),dp(32),dp(24),dp(24));
  col.setOnApplyWindowInsetsListener((v,insets)->{
    v.setPadding(dp(24),dp(24)+insets.getSystemWindowInsetTop(),dp(24),dp(24)+insets.getSystemWindowInsetBottom());return insets;
  });
  setContentView(scroll);
  TextView title=add(col,"Akıllı Devam • Teşhis 0.3",26);title.setTextColor(Color.rgb(0,105,79));
  add(col,"Kurulum başarılı",23).setTextColor(Color.rgb(0,105,79));
  add(col,"Bu bağımsız teşhis sürümü özel izin, erişilebilirlik hizmeti veya ağ erişimi içermez. ChatGPT'yi kontrol etmez.",17);
  add(col,"Android: "+Build.VERSION.RELEASE+" (API "+Build.VERSION.SDK_INT+")\nCihaz: "+Build.MANUFACTURER+" "+Build.MODEL+"\nPaket: "+getPackageName(),16);
  final TextView result=add(col,"Dokunma testi bekleniyor.",16);
  Button test=new Button(this);test.setText("Dokunma testini yap");test.setAllCaps(false);col.addView(test);
  test.setOnClickListener(v->result.setText("Dokunma testi başarılı. Sonucu geliştiriciye bildirin."));
 }
 private int dp(int n){return Math.round(n*getResources().getDisplayMetrics().density);}
 private TextView add(LinearLayout col,String s,int size){TextView t=new TextView(this);t.setText(s);t.setTextColor(Color.rgb(30,41,59));t.setTextSize(size);t.setPadding(0,dp(9),0,dp(9));col.addView(t);return t;}
}
