//package cz.mg.vulkantransformator.gui;
//
//import cz.mg.collections.text.Text;
//import cz.mg.vulkantransformator.Configuration;
//import cz.mg.vulkantransformator.Transformator;
//import cz.mg.vulkantransformator.utilities.DebugUtilities;
//import javax.swing.*;
//import java.awt.*;
//import java.io.File;
//import java.nio.file.Paths;
//
//
//public class MainWindowState {
//    private static final int WINDOW_WIDTH = 800;
//    private static final int WINDOW_HEIGHT = 600;
//
//    private final MainWindow window;
//    private final JFileChooser fileChooser = new JFileChooser(Paths.get(".").toFile());
//
//    public MainWindowState(MainWindow window) {
//        this.window = window;
//        window.setSize(WINDOW_WIDTH, WINDOW_HEIGHT);
//        window.setLocationRelativeTo(null);
//        window.jTextFieldVulkanCorePath.setText(Configuration.DEFAULT_VULKAN_CORE_PATH.toString());
//        window.jTextFieldOutputDirectory.setText(Configuration.DEFAULT_OUTPUT_DIRECTORY_PATH.toString());
//    }
//
//    public Text getVulkanCorePath(){
//        return new Text(window.jTextFieldVulkanCorePath.getText());
//    }
//
//    public Text getOutputDirectoryPath(){
//        return new Text(window.jTextFieldOutputDirectory.getText());
//    }
//
//    public void chooseVulkanCorePath(){
//        fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
//        fileChooser.setMultiSelectionEnabled(false);
//        if(fileChooser.showOpenDialog(window) == JFileChooser.APPROVE_OPTION){
//            File selectedFile = fileChooser.getSelectedFile();
//            if(selectedFile != null) window.jTextFieldVulkanCorePath.setText(selectedFile.getAbsolutePath().toString());
//        }
//    }
//
//    public void chooseOutputDirectoryPath(){
//        fileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
//        fileChooser.setMultiSelectionEnabled(false);
//        if(fileChooser.showOpenDialog(window) == JFileChooser.APPROVE_OPTION){
//            File selectedDirectory = fileChooser.getCurrentDirectory();
//            if(selectedDirectory != null) window.jTextFieldOutputDirectory.setText(selectedDirectory.getAbsolutePath().toString());
//        }
//    }
//
//    private Transformator createTransformator(){
//        return new Transformator(getVulkanCorePath(), getOutputDirectoryPath());
//    }
//
//    public void generateSelected(){
//        window.jLabelStatus.setText("Generating...");
//        SwingUtilities.invokeLater(new Runnable() {
//            @Override
//            public void run() {
//                try {
//                    Transformator transformator = createTransformator();
//                    transformator.run();
//                } catch(Exception e){
//                    showError(e);
//                    window.jLabelStatus.setText("ERROR");
//                }
//                window.jLabelStatus.setText("Files generated successfully!");
//            }
//        });
//    }
//
//    private void showError(Exception e){
//        JOptionPane.showMessageDialog(window, e.getClass().getSimpleName() + ": " + e.getMessage(), e.getClass().getSimpleName(), JOptionPane.ERROR_MESSAGE);
//        new ErrorLog(DebugUtilities.stackTraceToString(e)).setVisible(true);
//    }
//
//    public static void main(String args[]) {
//        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
//        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
//         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html
//         */
//        try {
//            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
//                if ("Nimbus".equals(info.getName())) {
//                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
//                    break;
//                }
//            }
//        } catch (ClassNotFoundException ex) {
//            java.util.logging.Logger.getLogger(MainWindow.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
//        } catch (InstantiationException ex) {
//            java.util.logging.Logger.getLogger(MainWindow.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
//        } catch (IllegalAccessException ex) {
//            java.util.logging.Logger.getLogger(MainWindow.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
//        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
//            java.util.logging.Logger.getLogger(MainWindow.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
//        }
//        //</editor-fold>
//        java.awt.EventQueue.invokeLater(new Runnable() {
//            @Override
//            public void run() {
//                new MainWindow().setVisible(true);
//            }
//        });
//    }
//}
