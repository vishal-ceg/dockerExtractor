/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.docker.dockerextractor;

import com.github.dockerjava.api.DockerClient;

/**
 *
 * @author Vishal
 */
public abstract class DockerCmdExecutor<T> {
    
    public abstract <T extends DockerCmdExecutor> T execute(DockerClient client);
    
}
