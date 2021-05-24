package di.uoa.gr.dira.security;

public class JwtConstants {
    public static final long EXPIRATION_TIME_MS = 900_000; // 15 minutes
    public static final String SECRET = "DIRA_SECRET_JWT_KEY";
    public static final String TOKEN_PREFIX = "Bearer ";
}
