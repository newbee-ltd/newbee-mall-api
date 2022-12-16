package ltd.newbee.mall.common;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.Tokenizer;

/**
 * 自己写个优化版的分词器
 */
public class MyIKAnalyzer extends Analyzer {

    private boolean useSmart;

    public MyIKAnalyzer() {
        this(false);
    }

    public MyIKAnalyzer(boolean useSmart) {
        this.useSmart = useSmart;
    }

    @Override
    protected TokenStreamComponents createComponents(String s) {
        Tokenizer myIKTokenizer = new MyIKTokenizer(this.useSmart());
        return new TokenStreamComponents(myIKTokenizer);
    }

    public boolean useSmart() {
        return this.useSmart;
    }

    public void setUseSmart(boolean useSmart) {
        this.useSmart = useSmart;
    }

}
