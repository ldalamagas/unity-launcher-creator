package com.ulc.model;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.attribute.PosixFilePermission;
import java.util.HashSet;
import java.util.Set;

public class Launcher {

    public enum LauncherType {Application, Link, Directory}

    private final static String LAUNCHER_EXTENSION = ".desktop";
    private final static String DESKTOP_ENTRY = "[Desktop Entry]" + System.lineSeparator();
    private final static String VERSION = "Version=1.0" + System.lineSeparator();
    private final static String NAME = "Name=%s" + System.lineSeparator();
    private final static String DESCRIPTION = "Comment=%s" + System.lineSeparator();
    private final static String EXEC = "Exec=%s" + System.lineSeparator();
    private final static String ICON = "Icon=%s" + System.lineSeparator();
    private final static String TERMINAL = "Terminal=%s" + System.lineSeparator();
    private final static String TYPE = "Type=%s" + System.lineSeparator();

    private final static Logger logger = LoggerFactory.getLogger(Launcher.class);
    private final String name;
    private final String description;
    private final String icon;
    private final String executable;
    private final LauncherType type;
    private final boolean terminal;

    public Launcher(String name,
                    String description,
                    String icon,
                    String executable,
                    LauncherType type,
                    boolean terminal) {
        this.name = name;
        this.description = description;
        this.icon = icon;
        this.executable = executable;
        this.type = type;
        this.terminal = terminal;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public String getIcon() {
        return icon;
    }

    public String getExecutable() {
        return executable;
    }

    public LauncherType getType() {
        return type;
    }

    public boolean isTerminal() {
        return terminal;
    }

    public static void createLauncher(Launcher launcher) {

        File file = new File(launcher.getName() + LAUNCHER_EXTENSION);
        try (FileWriter writer = new FileWriter(file)) {
            writer.write(DESKTOP_ENTRY);
            writer.write(VERSION);
            writer.write(String.format(NAME, launcher.getName()));
            writer.write(String.format(DESCRIPTION, launcher.getDescription()));
            writer.write(String.format(EXEC, launcher.getExecutable()));
            writer.write(String.format(ICON, launcher.getIcon()));
            writer.write(String.format(TERMINAL, String.valueOf(launcher.isTerminal())));
            writer.write(String.format(TYPE, launcher.getType().toString()));
        } catch (IOException e) {
            logger.error("Failed to create launcher", e);
        }

        setPermissions(file);
    }

    private static void setPermissions(File file) {
        logger.debug("Setting file permissions");
        Set<PosixFilePermission> permissions = new HashSet<>();

        permissions.add(PosixFilePermission.OWNER_READ);
        permissions.add(PosixFilePermission.OWNER_WRITE);
        permissions.add(PosixFilePermission.OWNER_EXECUTE);
        permissions.add(PosixFilePermission.GROUP_READ);
        permissions.add(PosixFilePermission.GROUP_EXECUTE);
        permissions.add(PosixFilePermission.OTHERS_READ);
        permissions.add(PosixFilePermission.OTHERS_EXECUTE);

        try {
            Files.setPosixFilePermissions(file.toPath(), permissions);
        } catch (IOException e) {
            logger.error("Failed to apply file permissions", e);
        }
    }

}
