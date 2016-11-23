package com.gplio.androidrivescript;

import android.hardware.camera2.utils.ArrayUtils;
import android.support.annotation.Nullable;
import android.util.Log;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Created by goncalopalaio on 19/11/16.
 */

public class U {
    public static final boolean DEBUG = true;

    @Nullable
    public static String parseFileExtension(String path){
        if (path == null || path.isEmpty()){
            return null;
        }
        String[] splat = path.split("\\.");
        if (splat.length >= 2){
            return splat[splat.length-1];
        }
        return null;
    }

    public static String join(String[] arr, String separator){
        StringBuilder sb = new StringBuilder();
        if (arr.length > 0) {
            sb.append(arr[0]);
            for (int i = 1; i < arr.length; i++) {
                sb.append(separator);
                sb.append(arr[i]);
            }
        }
        return sb.toString();
    }

    public static String join(Set<String> arr, String separator) {
        return join(arr.toArray(new String[]{}), separator);
    }

    public static String join(List<String> arr, String separator){
        StringBuilder sb = new StringBuilder();
        if (arr.size() > 0) {
            sb.append(arr.get(0));
            for (int i = 1; i < arr.size(); i++) {
                sb.append(separator);
                sb.append(arr.get(i));
            }
        }
        return sb.toString();
    }

    public static Set<String> arrayToSet(String[] arr) {
        Set<String> set = new HashSet<>(arr.length);
        for (String el : arr) {
            set.add(el);
        }
        return set;
    }
}
