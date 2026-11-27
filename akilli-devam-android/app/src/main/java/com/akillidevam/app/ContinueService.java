package com.akillidevam.app;
import android.accessibilityservice.AccessibilityService;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.SystemClock;
import android.view.accessibility.AccessibilityEvent;
import android.view.accessibility.AccessibilityNodeInfo;
import java.util.*;
public class ContinueService extends AccessibilityService {
 static boolean connected;
 private final Handler handler=new Handler(Looper.getMainLooper());
 private Decision current;
 private boolean entered,pending;
 private long pendingAt;
 private static final String MESSAGE="Devam et";
 private static final Set<String> CONT=labels("continue generating","continue response","continue","yanıt oluşturmaya devam et","yanıta devam et","devam et","devam");
 private static final Set<String> BUSY=labels("stop generating","stop response","stop streaming","stop","yanıtı durdur","oluşturmayı durdur","durdur");
 private static final Set<String> SEND=labels("send","send message","gönder","mesaj gönder");
 private static final Set<String> BLOCK=labels("retry","try again","tekrar dene","yeniden dene","upgrade","yükselt","usage limit reached","message limit reached","kullanım sınırına ulaştınız","mesaj sınırına ulaştınız");
 private final Runnable loop=new Runnable(){public void run(){try{tick();}catch(RuntimeException e){Session.stop("Arayüz hatası; güvenlik için durduruldu");}finally{handler.postDelayed(this,1000);}}};
 @Override protected void onServiceConnected(){connected=true;handler.removeCallbacks(loop);handler.post(loop);}
 @Override public void onAccessibilityEvent(AccessibilityEvent e){}
 @Override public void onInterrupt(){Session.stop("Erişilebilirlik kesildi");}
 @Override public void onDestroy(){connected=false;Session.stop("Erişilebilirlik kapatıldı");handler.removeCallbacks(loop);super.onDestroy();}
 private void tick(){
 if(!Session.active||Session.decision==null)return;
 long now=SystemClock.elapsedRealtime();
 if(current!=Session.decision){current=Session.decision;entered=false;pending=false;}
 if(now-Session.started>3600000){Session.stop("Bir saatlik oturum sınırı");return;}
 AccessibilityNodeInfo root=getRootInActiveWindow();
 if(root==null||!Session.TARGET.contentEquals(root.getPackageName()==null?"":root.getPackageName())){if(entered||now-Session.started>15000)Session.stop("ChatGPT ön planda değil");return;}
 entered=true;Snapshot s=new Snapshot();scan(root,s,0);
 if(s.truncated||s.blocked||s.cont.size()>1||s.send.size()>1||s.edit.size()>1){Session.stop("Uyarı veya belirsiz arayüz; durduruldu");return;}
 if(s.edit.size()!=1){Session.stop("Mesaj alanı güvenle tanınamadı");return;}
 AccessibilityNodeInfo edit=s.edit.get(0);CharSequence draft=edit.getText();
 boolean empty=draft==null||draft.length()==0||edit.isShowingHintText();
 if(pending){
 if(s.busy||draft==null||!MESSAGE.contentEquals(draft)){Session.stop("Mesaj alanı değişti; durduruldu");return;}
 if(now-pendingAt>5000){Session.stop("Gönder düğmesi doğrulanamadı; taslağı kontrol edin");return;}
 if(s.send.size()==1&&s.send.get(0).isEnabled()){if(!s.send.get(0).performAction(AccessibilityNodeInfo.ACTION_CLICK)){Session.stop("Gönderme başarısız; tekrar denenmedi");return;}pending=false;current.accepted(now);Session.status="Devam mesajı gönderildi";}return;
 }
 if(!empty){Session.stop("Mesaj kutusunda taslak var; durduruldu");return;}
 switch(current.evaluate(now,s.busy,s.cont.size()==1,empty,false)){
 case STOP:Session.stop("İşlem sınırı veya yanıt başlangıcı doğrulanamadı");break;
 case WAIT:Session.status=s.busy?"ChatGPT yanıt üretiyor":"Arayüz izleniyor";break;
 case CLICK:if(s.cont.get(0).performAction(AccessibilityNodeInfo.ACTION_CLICK)){current.accepted(now);Session.status="Devam düğmesine basıldı";}else Session.stop("Düğmeye basılamadı; tekrar denenmedi");break;
 case WRITE:Bundle b=new Bundle();b.putCharSequence(AccessibilityNodeInfo.ACTION_ARGUMENT_SET_TEXT_CHARSEQUENCE,MESSAGE);if(edit.performAction(AccessibilityNodeInfo.ACTION_SET_TEXT,b)){pending=true;pendingAt=now;Session.status="Gönder düğmesi doğrulanıyor";}else Session.stop("Mesaj yazılamadı");break;
 }
 }
 private static final class Snapshot{final ArrayList<AccessibilityNodeInfo> cont=new ArrayList<>(),send=new ArrayList<>(),edit=new ArrayList<>();boolean busy,blocked,truncated;int seen;}
 private static Set<String> labels(String...v){return new HashSet<>(Arrays.asList(v));}
 private static String norm(CharSequence s){return s==null?"":s.toString().trim().toLowerCase(Locale.ROOT).replace('ı','i').replace('İ','i').replace("i\u0307","i");}
 private static boolean match(Set<String> labels,CharSequence a,CharSequence b){String x=norm(a),y=norm(b);for(String label:labels){String n=norm(label);if(n.equals(x)||n.equals(y))return true;}return false;}
 private static void scan(AccessibilityNodeInfo n,Snapshot s,int depth){
 if(n==null)return;if(depth>70||++s.seen>1600){s.truncated=true;return;}
 if(n.isVisibleToUser()&&Session.TARGET.contentEquals(n.getPackageName()==null?"":n.getPackageName())){
 if(n.isEditable()&&n.isEnabled()&&!n.isPassword())s.edit.add(n);
 CharSequence a=n.getContentDescription(),b=n.getText();
 if(match(BLOCK,a,b))s.blocked=true;
 if(n.isClickable()){if(match(BUSY,a,b))s.busy=true;if(n.isEnabled()&&match(CONT,a,b))s.cont.add(n);if(n.isEnabled()&&match(SEND,a,b))s.send.add(n);}
 }
 for(int i=0;i<n.getChildCount()&&!s.truncated;i++)scan(n.getChild(i),s,depth+1);
 }
}
