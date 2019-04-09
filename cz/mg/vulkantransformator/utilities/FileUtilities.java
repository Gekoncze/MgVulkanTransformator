package cz.mg.vulkantransformator.utilities;

import cz.mg.collections.list.chainlist.CachedChainList;
import cz.mg.collections.list.chainlist.ChainList;
import cz.mg.collections.text.Text;

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

    public static Text loadFile(Class location, String name){
        try(BufferedReader reader = new BufferedReader(new InputStreamReader(location.getResourceAsStream(name)))){
            String text = "";
            String line;
            while((line = reader.readLine()) != null) text += line + "\n";
            return new Text(text);
        } catch (Exception e) {
            throw new RuntimeException("Could not load file " + name + " at location " + location.getSimpleName() + ": " + e.getClass().getSimpleName() + ": " + e.getMessage());
        }
    }

    public static void saveFile(String path, Text content) {
        if(content == null) return;
        try(BufferedWriter bw = new BufferedWriter(new FileWriter(new File(path)))){
            bw.write(content.toString());
            if(!content.endsWith("\n")) bw.write("\n");
        } catch (Exception e){
            throw new RuntimeException("Could not save file " + path + ": " + e.getClass().getSimpleName() + ": " + e.getMessage());
        }
    }

    public static void checkIfExists(Text path) {
        try {
            if(!new File(path.toString()).exists()) throw new FileNotFoundException(path.toString());
        } catch (Exception e){
            throw new RuntimeException("Could not find file or directory " + path + ": " + e.getClass().getSimpleName() + ": " + e.getMessage());
        }
    }

    public static ChainList<Text> loadFileLines(Text path) {
        try {
            ChainList<Text> lines = new CachedChainList<>();
            try(BufferedReader reader = new BufferedReader(new FileReader(new File(path.toString())))){
                String line = "";
                while((line = reader.readLine()) != null){
                    lines.addLast(new Text(line));
                }
            }
            return lines;
        } catch (Exception e){
            throw new RuntimeException("Could not load file " + path + ": " + e.getClass().getSimpleName() + ": " + e.getMessage());
        }
    }

    public static ChainList<Text> loadFileLines(Class location, String name) {
        try {
            InputStream stream = location.getResourceAsStream(name);
            ChainList<Text> lines = new CachedChainList<>();
            try(BufferedReader reader = new BufferedReader(new InputStreamReader(stream))){
                String line = "";
                while((line = reader.readLine()) != null){
                    lines.addLast(new Text(line));
                }
            }
            return lines;
        } catch (Exception e){
            throw new RuntimeException("Could not load buildin file " + name + ": " + e.getClass().getSimpleName() + ": " + e.getMessage());
        }
    }
}
