package ru.fizteh.fivt.students.ermolenko.shell;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class Mv implements Command<ShellState> {

    public String getName() {

        return "mv";
    }

    private void move(Path source, Path target) throws IOException {

        if (source.toFile().isFile()) {
            Files.copy(source, target);
        } else {
            File[] masOfSource = source.toFile().listFiles();
            target.toFile().mkdir();
            for (File sourceEntry : masOfSource != null ? masOfSource : new File[0]) {
                move(sourceEntry.toPath(), target);
            }
        }
        source.toFile().delete();
    }

    public void executeCmd(ShellState inState, String[] args) throws IOException {

        if (2 == args.length) {
            Path source = inState.getPath().resolve(args[0]).normalize();
            Path target = inState.getPath().resolve(args[1]).normalize();
            if (source.toFile().isFile() && target.toFile().isFile()) {
                throw new IOException("not allowed to move file to file");
            }
            if (source.equals(target)) {
                throw new IOException("not allowed to move file on itself");
            }
            if (!source.toFile().exists()) {
                throw new IOException("source file doesn't exist");
            }
            if (source.toFile().isDirectory() && target.toFile().isFile()) {
                throw new IOException("not allowed to move directory in file");
            }
            if (source.toFile().isDirectory() && !target.toFile().exists()) {
                throw new IOException("target directory doesn't exist");
            }
            if (source.toFile().isDirectory() && target.toFile().isDirectory() && target.startsWith(source)) {
                throw new IOException("not allowed to move parent directory in kid's directory");
            }

            if (source.toFile().isFile() && !target.toFile().exists()) {
                Files.copy(source, target);
                source.toFile().delete();
            } else
            if (source.toFile().isFile() && target.toFile().isDirectory()) {
                Files.copy(source, target.resolve(source.getFileName()));
                source.toFile().delete();
            } else
            if (source.toFile().isDirectory() && target.toFile().isDirectory()) {
                File[] masOfSource = source.toFile().listFiles();
                target.toFile().mkdir();
                if (masOfSource != null) {
                    for (File sourceEntry : masOfSource) {
                        move(sourceEntry.toPath(), target);
                    }
                }
            }
        } else {
            throw new IOException("not allowed number of arguments");
        }
        Path path = inState.getPath();
        while (!path.toFile().isDirectory()) {
            path = path.getParent();
        }
        inState.setPath(path);
    }
}
