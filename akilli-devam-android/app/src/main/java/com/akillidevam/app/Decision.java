package com.akillidevam.app;
final class Decision {
 enum Action { WAIT, CLICK, WRITE, STOP }
 private final int max;
 private final long delay;
 private final boolean fallback;
 private int count;
 private boolean sawBusy, awaitingBusy;
 private long idleSince = -1, actionAt = -1;
 Decision(int max, long delay, boolean fallback) { this.max=max; this.delay=delay; this.fallback=fallback; }
 int count(){return count;}
 Action evaluate(long now, boolean busy, boolean cont, boolean composerEmpty, boolean blocked) {
 if(blocked || count>=max) return Action.STOP;
 if(busy){sawBusy=true; awaitingBusy=false; idleSince=-1; return Action.WAIT;}
 if(awaitingBusy)return now-actionAt>=60000?Action.STOP:Action.WAIT;
 if(idleSince<0)idleSince=now;
 if(now-idleSince<delay)return Action.WAIT;
 if(cont&&composerEmpty)return Action.CLICK;
 if(fallback&&sawBusy&&composerEmpty)return Action.WRITE;
 return Action.WAIT;
 }
 void accepted(long now){count++;sawBusy=false;awaitingBusy=true;actionAt=now;idleSince=-1;}
}
