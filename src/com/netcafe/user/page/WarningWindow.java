package com.netcafe.user.page;

import javax.swing.*;

public class WarningWindow extends JDialog {
    public JPanel warningPanel;
    public JLabel warningLabel = new JLabel();

    public void setLabel(String a) {
        warningPanel = new JPanel();
        warningLabel.setText(a);
        warningPanel.add(warningLabel);
        this.add(warningPanel);
        this.pack();
    }
}
