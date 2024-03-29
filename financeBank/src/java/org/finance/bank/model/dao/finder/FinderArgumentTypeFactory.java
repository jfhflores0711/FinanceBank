package org.finance.bank.model.dao.finder;

import org.hibernate.type.Type;

/**
 * Used to locate any specific type mappings that might be necessary for a dao implementation
 */
public interface FinderArgumentTypeFactory {

    Type getArgumentType(Object arg);
}
