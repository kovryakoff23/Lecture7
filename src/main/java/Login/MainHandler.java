package Login;

import org.eclipse.jetty.security.ConstraintSecurityHandler;
import org.eclipse.jetty.security.HashLoginService;
import org.eclipse.jetty.security.LoginService;
import org.eclipse.jetty.server.Handler;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.handler.ContextHandler;
import org.eclipse.jetty.server.handler.HandlerList;
import org.eclipse.jetty.servlet.DefaultServlet;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;
import org.eclipse.jetty.util.resource.Resource;

import java.net.URL;
//http://localhost:3466/AddProduct/?id=7&name=name7&organization=org7&quantity=700
//http://localhost:3466/ListProducts
@SuppressWarnings("NotNullNullableValidation")
public final class MainHandler {

  public static void main(String[] args) throws Exception {
    final Server server = new DefaultServer().build();

    ServletContextHandler context = new ServletContextHandler(ServletContextHandler.NO_SESSIONS);
    context.setContextPath("/");
    final URL resource = LoginService.class.getResource("/static");
    context.setBaseResource(Resource.newResource(resource.toExternalForm()));
    context.setWelcomeFiles(new String[]{"/static/Info"});
    context.addServlet(new ServletHolder("default", DefaultServlet.class), "/*");

    final String hashConfig = MainHandler.class.getResource("/hash_config").toExternalForm();
    final HashLoginService hashLoginService = new HashLoginService("login", hashConfig);
    final ConstraintSecurityHandler securityHandler = new SecurityHandlerBuilder().build(hashLoginService);
    server.addBean(hashLoginService);
    securityHandler.setHandler(context);

    final ContextHandler contextHandlerGet = new ContextHandler();
    contextHandlerGet.setContextPath("/ListProducts");
    var myGetHandler = new MyGetHandler();
    contextHandlerGet.setHandler(myGetHandler);
    securityHandler.setHandler(contextHandlerGet);
    var handlerList = new HandlerList();

    final ContextHandler contextHandlerPost = new ContextHandler();
    contextHandlerPost.setContextPath("/AddProduct");
    var myPostHandler = new MyPostHandler();
    contextHandlerPost.setHandler(myPostHandler);
    securityHandler.setHandler(contextHandlerPost);

    handlerList.setHandlers(new Handler[]{securityHandler, contextHandlerGet, contextHandlerPost, context});

    server.setHandler(handlerList);

    server.start();
  }
}
