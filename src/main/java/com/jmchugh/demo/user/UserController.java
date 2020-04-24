package com.jmchugh.demo.user;

import com.jmchugh.demo.component.ToastrResponse;
import com.jmchugh.demo.user.model.EditForm;
import com.jmchugh.demo.user.model.FilterForm;
import com.jmchugh.demo.user.model.NewForm;
import com.jmchugh.demo.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.validation.Valid;

@Controller
@RequestMapping("")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @GetMapping("")
    public String getDefault() {

        return "user/view-default";
    }

    @GetMapping("/users")
    public String getUsersTable(Model model, @ModelAttribute("filter") FilterForm filter, @PageableDefault(size = 20) Pageable pageable) {

        model.addAttribute("pageable", userService.getPageable(pageable, filter));

        return "user/partial/partial-table";
    }

    @GetMapping("/user/new")
    public String getNewUserModal(Model model) {

        model.addAttribute("form", new NewForm());

        return "user/partial/modal-new";
    }

    @PostMapping("/user/new")
    public String postNewUserModal(Model model, @Valid @ModelAttribute("form") NewForm form, BindingResult results) {

        if (results.hasErrors()) {

            return "user/partial/modal-new";
        }

        userService.save(form);

        return new ToastrResponse(model)
                .toast("User Added", ToastrResponse.Type.SUCCESS)
                .event("usersTable", "reload")
                .build();
    }

    @GetMapping("/user/{id}")
    public String getUserEdit(Model model, @PathVariable("id") Long id) {

        return "user/view-edit";
    }

    @GetMapping("/user/{id}/edit")
    public String getUserEditForm(Model model, @PathVariable("id") Long id) {

        model.addAttribute("form", userService.createEditForm(id));

        return "user/partial/partial-edit";
    }

    @PostMapping("/user/{id}/edit")
    public String postUserEditModal(Model model, @PathVariable("id") Long id, @Valid @ModelAttribute("form") EditForm form, BindingResult results) {

        if (results.hasErrors()) {

            return "user/partial/partial-edit";
        }

        userService.update(id, form);

        return new ToastrResponse((model))
                .toast("User Updated", ToastrResponse.Type.SUCCESS)
                .event("userEdit", "reload")
                .build();
    }
}
