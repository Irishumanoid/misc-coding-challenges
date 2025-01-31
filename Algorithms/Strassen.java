import java.util.List;
import java.util.Arrays;
import java.util.function.Function;

public class Strassen {

    public double[][] multiplyMatrices(double[][] matOne, double[][] matTwo) {
        // direct application of theorem
        if (matOne.length == 2) {
            double a = matOne[0][0]; double b = matOne[0][1]; double c = matOne[1][0]; double d = matOne[1][1];
            double e = matTwo[0][0]; double f = matTwo[0][1]; double g = matTwo[1][0]; double h = matTwo[1][1];

            double s1 = a * (f - h); double s2 = (a + b) * h; double s3 = (c + d) * e; double s4 = d * (g - e); 
            double s5 = (a + d) * (e + h); double s6 = (b - d) * (g + h); double s7 = (a - c) * (e + f);

            double i = s5 + s6 + s4 - s2; double j = s1 + s2; double k = s3 + s4; double l = s1 - s7 - s3 + s5;
            return new double[][]{{i, j}, {k, l}};
        }

        // if larger sizes, call multiplyMatrices on quadrants of each when computing s1-s7, then combine i,j,k,l into larger matrix
        List<double[][]> one = getSubmatrices(matOne);
        List<double[][]> two = getSubmatrices(matTwo);
        double[][] a = one.get(0); double[][] b = one.get(1); double[][] c = one.get(2); double[][] d = one.get(3); 
        double[][] e = two.get(0); double[][] f = two.get(1); double[][] g = two.get(2); double[][] h = two.get(3); 

        Function<Tuple<Double, Double>, Double> subtract = subtract();
        Function<Tuple<Double, Double>, Double> add = add();

        double[][] s1 = multiplyMatrices(a, combineMatrices(f, h, subtract));
        double[][] s2 = multiplyMatrices(combineMatrices(a, b, add), h);
        double[][] s3 = multiplyMatrices(combineMatrices(c, d, add), e);
        double[][] s4 = multiplyMatrices(d, combineMatrices(g, e, subtract));
        double[][] s5 = multiplyMatrices(combineMatrices(a, d, add), combineMatrices(e, h, add));
        double[][] s6 = multiplyMatrices(combineMatrices(b, d, subtract), combineMatrices(g, h, add));
        double[][] s7 = multiplyMatrices(combineMatrices(a, c, subtract), combineMatrices(e, f, add));

        double[][] i = combineMatrices(combineMatrices(s4, s5, add), combineMatrices(s6, s2, subtract), add);
        double[][] j = combineMatrices(s1, s2, add);
        double[][] k = combineMatrices(s3, s4, add);
        double[][] l = combineMatrices(combineMatrices(s1, s7, subtract), combineMatrices(s5, s3, subtract), add);
        
        return getSupermatrix(Arrays.asList(i, j, k, l));
    }

    public List<double[][]> getSubmatrices(double[][] matrix) {
        int size = matrix.length / 2;
        double[][] a = new double[size][size],
            b = new double[size][size],
            c = new double[size][size],
            d = new double[size][size];
    
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                a[i][j] = matrix[i][j];            
                b[i][j] = matrix[i][j + size];  
                c[i][j] = matrix[i + size][j];
                d[i][j] = matrix[i + size][j + size]; 
            }
        }
        return Arrays.asList(a, b, c, d);
    }
    

    public double[][] getSupermatrix(List<double[][]> submatrices) {
        double[][] a = submatrices.get(0), 
                   b = submatrices.get(1),
                   c = submatrices.get(2),
                   d = submatrices.get(3);
        
        int size = a.length;
        double[][] result = new double[size * 2][size * 2];
    
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                result[i][j] = a[i][j];      
                result[i][j + size] = b[i][j];   
                result[i + size][j] = c[i][j];  
                result[i + size][j + size] = d[i][j]; 
            }
        }
        return result;
    }    


    public double[][] combineMatrices(double[][] matOne, double[][] matTwo, Function<Tuple<Double, Double>, Double> operation) {
        double[][] result = new double[matOne.length][matOne[0].length];
        for (int i = 0; i < matOne.length; i++) {
            for (int j = 0; j < matOne[0].length; j++) {
                result[i][j] = operation.apply(new Tuple<Double,Double>(matOne[i][j], matTwo[i][j]));
            }
        }
        return result;
    }

    public Function<Tuple<Double, Double>, Double> subtract() {
        return (input) -> input.getFirst() - input.getSecond();
    }

    public Function<Tuple<Double, Double>, Double> add() {
        return (input) -> input.getFirst() + input.getSecond();
    }
    public static void main(String[] args) {
        Strassen s = new Strassen();
        double[][] out = s.multiplyMatrices(new double[][]{{10, 1, 1, 1}, {1, 1, 1, 1}, {1, 1, 1, 1}, {1, 1, 1, 1}}, new double[][]{{1, 1, 1, 1}, {1, 1, 1, 1}, {1, 1, 1, 1}, {1, 1, 1, 1}});
        for (int i = 0; i < out[0].length; i++) {
            for (int j = 0; j < out.length; j++) {
                System.out.printf("%f ", out[i][j]);
            }
            System.out.println();
        }
    }
}
