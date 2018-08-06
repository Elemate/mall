package com.wq.item;

import freemarker.template.Configuration;
import freemarker.template.Template;
import org.junit.Test;

import java.io.File;
import java.io.FileWriter;
import java.io.Writer;
import java.util.HashMap;
import java.util.Map;

public class FreemakerTest {

    @Test
    public void makeFreemaker() throws Exception{

        //// 第一步：创建一个Configuration对象，直接new一个对象。构造方法的参数就是freemarker对于的版本号。
             Configuration configuration = new Configuration(Configuration.getVersion());
        //	 第二步：设置模板文件所在的路径。
             configuration.setDirectoryForTemplateLoading(new File("F:\\IntelliJ WorkSpace\\E3_mall\\e3-item-web\\src\\main\\webapp"));
        //	 第三步：设置模板文件使用的字符集。一般就是utf-8.
             configuration.setDefaultEncoding("utf-8");
        //   第四步：加载一个模板，创建一个模板对象。
             Template template = configuration.getTemplate("template.jsp");
        //	 第五步：创建一个模板使用的数据集，可以是pojo也可以是map。一般是Map。
             Map data = new HashMap();
        //	 向数据集中添加数据
             data.put("hello","this si my freemarker");
        //	 第六步：创建一个Writer对象，一般创建一FileWriter对象，指定生成的文件名。
             Writer out = new FileWriter("E:\\项目二：宜立方商城(80-93天）\\e3商城_day01\\01.教案-3.0\\hello.txt");
        //	 第七步：调用模板对象的process方法输出文件。
             template.process(data,out);
        //	 第八步：关闭流。
             out.close();
    }
}
