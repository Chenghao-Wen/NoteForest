package com.noteforest.utils;

import com.aliyun.oss.ClientException;
import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClientBuilder;
import com.aliyun.oss.OSSException;
import com.aliyun.oss.model.PutObjectRequest;

import java.io.InputStream;

public class AliOssUtil {
    /**
     * Upload files to Aliyun OSS
     * @param objectName
     * @param in
     * @return
     * @throws Exception
     */
    public static String upload(String objectName, InputStream in) throws Exception {
        // Endpoint example for East China 1 (Hangzhou), please fill in other regions as per actual situation.
        String endpoint = "https://oss-cn-beijing.aliyuncs.com";
        // Obtain access credentials from environment variables. Before running this code example, ensure that the environment variables OSS_ACCESS_KEY_ID and OSS_ACCESS_KEY_SECRET are set.
        // EnvironmentVariableCredentialsProvider credentialsProvider = CredentialsProviderFactory.newEnvironmentVariableCredentialsProvider();
        String KEY_ID = "";
        String KEY_SECRET = "";
        // Fill in the Bucket name, e.g., examplebucket.
        String bucketName = "big-event-";
        // Fill in the complete path of the Object, which cannot contain the Bucket name, e.g., exampledir/exampleobject.txt.

        // Create an OSSClient instance.
        OSS ossClient = new OSSClientBuilder().build(endpoint, KEY_ID, KEY_SECRET);
        String url = "https://";
        try {
            // Fill in the string.
            String content = "Hello OSS, Hello World";

            // Create a PutObjectRequest object.
            PutObjectRequest putObjectRequest = new PutObjectRequest(bucketName, objectName, in);

            // If you need to set storage type and access permissions during upload, refer to the following example code.
            // ObjectMetadata metadata = new ObjectMetadata();
            // metadata.setHeader(OSSHeaders.OSS_STORAGE_CLASS, StorageClass.Standard.toString());
            // metadata.setObjectAcl(CannedAccessControlList.Private);
            // putObjectRequest.setMetadata(metadata);

            // Upload the string.
            ossClient.putObject(putObjectRequest);
            // Indicate successful upload, concatenate the string
            url += objectName;
        } catch (OSSException oe) {
            System.out.println("Caught an OSSException, which means your request made it to OSS, "
                    + "but was rejected with an error response for some reason.");
            System.out.println("Error Message:" + oe.getErrorMessage());
            System.out.println("Error Code:" + oe.getErrorCode());
            System.out.println("Request ID:" + oe.getRequestId());
            System.out.println("Host ID:" + oe.getHostId());
        } catch (ClientException ce) {
            System.out.println("Caught an ClientException, which means the client encountered "
                    + "a serious internal problem while trying to communicate with OSS, "
                    + "such as not being able to access the network.");
            System.out.println("Error Message:" + ce.getMessage());
        } finally {
            if (ossClient != null) {
                ossClient.shutdown();
            }
        }
        return url;
    }
}
