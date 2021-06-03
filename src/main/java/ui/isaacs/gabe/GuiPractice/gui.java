package ui.isaacs.gabe.GuiPractice;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

public class gui{

    public static void main(String args[]) throws InterruptedException {
        guiSheet gabe = new guiSheet();
        while(gabe.getNumber() == Integer.MIN_VALUE || gabe.getFile() == null) {
            Thread.sleep(1);
        }
        System.out.println(gabe.getFile().getName());
        System.out.println(gabe.getNumber());
    }

}