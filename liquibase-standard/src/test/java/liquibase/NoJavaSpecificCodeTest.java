package liquibase;

import io.github.pixee.security.BoundedLineReader;
import org.junit.Test;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;

import static org.junit.Assert.fail;

public class NoJavaSpecificCodeTest {

    @Test
    public void nothing() {
        //no test at this point
    }
    
//    @Test
//    public void checkJavaCode() throws Exception {
//        checkJavaClasses(new File(TestContext.getInstance().findStandardProjectRoot(), "src/java"));
//    }

    private void checkJavaClasses(File directory) throws Exception {
        for (File file : directory.listFiles()) {
            if (file.getName().endsWith(".java")) {
                try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
                    String line;
                    while ((line = BoundedLineReader.readLine(reader, 5_000_000)) != null) {
                        if (line.contains("java.sql")) {
                            fail(file.getCanonicalPath() + " contains java.sql");
                        }
                    }
                }
            } else if (file.isDirectory()) {
                checkJavaClasses(file);
            }
        }
    }
}
