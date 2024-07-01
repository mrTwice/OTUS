package ru.otus.basic.yampolskiy;

import org.apache.commons.exec.CommandLine;
import org.apache.commons.exec.DefaultExecutor;
import org.apache.commons.exec.ExecuteException;
import org.apache.commons.exec.PumpStreamHandler;

import java.io.File;
import java.io.IOException;

public class DockerComposeLauncher {

    public static void start() {
        CommandLine cmd = new CommandLine("docker-compose");
        cmd.addArgument("-f");
        cmd.addArgument("homework_17/docker-compose.yml");
        cmd.addArgument("up");
        cmd.addArgument("-d");

        DefaultExecutor executor = new DefaultExecutor();
        executor.setWorkingDirectory(new File(System.getProperty("user.dir")));

        executor.setStreamHandler(new PumpStreamHandler(System.out, System.err, System.in));

        try {
            int exitValue = executor.execute(cmd);
            System.out.println("Exited with code: " + exitValue);
        } catch (ExecuteException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
