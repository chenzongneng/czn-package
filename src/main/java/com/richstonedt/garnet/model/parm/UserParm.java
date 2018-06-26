package com.richstonedt.garnet.model.parm;

import com.richstonedt.garnet.model.User;

/**
 * The type User parm.
 */
public class UserParm extends BaseParm{

    private User user;

    private Long loginUserId;

    public Long getLoginUserId() {
        return loginUserId;
    }

    public void setLoginUserId(Long loginUserId) {
        this.loginUserId = loginUserId;
    }

    /**
     * Gets user.
     *
     * @return the user
     */
    public User getUser() {
        return user;
    }

    /**
     * Sets user.
     *
     * @param user the user
     */
    public void setUser(User user) {
        this.user = user;
    }
}
