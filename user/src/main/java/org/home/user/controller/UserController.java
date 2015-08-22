package org.home.user.controller;

import java.util.Locale;

import org.apache.commons.lang.StringUtils;
import org.home.user.model.User;
import org.home.user.service.UserService;
import org.home.user.vo.UserListVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping(value = "/users")
public class UserController {

    private static final String DEFAULT_PAGE_DISPLAYED_TO_USER = "0";

    @Autowired
    private UserService userService;

    @Autowired
    private MessageSource messageSource;

    @Value("5")
    private int maxResults;

    @RequestMapping(method = RequestMethod.GET)
    public ModelAndView welcome() {
        return new ModelAndView("UsersList");
    }

    @RequestMapping(method = RequestMethod.GET, produces = "application/json")
    public ResponseEntity<?> listAll(@RequestParam int page, Locale locale) {
        return createListAllResponse(page, locale);
    }

    @RequestMapping(method = RequestMethod.POST, produces = "application/json")
    public ResponseEntity<?> create(@ModelAttribute("user") User User,
                                    @RequestParam(required = false) String searchFor,
                                    @RequestParam(required = false, defaultValue = DEFAULT_PAGE_DISPLAYED_TO_USER) int page,
                                    Locale locale) { 
        userService.save(User);

        if (isSearchActivated(searchFor)) {
            return search(searchFor, page, locale, "message.create.success");
        }

        return createListAllResponse(page, locale, "message.create.success");
    }

  
    
    @RequestMapping(value = "/{id}", method = RequestMethod.PUT, produces = "application/json")
    public ResponseEntity<?> update(@PathVariable("id") int UserId,
                                    @RequestBody User User,
                                    @RequestParam(required = false) String searchFor,
                                    @RequestParam(required = false, defaultValue = DEFAULT_PAGE_DISPLAYED_TO_USER) int page,
                                    Locale locale) {
        if (UserId != User.getId()) {
            return new ResponseEntity<String>("Bad Request", HttpStatus.BAD_REQUEST);
        }

        userService.save(User);

        if (isSearchActivated(searchFor)) {
            return search(searchFor, page, locale, "message.update.success");
        }

        return null;
    }

    @RequestMapping(value = "/{UserId}", method = RequestMethod.DELETE, produces = "application/json")
    public ResponseEntity<?> delete(@PathVariable("UserId") int UserId,
                                    @RequestParam(required = false) String searchFor,
                                    @RequestParam(required = false, defaultValue = DEFAULT_PAGE_DISPLAYED_TO_USER) int page,
                                    Locale locale) {


        try {
            userService.delete(UserId);
        } catch (AccessDeniedException e) {
            return new ResponseEntity<Object>(HttpStatus.FORBIDDEN);
        }

        if (isSearchActivated(searchFor)) {
            return search(searchFor, page, locale, "message.delete.success");
        }

        return null;
    }

    @RequestMapping(value = "/{name}", method = RequestMethod.GET, produces = "application/json")
    public ResponseEntity<?> search(@PathVariable("name") String name,
                                    @RequestParam(required = false, defaultValue = DEFAULT_PAGE_DISPLAYED_TO_USER) int page,
                                    Locale locale) {
        return search(name, page, locale, null);
    }

    private ResponseEntity<?> search(String name, int page, Locale locale, String actionMessageKey) {
        UserListVO UserListVO = userService.findByNameLike(page, maxResults, name);

        if (!StringUtils.isEmpty(actionMessageKey)) {
            addActionMessageToVO(UserListVO, locale, actionMessageKey, null);
        }

        Object[] args = {name};

        addSearchMessageToVO(UserListVO, locale, "message.search.for.active", args);

        return new ResponseEntity<UserListVO>(UserListVO, HttpStatus.OK);
    }

    private UserListVO listAll(int page) {
        return userService.findAll(page, maxResults); 
    }

    private ResponseEntity<UserListVO> returnListToUser(UserListVO UserList) {
        return new ResponseEntity<UserListVO>(UserList, HttpStatus.OK);
    }

        private ResponseEntity<?> createListAllResponse(int page, Locale locale) {
        return createListAllResponse(page, locale, null);
    }

   private ResponseEntity<?> createListAllResponse(int page, Locale locale, String messageKey) {
        UserListVO UserListVO = listAll(page);

        addActionMessageToVO(UserListVO, locale, messageKey, null);

        return returnListToUser(UserListVO);
    }

    private UserListVO addActionMessageToVO(UserListVO UserListVO, Locale locale, String actionMessageKey, Object[] args) {
        if (StringUtils.isEmpty(actionMessageKey)) {
            return UserListVO;
        }

        UserListVO.setActionMessage(messageSource.getMessage(actionMessageKey, args, null, locale));

        return UserListVO;
    }

    private UserListVO addSearchMessageToVO(UserListVO UserListVO, Locale locale, String actionMessageKey, Object[] args) {
        if (StringUtils.isEmpty(actionMessageKey)) {
            return UserListVO;
        }

        UserListVO.setSearchMessage(messageSource.getMessage(actionMessageKey, args, null, locale));

        return UserListVO;
    }

    private boolean isSearchActivated(String searchFor) {
        return !StringUtils.isEmpty(searchFor);
    }
}