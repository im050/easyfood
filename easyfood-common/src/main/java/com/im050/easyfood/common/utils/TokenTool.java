package com.im050.easyfood.common.utils;


import lombok.Data;
import lombok.experimental.Accessors;
import org.springframework.util.StringUtils;

import java.util.Base64;

@Data
@Accessors(chain = true)
public class TokenTool {
    private String token;
    private String shopId;

    public TokenTool(String base64Token) {
        try {
            byte[] token = Base64.getDecoder().decode(base64Token);
            String originToken = new String(token);
            String[] decodeToken = StringUtils.split(originToken, "|");
            String id = decodeToken != null && decodeToken.length > 0 ? decodeToken[0] : null;
            String shopId = decodeToken != null && decodeToken.length > 1 ? decodeToken[1] : null;
            setShopId(shopId)
                    .setToken(id);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
