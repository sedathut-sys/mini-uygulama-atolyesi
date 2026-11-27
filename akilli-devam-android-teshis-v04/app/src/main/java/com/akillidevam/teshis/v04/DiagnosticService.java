package com.akillidevam.teshis.v04;
import android.accessibilityservice.AccessibilityService;
import android.content.Intent;
import android.view.accessibility.AccessibilityEvent;
public final class DiagnosticService extends AccessibilityService {
 static volatile boolean connected;
 @Override protected void onServiceConnected(){super.onServiceConnected();connected=true;}
 @Override public void onAccessibilityEvent(AccessibilityEvent event){}
 @Override public void onInterrupt(){}
 @Override public boolean onUnbind(Intent intent){connected=false;return super.onUnbind(intent);}
 @Override public void onDestroy(){connected=false;super.onDestroy();}
}
