package main.java.service;

import java.awt.Component;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;
import javax.swing.JFrame;
import main.java.controller.StartMenu;
import main.java.util.AccessingAllClassesInPackage;

public class SceneManager {
    Class<?>[] classes;
    List<Component> sceneList = new ArrayList<>();
    private JFrame frame;

    Component currentScene;

    public SceneManager(JFrame frame) {
        this.frame = frame;

        try {
            classes = AccessingAllClassesInPackage.getClasses("main.java.controller");
            Logger.getLogger(classes[0].toString());
        } catch (ClassNotFoundException | IOException ex) {
            Logger.getLogger(ex.getMessage());
        }

        // Sort scene
        try {
            quickSort(classes, 0, classes.length - 1);
        } catch (InstantiationException | IllegalAccessException | IllegalArgumentException | InvocationTargetException
                | NoSuchMethodException | SecurityException e) {
            Logger.getLogger(e.getMessage());
        }

        for (Class<?> clazz : classes) {
            Component comp;
            try {
                comp = (Component) clazz.getDeclaredConstructor().newInstance();
                sceneList.add(comp);
            } catch (InstantiationException | IllegalAccessException | IllegalArgumentException
                    | InvocationTargetException | NoSuchMethodException | SecurityException e) {
                Logger.getLogger(e.getMessage());
            }
        }

        try {
            // init stage
            StartMenu startMenu = new StartMenu();
            currentScene = startMenu;
            startMenu.requestFocus();
            frame.add(startMenu);
        } catch (IOException ex) {
            Logger.getLogger(ex.getMessage());
        }

        frame.pack();
        // Request focus for flappybird class: required class flappybird setFocusable()
        frame.setVisible(true);

    }

    public void loadScene(int index) {
        frame.remove(currentScene);
        try {
            Component comp;
            try {
                comp = (Component) classes[index].getDeclaredConstructor().newInstance();
                frame.add(comp);
                comp.requestFocus();

            } catch (InstantiationException | IllegalAccessException | IllegalArgumentException
                    | InvocationTargetException e) {
                Logger.getLogger(e.getMessage());
            }
        } catch (NoSuchMethodException | SecurityException ex) {
            Logger.getLogger(ex.getMessage());
        }
    }

    // Partition function
    static int partition(Class<?>[] arr, int low, int high) throws InstantiationException, IllegalAccessException,
            IllegalArgumentException, InvocationTargetException, NoSuchMethodException, SecurityException {

        // Choose the pivot
        Object o = arr[high].getDeclaredConstructor().newInstance();
        Method method = arr[high].getMethod("getSceneIndex");
        int pivot = (int) method.invoke(o);

        // Index of smaller element and indicates
        // the right position of pivot found so far
        int i = low - 1;

        // Traverse arr[low..high] and move all smaller
        // elements to the left side. Elements from low to
        // i are smaller after every iteration
        for (int j = low; j <= high - 1; j++) {
            Object o1 = arr[j].getDeclaredConstructor().newInstance();
            Method method1 = arr[j].getMethod("getSceneIndex");
            int sceneIndex = (int) method1.invoke(o1);

            if (sceneIndex < pivot) {
                i++;
                swap(arr, i, j);
            }
        }

        // Move pivot after smaller elements and
        // return its position
        swap(arr, i + 1, high);
        return i + 1;
    }

    // Swap function
    static void swap(Class<?>[] arr, int i, int j) {
        Class<?> temp = arr[i];
        arr[i] = arr[j];
        arr[j] = temp;
    }

    // The QuickSort function implementation
    static void quickSort(Class<?>[] arr, int low, int high) throws InstantiationException, IllegalAccessException,
            IllegalArgumentException, InvocationTargetException, NoSuchMethodException, SecurityException {
        if (low < high) {

            // pi is the partition return index of pivot
            int pi = partition(arr, low, high);

            // Recursion calls for smaller elements
            // and greater or equals elements
            quickSort(arr, low, pi - 1);
            quickSort(arr, pi + 1, high);
        }
    }
}
