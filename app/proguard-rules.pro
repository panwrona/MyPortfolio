# Add project specific ProGuard rules here.
# By default, the flags in this file are appended to flags specified
# in D:\Coding\Android Studio\SDK/tools/proguard/proguard-android.txt
# You can edit the include path and order by changing the proguardFiles
# directive in build.gradle.
#
# For more details, see
#   http://developer.android.com/guide/developing/tools/proguard.html

# Add any project specific keep options here:

# If your project uses WebView with JS, uncomment the following
# and specify the fully qualified class name to the JavaScript interface
# class:
#-keepclassmembers class fqcn.of.javascript.interface.for.webview {
#   public *;
#}
##---------------Begin: proguard configuration for Gson  ----------
# Adapted from https://code.google.com/p/google-gson/source/browse/trunk/examples/android-proguard-example/proguard.cfg
#
# Gson uses generic type information stored in a class file when working with fields. Proguard
# removes such information by default, so configure it to keep all of it.
-keepattributes Signature

# We use Gson's @SerializedName annotation which won't work without this:
-keepattributes *Annotation*

# Gson specific classes
#-keep class sun.misc.Unsafe { *; }
#-keep class com.google.gson.stream.** { *; }

# Classes that will be serialized/deserialized over Gson
# http://stackoverflow.com/a/7112371/56285
-keep class pl.sknerus.sknerusapp.data.entities.** { *; }

##---------------End: proguard configuration for Gson  ----------

# Remove logging calls
-assumenosideeffects class android.util.Log {
    public static boolean isLoggable(java.lang.String, int);
    public static int v(...);
    public static int i(...);
    public static int w(...);
    public static int d(...);
    public static int e(...);
}

# RETROFIT
-keep class com.squareup.** { *; }
-keep interface com.squareup.** { *; }
-dontwarn com.squareup.okhttp.**
-keep class retrofit.** { *; }

-keepclasseswithmembers class * {
    @retrofit.http.* <methods>;
}

-keep interface retrofit.** { *;}
-keep interface com.squareup.** { *; }
-dontwarn rx.**
-dontwarn retrofit.**

# RETROLAMBDA

-dontwarn java.lang.invoke.*

# BUTTERKNIFE

-keep class butterknife.** { *; }
-dontwarn butterknife.internal.**
-keep class **$$ViewBinder { *; }

-keepclasseswithmembernames class * {
    @butterknife.* <fields>;
}

-keepclasseswithmembernames class * {
    @butterknife.* <methods>;
}

# OKIO
-dontwarn okio.**

# OKHTTP
-dontwarn com.squareup.okhttp.*

