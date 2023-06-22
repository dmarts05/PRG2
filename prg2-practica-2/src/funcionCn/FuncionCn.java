package funcionCn;

public class FuncionCn implements IFuncionCn {
  @Override
  public double CnI(int n) {
    double[] resultsArr = new double[n + 1];
    resultsArr[0] = 1.0;

    for (int i = 1; i <= n; i++) {
      double currentResult;
      double sumPreviousResults = 0.0;

      for (int j = 0; j < i; j++) {
        sumPreviousResults += resultsArr[j];
      }

      currentResult = i + (2.0 / i) * sumPreviousResults;
      resultsArr[i] = currentResult;

    }

    return resultsArr[n];
  }

  @Override
  public double CnCRD(int n) {
    if (n <= 0) {
      return 1.0;
    } else {
      double sum = getSumCnRec(0.0, 0, n);

      return n + (2.0 / n) * sum;
    }
  }

  private double getSumCnRec(double sum, int counter, int n) {
    if (counter < n) {
      sum += CnRD(counter);
      return getSumCnRec(sum, counter + 1, n);
    } else {
      return sum;
    }
  }

  @Override
  public double CnRD(int n) {
    if (n <= 0) {
      return 1.0;
    } else {
      double sum = 0.0;

      for (int i = 0; i < n; i++) {
        sum += CnRD(i);
      }

      return n + (2.0 / n) * sum;
    }
  }

  @Override
  public double CnIterativaLineal(int n) {
    if (n <= 0) {
      return 1.0;
    } else {
      return (2.0 / n) * (sumIt(n - 1)) + n;
    }
  }

  private double sumIt(int n) {
    double[] resultsArr = new double[n + 1];
    resultsArr[0] = 1.0;

    for (int i = 1; i <= n; i++) {
      resultsArr[i] = (2.0 / i) * resultsArr[i - 1] + i + resultsArr[i - 1];
    }

    return resultsArr[n];
  }

  @Override
  public double CnRecursivaLineal(int n) {
    if (n <= 0) {
      return 1.0;
    } else {
      return n + (2.0 / n) * sumRec(n - 1);
    }
  }

  private double sumRec(int n) {
    if (n == 0) {
      return 1;
    } else {
      return (2.0 / n) * (sumRec(n - 1)) + n + sumRec(n - 1);
    }
  }
}
