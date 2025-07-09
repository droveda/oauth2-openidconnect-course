package org.jaubs.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import jakarta.validation.constraints.NotNull;
import org.jaubs.service.BookItem;
import org.jaubs.service.GitLabService;
import org.jaubs.service.IJaubsBookService;
import org.jaubs.service.SoldItem;
import org.jaubs.util.JaubsUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClient;
import org.springframework.security.oauth2.client.annotation.RegisteredOAuth2AuthorizedClient;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.core.OAuth2AccessToken;
import org.springframework.security.oauth2.core.OAuth2RefreshToken;
import org.springframework.security.oauth2.core.OAuth2Token;
import org.springframework.security.oauth2.core.oidc.OidcIdToken;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@Controller
public class JaubsUIController {

    private static final Logger log = LoggerFactory.getLogger(JaubsUIController.class);

    private final IJaubsBookService bookService;

    private final GitLabService gitLabService;

    public JaubsUIController(IJaubsBookService bookService, GitLabService gitLabService) {
        this.bookService = bookService;
        this.gitLabService = gitLabService;
    }

    @GetMapping("/")
    public String slash() {
        return "redirect:/jaubs/ui";
    }

    @GetMapping("/login")
    public String showLoginPage() {
        return "redirect:/oauth2/authorization/keycloak-oidc";
    }

    @GetMapping("/jaubs/ui")
    public ModelAndView home(
            OAuth2AuthenticationToken token,
            @RegisteredOAuth2AuthorizedClient OAuth2AuthorizedClient client) {

        OidcUser principal = (OidcUser) token.getPrincipal();

        ModelAndView model = generateDefaultModel(token);

        // Add accesstoken, refreshtoken and idtoken
        OAuth2AccessToken accessToken = client.getAccessToken();
        OAuth2RefreshToken refreshToken = client.getRefreshToken();
        OidcIdToken idToken = principal.getIdToken();

        model.setViewName("home");
        model.addObject("accesstoken",
                JaubsUtils.prettyBody(tokenValue(accessToken)));
        model.addObject("refreshtoken",
                JaubsUtils.prettyBody(tokenValue(refreshToken)));
        model.addObject("idtoken",
                JaubsUtils.prettyBody(tokenValue(idToken)));
        return model;
    }

    @GetMapping("/jaubs/ui/import-books")
    public ModelAndView importBooks(
            OAuth2AuthenticationToken token,
            @RegisteredOAuth2AuthorizedClient("gitlab-oauth") OAuth2AuthorizedClient gitLabClient
    ) throws JsonProcessingException {
        //get the gitlab token
        OAuth2AccessToken gitlabToken = gitLabClient.getAccessToken();
        log.info("gitlab token: {}", gitlabToken.getTokenValue());

        var books = gitLabService.getBooksFromGitLab(gitlabToken.getTokenValue());

        List<BookItem> openItems = bookService.findAllOpenItems();
        openItems.addAll(books);

        ModelAndView model = generateDefaultModel(token);
        model.setViewName("items");
        model.addObject("items", openItems);
        return model;
    }

    @GetMapping("/jaubs/ui/show-items")
    public ModelAndView showBookItems(OAuth2AuthenticationToken token) {

        List<BookItem> openItems = bookService.findAllOpenItems();

        ModelAndView model = generateDefaultModel(token);
        model.setViewName("items");
        model.addObject("items", openItems);
        return model;
    }

    @GetMapping("/jaubs/ui/admin/show-create-form")
    public ModelAndView showCreateBookForm(OAuth2AuthenticationToken token) {

        BookItem defaultItem = BookItem.emptyItem();

        ModelAndView model = generateDefaultModel(token);
        model.setViewName("book-item-form");
        model.addObject("item", defaultItem);
        return model;
    }

    @GetMapping("/jaubs/ui/admin/show-update-form")
    public ModelAndView showUpdateBookForm(OAuth2AuthenticationToken token,
                                           @RequestParam @NotNull Long itemId) {

        BookItem item = bookService.getItem(itemId);

        ModelAndView model = generateDefaultModel(token);
        model.setViewName("book-item-form");
        model.addObject("item", item);
        return model;
    }

    @PostMapping("/jaubs/ui/admin/save-item")
    public String saveBookItem(@ModelAttribute BookItem item) {

        if (item.getId() == null)
            bookService.createBookItem(item);
        else
            bookService.updateBookItem(item);

        return "redirect:/jaubs/ui/show-items";
    }

    @GetMapping("/jaubs/ui/admin/delete-item-conf")
    public ModelAndView deleteBookItemConf(OAuth2AuthenticationToken token,
                                           @RequestParam @NotNull Long itemId) {

        BookItem item = bookService.getItem(itemId);

        ModelAndView model = generateDefaultModel(token);
        model.setViewName("delete-item-conf");
        model.addObject("item", item);
        return model;
    }

    @PostMapping("/jaubs/ui/admin/delete-item")
    public String deleteBookItem(@RequestParam @NotNull Long id) {

        bookService.deleteItem(id);

        return "redirect:/jaubs/ui/show-items";
    }

    @GetMapping("/jaubs/ui/buy-item-conf")
    public ModelAndView buyBookItemConf(OAuth2AuthenticationToken token,
                                        @RequestParam @NotNull Long itemId) {

        BookItem item = bookService.getItem(itemId);

        ModelAndView model = generateDefaultModel(token);
        model.setViewName("buy-item-conf");
        model.addObject("item", item);
        return model;
    }

    @PostMapping("/jaubs/ui/buy-item")
    public String buyBookItem(@RequestParam @NotNull Long id) {

        bookService.buyItem(id);

        return "redirect:/jaubs/ui/show-items";
    }

    @GetMapping("/jaubs/ui/show-bought-items")
    public ModelAndView showBoughtItems(OAuth2AuthenticationToken token) {

        OidcUser principal = (OidcUser) token.getPrincipal();

        List<SoldItem> soldItems
                = bookService.findSoldItems(principal.getEmail());

        ModelAndView model = generateDefaultModel(token);
        model.setViewName("bought-items");
        model.addObject("soldItems", soldItems);
        return model;
    }


    /*
     * Sets some basic user information. The call can add more properties
     * to it before passing to the view file.
     */
    private ModelAndView generateDefaultModel(OAuth2AuthenticationToken token) {

        OidcUser principal = (OidcUser) token.getPrincipal();

        ModelAndView model = new ModelAndView();
        model.addObject("user", principal);
        return model;
    }

    private String tokenValue(OAuth2Token token) {
        return (token != null) ? token.getTokenValue() : "Not Available";
    }

}
