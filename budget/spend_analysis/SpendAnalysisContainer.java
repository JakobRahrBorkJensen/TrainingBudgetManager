package budget.spend_analysis;

/**
 * Strategy container for the SpendAnalysis strategy
 */
public class SpendAnalysisContainer {
    private SpendAnalysis analysis;

    public void setAnalysis(SpendAnalysis analysis) {
        this.analysis = analysis;
    }

    public void performAnalysis() {
        analysis.analyse();
    }
}
