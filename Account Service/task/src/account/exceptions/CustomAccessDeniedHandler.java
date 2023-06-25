//package account.exceptions;
//
//import com.fasterxml.jackson.databind.ObjectMapper;
//import jakarta.servlet.ServletException;
//import jakarta.servlet.http.HttpServletRequest;
//import jakarta.servlet.http.HttpServletResponse;
//import org.springframework.http.HttpStatus;
//import org.springframework.security.access.AccessDeniedException;
//import org.springframework.security.web.access.AccessDeniedHandler;
//
//import java.io.IOException;
//import java.util.Calendar;
//import java.util.HashMap;
//import java.util.Map;
//
//public class CustomAccessDeniedHandler implements AccessDeniedHandler {
//
//    private ObjectMapper objectMapper = new ObjectMapper();
//
//    @Override
//    public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException accessDeniedException) throws IOException, ServletException {
//                response.setStatus(HttpStatus.FORBIDDEN.value());
//        Map<String, Object> data = new HashMap<>();
//        data.put("timestamp", Calendar.getInstance().getTime());
//        data.put("exception", accessDeniedException.getMessage());
//        data.put("exception", accessDeniedException.getMessage());
//        data.put("exception", accessDeniedException.getMessage());
//        data.put("p);
//
//
//        response.getOutputStream()
//                .println(objectMapper.writeValueAsString(data));
//    }
//    }
//
