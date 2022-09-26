@PreMatching
@Provider
public class CorsFilter implements ContainerResponseFilter {
    @Override
    public void filter( ContainerRequestContext requestCtx, ContainerResponseContext res )
    throws IOException {
        res.getHeaders().add("Access-Control-Allow-Origin", "*" );
        res.getHeaders().add("Access-Control-Allow-Credentials", "true" );
        res.getHeaders().add("Access-Control-Allow-Methods", "GET, POST, DELETE, PUT" );
        res.getHeaders().add("Access-Control-Allow-Headers", "Origin, Accept, Content-Type, Authorization,x-access-token");
    }
}
