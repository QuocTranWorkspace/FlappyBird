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

    static int a = 0;

    Component currentScene;

    private static final String START_TIMER = "setIsStart";

    public SceneManager(JFrame frame) {
        this.frame = frame;

        try {
            classes = AccessingAllClassesInPackage.getFilteredClasses("main.java.controller", "BaseScene");
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
            a++;
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
            // Init the start scene to run
            Method method;

            method = startMenu.getClass().getMethod(START_TIMER, boolean.class);
            method.invoke(startMenu, true);
            // Request focus for curent sccene: required class ... setFocusable()
            // Must request focus before add
            frame.add(startMenu);
            startMenu.requestFocus();

        } catch (IOException | NoSuchMethodException | SecurityException | IllegalAccessException
                | IllegalArgumentException
                | InvocationTargetException ex) {
            Logger.getLogger(ex.getMessage());
        }
    }

    public void loadScene(int index) {
        frame.remove(currentScene);
        /*
         * @Solution for problem noted in Base scene
         */
        Method method;
        try {
            method = currentScene.getClass().getMethod(START_TIMER, boolean.class);
            method.invoke(currentScene, false);
        } catch (NoSuchMethodException | SecurityException | IllegalAccessException | IllegalArgumentException
                | InvocationTargetException e) {
            Logger.getLogger(e.getMessage());
        }
        currentScene = null;
        Component comp;
        try {
            comp = (Component) classes[index].getDeclaredConstructor().newInstance();
            frame.add(comp);
            comp.requestFocus();

            method = comp.getClass().getMethod(START_TIMER, boolean.class);
            method.invoke(comp, true);

            // Set current scence
            currentScene = comp;
        } catch (InstantiationException | IllegalAccessException | IllegalArgumentException
                | InvocationTargetException | SecurityException | NoSuchMethodException e) {
            Logger.getLogger(e.getMessage());
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

    public static int getSceneNum() {
        return a;
    }

    public void closeApp() {
        this.frame.dispose();
    }
}
