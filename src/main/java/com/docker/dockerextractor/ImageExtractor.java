/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.docker.dockerextractor;

import com.github.dockerjava.api.DockerClient;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import org.apache.commons.compress.archivers.ArchiveEntry;
import org.apache.commons.compress.archivers.ar.ArArchiveEntry;
import org.apache.commons.compress.archivers.tar.TarArchiveEntry;
import org.apache.commons.compress.archivers.tar.TarArchiveInputStream;
import org.apache.commons.compress.utils.IOUtils;

/**
 *
 * @author Vishal
 */
public class ImageExtractor extends DockerCmdExecutor<ImageExtractor> {

    private final String imageName;

    public ImageExtractor(String imageName) {
        this.imageName = imageName;
    }

    @Override
    public ImageExtractor execute(DockerClient client) {
        List<File> untaredFiles = extractImage(client);
        // we can extract tar which are inside the image
        return this;
    }

    private List<File> extractImage(DockerClient client) {
        List<File> outFiles = new ArrayList<>();
        InputStream io = client.saveImageCmd(imageName).exec();
        try {
            TarArchiveInputStream myTarFile = new TarArchiveInputStream(io);

            File destFile = getDistinationFile(imageName);
            TarArchiveEntry tarEntry = null;
            while ((tarEntry = myTarFile.getNextTarEntry()) != null) {
                File outputFile = new File(destFile + File.separator + tarEntry.getName());
                if (tarEntry.isDirectory()) {
                    System.out.println("outputFile Directory ---- "
                            + outputFile.getAbsolutePath());
                    if (!outputFile.exists()) {
                        outputFile.mkdirs();
                    }
                } else {
                    System.out.println("outputFile File ---- " + outputFile.getAbsolutePath());
                    outputFile.getParentFile().mkdirs();
                    FileOutputStream fos = new FileOutputStream(outputFile);
                    IOUtils.copy(myTarFile, fos);
                    fos.close();
                    outFiles.add(outputFile);
                }
            }
            myTarFile.close();
        } catch (IOException e) {
            System.err.println("Error: " + e);
        }
        return outFiles;
    }

    private static File getDistinationFile(String folder) {
        File destFile = new File("target" +File.separator+ folder);
        if (!destFile.exists()) {
            System.out.println("Adding folder -------" + destFile.getAbsolutePath());
            destFile.mkdir();
        } 
        return destFile;
    }

}
