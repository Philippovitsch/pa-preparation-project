package com.codecool.tasktracker.security;

import com.codecool.tasktracker.model.Task;
import com.codecool.tasktracker.model.User;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.Collection;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class AuthContextUtilTest {

    @Mock
    Authentication authentication;

    @Mock
    SecurityContext securityContext;

    @InjectMocks
    AuthContextUtil authContextUtil;

    @Test
    public void isTaskOwnerAndAdmin() {
        setupMock(List.of("ROLE_USER", "ROLE_ADMIN"));
        Task task = setupTask("testUser");
        assertTrue(authContextUtil.isTaskOwnerOrAdmin(task));
    }

    @Test
    public void isOnlyTaskOwner() {
        setupMock(List.of("ROLE_USER"));
        Task task = setupTask("testUser");
        assertTrue(authContextUtil.isTaskOwnerOrAdmin(task));
    }

    @Test
    public void isOnlyAdmin() {
        setupMock(List.of("ROLE_ADMIN"));
        Task task = setupTask("otherUser");
        assertTrue(authContextUtil.isTaskOwnerOrAdmin(task));
    }

    @Test
    public void isNeitherTaskOwnerNorAdmin() {
        setupMock(List.of("ROLE_USER"));
        Task task = setupTask("otherUser");
        assertFalse(authContextUtil.isTaskOwnerOrAdmin(task));
    }

    private void setupMock(List<String> roles) {
        Collection grantedAuthorities = AuthorityUtils.createAuthorityList(roles.toArray(new String[0]));
        when(securityContext.getAuthentication()).thenReturn(authentication);
        when(authentication.getAuthorities()).thenReturn(grantedAuthorities);
        when(authentication.getName()).thenReturn("testUser");
        SecurityContextHolder.setContext(securityContext);
    }

    private Task setupTask(String userName) {
        User user = mock(User.class);
        Task task = mock(Task.class);
        when(user.getUsername()).thenReturn(userName);
        when(task.getUser()).thenReturn(user);
        return task;
    }

}
