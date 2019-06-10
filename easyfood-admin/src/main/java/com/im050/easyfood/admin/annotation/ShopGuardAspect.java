package com.im050.easyfood.admin.annotation;

import com.im050.easyfood.admin.utils.Session;
import com.im050.easyfood.common.entity.Merchant;
import com.im050.easyfood.common.exception.HttpException;
import com.im050.easyfood.common.service.ShopService;
import com.im050.easyfood.common.shiro.HeaderSessionManager;
import com.im050.easyfood.common.utils.TokenTool;
import com.im050.easyfood.common.utils.response.ResponseCode;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import javax.el.MethodNotFoundException;
import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Method;

@Component
@Aspect
public class ShopGuardAspect {

    @Autowired
    private ShopService shopService;

    private Logger log = LoggerFactory.getLogger(ShopGuardAspect.class);

    @Around("@annotation(ShopGuard)")
    public Object logExecutionTime(ProceedingJoinPoint joinPoint) throws Throwable {
        // 获取headers
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = attributes.getRequest();
        // 根据token获取shopId
        String base64Token = request.getHeader(HeaderSessionManager.TOKEN_NAME);
        Integer shopId = TokenTool.build(base64Token).getShopId();
        // 获取所有参数
        Object[] objects = joinPoint.getArgs();
        // 如果第一个参数是Integer，将其置为shopId，所以在使用ShopGuard注解前，请把shopId参数放在第一个
        if (objects[0] instanceof Integer) {
            objects[0] = shopId;
        } else if (objects[0] != null) {
            // 如果是个对象，根据反射方法设置其shopId
            try {
                Method method = objects[0].getClass().getMethod("setShopId", Integer.class);
                method.invoke(objects[0], shopId);
            } catch (MethodNotFoundException ex) {
                log.warn("`ShopGuard` annotation has not found setShopId method: {}", ex);
            }
        }
        // 验证shop是否有权限管理
        Merchant merchant = Session.getMerchant();
        if (!shopService.checkShopOwner(shopId, merchant.getId())) {
            throw new HttpException(ResponseCode.SHOP_OWNER_ERROR);
        }
        return joinPoint.proceed(objects);
    }

//    public void test() {
    //        Map<String, Object> params = getNameAndValue(joinPoint);
//        for (Map.Entry<String, Object> entry : params.entrySet()) {
//            System.out.println("name: " + entry.getKey() + " value: " + entry.getValue());
//            if (entry.getValue() instanceof ShopManager) {
//                ((ShopManager) entry.getValue()).setShopId(shopId);
//                continue;
//            }
//            if (entry.getKey().equals("shopId")) {
//                entry.setValue(shopId);
//            }
//        }
//    }

//    private Map<String, Object> getNameAndValue(ProceedingJoinPoint joinPoint) {
//        Map<String, Object> param = new HashMap<>();
//
//        Object[] paramValues = joinPoint.getArgs();
//        String[] paramNames = ((CodeSignature)joinPoint.getSignature()).getParameterNames();
//
//        for (int i = 0; i < paramNames.length; i++) {
//            param.put(paramNames[i], paramValues[i]);
//        }
//
//        return param;
//    }

}
