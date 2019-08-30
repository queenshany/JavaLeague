package utils;

public class FileAppender implements Comparable<FileAppender>{

    private static int arc = 0;
    public final int id = ++arc;
    private boolean isUsed = false;

    private StringBuilder builder = new StringBuilder();

    public void write(String str){
        builder.append(str).append("\n");
        isUsed = true;
    }

    public boolean isUsed() {
        return isUsed;
    }

    public void flush(){
        MyFileLogWriter.writeToFile(builder.toString());
    }

    @Override
    public int compareTo(FileAppender o) {
        return Integer.compare(id, o.id);
    }
}
