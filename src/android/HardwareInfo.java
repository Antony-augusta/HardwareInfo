package cordova.plugin.hardwareinfo;

import org.apache.cordova.CordovaPlugin;
import org.apache.cordova.CallbackContext;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.io.IOException;
import java.io.InputStream;
import android.os.Bundle;
import android.os.StatFs;
import android.os.Environment;
import android.app.Activity;
import android.app.ActivityManager;
import android.app.ActivityManager.MemoryInfo;
import java.io.File;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import android.os.Build;

/**
 * This class echoes a string called from JavaScript.
 */
public class HardwareInfo extends CordovaPlugin {

    
    @Override
    public boolean execute(String action, JSONArray args, CallbackContext callbackContext) throws JSONException {
    
        if (action.equals("coolMethod")) {
            String message = args.getString(0);
            this.coolMethod(message, callbackContext);
            return true;
        }
        else if (action.equals("CPUInfo")) {
            String message = args.getString(0);
            this.CPUInfo(message, callbackContext);
            return true;
        }
        else if (action.equals("RAMInfo")) {
            String message = args.getString(0);
            this.RAMInfo(message, callbackContext);
            return true;
        }
        else if (action.equals("DeviceInfo")) {
            String message = args.getString(0);
            this.DeviceInfo(message, callbackContext);
            return true;
        }
        return false;
    }

    private void coolMethod(String message, CallbackContext callbackContext) {
        if (message != null && message.length() > 0) {
            callbackContext.success(message);
        } else {
            callbackContext.error("Expected one non-empty string argument.");
        }
    }
    private void DeviceInfo(String message, CallbackContext callbackContext){
        String output;
        try{
            JSONObject jsonobj = new JSONObject();
            jsonobj.put("SERIAL", "" + Build.SERIAL);
            jsonobj.put("MODEL", "" + Build.MODEL);
            jsonobj.put("ID", "" + Build.ID);
            jsonobj.put("Manufacture", "" + Build.MANUFACTURER);
            jsonobj.put("brand", "" + Build.BRAND);
            jsonobj.put("type", "" + Build.TYPE);
            jsonobj.put("HARDWARE", "" + Build.HARDWARE);
            jsonobj.put("Boot", "" + Build.BOOTLOADER);

            output = jsonobj.toString();
        }
        catch(Exception ex){
            output = ex+"";
        }
        callbackContext.success(output);
    }
    private void CPUInfo(String message, CallbackContext callbackContext) {
        ProcessBuilder processBuilder;
        String Holder = "";
        String[] DATA = { "/system/bin/cat", "/proc/cpuinfo" };
        InputStream inputStream;
        Process process;
        byte[] byteArry;

        if (message != null && message.length() > 0) {
            byteArry = new byte[1024];
            try{
                processBuilder = new ProcessBuilder(DATA);

                process = processBuilder.start();

                inputStream = process.getInputStream();

                while (inputStream.read(byteArry) != -1) {

                    Holder = Holder + new String(byteArry);
                }

                inputStream.close();

            }
            catch (IOException ex) {
                callbackContext.error("Error . "+ ex);
            }
            callbackContext.success(Holder);
        } else {
            callbackContext.error("Expected one non-empty string argument.");
        }
    }
    private void RAMInfo(String message, CallbackContext callbackContext){
        long totalExternalValue = getTotalExternalMemorySize();
        long freeExternalValue = getAvailableExternalMemorySize();
        long totalInternalValue = getTotalInternalMemorySize();
        long freeInternalValue = getAvailableInternalMemorySize();
        long usedExternalValue = totalExternalValue - freeExternalValue;
        String output;
        try{
            JSONObject jsonobj = new JSONObject();
            jsonobj.put("totalExternalValue", ""+totalExternalValue);
            jsonobj.put("freeExternalValue", ""+freeExternalValue);
            jsonobj.put("totalInternalValue", "" + totalInternalValue);
            jsonobj.put("freeInternalValue", "" + freeInternalValue);
            output = jsonobj.toString();
        }
        catch(Exception ex){
            output = ex +"";
        }
        callbackContext.success(output);
    }
    
    public static boolean externalMemoryAvailable() {
        return android.os.Environment.getExternalStorageState().equals(android.os.Environment.MEDIA_MOUNTED);
    }

    public static long getAvailableInternalMemorySize() {
        File path = Environment.getDataDirectory();
        StatFs stat = new StatFs(path.getPath());
        long blockSize = stat.getBlockSize();
        long availableBlocks = stat.getAvailableBlocks();
        return availableBlocks * blockSize;
    }

    public static long getTotalInternalMemorySize() {
        File path = Environment.getDataDirectory();
        StatFs stat = new StatFs(path.getPath());
        long blockSize = stat.getBlockSize();
        long totalBlocks = stat.getBlockCount();
        return totalBlocks * blockSize;
    }

    public static long getAvailableExternalMemorySize() {
        if (externalMemoryAvailable()) {
            File path = Environment.getExternalStorageDirectory();
            StatFs stat = new StatFs(path.getPath());
            long blockSize = stat.getBlockSize();
            long availableBlocks = stat.getAvailableBlocks();
            return availableBlocks * blockSize;
        } else {
            return 0;
        }
    }

    public static long getTotalExternalMemorySize() {
        if (externalMemoryAvailable()) {
            File path = Environment.getExternalStorageDirectory();
            StatFs stat = new StatFs(path.getPath());
            long blockSize = stat.getBlockSize();
            long totalBlocks = stat.getBlockCount();
            return totalBlocks * blockSize;
        } else {
            return 0;
        }
    }
}
