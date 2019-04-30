package com.gbicc.ikClass;

//import org.apache.calcite.util.NumberUtil;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.TokenStream;
import org.apache.lucene.analysis.tokenattributes.CharTermAttribute;
import org.wltea.analyzer.lucene.IKAnalyzer;

import java.io.IOException;
import java.io.StringReader;

public class IKAnalyzerSupplyProduct {
//    public static String startIKAnalyzer(String line) throws IOException {
//        IKAnalyzer analyzer = new IKAnalyzer();
//        // 使用智能分词
//        analyzer.setUseSmart(true);
//        // 打印分词结果
//        try {
//            return printAnalysisResult(analyzer, line);
//        } catch (Exception e) {
//            e.printStackTrace();
//        } finally {
//            if (analyzer != null) {
//                analyzer.close();
//            }
//        }
//        return null;
//    }
//
//    private static String printAnalysisResult(Analyzer analyzer, String keyWord)
//            throws Exception {
//        String resultdata = "";
//        String infoData = "";
//        if (keyWord != "" && keyWord != null) {
//            TokenStream tokenStream = analyzer.tokenStream("content",
//                    new StringReader(keyWord));
//            tokenStream.addAttribute(CharTermAttribute.class);
//            tokenStream.reset();
//            while (tokenStream.incrementToken()) {
//                CharTermAttribute charTermAttribute = tokenStream
//                        .getAttribute(CharTermAttribute.class);
//                String dest = NumberUtil.checkNumber(charTermAttribute.toString().replace("-", ""));
//                boolean mailres = RegExpUtil.isEmail(charTermAttribute.toString());
//                boolean hpres = RegExpUtil.isHomepage(charTermAttribute.toString());
//                boolean num = RegExpUtil.isNum(charTermAttribute.toString().replace("-", "").replace("qq", "").replace("QQ", "").replace("+", ""));
//                if (dest != "CELLPHONE" && dest != "FIXEDPHONE" && mailres == false && hpres == false && num == false) {
//                    infoData = infoData + " " + charTermAttribute.toString();
//                }
//            }
//            if (infoData != "" && infoData != null) {
//                resultdata = resultdata + infoData.trim() + "\r\n";
//            } else {
//                resultdata = "";
//            }
//        }
//        return resultdata;
//    }

    public static void main(String[] args)  throws IOException {
        String text="基于java语言开发的轻量级的中文分词工具包";
        //创建分词对象
        Analyzer anal=new IKAnalyzer(true);
        StringReader reader=new StringReader(text);
        //分词
        TokenStream ts=anal.tokenStream("", reader);
        CharTermAttribute term=ts.getAttribute(CharTermAttribute.class);
        //遍历分词数据
        while(ts.incrementToken()){
            System.out.print(term.toString()+"|");
        }
        reader.close();
        System.out.println();

    }
}
