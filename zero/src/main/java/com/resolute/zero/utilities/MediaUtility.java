package com.resolute.zero.utilities;

public class MediaUtility {

    private static final String LOCAL_PATH="D:\\Projects\\programming\\backend\\resoluteApi\\zero\\media\\";
    private static final String SERVER_PATH="/media/";

    private static String getOs(){
        return System.getProperty("os.name");
    }

    private static boolean checkIfLinux(){
        return getOs().equalsIgnoreCase("linux");
    }

    public static String getPath(String fileName){
        return checkIfLinux()?SERVER_PATH+fileName:LOCAL_PATH+fileName;
    }
}
