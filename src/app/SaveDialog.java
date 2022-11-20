package app;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.io.*;

public class SaveDialog extends JDialog {
    private JPanel pnlCenter;
    private JLabel lblMode;
    private JComboBox<String> cmbMode;
    private JPanel pnlSouth;
    private JButton btnCancel;
    private JButton btnSelect;

    public SaveDialog(MainWindow mw, JTextArea taDisplayMW) {
        super(mw, true);
        super.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
        super.setLayout(new BorderLayout());
        super.setTitle("MassLinkOpener - Save");
        super.setLocation(mw.getLocation());
        super.setResizable(false);
        super.setBounds(mw.getX(), mw.getY(), 250, 125);

        // CENTER
        pnlCenter = new JPanel();
        pnlCenter.setBorder(new EmptyBorder(5, 5, 0, 5));
        FlowLayout flowCenter = new FlowLayout();
        flowCenter.setAlignment(FlowLayout.CENTER);
        pnlCenter.setLayout(flowCenter);
        lblMode = new JLabel("Mode:");
        cmbMode = new JComboBox<>();
        cmbMode.addItem("Readable (txt)");
        cmbMode.addItem("MassLinkList (mll)");
        pnlCenter.add(lblMode);
        pnlCenter.add(cmbMode);

        // SOUTH
        pnlSouth = new JPanel();
        FlowLayout flowRight = new FlowLayout();
        flowRight.setAlignment(FlowLayout.RIGHT);
        pnlSouth.setLayout(flowRight);
        pnlSouth.setBorder(new EmptyBorder(0, 5, 5, 5));
        btnCancel = new JButton("Cancel");
        btnCancel.addActionListener(e -> {
            super.setVisible(false);
        });
        btnSelect = new JButton("Select");
        btnSelect.addActionListener(e -> {
            super.setVisible(false);
            if(cmbMode.getSelectedIndex() == 0) {
                JFileChooser fc = new JFileChooser();
                fc.setCurrentDirectory(new java.io.File("."));
                fc.setDialogTitle("MassLinkOpener - Save");
                FileNameExtensionFilter filter = new FileNameExtensionFilter("Text file (.txt)", "txt");
                fc.setFileFilter(filter);
                int retVal = fc.showSaveDialog(mw);
                if (retVal == JFileChooser.APPROVE_OPTION) {
                    File file = new File(fc.getSelectedFile().getAbsolutePath() + ".txt");
                    try(BufferedWriter bw = new BufferedWriter(new FileWriter(file))) {
                        bw.write(taDisplayMW.getText());
                    } catch (IOException io) {
                        JOptionPane.showMessageDialog(mw, io.getMessage());
                    }
                }
            }
            if(cmbMode.getSelectedIndex() == 1) {
                JFileChooser fc = new JFileChooser();
                fc.setCurrentDirectory(new java.io.File("."));
                fc.setDialogTitle("MassLinkOpener - Save");
                FileNameExtensionFilter filter = new FileNameExtensionFilter("MassLinkList (.mll)", "mll");
                fc.setFileFilter(filter);
                int retVal = fc.showSaveDialog(mw);
                if (retVal == JFileChooser.APPROVE_OPTION) {
                    File file = new File(fc.getSelectedFile().getAbsolutePath() + ".mll");
                    try(ObjectOutputStream o_out = new ObjectOutputStream(new FileOutputStream(file))) {
                        HashObject inObj = new HashObject(taDisplayMW.getText());
                        o_out.writeObject(inObj);
                    } catch (FileNotFoundException fnf) {
                        JOptionPane.showMessageDialog(mw, fnf.getMessage());
                    } catch (IOException io) {
                        JOptionPane.showMessageDialog(mw, io.getMessage());
                    }
                }
            }
        });
        pnlSouth.add(btnSelect);
        pnlSouth.add(btnCancel);

        super.getContentPane().add(pnlCenter, BorderLayout.CENTER);
        super.getContentPane().add(pnlSouth, BorderLayout.SOUTH);
        super.setVisible(true);
    }

}
