package ltd.newbee.mall.util;

import org.springframework.util.StringUtils;

import java.net.URI;

/**
 * @author 13
 */
public class NewBeeMallUtils {

    /**
     * 这个方法是给admin上传图片用的 照理说和咱这次没关系 但对我的大作业有借鉴意义 所以看一眼
     * 由于他没有上security,没有filter,所以非管理员也能访问上传的upload的接口地址
     * 上了security的话白名单也无法限制用户访问这个upload
     * 所以下面传入的这个接口返回的地址，并不是应该图片上传到服务器后的真实地址，而我们自定义的非真实地址
     * 通过重新拼接，将图片上传到服务器后的真实地址返回<br>
     * <a href="https://blog.csdn.net/csucsgoat/article/details/123271937">可以看看这篇blog</a>
     * @param uri 传入的uri 例如：http://localhost:8080/manage-api/v1/upload/2021/04/04/1617510000.jpg
     * @return 返回的是：/upload/2021/04/04/1617510000.jpg
     */
    public static URI getHost(URI uri) {
        URI effectiveURI = null;
        try {
            effectiveURI = new URI(uri.getScheme(), uri.getUserInfo(), uri.getHost(), uri.getPort(), null, null, null);
        } catch (Throwable var4) {
            effectiveURI = null;
        }
        return effectiveURI;
    }

    public static String cleanString(String value) {
        if (StringUtils.hasLength(value)) {
            return "";
        }
        value = value.toLowerCase();
        value = value.replaceAll("<", "& lt;").replaceAll(">", "& gt;");
        value = value.replaceAll("\\(", "& #40;").replaceAll("\\)", "& #41;");
        value = value.replaceAll("'", "& #39;");
        value = value.replaceAll("onload", "0nl0ad");
        value = value.replaceAll("xml", "xm1");
        value = value.replaceAll("window", "wind0w");
        value = value.replaceAll("click", "cl1ck");
        value = value.replaceAll("var", "v0r");
        value = value.replaceAll("let", "1et");
        value = value.replaceAll("function", "functi0n");
        value = value.replaceAll("return", "retu1n");
        value = value.replaceAll("$", "");
        value = value.replaceAll("document", "d0cument");
        value = value.replaceAll("const", "c0nst");
        value = value.replaceAll("eval\\((.*)\\)", "");
        value = value.replaceAll("[\\\"\\\'][\\s]*javascript:(.*)[\\\"\\\']", "\"\"");
        value = value.replaceAll("script", "scr1pt");
        value = value.replaceAll("insert", "1nsert");
        value = value.replaceAll("drop", "dr0p");
        value = value.replaceAll("create", "cre0ate");
        value = value.replaceAll("update", "upd0ate");
        value = value.replaceAll("alter", "a1ter");
        value = value.replaceAll("from", "fr0m");
        value = value.replaceAll("where", "wh1re");
        value = value.replaceAll("database", "data1base");
        value = value.replaceAll("table", "tab1e");
        value = value.replaceAll("tb", "tb0");
        return value;
    }
}
