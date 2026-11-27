package com.akillidevam.app;
final class Session {
 static final String TARGET = "com.openai.chatgpt";
 static boolean active = false;
 static long started = 0;
 static String status = "Hazır";
 static Decision decision;
 static void stop(String reason) { active = false; status = reason; }
}
