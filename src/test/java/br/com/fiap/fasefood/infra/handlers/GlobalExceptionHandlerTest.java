package br.com.fiap.fasefood.infra.handlers;

import br.com.fiap.fasefood.core.exceptions.AuthenticationFailedException;
import br.com.fiap.fasefood.core.exceptions.RegraDeNegocioException;
import br.com.fiap.fasefood.core.exceptions.ResourceAlreadyExists;
import br.com.fiap.fasefood.core.exceptions.ResourceNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.core.MethodParameter;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import br.com.fiap.fasefood.infra.handlers.GlobalExceptionHandler.ApiError;
import br.com.fiap.fasefood.infra.handlers.GlobalExceptionHandler.ValidationError;

import java.lang.reflect.Method;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class GlobalExceptionHandlerTest {

    private GlobalExceptionHandler globalExceptionHandler;


    @BeforeEach
    public void setUp() {
        globalExceptionHandler = new GlobalExceptionHandler();
    }

    @Test
    public void deveRetornarResourceNotFoundException(){
        String mensagemErro = "Recurso não encontrado";
        ResourceNotFoundException ex = new ResourceNotFoundException(mensagemErro);
        ResponseEntity<ApiError> response = globalExceptionHandler.handleResourceNotFoundException(ex);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(404, response.getBody().status());
        assertEquals(mensagemErro, response.getBody().message());
        assertNotNull(response.getBody().timestamp());
    }

    @Test
    public void deveRetornarValidationErrors() throws NoSuchMethodException {
        BindingResult bindingResultMock = mock(BindingResult.class);

        FieldError erroNome = new FieldError("CreateUserDTO", "nome", "O nome é obrigatório");
        FieldError erroEmail = new FieldError("CreateUserDTO", "email", "O email é obrigatório");

        when(bindingResultMock.getAllErrors()).thenReturn(List.of(erroNome, erroEmail));

        Method method = DummyController.class.getMethod("dummyMethod", String.class);
        MethodParameter methodParameter = new MethodParameter(method, 0);
        MethodArgumentNotValidException exception = new MethodArgumentNotValidException(methodParameter, bindingResultMock);

        ResponseEntity<ValidationError> response = globalExceptionHandler.handleValidationErrors(exception);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        ValidationError body = response.getBody();

        assertNotNull(body);
        assertEquals(400, body.status());
        assertEquals("Erro de validação", body.message());
        assertNotNull(body.timestamp());

        assertEquals(2, body.errors().size());
        assertEquals("O nome é obrigatório", body.errors().get("nome"));
        assertEquals("O email é obrigatório", body.errors().get("email"));

    }

    @Test
    public void deveLancarAuthenticationFailedException(){
        AuthenticationFailedException authenticationFailedException = mock(AuthenticationFailedException.class);
        ResponseEntity<ApiError> response = globalExceptionHandler.handleAuthenticationFailedException(authenticationFailedException);

        assertEquals(HttpStatus.UNAUTHORIZED, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(401, response.getBody().status());
        assertNotNull(response.getBody().timestamp());
    }

    @Test
    public void deveLancarResourceAlreadyExists(){
        ResourceAlreadyExists resourceAlreadyExists = mock(ResourceAlreadyExists.class);
        ResponseEntity<ApiError> response = globalExceptionHandler.handleResourceAlreadyExists(resourceAlreadyExists);

        assertEquals(HttpStatus.CONFLICT, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(409, response.getBody().status());
        assertNotNull(response.getBody().timestamp());
    }

    @Test
    public void deveLancarBusinessRuleException(){
        RegraDeNegocioException regraDeNegocioException = mock(RegraDeNegocioException.class);
        ResponseEntity<ApiError> response = globalExceptionHandler.handleBusinessRuleException(regraDeNegocioException);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(400, response.getBody().status());
        assertNotNull(response.getBody().timestamp());
    }

    static class DummyController {
        public void dummyMethod(String input) {
        }
    }
}
