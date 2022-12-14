package ltd.newbee.mall.api.random;

import lombok.Data;

@Data
public class Content {
    private Integer Id;
    private String Title;
    private String Price;
    private String nothing;
    private String Descs;

    public Content(Integer id, String title, String price, String nothing, String descs) {
        Id = id;
        Title = title;
        Price = price;
        this.nothing = nothing;
        Descs = descs;
    }

    public Content() {}
}
