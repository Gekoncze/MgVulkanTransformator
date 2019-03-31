package cz.mg.vulkantransformator.utilities;

import cz.mg.collections.list.chainlist.CachedChainList;
import cz.mg.collections.list.chainlist.ChainList;
import java.io.*;
import java.nio.file.Files;


public class FileUtilities {
    public static void deleteDirectory(String path) {
        try {
            File file = new File(path);
            File[] files = file.listFiles();
            if (files != null) {
                for (File f : files) {
                    if (!Files.isSymbolicLink(f.toPath())) {
                        deleteDirectory(f.getAbsolutePath());
                    }
                }
            }
            file.delete();
        } catch (Exception e){
            throw new RuntimeException("Could not delete directory " + path + ": " + e.getClass().getSimpleName() + ": " + e.getMessage());
        }
    }

    public static void createDirectory(String path){
        try {
            new File(path).mkdirs();
        } catch (Exception e){
            throw new RuntimeException("Could not create directory " + path + ": " + e.getClass().getSimpleName() + ": " + e.getMessage());
        }
    }

    public static String loadFile(Class location, String name){
        try(BufferedReader reader = new BufferedReader(new InputStreamReader(location.getResourceAsStream(name)))){
            String text = "";
            String line;
            while((line = reader.readLine()) != null) text += line + "\n";
            return text;
        } catch (Exception e) {
            throw new RuntimeException("Could not load file " + name + " at location " + location.getSimpleName() + ": " + e.getClass().getSimpleName() + ": " + e.getMessage());
        }
    }

    public static void saveFile(String path, String content) {
        if(content == null) return;
        try(BufferedWriter bw = new BufferedWriter(new FileWriter(new File(path)))){
            bw.write(content);
            if(!content.endsWith("\n")) bw.write("\n");
        } catch (Exception e){
            throw new RuntimeException("Could not save file " + path + ": " + e.getClass().getSimpleName() + ": " + e.getMessage());
        }
    }

    public static void checkIfExists(String path) {
        try {
            if(!new File(path).exists()) throw new FileNotFoundException(path);
        } catch (Exception e){
            throw new RuntimeException("Could not find file or directory " + path + ": " + e.getClass().getSimpleName() + ": " + e.getMessage());
        }
    }

    public static ChainList<String> loadFileLines(String path) {
        try {
            ChainList<String> lines = new CachedChainList<>();
            try(BufferedReader reader = new BufferedReader(new FileReader(new File(path)))){
                String line = "";
                while((line = reader.readLine()) != null){
                    lines.addLast(line);
                }
            }
            return lines;
        } catch (Exception e){
            throw new RuntimeException("Could not load file " + path + ": " + e.getClass().getSimpleName() + ": " + e.getMessage());
        }
    }

    public static ChainList<String> loadFileLines(Class location, String name) {
        try {
            InputStream stream = location.getResourceAsStream(name);
            ChainList<String> lines = new CachedChainList<>();
            try(BufferedReader reader = new BufferedReader(new InputStreamReader(stream))){
                String line = "";
                while((line = reader.readLine()) != null){
                    lines.addLast(line);
                }
            }
            return lines;
        } catch (Exception e){
            throw new RuntimeException("Could not load buildin file " + name + ": " + e.getClass().getSimpleName() + ": " + e.getMessage());
        }
    }
}
