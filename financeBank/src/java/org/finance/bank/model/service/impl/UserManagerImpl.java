
package org.finance.bank.model.service.impl;

/**
 *
 * @author oscar
 */
import org.springframework.security.providers.encoding.PasswordEncoder;
import org.springframework.security.userdetails.UsernameNotFoundException;
import org.finance.bank.model.dao.UserDao;
import org.finance.bank.bean.TCuentaAcceso;
import org.finance.bank.model.service.UserExistsException;
import org.finance.bank.model.service.UserManager;
import org.finance.bank.model.service.UserService;
import org.springframework.beans.factory.annotation.Required;
import org.springframework.dao.DataIntegrityViolationException;
import javax.persistence.EntityExistsException;
import java.util.List;

/**
 * Implementation of UserManager interface.
 *
 */
//@WebService(serviceName = "UserService", endpointInterface = "org.finance.bank.model.service.UserService")
public class UserManagerImpl extends UniversalManagerImpl implements UserManager, UserService {

    private UserDao dao1;
    private PasswordEncoder passwordEncoder;

    /**
     * Set the Dao for communication with the data layer.
     * @param dao the UserDao that communicates with the database
     */
    @Required
    public void setUserDao(UserDao dao) {
        this.dao1 = dao;
    }

    /**
     * Set the PasswordEncoder used to encrypt passwords.
     * @param passwordEncoder the PasswordEncoder implementation
     */
    @Required
    public void setPasswordEncoder(PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }

    /**
     * {@inheritDoc}
     */
    public TCuentaAcceso getUser(String userId) {
        return dao1.get(new String(userId));
    }

    /**
     * {@inheritDoc}
     */
    public List<TCuentaAcceso> getUsers(TCuentaAcceso user) {
        return dao1.getUsers();
    }

    /**
     * {@inheritDoc}
     */
    public TCuentaAcceso saveUser(TCuentaAcceso user) throws UserExistsException {

        if (user.getIdAcceso() == null) {
            user.setLogin(user.getLogin().toLowerCase());
        }

        // Get and prepare password management-related artifacts
        boolean passwordChanged = false;
        if (passwordEncoder != null) {
            if (user.getIdUser() == null) {
                passwordChanged = true;
            } else {
                String currentPassword = dao1.getUserPassword(user.getLogin());
                if (currentPassword == null) {
                    passwordChanged = true;
                } else {
                    if (!currentPassword.equals(user.getContrasenia())) {
                        passwordChanged = true;
                    }
                }
            }
            if (passwordChanged) {
                user.setContrasenia(passwordEncoder.encodePassword(user.getContrasenia(), null));
            }
        } else {
            log.warn("PasswordEncoder not set, skipping password encryption...");
        }
        try {
            return dao1.saveUser(user);
        } catch (DataIntegrityViolationException e) {
            e.printStackTrace();
            log.warn(e.getMessage());
            throw new UserExistsException("User '" + user.getLogin() + "' already exists!");
        } catch (EntityExistsException e) {
            e.printStackTrace();
            log.warn(e.getMessage());
            throw new UserExistsException("User '" + user.getLogin() + "' already exists!");
        }
    }

    /**
     * {@inheritDoc}
     */
    public void removeUser(String userId) {
        log.debug("removing user: " + userId);
        dao1.remove(new String(userId));
    }

    /**
     * {@inheritDoc}
     * @param username the login name of the human
     * @return User the populated user object
     * @throws UsernameNotFoundException thrown when username not found
     */
    public TCuentaAcceso getUserByUsername(String username) throws UsernameNotFoundException {
        return (TCuentaAcceso) dao1.loadUserByUsername(username);
    }
}
