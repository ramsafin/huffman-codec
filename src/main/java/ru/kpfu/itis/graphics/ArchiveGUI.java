package ru.kpfu.itis.graphics;

import ru.kpfu.itis.huffman.Codec;

import javax.swing.*;
import java.awt.*;
import java.io.File;

public class ArchiveGUI {

    private static void createGUI() {

        JFrame.setDefaultLookAndFeelDecorated(true);

        final JFrame frame = new JFrame("CompressorV1");

        JPanel panel = new JPanel();

        panel.setLayout(null);


        final JLabel label_1 = new JLabel("Choose a file ...");
        label_1.setBounds(40, 30, 200, 30);
        panel.add(label_1);

        final JButton InputButton = new JButton("Open ...");
        InputButton.setVerticalTextPosition(AbstractButton.CENTER);
        InputButton.setHorizontalTextPosition(AbstractButton.LEADING);
        InputButton.setBounds(170, 20, 150, 50);
        panel.add(InputButton);


        final JLabel form = new JLabel("Choose file format:");
        form.setBounds(100, 110, 300, 30);
        panel.add(form);


        final JLabel fileName = new JLabel();
        fileName.setBounds(15, 90, 300, 20);
        panel.add(fileName);

        final JRadioButton radioHuf = new JRadioButton(".HUF");
        radioHuf.setBounds(90, 140, 100, 30);
        panel.add(radioHuf);

        final JRadioButton radioItis = new JRadioButton(".ITIS");
        radioItis.setBounds(200, 140, 100, 30);
        radioItis.setSelected(true);
        panel.add(radioItis);

        final JButton archive = new JButton("COMPRESS");
        archive.setVerticalTextPosition(AbstractButton.CENTER);
        archive.setBounds(70, 190, 220, 50);
        archive.setEnabled(false);
        panel.add(archive);

        final JLabel progress = new JLabel("Status: waiting ...");
        progress.setBounds(75, 250, 200, 30);
        panel.add(progress);

        final JLabel about = new JLabel("CompressorV1 ver. 0.1");
        about.setBounds(110, 320, 200, 30);
        panel.add(about);

        final JLabel rights = new JLabel("No rights reserved.");
        rights.setBounds(110, 340, 200, 30);
        panel.add(rights);


        final String[] archive_format = {".itis"}; // default format

        radioHuf.addActionListener(e -> {
            radioItis.setSelected(false);
            archive_format[0] = ".huf";
        });

        radioItis.addActionListener(e -> {
            radioHuf.setSelected(false);
            archive_format[0] = ".itis";
        });

        final File[] file = new File[1];

        InputButton.addActionListener(e -> {

            JFileChooser file_open = new JFileChooser();

            int ret = file_open.showDialog(null, "Choose a file");

            if (ret == JFileChooser.APPROVE_OPTION) {

                file[0] = file_open.getSelectedFile();

                fileName.setText(file[0].getName());


                archive.setEnabled(true);

            }
        });

        Codec codec = new Codec();

        archive.addActionListener(

                e -> {

                    progress.setText("Status : compressing ...");

                    String path = file[0].getPath();

                    path = path.substring(0, path.lastIndexOf('.'));

                    File result = new File(path + archive_format[0]);

                    String ext = file[0].getPath().substring(file[0].getPath().lastIndexOf('.'));

                    File output = new File(path + "_dec" + ext);

                    codec.compress(file[0], result);

                    codec.decompress(result, output);

                    progress.setText(" Compression's completed! ");
                });


        frame.getContentPane().add(panel);

        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        frame.setPreferredSize(new Dimension(350, 400));

        frame.setResizable(false);

        frame.pack();

        frame.setLocationRelativeTo(null);

        frame.setVisible(true);
    }

    public void start() {
        javax.swing.SwingUtilities.invokeLater(ArchiveGUI::createGUI);
    }
}
