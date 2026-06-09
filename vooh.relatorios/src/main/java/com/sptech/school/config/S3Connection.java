package com.sptech.school.config;

import io.github.cdimascio.dotenv.Dotenv;

public class S3Connection {

    private static final Dotenv dotenv = Dotenv.configure()
            .ignoreIfMissing()
            .load();

    private S3Connection() {}


    public static String getACCESS_KEY_ID()    { return dotenv.get("AWS_ACCESS_KEY_ID"); }
    public static String getSECRET_ACCESS_KEY() { return dotenv.get("AWS_SECRET_ACCESS_KEY"); }
    public static String getSESSION_TOKEN()     { return dotenv.get("AWS_SESSION_TOKEN"); }
    public static String getREGION()            { return dotenv.get("AWS_REGION", "us-east-1"); }
    public static String getBUCKET_NAME()       { return dotenv.get("AWS_BUCKET_NAME"); }
}
