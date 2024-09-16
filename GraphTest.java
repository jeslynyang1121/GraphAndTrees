package Project_4.src;

import org.junit.jupiter.api.*;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;

@TestMethodOrder(MethodOrderer.DisplayName.class)
public class GraphTest {
    private static final String DATA_DIRPATH = "/Users/jeslynyang/Desktop/cs/CS251/Lab/src/Project_4/";

    @Test
    @DisplayName("1. TreeCheck tests")
    public void testTreeCheck() throws IOException {
        runTestCase(1, "treeCheck", tokens -> {
            return Boolean.parseBoolean(tokens.get(0));
        }, inputPath -> {
            ListGraph G = ListGraph.read(inputPath);
            return UndirectedCheck.treeCheck(G);
        }, Assertions::assertEquals);
    }

    @Test
    @DisplayName("2. CountTriangles tests")
    public void testCountTriangles() throws IOException {
        runTestCase(1, "countTriangles", tokens -> {
            return Integer.parseInt(tokens.get(0));
        }, inputPath -> {
            MatrixGraph G = MatrixGraph.read(inputPath);
            return UndirectedCheck.countTriangles(G);
        }, Assertions::assertEquals);
    }

    @Test
    @DisplayName("3. VertexClusterCoeff tests")
    public void testVertexClusterCoeff() throws IOException {
        runTestCase(1, "vertexClusterCoeff", tokens -> {
            double[] result = new double[tokens.size()];
            for (int i = 0; i < tokens.size(); i++) {
                result[i] = Double.parseDouble(tokens.get(i));
            }
            return result;
        }, inputPath -> {
            ListGraph G = ListGraph.read(inputPath);
            int n = readGraphSize(inputPath);
            double[] result = new double[n];
            for (int v = 0; v < n; v++) {
                result[v] = UndirectedCheck.vertexClusterCoeff(G, v);
            }
            return result;
        }, (expected, actual) -> {
            Assertions.assertArrayEquals((double[]) expected, (double[]) actual, 1e-4);
        });
    }

    @Test
    @DisplayName("4. GraphClusterCoeff tests")
    public void testGraphClusterCoeff() throws IOException {
        runTestCase(1, "graphClusterCoeff", tokens -> {
            return Double.parseDouble(tokens.get(0));
        }, inputPath -> {
            ListGraph G = ListGraph.read(inputPath);
            return UndirectedCheck.graphClusterCoeff(G);
        }, (expected, actual) -> {
            Assertions.assertEquals((double) expected, (double) actual, 1e-4);
        });
    }

    @Test
    @DisplayName("5. ComputeInDegrees tests")
    public void testComputeInDegrees() throws IOException {
        runTestCase(2, "computeInDegrees", tokens -> {
            int[] result = new int[tokens.size()];
            for (int i = 0; i < tokens.size(); i++) {
                result[i] = Integer.parseInt(tokens.get(i));
            }
            return result;
        }, inputPath -> {
            ListGraph G = ListGraph.read(inputPath);
            return DirectedCheck.computeInDegrees(G);
        }, (expected, actual) -> {
            Assertions.assertArrayEquals((int[]) expected, (int[]) actual);
        });
    }

    @Test
    @DisplayName("6. DagCheck tests")
    public void testDagCheck() throws IOException {
        runTestCase(2, "dagCheck", tokens -> {
            return new int[] {
                Integer.parseInt(tokens.get(0)),
                Integer.parseInt(tokens.get(1))
            };
        }, inputPath -> {
            ListGraph G = ListGraph.read(inputPath);
            return DirectedCheck.dagCheck(G);
        }, (expected, actual) -> {
            Assertions.assertArrayEquals((int[]) expected, (int[]) actual);
        });
    }

    @Test
    @DisplayName("7. MaxWeightChain tests")
    public void testMaxWeightChain() throws IOException {
        runTestCase(3, "maxWeightChain", tokens -> {
            return Integer.parseInt(tokens.get(0));
        }, inputPath -> {
            ListGraph G = ListGraph.readWeighted(inputPath);
            return MaxWeight.maxWeightChain(G);
        }, Assertions::assertEquals);
    }

    @Test
    @DisplayName("8. MaxWeightTree tests")
    public void testMaxWeightTree() throws IOException {
        runTestCase(3, "maxWeightTree", tokens -> {
            return Integer.parseInt(tokens.get(0));
        }, inputPath -> {
            ListGraph G = ListGraph.readWeighted(inputPath);
            return MaxWeight.maxWeightTree(G);
        }, Assertions::assertEquals);
    }

    private void runTestCase(
            int part, String methodName, ExpectedParser parser, TestMethod implementation, Criterion criterion
    ) throws IOException
    {
        Path expectedPath = Paths.get(DATA_DIRPATH, "expected", methodName + ".txt");
        List<String> lines = Files.readAllLines(expectedPath);
        for (String line : lines) {
            List<String> tokens = Arrays.asList(line.split(" "));
            String graphName = tokens.get(0);
            System.out.println("Started testing " + graphName + ".");

            List<String> view = tokens.subList(1, tokens.size());
            Object expected = parser.apply(view);
            Path inputPath = Paths.get(DATA_DIRPATH, "input", "part_" + part, graphName + ".txt");
            Object actual = implementation.apply(inputPath.toString());
            criterion.apply(expected, actual);
        }
    }

    private static int readGraphSize(String filepath) throws IOException {
        BufferedReader reader = new BufferedReader(new FileReader(filepath));
        int result = Integer.parseInt(reader.readLine());
        reader.close();
        return result;
    }

    @FunctionalInterface
    private interface ExpectedParser {
        Object apply(List<String> tokens);
    }

    @FunctionalInterface
    private interface TestMethod {
        Object apply(String inFilepath) throws IOException;
    }

    @FunctionalInterface
    private interface Criterion {
        void apply(Object expected, Object actual);
    }
}
