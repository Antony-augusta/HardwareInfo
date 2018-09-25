package cordova.plugin.hardwareinfo;

import org.apache.cordova.CordovaPlugin;
import org.apache.cordova.CallbackContext;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.io.IOException;
import java.io.InputStream;
import android.app.ActivityManager;
import android.app.ActivityManager.MemoryInfo;

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
        return false;
    }

    private void coolMethod(String message, CallbackContext callbackContext) {
        if (message != null && message.length() > 0) {
            callbackContext.success(message);
        } else {
            callbackContext.error("Expected one non-empty string argument.");
        }
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
        long totalmem = Runtime.getRuntime().maxMemory();
        callbackContext.success(totalmem);
    }
}
