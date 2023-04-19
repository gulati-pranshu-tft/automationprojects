package com.cbla.utils;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Stack;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

public class TestResultsUtil {
    public static void zip(String filepath) {
        try {
            File inFolder = new File(filepath);
            File outFolder = new File("Report.zip");
            ZipOutputStream out = new ZipOutputStream(new BufferedOutputStream(new FileOutputStream(outFolder)));
            BufferedInputStream in = null;
            byte[] data = new byte[1000];
            String files[] = inFolder.list();
            for (int i = 0; i < files.length; i++) {
                in = new BufferedInputStream(new FileInputStream
                        (inFolder.getPath() + "/" + files[i]), 1000);
                out.putNextEntry(new ZipEntry(files[i]));
                int count;
                while ((count = in.read(data, 0, 1000)) != -1) {
                    out.write(data, 0, count);
                }
                out.closeEntry();
            }
            out.flush();
            out.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void zipDir(String directory, String zipFileName) throws IOException {
        BufferedOutputStream out = new BufferedOutputStream(new FileOutputStream(zipFileName));
        File sourceFile = new File(directory);
        createZipFile(sourceFile, out, true);
        out.flush();
        out.close();
    }

    //code from http://stackoverflow.com/questions/1399126/java-util-zip-recreating-directory-structure
    private static void createZipFile(File srcDir, OutputStream out,
                                      boolean verbose) throws IOException {

        List<String> fileList = listDirectory(srcDir);
        ZipOutputStream zout = new ZipOutputStream(out);

        zout.setLevel(9);
        zout.setComment("Zipper v1.2");

        for (String fileName : fileList) {
            File file = new File(srcDir.getParent(), fileName);
            if (verbose)
                System.out.println("  adding: " + fileName);

            // Zip always use / as separator
            String zipName = fileName;
            if (File.separatorChar != '/')
                zipName = fileName.replace(File.separatorChar, '/');
            ZipEntry ze;
            if (file.isFile()) {
                ze = new ZipEntry(zipName);
                ze.setTime(file.lastModified());
                zout.putNextEntry(ze);
                FileInputStream fin = new FileInputStream(file);
                byte[] buffer = new byte[4096];
                for (int n; (n = fin.read(buffer)) > 0; )
                    zout.write(buffer, 0, n);
                fin.close();
            } else {
                ze = new ZipEntry(zipName + '/');
                ze.setTime(file.lastModified());
                zout.putNextEntry(ze);
            }
        }
        zout.close();
    }

    public static List<String> listDirectory(File directory)
            throws IOException {

        Stack<String> stack = new Stack<String>();
        List<String> list = new ArrayList<String>();

        // If it's a file, just return itself
        if (directory.isFile()) {
            if (directory.canRead())
                list.add(directory.getName());
            return list;
        }

        // Traverse the directory in width-first manner, no-recursively
        String root = directory.getParent();
        stack.push(directory.getName());
        while (!stack.empty()) {
            String current = (String) stack.pop();
            File curDir = new File(root, current);
            String[] fileList = curDir.list();
            if (fileList != null) {
                for (String entry : fileList) {
                    File f = new File(curDir, entry);
                    if (f.isFile()) {
                        if (f.canRead()) {
                            list.add(current + File.separator + entry);
                        } else {
                            System.err.println("File " + f.getPath()
                                    + " is unreadable");
                            throw new IOException("Can't read file: "
                                    + f.getPath());
                        }
                    } else if (f.isDirectory()) {
                        list.add(current + File.separator + entry);
                        stack.push(current + File.separator + f.getName());
                    } else {
                        throw new IOException("Unknown entry: " + f.getPath());
                    }
                }
            }
        }
        return list;
    }
}

