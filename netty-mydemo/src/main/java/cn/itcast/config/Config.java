package cn.itcast.config;

import cn.itcast.protocol.Serializer;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * @ClassName Config
 * @Description TODO
 * @Author admin
 * @Date 2022/6/7 15:04
 * @Version 1.0
 **/
public class Config {
    static Properties properties;
    static {
        try {
            InputStream in = Config.class.getResourceAsStream("/application.properties");
            properties = new Properties();
            properties.load(in);
        } catch (IOException e) {
          throw new ExceptionInInitializerError(e);
        }
    }
    public static int getServerPort(){
        String value = properties.getProperty("server.port");
        if(value==null){
            return 8080;
        }else{
            return Integer.parseInt(value);
        }
    }
    public static Serializer.SerializerAlgorithm getSerializerAlgorithm(){
        String value = properties.getProperty("serializer.algorithm");
        if(value==null){
            return Serializer.SerializerAlgorithm.Java;
        }else{
            return Serializer.SerializerAlgorithm.valueOf(value);
        }
    }
}
