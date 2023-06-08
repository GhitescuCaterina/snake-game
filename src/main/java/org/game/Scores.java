package org.game;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumnModel;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.sql.*;

public class Scores extends Scene {
    private MouseListener mouseListener;
    private KeyListener keyListener;
    private BufferedImage back, backPressed, backCurrentImage;
    private Rect backRect;
    private KeyboardListener keyboardListener;
    private DefaultTableModel tableModel;
    private Connection conn;
    private JTable highScoresTable;


    public Scores(MouseListener mouseListener, KeyListener keyListener) {
        this.mouseListener = mouseListener;
        this.keyListener = keyListener;

        try {
            back = ImageIO.read(new File("pictures/back.png"));
            backPressed = ImageIO.read(new File("pictures/back-pressed.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }

        try {
            conn = DriverManager.getConnection("jdbc:mysql://localhost/snake_database", "root", "Loseyourself!2");
        } catch (SQLException e) {
            e.printStackTrace();
        }

        backCurrentImage = back;
        backRect = new Rect(320, 100, 600, 200, Direction.UP);

        tableModel = new DefaultTableModel();
        highScoresTable = new JTable(tableModel);
        JScrollPane scrollPane = new JScrollPane(highScoresTable);

        // Define columns for the table
        tableModel.addColumn("Rank");
        tableModel.addColumn("Name");
        tableModel.addColumn("Score");
        tableModel.addColumn("Time Taken");

        JPanel panel = new JPanel(new GridBagLayout());  // use GridBagLayout for center alignment
        panel.add(scrollPane);


        fetchHighScores();

    }

    private void fetchHighScores() {
        try {
            String sql = "SELECT name, score, time_taken FROM player_scores ORDER BY score DESC, time_taken ASC LIMIT 10";
            PreparedStatement statement = conn.prepareStatement(sql);

            ResultSet rs = statement.executeQuery();

            tableModel.setRowCount(0);

            int rank = 1;
            while (rs.next()) {
                String name = rs.getString("name");
                int score = rs.getInt("score");
                long timeTaken = rs.getLong("time_taken");

                // Create a new row and add it to the table model.
                tableModel.addRow(new Object[]{rank, name, score, timeTaken});
                rank++;
            }

            rs.close();
            statement.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void update(double dt) {
        if (mouseListener.getX() >= backRect.x && mouseListener.getX() <= backRect.x + backRect.width &&
                mouseListener.getY() >= backRect.y && mouseListener.getY() <= backRect.y + backRect.height) {
            backCurrentImage = backPressed;
            if (mouseListener.isPressed()) {
                Window.getWindow().changeState(0);
            }
        } else {
            backCurrentImage = back;
        }
    }

    @Override
    public void draw(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;


        g.setColor(new Color(146, 151, 196));
        g.fillRect(0, 0, Constant.SCREEN_WIDTH, Constant.SCREEN_HEIGHT);

        backRect = new Rect((Constant.SCREEN_WIDTH - backRect.width) / 2, (Constant.SCREEN_HEIGHT - backRect.height) / 2 + 250, 324, 120, Direction.UP);
        g.drawImage(backCurrentImage, (int) backRect.x, (int) backRect.y, (int) backRect.width, (int) backRect.height, null);

        Color tableBgColor = new Color(255, 255, 255, 150);

        int desiredColumnWidth = 76;
        int tableWidth = desiredColumnWidth * highScoresTable.getColumnCount();
        int tableHeight = Constant.SCREEN_HEIGHT - 520;
        int tableX = (Constant.SCREEN_WIDTH - tableWidth) / 2;
        int tableY = (Constant.SCREEN_HEIGHT - tableHeight) / 2;
        int partWidth = tableWidth / (highScoresTable.getColumnCount() + 1);

        Dimension tableSize = new Dimension(tableWidth, tableHeight);
        highScoresTable.setPreferredScrollableViewportSize(tableSize);
        highScoresTable.setFillsViewportHeight(true);

        int columnWidth = tableWidth / highScoresTable.getColumnCount();
        for (int i = 0; i < highScoresTable.getColumnCount(); i++) {
            if (i == 1) {
                highScoresTable.getColumnModel().getColumn(i).setPreferredWidth(2 * partWidth);
            } else {
                highScoresTable.getColumnModel().getColumn(i).setPreferredWidth(partWidth);
            }
        }


        TableColumnModel columnModel = highScoresTable.getColumnModel();
        for (int i = 0; i < columnModel.getColumnCount(); i++) {
            columnModel.getColumn(i).setPreferredWidth(desiredColumnWidth);
        }

        int rowHeight = tableHeight / highScoresTable.getRowCount();
        highScoresTable.setRowHeight(rowHeight);

        int contentStartX = tableX + (tableWidth - highScoresTable.getColumnModel().getTotalColumnWidth()) / 2;
        int contentStartY = tableY + (tableHeight - highScoresTable.getRowHeight() * highScoresTable.getRowCount()) / 2;

        highScoresTable.setBounds(tableX, tableY, tableWidth, tableHeight);
        highScoresTable.setRowHeight(30);
        highScoresTable.getTableHeader().setFont(new Font("Century Gothic", Font.BOLD, 20));
        highScoresTable.setFont(new Font("Century Gothic", Font.PLAIN, 18));

        g2d.setColor(tableBgColor);
        g2d.setStroke(new BasicStroke(3));
        g2d.drawRect(tableX, tableY, tableWidth, tableHeight);

        g2d.translate(contentStartX, contentStartY);
        highScoresTable.paint(g2d);

        highScoresTable.paint(g2d);
    }
}