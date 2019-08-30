package utils;

@FunctionalInterface
public interface Command {
    void execute(FileAppender appender,String... args) throws Exception;
}