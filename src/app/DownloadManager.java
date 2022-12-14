package app;

import config.Config;

import java.io.*;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class DownloadManager {
    private String path;
    private List<String> filetypeList;
    public static final char[] illegalChars = {'<', '>', '/', '\\', '|', '*', ':', '"'};

    public DownloadManager(String path) {
        this.path = path;
        filetypeList = DownloadManager.getSupportedFiletypes();
    }

    public static ArrayList<String> getSupportedFiletypes() {
        ArrayList<String> supportedFiletypes = new ArrayList<>();
        try {
            String[] properties = Config.getProperties("supportedfiletypes").split(",");
            for (String filetype : properties) {
                supportedFiletypes.add(filetype);
            }
        } catch (Exception e) {
            System.err.println("Properties file error");
        }
        return supportedFiletypes;
    }

    public void download(String linkToMedia, String filename, int imageNumber) throws IOException {
        String filetype = null;
        for (String filetypeFromList : filetypeList) {
            if (linkToMedia.contains(filetypeFromList) || filetypeFromList.equals(filetypeList.get(filetypeList.size() - 1))) {
                if (filetypeFromList.equals("webp") || (filetypeFromList.equals(filetypeList.get(filetypeList.size() - 1)) && !filetypeFromList.equals(filetypeList.get(filetypeList.size() - 1)))) {
                    filetype = "jpg";
                    break;
                } else {
                    filetype = filetypeFromList;
                    break;
                }
            }
        }

        URL url = new URL(linkToMedia.trim());
        InputStream in = new BufferedInputStream(url.openStream());
        OutputStream out = new BufferedOutputStream(new FileOutputStream(path + filename + imageNumber + "." + filetype));

        for (int i; (i = in.read()) != -1; ) {
            out.write(i);
        }
        in.close();
        out.close();
    }

    public static void download(String linkToMedia, String path, String filename, String filetype) throws IOException {
        URL url = new URL(linkToMedia.trim());
        InputStream in = new BufferedInputStream(url.openStream());
        OutputStream out = new BufferedOutputStream(new FileOutputStream(path + filename + "." + filetype));

        for (int i; (i = in.read()) != -1; ) {
            out.write(i);
        }
        in.close();
        out.close();
    }
}