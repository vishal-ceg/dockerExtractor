/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.docker.dockerextractor;

import com.github.dockerjava.api.DockerClient;
import com.github.dockerjava.api.command.InspectImageCmd;
import com.github.dockerjava.api.command.InspectImageResponse;
import com.github.dockerjava.api.model.Container;
import com.github.dockerjava.core.DockerClientBuilder;
import java.io.File;
import java.io.InputStream;
import java.util.List;
import org.apache.commons.lang.SystemUtils;

/**
 *
 * @author Vishal
 */
public class App {
    
    
    public static void main(String[] args) throws Exception{
        System.out.println("Starting...........");
        if(args.length != 1){
            System.out.println("Invalid input. Please provide image name as arg");
            System.exit(0);
        }
        
        ImageExtractor imageExtractor = new ImageExtractor(args[0]);
        imageExtractor.execute(DockerClientConfig.getClient());
        
        System.out.println("Completed!!!!!!");
        
    }
    
}
