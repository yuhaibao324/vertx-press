package io.vertPress.manage.handle.impl;

import io.vertPress.manage.dto.ConstantDTO;
import io.vertPress.manage.dto.UserDTO;
import io.vertx.core.logging.Logger;
import io.vertx.core.logging.LoggerFactory;
import io.vertx.ext.web.RoutingContext;
import io.vertx.ext.web.Session;

/**
 * @ClassName: RedirectAuthHandlerImpl
 * @Description: TODO 请求跳转接口实现类
 * @author FoamValue foamvalue@live.cn
 * @date 2017年3月29日 下午8:37:59
 * 
 */
public class RedirectAuthHandlerImpl extends AuthHandlerImpl {
	
	private final static Logger LOGGER = LoggerFactory.getLogger(RedirectAuthHandlerImpl.class);

	private final String loginRedirectURL;

	private final String returnURLParam;

	public RedirectAuthHandlerImpl(String loginRedirectURL, String returnURLParam) {
		this.loginRedirectURL = loginRedirectURL;
		this.returnURLParam = returnURLParam;
	}

	@Override
	public void handle(RoutingContext context) {
		Session session = context.session();
		if (session != null) {
			UserDTO user = session.get(ConstantDTO.DEFAULT_USER_SESSION_KEY);
			LOGGER.info("userDTO: " + user);
			if (user != null) {
				context.next();
			} else {
				session.put(returnURLParam, context.request().uri());
				context.response().putHeader("location", loginRedirectURL).setStatusCode(302).end();
			}
		} else {
			context.fail(new NullPointerException("No session - did you forget to include a SessionHandler?"));
		}

	}
}
