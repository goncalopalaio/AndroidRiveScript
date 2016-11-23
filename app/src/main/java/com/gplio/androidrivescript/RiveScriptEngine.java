package com.gplio.androidrivescript;

import android.content.Context;
import android.content.res.AssetManager;
import android.util.Log;

import com.rivescript.RiveScript;
import com.rivescript.Util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.ref.WeakReference;
import java.util.Set;
import java.util.Vector;

import static android.security.KeyStore.getApplicationContext;

/**
 * Created by goncalopalaio on 23/11/16.
 */

public class RiveScriptEngine extends RiveScript {
    WeakReference<Context> context;

    public RiveScriptEngine(Context context) {
        super(U.DEBUG);
        this.context = new WeakReference<Context>(context);
    }

    public static RiveScriptEngine startEngine(String assetsDirectory, String extension){
        RiveScriptEngine rive = new RiveScriptEngine(getApplicationContext());
        rive.loadDirectory(assetsDirectory,new String[]{extension});
        rive.sortReplies();
        return rive;
    }

    @Override
    public boolean loadDirectory(String path, String[] exts) {
        AssetManager assetManager = getAssetManager();
        Set<String> extensionSet = U.arrayToSet(exts);
        log("Loading directory files with the following extensions " + U.join(extensionSet, ", "));
        if (assetManager != null) {
            try {
                log("Files in path: " + U.join(assetManager.list(path),","));
                for (String filename : assetManager.list(path)) {
                    String extension = U.parseFileExtension(filename);
                    log("Checking " + filename + " with extension " + extension);
                    if (extensionSet.contains(extension)) {
                        String filepath = path + "/" + filename;
                        loadFile(filepath);
                    }
                }
            } catch (IOException e) {
                log("Exception while loading directory " + path + e.toString());
            }
        }
        return true;
    }

    public String reply(String question){
        return reply("MASTER_USER", question);
    }

    @Override
    public boolean loadFile(String file) {
        log("Loading file " + file);
        AssetManager assetManager = getAssetManager();
        if (assetManager != null) {
            try {
                InputStream fileStream = assetManager.open(file);
                InputStreamReader streamReader = new InputStreamReader(fileStream);
                BufferedReader reader = new BufferedReader(streamReader);

                Vector<String> lines = new Vector<>();
                String line;
                while ((line = reader.readLine()) != null) {
                    lines.add(line);
                }

                reader.close();
                String[] code = Util.Sv2s(lines);
                log("Loaded " + lines.size() + " lines");
                return parse(file, code);
            } catch (IOException ex) {
                log("Failed to read " + file + " : " + ex.toString());
                return false;
            }
        }
        return true;
    }

    private AssetManager getAssetManager() {
        Context ctx = context.get();
        if (ctx != null) {
            return ctx.getAssets();
        }
        return null;
    }

    private void log(String text) {
        Log.d(getTag(), text);
    }

    @Override
    protected void cry(String text, String file, int line) {
        log(text + " - " + file + " - " + line);
    }

    @Override
    protected void cry(String line) {
        log(line);
    }

    @Override
    protected void say(String line) {
        log(line);
    }

    @Override
    protected void println(String line) {
        log(line);
    }

    @Override
    protected void trace(IOException ex) {
        log(ex.toString());
    }

    private String getTag() {
        return getClass().getCanonicalName();
    }
}
