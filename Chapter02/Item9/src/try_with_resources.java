import java.io.*;

public class try_with_resources {
    static String firstLineOfFile(String path) throws IOException {
        try (Bufferedreader br = new BufferedReader(
                new Filereader(path))) {
            return br.readLine();
        }
    }

    static void copy(String src, String dst) throws IOException {
        try (InputStream in = new FileInputStream(src);
             OutputStream out = new FileOutputStream(dst)) {
            byte[] buf = new byte[BUFFER_SIZE];
            int n;
            while ((n=in.read(buf))>=0)
                out.write(buf, 0, n);
        }
    }
}
